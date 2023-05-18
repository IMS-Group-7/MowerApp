package se.ju.mobile.mowerapp.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

class SocketManager {
    private var mowerMobileSocket: Socket? = null

    init {
        try {
            val options = IO.Options()
            options.reconnection = true
            mowerMobileSocket = IO.socket("http://34.173.248.99/", options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun connectMobileAppToBackend() {
        mowerMobileSocket?.connect()

        // Set up event listeners
        mowerMobileSocket?.on(Socket.EVENT_CONNECT, onConnect)
        mowerMobileSocket?.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mowerMobileSocket?.on("welcome", onWelcome)

        Log.d("SocketManager", "Event listeners have been set up")


    }
    private val onWelcome = Emitter.Listener { args ->
        val message = args[0] as String
        Log.d("SocketManager", "Received welcome message: $message")
    }

    fun disconnectMobileAppToBackend() {
        mowerMobileSocket?.disconnect()
    }

    fun onMessageReceived(listener: (String) -> Unit) {
        mowerMobileSocket?.on("Message") { args ->
            val message = args[0] as String
            listener(message)
        }
    }

    fun sendMessage(message: String) {
        mowerMobileSocket?.emit("message", message)
    }

    private val onConnect = Emitter.Listener {
        Log.d("SocketManager", "Connected to the server")
    }

    private val onDisconnect = Emitter.Listener {
        Log.d("SocketManager", "Disconnected from the server")
    }

    fun startMower() {
        var data = """{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "start"
          }
        }"""

        this.sendMessage(data)
    }

    fun stopMower() {
        var data = """{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "stop"
          }
        }"""

        this.sendMessage(data)
    }

    fun moveForward(){
        var data = """{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "forward"
          }
        }"""

        this.sendMessage(data)
    }

    fun moveBackward(){
        var data = """{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "backward"
          }
        }"""

        this.sendMessage(data)
    }

    fun moveLeft(){
        var data = """{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "left"
          }
        }"""

        this.sendMessage(data)
    }

    fun moveRight(){
        var data = """{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "right"
          }
        }"""

        this.sendMessage(data)
    }
    fun driverModeAuto(){
        var data = """{
          "type": "DRIVING_MODE",
          "data": {
            "mode": "auto"
          }
        }"""

        this.sendMessage(data)
    }

    fun driverModeMan(){
        var data = """{
          "type": "DRIVING_MODE",
          "data": {
            "mode": "manual"
          }
        }"""

        this.sendMessage(data)
    }


    fun getSocket(): Socket? {
        return mowerMobileSocket
    }

}


