package com.lxkj.aidldemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv_play = findViewById<TextView>(R.id.tv_play)
        tv_play.setOnClickListener {
             var intent=Intent(this,PlayActivity::class.java)
            startActivity(intent)
        }



    }
}
