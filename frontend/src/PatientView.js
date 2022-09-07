import {useParams} from "react-router-dom";
import React, {useEffect, useState} from 'react';
import axios from "axios";
import {backlink} from "./Consts";
import {Calendar} from "./Calendar";
import Button from "react-bootstrap/Button";
import {AppointmentCards} from "./AppointmentCards";
import {Form, Modal} from "react-bootstrap";
import {AppointmentForm} from "./AppointmentForm";
import {AddAppointmentError} from "./AddAppointmentError";
import {CancelAppointmentError} from "./CancelAppointmentError";
import {logOut} from "./LogOut";

export function PatientView() {
    const {id} = useParams();

    const [appointments, setAppointments] = useState([]);
    const [events, setEvents] = useState([]);

    const [formData, setFormData] = useState({
        date: new Date().toISOString().substring(0, 10),
        time: "09:00",
        duration: "30",
        patientId: id,
        type: ""
    });

    const fetchAppointments = () => {
        axios.get(backlink + "appointment/" + id)
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
                        title: a.type
                    })
                }
                setEvents(newEvents);
            })
            .catch(error => {
                console.log(error);
            });
    };

    useEffect(() => {
        fetchAppointments();
    }, []);

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
            type: formData.type,
            patientId: id
        }

        axios.post(backlink + "appointment/add", dto).then(res => {
            window.location.reload();
        }).catch(err => {
            setShowError(true);
        })
    }

    const [showCancelError, setShowCancelError] = useState(false);
    const [showError, setShowError] = useState(false);
    const [show, setShow] = useState(false);

    let allowed = localStorage.getItem("type") === "patient" && localStorage.getItem("id") === id;

    let html = <>
        <div className="d-flex p-3" style={{
            backgroundImage: "linear-gradient(0deg, #08AEEA 0%, #2AF598 100%)"
        }}>

            <Calendar events={events} appointments={appointments} cancelAppointment={cancelAppointment}/>
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
            </div>

            <Modal show={show} onHide={() => setShow(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Novi termin</Modal.Title>
                </Modal.Header>
                <div className="m-3">

                    <Form>
                        <AppointmentForm formData={formData} setFormData={setFormData}/>
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
    </>;
    if (allowed) {
        return html
    }

}