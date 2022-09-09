import {Form} from "react-bootstrap";
import React from "react";

export function NewPatientForm({formData, setFormData}) {

    const setField = (field, value) => {
        setFormData({
            ...formData,
            [field]: value
        })
    }

    return (<div id="new-appUser-appointment">
        <Form.Group>
            <Form.Label>Ime</Form.Label>
            <Form.Control type="text"
                          value={formData.firstName}
                          onChange={(e) => setField("firstName", e.target.value)}></Form.Control>
        </Form.Group>
        <Form.Group>
            <Form.Label>Prezime</Form.Label>
            <Form.Control type="text"
                          value={formData.lastName}
                          onChange={(e) => setField("lastName", e.target.value)}></Form.Control>
        </Form.Group>
        <Form.Group>
            <Form.Label>JMBG</Form.Label>
            <Form.Control type="text"
                          value={formData.jmbg}
                          onChange={(e) => setField("jmbg", e.target.value)}></Form.Control>
        </Form.Group>
        <Form.Group>
            <Form.Label>LBO</Form.Label>
            <Form.Control type="text"
                          value={formData.lbo}
                          onChange={(e) => setField("lbo", e.target.value)}></Form.Control>
        </Form.Group>
        <Form.Group>
            <Form.Label>Email</Form.Label>
            <Form.Control type="email"
                          value={formData.email}
                          onChange={(e) => setField("email", e.target.value)}></Form.Control>
        </Form.Group>

        <Form.Group>
            <Form.Label>Lozinka</Form.Label>
            <Form.Control type="password"
                          value={formData.password}
                          onChange={(e) => setField("password", e.target.value)}></Form.Control>
        </Form.Group>

    </div>)
}