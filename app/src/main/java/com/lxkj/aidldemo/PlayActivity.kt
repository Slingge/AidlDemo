package com.lxkj.aidldemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast


/**
 * 播放
 * Created by Slingge on 2018/2/6 0006.
 */

class PlayActivity : AppCompatActivity(), View.OnClickListener, PlayInterface, SeekBar.OnSeekBarChangeListener {

    var playService: PlayService? = null

    private var tv_progress: TextView? = null
    private var tv_total: TextView? = null
    private var seekBar: SeekBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        init()
    }

    private fun init() {
        val tv_play = findViewById<TextView>(R.id.tv_play)
        tv_play.setOnClickListener(this)

        val tv_stop = findViewById<TextView>(R.id.tv_stop)
        tv_stop.setOnClickListener(this)
        seekBar = findViewById(R.id.seekBar)
        seekBar!!.setOnSeekBarChangeListener(this)

        tv_progress = findViewById(R.id.tv_progress)
        tv_total = findViewById(R.id.tv_total)

        if (!PlayService().isStartUp) {
            val intent = Intent(this, PlayService::class.java)
            bindService(intent, trajectoryConnection, BIND_AUTO_CREATE)
        }

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_play -> {
                playService!!.startPlay()
            }
            R.id.tv_stop -> {
                playService!!.stopPlay()
                playService!!.onDestory()
            }
        }
    }

    override fun isBuffer(isBuffer: Boolean) {
        if (isBuffer) {
            Progress.dialog(this)
        } else {
            Progress.dissBuilder()
        }
    }

    override fun progress(totalProgress: Int, progress: Int) {
        tv_total!!.text = TimerTransformationUtil.SecondToMinute(totalProgress)
        tv_progress!!.text = TimerTransformationUtil.SecondToMinute(progress)

        seekBar!!.max = totalProgress
        seekBar!!.progress = progress
    }


    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if (p2) {
            Toast.makeText(this, "手动"+p1, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        Toast.makeText(this, "滑动" + p0!!.progress, Toast.LENGTH_SHORT).show()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }


    private val trajectoryConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val binder = iBinder as PlayService.Binder
            playService = binder.getService()
            playService!!.setPlayInterface(this@PlayActivity)
            playService!!.create()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {

        }
    }



}
