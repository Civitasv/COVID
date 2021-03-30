function DateStringToTimestamp(DateString) {
    var date = new Date(DateString.replace(/-/g, '/'));
    return Date.parse(date) / 1000;
}

function TimestampToDateString(timestamp) {
    var date = new Date(timestamp * 1000);
    return date.getFullYear + "-" + date.getMonth + "-" + date.getDate();
}

export { DateStringToTimestamp, TimestampToDateString }