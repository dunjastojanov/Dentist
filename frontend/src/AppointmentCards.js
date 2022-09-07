import {Card} from "react-bootstrap";
import {getAppointmentDateString, getAppointmentTimeString} from "./AppointmetTime";
import Button from "react-bootstrap/Button";
import React from "react";

export function AppointmentCards({appointments, cancelAppointment}) {
    return (<div>
        {appointments.map((appointment, index) => {
            return (
                <Card key={index} className="m-3">
                    <Card.Header
                        style={{fontWeight: "bold"}}>{appointment.patient.firstName + " " + appointment.patient.lastName}</Card.Header>
                    <Card.Body className="d-flex flex-column">

                        <div className="d-flex flex-column">
                            <div>Datum: {getAppointmentDateString(appointment.startTime)}</div>
                            <div>Vreme: {getAppointmentTimeString(appointment.startTime)}</div>
                            <div>Trajanje: {appointment.duration} minuta</div>
                            <div>Tip: {appointment.type}</div>
                            <div>Email pacijenta: {appointment.patient.email}</div>
                        </div>

                        <Button variant="primary" className="ms-auto"
                                onClick={() => cancelAppointment(appointment.id)}>Otkaži</Button>
                    </Card.Body>
                </Card>
            );
        })}
    </div>)
}