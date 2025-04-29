package com.example.mymttqdemo2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MqttViewModel(application: Application) : AndroidViewModel(application) {

    private val mqttManager = MqttClientManager(application.applicationContext)

    private val _connectionStatus = MutableStateFlow("Disconnected")
    val connectionStatus: StateFlow<String> = _connectionStatus

    private val _receivedMessage = MutableStateFlow("")
    val receivedMessage: StateFlow<String> = _receivedMessage

    fun connect(serverUri: String, clientId: String, topic: String) {
        mqttManager.connect(
            serverUri,
            clientId,
            onConnected = {
                _connectionStatus.value = "Connected"
                mqttManager.subscribe(topic)
            },
            onFailure = { throwable ->
                _connectionStatus.value = "Failed: ${throwable.localizedMessage}"
            },
            onMessageReceived = { message ->
                _receivedMessage.value = message
            }
        )
    }

    fun sendMessage(topic: String, message: String) {
        mqttManager.publish(topic, message)
    }

    fun disconnect() {
        mqttManager.disconnect()
        _connectionStatus.value = "Disconnected"
    }
}
