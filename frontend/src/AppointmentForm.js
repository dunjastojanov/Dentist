import {Form, FormGroup} from "react-bootstrap";
import {appointmentTimes} from "./AppointmetTime";
import React from "react";

export function AppointmentForm({formData, setFormData}) {

    const setField = (field, value) => {
        setFormData({
            ...formData,
            [field]: value
        })
    }
    return (<div>

        <Form.Group>
            <Form.Label>Tip pregleda</Form.Label>
            <Form.Control type="text"
                          value={formData.type}
                          onChange={(e) => setField("type", e.target.value)}
            ></Form.Control>
        </Form.Group>

        <Form.Group>
            <Form.Label>Datum</Form.Label>
            <Form.Control type="date"
                          value={formData.date}
                          onChange={(e) => setField("date", e.target.value)}></Form.Control>
        </Form.Group>

        <div className="d-flex">

            <Form.Group className="w-50 pe-1">
                <Form.Label>Vreme početka termina</Form.Label>

                <Form.Select
                    value={formData.time}
                    onChange={(e) => setField("time", e.target.value)}>
                    {appointmentTimes.map((time, index) => {
                        return (<option key={index} value={time}>{time}</option>)
                    })}
                </Form.Select>
            </Form.Group>

            <Form.Group className="w-50 ps-1">
                <Form.Label>Trajanje termina</Form.Label>
                <Form.Select
                    value={formData.duration}
                    onChange={(e) => setField("duration", e.target.value)}>>
                    <option value={30}>30 minuta</option>
                    <option value={60}>60 minuta</option>
                </Form.Select>
            </Form.Group>

        </div>

    </div>)
}