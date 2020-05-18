package com.qindal.f18ufc

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fa18_ufc.*
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var textHandler : Handler

    private val TAG_WORKER_FRAGMENT = "ServerFragment"
    private var mServerFragment: ServerFragment? = null

    // Variables for swype detection
    private var y1: Float = 0f
    private  var y2: Float = 0f
    val MIN_DISTANCE = 150


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val _txtStatus: TextView = txtStatus

        var statusText: String? = null
        if (savedInstanceState != null) {
            statusText = savedInstanceState.getString("status_text")
        }
        if (statusText != null) txtStatus.setText(statusText)

        textHandler = HandlerExtension(this)

        // find the retained fragment on activity restarts
        val fragmentManager = supportFragmentManager
        mServerFragment = fragmentManager.findFragmentByTag(TAG_WORKER_FRAGMENT) as ServerFragment?

        // create the fragment and data the first time
        if (mServerFragment == null) {
            // add the fragment
            val fragment = ServerFragment(textHandler, txtStatus)
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.mainLayout, fragment, TAG_WORKER_FRAGMENT)
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
        }

        mServerFragment = fragmentManager.findFragmentByTag(TAG_WORKER_FRAGMENT) as ServerFragment?
        if(mServerFragment != null) {
            Log.w("LOG", "SocketFragment is running")
            // Reconect fragment with UI
            mServerFragment?.setData(textHandler, txtStatus)
        }

        AP.setOnTouchListener(this)
        IFF.setOnTouchListener(this)
        TCN.setOnTouchListener(this)
        ILS.setOnTouchListener(this)
        DL.setOnTouchListener(this)
        BCN.setOnTouchListener(this)
        ONOFF.setOnTouchListener(this)
        option1.setOnTouchListener(this)
        option2.setOnTouchListener(this)
        option3.setOnTouchListener(this)
        option4.setOnTouchListener(this)
        option5.setOnTouchListener(this)
        numeric0.setOnTouchListener(this)
        numeric1.setOnTouchListener(this)
        numeric2.setOnTouchListener(this)
        numeric3.setOnTouchListener(this)
        numeric4.setOnTouchListener(this)
        numeric5.setOnTouchListener(this)
        numeric6.setOnTouchListener(this)
        numeric7.setOnTouchListener(this)
        numeric8.setOnTouchListener(this)
        numeric9.setOnTouchListener(this)
        numericEnt.setOnTouchListener(this)
        numericCLR.setOnTouchListener(this)
        btComm1.setOnTouchListener(this)
        btComm2.setOnTouchListener(this)
    }

    // Numbers extracted from command_defs.lua
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view) {
            AP -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(1,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(1,0)
                    }
                }
            }
            IFF -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(2,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(2,0)
                    }
                }
            }
            TCN -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(3,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(3,0)
                    }
                }
            }
            ILS -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(4,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(4,0)
                    }
                }
            }
            DL -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(5,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(5,0)
                    }
                }
            }
            BCN -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(6,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(6,0)
                    }
                }
            }
            ONOFF -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(7,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(7,0)
                    }
                }
            }
            option1 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(10,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(10,0)
                    }
                }
            }
            option2 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(11,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(11,0)
                    }
                }
            }
            option3 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(12,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(12,0)
                    }
                }
            }
            option4 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(13,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(13,0)
                    }
                }
            }
            option5 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(14,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(14,0)
                    }
                }
            }
            numeric0 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(18,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(18,0)
                    }
                }
            }
            numeric1 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(19,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(19,0)
                    }
                }
            }
            numeric2 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(20,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(20,0)
                    }
                }
            }
            numeric3 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(21,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(21,0)
                    }
                }
            }
            numeric4 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(22,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(22,0)
                    }
                }
            }
            numeric5 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(23,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(23,0)
                    }
                }
            }
            numeric6 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(24,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(24,0)
                    }
                }
            }
            numeric7 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(25,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(25,0)
                    }
                }
            }
            numeric8 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(26,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(26,0)
                    }
                }
            }
            numeric9 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(27,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(27,0)
                    }
                }
            }
            numericCLR -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(28,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(28,0)
                    }
                }
            }
            numericEnt -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(29,1)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        mServerFragment?.newAction(29,0)
                    }
                }
            }
            btComm1 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(8,1)
                        y1 = motionEvent.y;
                    }
                    MotionEvent.ACTION_UP -> {
                        y2 = motionEvent.y
                        val deltaX = y2 - y1
                        if (Math.abs(deltaX) > MIN_DISTANCE && y2 < y1) {
                            mServerFragment?.newAction(33,1)
                        }
                        else if(Math.abs(deltaX) > MIN_DISTANCE && y2 > y1) {
                            mServerFragment?.newAction(33,0)
                        }
                        else {
                            mServerFragment?.newAction(8, 0)
                        }
                        view.performClick()
                    }
                }
            }
            btComm2 -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        mServerFragment?.newAction(9,1)
                        y1 = motionEvent.y;
                    }
                    MotionEvent.ACTION_UP -> {
                        y2 = motionEvent.y
                        val deltaX = y2 - y1
                        if (Math.abs(deltaX) > MIN_DISTANCE && y2 < y1) {
                            mServerFragment?.newAction(34,1)
                        }
                        else if(Math.abs(deltaX) > MIN_DISTANCE && y2 > y1) {
                            mServerFragment?.newAction(34,0)
                        }
                        else {
                            mServerFragment?.newAction(9, 0)
                        }
                        view.performClick()
                    }
                }
            }
        }
        return true
    }

    private class HandlerExtension(activity: MainActivity?) : Handler() {
        private val currentActivity: WeakReference<MainActivity>

        init {
            currentActivity = WeakReference<MainActivity>(activity)!!
        }

        override fun handleMessage(message: Message) {
            val activity: MainActivity? = currentActivity.get()
            if (activity != null) {
                var messageText = message.data.getString("text")
                if (messageText != null) {
                    messageText = messageText.replace('`','1',false)
                    messageText = messageText.replace('~','2',false)
                    messageText = messageText.replace('_','-',false)
                }

                when (message.what) {
                    // optionDisplay1
                    1 -> activity.txtOptionDisplay1.text = messageText
                    11 -> {if(messageText!!.length > 0)  activity.txtCue1.text =  messageText
                    else activity.txtCue1.text =  " " }
                    // optionDisplay2
                    2 -> activity.txtOptionDisplay2.text = messageText
                    21 -> {if(messageText!!.length > 0)  activity.txtCue2.text =  messageText
                    else activity.txtCue2.text =  " " }
                    // optionDisplay3
                    3 -> activity.txtOptionDisplay3.text = messageText
                    31 -> {if(messageText!!.length > 0)  activity.txtCue3.text =  messageText
                    else activity.txtCue3.text =  " " }
                    // optionDisplay4
                    4 -> activity.txtOptionDisplay4.text = messageText
                    41 -> {if(messageText!!.length > 0)  activity.txtCue4.text =  messageText
                    else activity.txtCue4.text =  " " }
                    // optionDisplay5
                    5 -> activity.txtOptionDisplay5.text = messageText
                    51 -> {if(messageText!!.length > 0)  activity.txtCue5.text =  messageText
                    else activity.txtCue5.text =  " " }
                    // scratchPad
                    6 -> activity.txtScratchPad.text = messageText
                    61 -> {if(messageText!!.length > 0) activity.txtString1.text =  messageText
                        else activity.txtString1.text =  " " }
                    62 -> {if(messageText!!.length > 0)  activity.txtString2.text =  messageText
                    else activity.txtString2.text =  " " }
                    // comm displays
                    71 -> activity.txtComm1.text = messageText
                    72 -> activity.txtComm2.text = messageText
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("status_text", txtStatus.text.toString())
    }
}
