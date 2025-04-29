package com.example.mymttqdemo2

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttClientManager(private val context: Context) {

    private lateinit var mqttClient: MqttAndroidClient

    fun connect(
        serverUri: String,
        clientId: String,
        onConnected: () -> Unit,
        onFailure: (Throwable) -> Unit,
        onMessageReceived: (String) -> Unit
    ) {
        mqttClient = MqttAndroidClient(context, serverUri, clientId)

        val options = MqttConnectOptions().apply {
            isCleanSession = true
        }

        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                onMessageReceived(message.toString())
            }

            override fun connectionLost(cause: Throwable?) {}

            override fun deliveryComplete(token: IMqttDeliveryToken?) {}
        })

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                onConnected()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                if (exception != null) {
                    onFailure(exception)
                }
            }
        })
    }

    fun subscribe(topic: String) {
        mqttClient.subscribe(topic, 1)
    }

    fun publish(topic: String, message: String) {
        mqttClient.publish(topic, MqttMessage(message.toByteArray()))
    }

    fun disconnect() {
        mqttClient.disconnect()
    }
}
