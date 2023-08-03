
// Function that converts a number (in miliseconds) to a
// human-readable text.
export function convertExecutionTime(time) {
    let result = '';

    if(time === -1) {
        result = 'Timeout';
    } else {
        const sec =  Math.round(time / 1000 / 60)
        const min =  Math.round((time % (1000 * 60)) / 1000)

        const formatMin = String(sec).padStart(2, '0')
        const formatSec = String (min).padStart(2, '0')

        return formatMin + ':' + formatSec
    }

    return result;
}

export function convertTimestampHumanReadable(time) {
    const options = {
        year: 'numeric', // 4-digit year (e.g., "2023")
        month: 'numeric', // Full name of the month (e.g., "Juli" for July)
        day: 'numeric', // Day of the month (e.g., "22")
        hour: 'numeric', // Hour (e.g., "20" for 8 PM)
        minute: 'numeric', // Minute (e.g., "14")
        second: 'numeric', // Second (e.g., "46")
    };

    return new Intl.DateTimeFormat('de-DE', options).format(new Date(time));
}

export function toolColor(group) {
    if (group === 'dependencies') {
        return '#7e7e7e';
    } else if (group === 'source') {
        return  '#2c9f2c';
    } else if (group === 'container') {
        return  '#1f76b3';
    } else if (group === 'release') {
        return '#fd7e0e';
    }
}

export function toolLightColor(group) {
    if (group === 'dependencies') {
        return '#e3e3e3';
    } else if (group === 'source') {
        return  '#edffed';
    } else if (group === 'container') {
        return  '#e8f5ff';
    } else if (group === 'release') {
        return '#fff7f2';
    }
}
