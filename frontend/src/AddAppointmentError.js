import {Modal} from "react-bootstrap";
import React from "react";

export function AddAppointmentError({show, setShow}) {
    return (<Modal
        show={show}
        onHide={() => setShow(false)}
        style={{background: "indianred"}}
    >
        <Modal.Header closeButton>
            <h5>Greška pri zakazivanju termina.</h5>
        </Modal.Header>
        <div className="m-3">
            <div>
                Termin koji ste pokušali da zauzmete je već zauzet ili ste izabrali termin van radnog
                vremena.
            </div>
            <div>
                Radno vreme je radnim danima od 9 do 17 časova. Vikendom ne radimo.
            </div>
        </div>
    </Modal>)
}