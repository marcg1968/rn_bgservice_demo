// index.js

import { AppRegistry } from 'react-native'
import App from './App'
import { name as appName } from './app.json'
import BackgroundTask from './BackgroundTask'

AppRegistry.registerComponent(appName, () => App)

AppRegistry.registerHeadlessTask('ReminderService', () => BackgroundTask)
