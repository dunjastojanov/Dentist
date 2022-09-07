export function getAppointmentDateString(startTime) {
    return String(startTime[2]).padStart(2, '0') + "." + String(startTime[1]).padStart(2, '0') + "." + startTime[0] + "."
}

export function getAppointmentTimeString(startTime) {
    return String(startTime[3]).padStart(2, '0') + ":" + String(startTime[4]).padStart(2, '0')
}

export let appointmentTimes = [
    "09:00", "09:30",
    "10:00", "10:30",
    "11:00", "11:30",
    "12:00", "12:30",
    "13:00", "13:30",
    "14:00", "14:30",
    "15:00", "15:30",
    "16:00", "16:30",
]