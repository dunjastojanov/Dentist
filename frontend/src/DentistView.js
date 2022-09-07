import React, {useEffect, useState} from 'react';
import axios from "axios";
import {backlink} from "./Consts";
import {Form, Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Collapse from 'react-bootstrap/Collapse';
import {Calendar} from "./Calendar";
import {AppointmentCards} from "./AppointmentCards";
import {AddAppointmentError} from "./AddAppointmentError";
import {CancelAppointmentError} from "./CancelAppointmentError";
import {AppointmentForm} from "./AppointmentForm";
import {NewPatientForm} from "./NewPatientForm";
import {logOut} from "./LogOut";

export function DentistView() {

    const [appointments, setAppointments] = useState([]);
    const [patients, setPatients] = useState([]);
    const [events, setEvents] = useState([]);

    const [formData, setFormData] = useState({
        type: "",
        date: new Date().toISOString().substring(0, 10),
        time: "09:00",
        duration: "30",
        patientId: "1",
        firstName: "",
        lastName: "",
        lbo: "",
        jmbg: "",
        email: ""
    });

    const setField = (field, value) => {
        setFormData({
            ...formData,
            [field]: value
        })
    }

    const fetchAppointments = () => {
        axios.get(backlink + "appointment/all")
            .then(res => {
                setAppointments(res.data);
                let newEvents = []
                for (let i in res.data) {
                    let a = res.data.at(i);
                    let start = new Date(a.startTime[0], a.startTime[1] - 1, a.startTime[2], a.startTime[3], a.startTime[4]);
                    let end = new Date(start.getTime() + a.duration * 60000);
                    newEvents.push({
                        start: start,
                        end: end,
                        id: a.id,
                        title: a.type + ": "+ a.patient.firstName + " " + a.patient.lastName
                    })
                }
                setEvents(newEvents);
            })
            .catch(error => {
                console.log(error);
            });
    };

    const fetchPatients = () => {
        axios.get(backlink + "patient/all")
            .then(res => {
                setPatients(res.data);
            })
            .catch(error => {
                console.log(error);
            });
    }

    const cancelAppointment = (id) => {
        axios.post(backlink + "appointment/cancel/" + id).then(res => {
            window.location.reload();
        }).catch(err => {
            setShowCancelError(true);
        })
    }

    const addAppointment = () => {
        let dto = {
            year: formData.date.substring(0, 4),
            month: formData.date.substring(5, 7),
            day: formData.date.substring(8, 10),
            hour: formData.time.substring(0, 2),
            minute: formData.time.substring(3, 5),
            duration: formData.duration,
            type: formData.type
        }

        let link = backlink;

        if (addNewPatient) {
            dto = {
                ...dto,
                firstName: formData.firstName,
                lastName: formData.lastName,
                jmbg: formData.jmbg,
                lbo: formData.lbo,
                email: formData.email
            }
            link += "appointment/addWithNewPatient";
        } else {
            dto = {
                ...dto,
                patientId: formData.patientId
            }
            link += "appointment/add";

        }

        axios.post(link, dto).then(res => {
            window.location.reload();
        }).catch(err => {
            setShowError(true);
        })

    }

    useEffect(() => {
        fetchAppointments();
        fetchPatients();
    }, []);

    const [show, setShow] = useState(false);
    const [showCancelError, setShowCancelError] = useState(false);
    const [addNewPatient, setAddNewPatient] = useState(false);
    const [showError, setShowError] = useState(false);

    let allowed = localStorage.getItem("type") === "dentist";

    let html = <>
        <div className="d-flex p-3" style={{
            backgroundImage: "linear-gradient(0deg, #08AEEA 0%, #2AF598 100%)"
        }}>

            <Calendar appointments={appointments} events={events} cancelAppointment={cancelAppointment}/>

            <div className="w-25">
                <div className="d-flex flex-column">
                    <div className="d-flex" className="ms-auto me-3">
                        <Button variant="primary"
                                onClick={() => setShow(true)}>
                            Zakaži novi termin</Button>
                        <Button variant="primary" className="ms-2"
                                onClick={() => logOut()}>
                            Odjavi se</Button>
                    </div>
                    <AppointmentCards cancelAppointment={cancelAppointment} appointments={appointments}/>
                </div>

                <Modal show={show} onHide={() => setShow(false)}>
                    <Modal.Header closeButton>
                        <Modal.Title>Novi termin</Modal.Title>
                    </Modal.Header>
                    <div className="m-3">

                        <Form>
                            <AppointmentForm formData={formData} setFormData={setFormData}/>
                            <hr className="mt-3 mb-3"/>
                            <Form.Check
                                type="switch"
                                id="custom-switch"
                                label="Termin za novog pacijenta"
                                onChange={() => setAddNewPatient(!addNewPatient)}
                                aria-controls="example-collapse-text"
                                aria-expanded={addNewPatient}

                            />

                            <Collapse in={addNewPatient}>
                                <div>
                                    <NewPatientForm formData={formData} setFormData={setFormData}/>
                                </div>
                            </Collapse>

                            <Collapse in={!addNewPatient}>
                                <div id="existing-patient-appointment">
                                    <Form.Label>Pacijent</Form.Label>
                                    <Form.Select
                                        value={formData.patientId}
                                        onChange={(e) => setField("patientId", e.target.value)}
                                    >
                                        {patients.map((patient, index) => {
                                            return (<option key={index}
                                                            value={patient.id}>{patient.firstName + " " + patient.lastName}</option>)
                                        })}
                                    </Form.Select>
                                </div>
                            </Collapse>
                        </Form>

                    </div>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => setShow(false)}>
                            Odustani
                        </Button>
                        <Button variant="primary" onClick={() => addAppointment()}>
                            Dodaj termin
                        </Button>
                    </Modal.Footer>
                </Modal>

                <AddAppointmentError show={showError} setShow={setShowError}/>
                <CancelAppointmentError show={showCancelError} setShow={setShowCancelError}/>

            </div>
        </div>
    </>;
    if (allowed) {
        return html;
    }

}

