package com.lxkj.aidldemo

/**
 * 秒数转分钟
 * Created by Slingge on 2018/2/7 0007.
 */
object TimerTransformationUtil {

    //毫秒转分钟
    fun SecondToMinute(long: Int): String {
        val s = long / 1000
        val m: Int = (s / 60) //分钟
        val second: Int = s - (60 * m)
        var min = m.toString()
        var sec = second.toString()
        if (m.toString().length == 1) {
            min = "0" + m
        }
        if (second.toString().length == 1) {
            sec = "0" + second
        }
        return min + ":" + sec
    }


}