package com.qindal.f18ufc

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * This Fragment manages a single background task and retains
 * itself across configuration changes.
 */
class ServerFragment(private var textHandler: Handler, private var serverStatus : TextView) : Fragment() {

    private var serverThread : Server? = null

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retain this fragment across configuration changes.
        retainInstance = true

        Log.w("LOG","ServerFragment onCreate()")
        // Create and execute the background task.
        serverThread = Server(textHandler, serverStatus)
        val threadWithRunnable = Thread(serverThread)
        threadWithRunnable.start()
    }

    fun setData(_textHandler : Handler, _txtStatus : TextView){
        if(serverThread!=null){
            serverThread!!.setData(_textHandler, _txtStatus)
        }
    }

    fun newAction(_button : Int, _inOut : Int) {
        Log.w("LOG","ServerFragment newAction")
        serverThread!!.newAction(_button, _inOut)
    }
}