import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import React, {useState} from 'react';

import "./login.css"
import axios from "axios";
import {backlink, frontlink} from "./Consts";
import {Modal} from "react-bootstrap";
import {AppointmentForm} from "./AppointmentForm";
import {AddAppointmentError} from "./AddAppointmentError";
import {NewPatientForm} from "./NewPatientForm";

export function LoginView() {
    
    const [showDentistLogin, setShowDentistLogin] = useState(false);
    const [show, setShow] = useState(false);
    const [showError, setShowError] = useState(false);

    const [formData, setFormData] = useState({
        lbo: "",
        jmbg: "",
        password: "",
        type: "",
        date: new Date().toISOString().substring(0, 10),
        time: "09:00",
        duration: "30",
        patientId: "1",
        firstName: "",
        lastName: "",
        email: ""
    });

    const addAppointment = () => {
        let dto = {
            year: formData.date.substring(0, 4),
            month: formData.date.substring(5, 7),
            day: formData.date.substring(8, 10),
            hour: formData.time.substring(0, 2),
            minute: formData.time.substring(3, 5),
            duration: formData.duration,
            type: formData.type,
            firstName: formData.firstName,
            lastName: formData.lastName,
            jmbg: formData.jmbg,
            lbo: formData.lbo,
            email: formData.email
        }

        axios.post(backlink + "appointment/addWithNewPatient", dto).then(res => {
            window.location.reload();
        }).catch(err => {
            setShowError(true);
        })

    }

    const setField = (field, value) => {
        setFormData({
            ...formData,
            [field]: value
        })
    }

    const loginAsDentist = () => {
        let dto= {
            password: formData.password
        }

        axios.post(backlink+"login/dentist", dto).then(res=>{
            localStorage.setItem("type", "dentist");
            window.location.href = frontlink + "dentist"
        }).catch(err=> {
            console.log(err);
        })
    }

    const loginAsPatient = () => {
        let dto= {
            jmbg: formData.jmbg,
            lbo: formData.lbo
        }

        axios.post(backlink+"login/patient", dto).then(res=>{
            localStorage.setItem("type", "patient");
            localStorage.setItem("id", res.data.id);
            window.location.href = frontlink + "patient/" + res.data.id
        }).catch(err=> {
            console.log(err);
        })
    }
    
    return(
        <div className="login-background">
            <div className="banner d-flex flex-column justify-content-center align-items-center w-100">
                <h1>Prijava</h1>
            </div>
            {!showDentistLogin &&
                <Form className="center-form d-flex flex-column">
                    <Form.Group>
                        <Form.Label>JMBG</Form.Label>
                        <Form.Control type="text"
                                      placeholder="Unesite JMBG"
                                      value={formData.jmbg}
                                      onChange={(e)=>setField("jmbg", e.target.value)}/>
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>LBO</Form.Label>
                        <Form.Control type="text"
                                      placeholder="Unesite LBO"
                                      value={formData.lbo}
                                      onChange={(e)=>setField("lbo", e.target.value)}
                        />
                    </Form.Group>

                    <div className="d-flex btn-footer">
                        <Button variant="link" onClick={()=>setShow(true)}>
                            Prvi put si kod nas? Zakaži svoj prvi termin!
                        </Button>
                        <Button variant="primary" onClick={()=>loginAsPatient()} className="ms-auto">
                            Prijavi se
                        </Button>
                        <Button variant="primary" className="ms-2" onClick={()=>setShowDentistLogin(true)}>
                            Prijavi se kao zubar
                        </Button>
                    </div>

                </Form>
            
            }
            {showDentistLogin &&
                <Form className="center-form d-flex flex-column">

                    <Form.Group>
                        <Form.Label>Lozinka</Form.Label>
                        <Form.Control type="password"
                                      value={formData.password}
                                      onChange={(e)=>setField("password", e.target.value)}/>
                    </Form.Group>

                    <div className="d-flex btn-footer">
                        <Button variant="primary" onClick={()=>loginAsDentist()}>
                            Prijavi se
                        </Button>
                        <Button variant="primary" className="ms-2" onClick={()=>setShowDentistLogin(false)}>
                            Prijavi se kao pacijent
                        </Button>
                    </div>

                </Form>

            }
            <Modal show={show} onHide={() => setShow(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Novi termin</Modal.Title>
                </Modal.Header>
                <div className="m-3">

                    <Form>
                        <AppointmentForm formData={formData} setFormData={setFormData}/>
                        <NewPatientForm formData={formData} setFormData={setFormData}/>
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
        </div>



    )
}