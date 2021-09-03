// Reminder.js

import { NativeModules } from 'react-native'
const { Reminder } = NativeModules /* must be same as REACT_CLASS variable in module class */

/* alternative 1 */
// export default Reminder

/* alternative 2 */
const _ = {
    startService() { Reminder.startService() },
    stopService() { Reminder.stopService() },
    /* any other methods similarly defined */
    ping() { Reminder.ping() },
    setAlarm() { Reminder.setAlarm() },
    setAlarmOne() { Reminder.setAlarmOne() },
    setAlarmTwo() { Reminder.setAlarmTwo() },
}

export default _
