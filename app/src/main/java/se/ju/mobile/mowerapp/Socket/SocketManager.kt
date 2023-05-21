package se.ju.mobile.mowerapp.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

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
        try {
            mowerMobileSocket?.connect()
        }catch (e: Exception){
            Log.e("CONNECTED", "ERROR CONNECTING TO BACKEND", e)
        }
        try{
            mowerMobileSocket?.on(Socket.EVENT_CONNECT, onConnect)
            mowerMobileSocket?.on(Socket.EVENT_DISCONNECT, onDisconnect)
            mowerMobileSocket?.on("welcome", onWelcome)
            mowerMobileSocket?.on("ERROR", onError)
        }
        catch (e: Exception){
            Log.e("EVENT LISTENER", "ERROR INITIATING EVENT LISTENER", e)
        }
        Log.d("SocketManager", "Event listeners have been set up")
    }

    private val onWelcome = Emitter.Listener { args ->
        val message = args[0] as String
        Log.d("SocketManager", "Received welcome message: $message")
    }
    private val onError = Emitter.Listener { args ->
        val errorData = args[0] as JSONObject
        val errorCode = errorData.getInt("code")
        Log.e("SocketManager", "Received error code $errorCode")

        when(errorCode){
            0 -> unknownErrorHandler()
            1 -> invalidMessageFormatErrorHandler()
            10 -> mowerOffileErrorHandler()
        }
    }

    fun unknownErrorHandler(){
        Log.e("SocketManager", "UNKNOWN ERROR")
    }
    fun invalidMessageFormatErrorHandler(){
        Log.e("SocketManager", "INVALID MESSAGE FORMAT")
    }
    fun mowerOffileErrorHandler(){
        Log.e("SocketManager", "MOWER IS OFFLINE")
    }


    fun disconnectMobileAppToBackend() {
        try {
            mowerMobileSocket?.disconnect()
        }catch (e: Exception){
            Log.e("DISCONNECT", "ERROR DISCONNECTING FROM BACKEND", e)
        }
    }

    fun onMessageReceived(listener: (String) -> Unit) {
        mowerMobileSocket?.on("Message") { args ->
            val message = args[0] as String
            listener(message)
        }
    }

    fun sendMessage(message: String) {
        try {
            mowerMobileSocket?.emit("message", message)
        }
        catch (e: Exception){
            Log.e("SEND", "SENDING MESSAGE FAILED", e)
        }
    }

    private val onConnect = Emitter.Listener {
        Log.d("SocketManager", "Connected to the server")
    }

    private val onDisconnect = Emitter.Listener {
        Log.d("SocketManager", "Disconnected from the server")
    }

    fun startMower() {
        this.sendMessage("""{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "start"
          }
        }""")
    }

    fun stopMower() {
        this.sendMessage("""{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "stop"
          }
        }"""
        )
    }

    fun moveForward(){
        this.sendMessage("""{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "forward"
          }
        }"""
        )
    }

    fun moveBackward(){
        this.sendMessage("""{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "backward"
          }
        }"""
        )
    }

    fun moveLeft(){
        this.sendMessage("""{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "left"
          }
        }""")
    }

    fun moveRight(){
        this.sendMessage("""{
          "type": "MOWER_COMMAND",
          "data": {
            "action": "right"
          }
        }""")
    }
    fun autonomousMode(){
        this.sendMessage("""{
          "type": "DRIVING_MODE",
          "data": {
            "mode": "auto"
          }
        }""")
    }

    fun manualMode(){
        this.sendMessage("""{
          "type": "DRIVING_MODE",
          "data": {
            "mode": "manual"
          }
        }""")
    }

   // fun connectionError(){
   //     this.sendMessage("""{
    //      "type": "ERROR",
   //       "data": {
   //         "code": "1"
   //       }
   //     }""")
   // }
}


