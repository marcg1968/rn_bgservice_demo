// BackgroundTask.js

/**
 * This "listens" for data sent by Java native layer
 * available in the taskData parameter
 */

const { ToastAndroid } = require("react-native")

module.exports = async (taskData) => {
    // do stuff, e.g. redux actions etc
    const pong = Object.keys(taskData).find(key =>
        key.toUpperCase().startsWith('PONG'))
    if (pong) {
        console.log(12, {pong: taskData[pong]})
        /* taskData[pong] will hold millisec epoch timestamp */
        ToastAndroid.show(
            'Received pong at ' +
                (new Date(parseInt(taskData[pong]))).toISOString(),
            ToastAndroid.LONG
        )
    }

    /* listen for key 'ACTION' */
    const action = Object.keys(taskData).find(key => key.toUpperCase().startsWith('ACTION'))
    switch (action) {
        case 'FETCH_DATA':
            /* TODO: simulate data fetching and store to asyncStorage */
            break
        default:
            break
    }
}
