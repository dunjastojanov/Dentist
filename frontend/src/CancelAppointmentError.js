import {Modal} from "react-bootstrap";
import React from "react";

export function CancelAppointmentError({show, setShow}) {
    return (<Modal
        show={show}
        onHide={() => setShow(false)}
        style={{background: "indianred"}}
    >
        <Modal.Header closeButton>
            <h5>Greška pri otkazivanju termina.</h5>
        </Modal.Header>
        <div className="m-3">
            <div>
                Termin je moguće otkazati najranije 24 časa pre njegovog početka.
            </div>
        </div>
    </Modal>)
}