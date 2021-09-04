// BackgroundTask.js

/**
 * This "listens" for data sent by Java native layer
 * available in the taskData parameter
 */

const { ToastAndroid } = require("react-native")
import AsyncStorage from '@react-native-async-storage/async-storage'

const storeData = async (key, value) => {
    key     = key || false
    value   = value || false
    console.log(13, {key, value})
    if (![key, value].every(Boolean)) return
    console.log(15, {key, value})
    try {
        await AsyncStorage.setItem('@last_fetch', new Date().toISOString())
        await AsyncStorage.setItem(key, value)
    } catch (e) {
        // saving error
    }
}

module.exports = async (taskData) => {
    // do stuff, e.g. redux actions etc

    if (Object.keys(taskData).length === 0) return
    if (Object.keys(taskData).length) console.log(25, {taskData})

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
    const { ACTION } = Object
        .keys(taskData)
        .map(key => key === "ACTION" ? { [key]: taskData[key] } : false)
        .find(Boolean) || {}
    switch (ACTION) {
        case 'FETCH_DATA':
            /* TODO: simulate data fetching and store to asyncStorage */
            await storeData('foo', 'bar')
            break
        default:
            break
    }
}
