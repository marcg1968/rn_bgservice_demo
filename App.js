
import React, { Component } from 'react'
import {
    Text,
    View,
    TouchableOpacity,
    StyleSheet,
} from 'react-native'
import Reminder from './Reminder'

class App extends Component {

    constructor(props) {
        super(props)
        this.state = {}
    }

    render() {

        const delayMs = 15000

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
        ]

        return (
            <View style={styles.container}>
                {buttons.map((btn, i) => (
                    <View
                        key={'btn'+i}
                        style={{ flex: ('text' in btn) ? 3 : 2 }}
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
                <View style={{ flex: 1 }}/>
            </View>
        )
    }
}

export default App

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center', /* prevents items taking up full width */
        justifyContent: 'center',
        backgroundColor: '#ecf0f1',
        paddingTop: 48,
        paddingBottom: 48,
        margin: 12,
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

