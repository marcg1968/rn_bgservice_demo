
import React, { Component } from 'react'
import {
    AppState,
    ScrollView,
    StyleSheet,
    Text,
    TouchableOpacity,
    View,
} from 'react-native'
import AsyncStorage from '@react-native-async-storage/async-storage'
import Reminder from './Reminder'

class App extends Component {

    constructor(props) {
        super(props)
        this.state = {
            appState: AppState.currentState,
            statusInfo: null,
        }
    }

    componentDidMount = async () => {

        AppState.addEventListener('change', await this.handleAppStateChange)

        try {
            const allKeys = await AsyncStorage.getAllKeys()
            console.log(25, {allKeys})
            const last_fetch = await AsyncStorage.getItem('@last_fetch')
            this.setState({statusInfo: `last fetched: ${last_fetch}`})
        } catch(e) { /* error reading value */ }
    }

    handleAppStateChange = async nextAppState => {
        const { appState } = this.state
        if (appState.match(/inactive|background/) && nextAppState === 'active') {
            console.log(37, `App has come to the foreground! [Was: ${appState}]`, new Date())
            try {
                const allKeys = await AsyncStorage.getAllKeys()
                console.log(25, {allKeys})
                const last_fetch = await AsyncStorage.getItem('@last_fetch')
                this.setState({ statusInfo: `last fetched: ${last_fetch}` })
            } catch(e) { /* error reading value */ }
        }
        this.setState({ appState: nextAppState })
    }

    render() {

        const delayMs = 15000
        const {
            statusInfo,
        } = this.state

        const buttons = [
            {
                title: 'Start',
                onPress: () => Reminder.startService(),
                style: styles.button,
            },
            {
                title: 'Stop',
                onPress: () => Reminder.stopService(),
                style: styles.button,
            },
            {
                title: 'PING',
                onPress: () => Reminder.ping(),
                style: styles.button,
            },
            {
                title: 'Alarm 1',
                onPress: () => Reminder.setAlarmOne(),
                style: styles.button,
                text: 'Set alarm to start service in 1 min:',
            },
            {
                title: 'Alarm 2',
                onPress: () => Reminder.setAlarmTwo(),
                style: styles.button,
                text: 'Set alarm to start service in 2 mins:',
            },
            {
                title: 'Alarm 3',
                onPress: () => Reminder.setAlarmThree(),
                style: styles.button,
                text: 'Set alarm to start service in 30 secs:',
            },
            // {
            //     title: 'Alarm 4',
            //     onPress: () => Reminder.setAlarmTwo(),
            //     style: styles.button,
            //     text: 'Set alarm to start service in 2 mins:',
            // },
        ]

        return (
            <ScrollView>
                <View style={styles.container}>
                    {buttons.map((btn, i) => (
                        <View
                            key={'btn'+i}
                            // style={{ flex: ('text' in btn) ? 3 : 2 }}
                            style={('text' in btn) ? styles.btnContainerWithText : styles.btnContainer}
                        >
                            {('text' in btn) ? (<Text>{btn.text}</Text>) : null}
                            <TouchableOpacity
                                style={styles.button}
                                onPress={btn.onPress}
                            >
                                <Text style={styles.btntxt}>{btn.title}</Text>
                            </TouchableOpacity>
                        </View>
                    ))}
                    <Spacer/>
                </View>
                <View
                    style={styles.status}
                >
                    <Text>Status: {statusInfo || '--'}</Text>
                </View>
                <Spacer/>
            </ScrollView>
        )
    }
}

const Spacer = () => <View style={{ flex: 1 }}/>

export default App

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center', /* prevents items taking up full width */
        justifyContent: 'center',
        backgroundColor: '#ecf0f1',
        paddingTop: 36,
        paddingBottom: 24,
        margin: 12,
        minHeight: 360,
    },
    status: {
        flex: 1,
        paddingLeft: 12,
        paddingRight: 12,
    },
    btnContainer: {
        flex: 2,
    },
    btnContainerWithText: {
        flex: 3,
    },
    button: {
        flex: 1,
        backgroundColor: 'rgba(175,134,70,0.33)',
        borderColor: '#ccc',
        borderWidth: 3,
        borderRadius: 9,
        textAlign: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        paddingLeft: 18,
        paddingRight: 18,
        margin: 12,
    },
    btntxt: {
        fontSize: 24,
    }
})

