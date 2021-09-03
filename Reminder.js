// Reminder.js

import { NativeModules } from 'react-native'
const { Reminder } = NativeModules /* must be same as REACT_CLASS variable in module class */

/* alternative 1 */
// export default Reminder

/* alternative 2: specifically register methods available */
const _ = {
    startService() { Reminder.startService() },
    stopService() { Reminder.stopService() },
    ping() { Reminder.ping() },
    setAlarmOne() { Reminder.setAlarmOne() },
    setAlarmTwo() { Reminder.setAlarmTwo() },
}

export default _
