import {frontlink} from "./Consts";

export function logOut() {
    window.location.href = frontlink;
    localStorage.clear();
}