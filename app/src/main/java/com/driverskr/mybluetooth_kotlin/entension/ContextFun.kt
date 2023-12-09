package com.driverskr.mybluetooth_kotlin.entension

import android.content.Context
import android.widget.Toast

/**
 * @Author: driverSkr
 * @Time: 2023/12/9 11:57
 * @Description: Context相关方法$
 */
fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this,content,duration).show()
}