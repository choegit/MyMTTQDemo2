package com.example.mymttqdemo2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MqttScreen(viewModel: MqttViewModel = viewModel()) {
    var inputMessage by remember { mutableStateOf("") }

    val connectionStatus by viewModel.connectionStatus.collectAsState()
    val receivedMessage by viewModel.receivedMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Status: $connectionStatus")
        Button(onClick = {
            viewModel.connect(
                serverUri = "tcp://broker.hivemq.com:1883",
                clientId = "AndroidComposeClient",
                topic = "test/topic"
            )
        }) {
            Text("Connect")
        }

        OutlinedTextField(
            value = inputMessage,
            onValueChange = { inputMessage = it },
            label = { Text("Message to send") }
        )

        Button(onClick = {
            viewModel.sendMessage("test/topic", inputMessage)
        }) {
            Text("Publish Message")
        }

        if (receivedMessage.isNotEmpty()) {
            Text(text = "Received: $receivedMessage")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.disconnect() }) {
            Text("Disconnect")
        }
    }
}
