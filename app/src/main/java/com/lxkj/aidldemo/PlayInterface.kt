package com.lxkj.aidldemo

/**
 * Created by Slingge on 2018/2/7 0007.
 */
interface PlayInterface {

    //是否在缓冲
    fun isBuffer(isBuffer: Boolean)

    // 总时间、进度
    fun progress(totalProgress: Int, progress: Int)
}