import {useParams} from "react-router-dom";

export function PatientView() {
    const {id} = useParams();
    return(
        <h1>Patient {id}</h1>
    )
}