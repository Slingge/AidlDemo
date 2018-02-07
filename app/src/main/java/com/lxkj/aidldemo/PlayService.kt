package com.lxkj.aidldemo

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.widget.Toast
import java.util.*


/**
 * 播放服务
 * Created by Slingge on 2018/2/6 0006.
 */
class PlayService : Service() {

    var isStartUp = false//服务是否已启动

    var isPlay = true
    private var isBuffer = true

    private var player: MediaPlayer? = null

    private var playCallBack: PlayInterface? = null

    private var totalProgress: Int = 0//总长度
    private var progress: Int = 0//播放长度

    fun setPlayInterface(playCallBack: PlayInterface) {
        this.playCallBack = playCallBack
    }

    fun create() {
        isStartUp = true

        val url = "http://vip.baidu190.com/uploads/2017/2017012aad53513e56505c9429f1cf8fffcc0f.mp3"

        player = MediaPlayer()
        player!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player!!.reset()
        player!!.setDataSource(url) // 设置数据源
        player!!.prepare()// prepare自动播放
//        player = MediaPlayer.create(this, Uri
//                .parse(url))//实例化对象，通过播放本机服务器上的一首音乐

        player!!.isLooping = true//设置不循环播放
        player!!.start()

        this.playCallBack!!.isBuffer(true)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if(player==null){
                    return
                }
                if (player!!.isPlaying) {
                    isBuffer = false
                    handler.sendMessage(Message())
                }
            }
        }, 500, 1000)
    }


    val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (!isBuffer) {
                totalProgress = player!!.duration //总长度
                progress = player!!.currentPosition //播放长度

                this@PlayService.playCallBack!!.progress(totalProgress, progress)
                this@PlayService.playCallBack!!.isBuffer(false)
            }
        }
    }

    inner class Binder : android.os.Binder() {
        fun getService(): PlayService {
            return this@PlayService
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return Binder()
    }

    fun stopPlay() {
        if (player != null && player!!.isPlaying) {
            player!!.stop()
        }
    }

    fun startPlay() {
        if (player != null) {
            player!!.start()
        }
    }



    fun onDestory() {
        isPlay = false
        if (player != null) {
            player!!.stop()
            player!!.reset()
            player!!.release()
            player = null
        }

        isStartUp = false
    }


}