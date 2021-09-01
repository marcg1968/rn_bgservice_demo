
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
        ]

        return (
            <View style={styles.container}>
                <View style={{ flex: 1 }}/>
                {buttons.map((btn, i) => (
                    <TouchableOpacity
                        key={'btn'+i}
                        style={styles.button}
                        onPress={btn.onPress}
                    >
                        <Text style={styles.btntxt}>{btn.title}</Text>
                    </TouchableOpacity>
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
        justifyContent: 'center',
        backgroundColor: '#ecf0f1',
        padding: 36,
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
        margin: 18,
    },
    btntxt: {
        fontSize: 24,
    }
})

