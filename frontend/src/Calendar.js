import {Dropdown, Modal} from "react-bootstrap";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import React, {useState} from "react";
import {getAppointmentDateString, getAppointmentTimeString} from "./AppointmetTime";
import Button from "react-bootstrap/Button";

export function Calendar({events, appointments, cancelAppointment}) {

    const calendarRef = React.createRef();

    const changeView = (view) => {
        calendarRef.current
            .getApi()
            .changeView(view);
    }

    const [appointment, setAppointment] = useState({
        patient: {firstName: "", lastName: ""}, startTime: [0, 0, 0, 0, 0]
    });
    const [showAppointment, setShowAppointment] = useState(false);

    return (

        <div className="w-75 m-1 p-4" style={{
            backgroundColor: "white", borderRadius: "10px"
        }}>
            <Dropdown>
                <Dropdown.Toggle className="mb-3" variant="outline-primary" id="dropdown-basic">
                    Tip pregleda kalendara
                </Dropdown.Toggle>

                <Dropdown.Menu>
                    <Dropdown.Item onClick={() => changeView('dayGrid')}>Dnevni pregled</Dropdown.Item>
                    <Dropdown.Item onClick={() => changeView('dayGridWeek')}>Nedelji pregled</Dropdown.Item>
                    <Dropdown.Item onClick={() => changeView('dayGridMonth')}>Mesečni pregled</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
            <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin]}
                initialView="dayGridMonth"
                timeZone="CEST"
                events={events}
                ref={calendarRef}
                eventClick={

                    function (arg) {
                        for (let index in appointments) {
                            if (appointments.at(index).id.toString() === arg.event.id) {
                                setAppointment(appointments.at(index));
                                setShowAppointment(true);
                            }
                        }
                    }}
            />
            <Modal show={showAppointment}
                   onHide={() => setShowAppointment(false)}>

                <Modal.Header>{appointment.patient.firstName + " " + appointment.patient.lastName}</Modal.Header>
                <div className="d-flex m-3 flex-column">
                    <div>Datum: {getAppointmentDateString(appointment.startTime)}</div>
                    <div>Vreme: {getAppointmentTimeString(appointment.startTime)}</div>
                    <div>Trajanje: {appointment.duration} minuta</div>
                </div>

                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowAppointment(false)}>
                        Odustani
                    </Button>
                    <Button variant="primary" onClick={() => cancelAppointment(appointment.id)}>
                        Otkaži
                    </Button>
                </Modal.Footer>

            </Modal>
        </div>)
}