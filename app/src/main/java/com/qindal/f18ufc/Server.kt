package com.qindal.f18ufc

import android.os.*
import android.util.Log
import android.widget.TextView
import java.io.*
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.Socket
import java.util.*

class Server(_textHandler:Handler, _txtStatus:TextView) : Runnable {

    private var textHandler : Handler? = _textHandler
    private var serverStatus: TextView? = _txtStatus

    // DESIGNATE A PORT
    private val SERVERPORT = 61528

    private val handler = Handler()

    private var serverSocket: ServerSocket? = null
    private var client: Socket? = null
    private var dataInputStream: DataInputStream? = null
    private var dataOutputStream: DataOutputStream? = null
    private var output: PrintWriter? = null
    private var input: BufferedReader? = null

    private var previousLine = ""
    private var button = "0"
    private var inOut = "0"
    private var waitingAction = false

    fun setData(_handler: Handler, _serverStatus : TextView){
        Log.w("LOG","Socket data updated")
        textHandler = _handler
        serverStatus = _serverStatus
    }

    override fun run() {
        try {
            serverSocket = ServerSocket(SERVERPORT)
            Log.w("LOG","Socket created on " + getIPAddress(true).toString() + ":" + SERVERPORT)
            if(serverStatus != null) handler.post { serverStatus!!.text = "Listening on: " + getIPAddress(true).toString() + ":" + SERVERPORT }
            while (true) {
                // LISTEN FOR INCOMING CLIENTS
                client = serverSocket!!.accept()
                Log.w("LOG", "Client accepted")
                if(serverStatus != null) handler.post { serverStatus!!.text = "Client accepted" }
                output = PrintWriter(
                    BufferedWriter(
                        OutputStreamWriter(client!!.getOutputStream())
                    )
                )
                input = BufferedReader(InputStreamReader(client!!.getInputStream()))
                var line: String? = null
                while (input!!.readLine().also { line = it } != null) {
                    // Log.w("LOG", "Received " + line);
                    //  Process input
                    ProcessLine(line)

                    // Command the airplane
                    if(waitingAction) {
                        val command: String = button + "=" + inOut
                        //Log.w("LOG", "Commanded " + command);
                        waitingAction = false
                        output!!.write(command)
                        output!!.flush()
                    }

                    val status: String = ""
                    if (status.length >= 5) if(serverStatus != null) handler.post { serverStatus!!.text = status }
                }
                // break;
            }
        } catch (e: Exception) {
            if(serverStatus != null) handler.post { serverStatus!!.text = "Error" }
            e.printStackTrace()
        } finally {
            if (client != null) {
                try {
                    client!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (dataInputStream != null) {
                try {
                    dataInputStream!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (dataOutputStream != null) {
                try {
                    dataOutputStream!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (serverSocket!!.isClosed) {
                try {
                    serverSocket!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return
        }
    }


    private fun ProcessLine(line: String?) {
        try {
            //Extract the info and update the state of the autopilot
            // Process the data sent by the client.
            val words = line!!.split("\n").toTypedArray()
            for (w in words.indices) {
                //Log.w("LOG", "Word " + w + " " + words[w]);
                if (words[w].contains("FA-18C_hornet")) {
                    Log.w("LOG", "Riding a " + words[w])
                    val text = "Riding a " + words[w]
                    if(serverStatus != null) handler.post { serverStatus!!.text = text }
                    break
                }
                if (words[w].contains("exit")) {
                    Log.w("LOG", "Client disconnected")
                    val text = "Disconnected"
                    if(serverStatus != null) handler.post { serverStatus!!.text = text }
                    break
                }

                val text: String = words[w]
                val msgBundle = Bundle()
                msgBundle.putString("text", text)
                val msg = Message()
                msg.data = msgBundle
                msg.what = -1
                msg.obj =  words[w]
                when (previousLine) {
                    "UFC_OptionDisplay1" -> {msg.what = 1}
                    "UFC_OptionDisplay2" -> {msg.what = 2}
                    "UFC_OptionDisplay3" -> {msg.what = 3}
                    "UFC_OptionDisplay4" -> {msg.what = 4}
                    "UFC_OptionDisplay5" -> {msg.what = 5}
                    "UFC_OptionCueing1" -> {msg.what = 11}
                    "UFC_OptionCueing2" -> {msg.what = 21}
                    "UFC_OptionCueing3" -> {msg.what = 31}
                    "UFC_OptionCueing4" -> {msg.what = 41}
                    "UFC_OptionCueing5" -> {msg.what = 51}
                    "UFC_ScratchPadNumberDisplay" -> {msg.what = 6}
                    "UFC_ScratchPadString1Display"  -> {msg.what = 61}
                    "UFC_ScratchPadString2Display"  -> {msg.what = 62}
                }
                if(msg.what > 0){
                    //Log.v("LOG", "Sending " + msg.what + " -> " + words[w])
                    if(textHandler != null) textHandler?.sendMessage(msg)
                }

                previousLine = words[w]
            }
        } catch (e: NumberFormatException) {
            Log.w("LOG", "NumberFormatException")
        }
    }

    private fun getIPAddress(useIPv4: Boolean): String? {
        try {
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs: List<InetAddress> =
                    Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        val sAddr = addr.hostAddress
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        val isIPv4 = sAddr.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4) return sAddr
                        } else {
                            if (!isIPv4) {
                                val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(
                                    0,
                                    delim
                                ).toUpperCase()
                            }
                        }
                    }
                }
            }
        } catch (ignored: java.lang.Exception) {
        } // for now eat exceptions
        return ""
    }

    fun newAction(_button : Int, _in_out : Int){
        button = (3000 + _button).toString();
        inOut = _in_out.toString();
        waitingAction = true
    }

}