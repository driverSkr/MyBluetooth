package com.driverskr.mybluetooth_kotlin.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass.Device.*
import android.bluetooth.BluetoothClass.Device.Major.*
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.driverskr.mybluetooth_kotlin.R
import com.driverskr.mybluetooth_kotlin.databinding.ItemDeviceListBinding
import com.driverskr.mybluetooth_kotlin.entension.toast
import com.driverskr.mybluetooth_kotlin.utils.PermissionUtils

/**
 * 蓝牙设备适配器
 */
class DeviceAdapter(private val context: Context,
                    private val data: MutableList<BluetoothDevice>?,
                    private val listener: (position: Int) -> Unit):
     RecyclerView.Adapter<DeviceAdapter.ViewHolder>(){


     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
          = ViewHolder(ItemDeviceListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

     @SuppressLint("MissingPermission")
     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          val tvName = holder.binding.tvName
          val icon = holder.binding.ivDeviceType
          val item = data?.get(position)
          //根据设备类型设置图标
          item?.let {
               if (PermissionUtils(context as AppCompatActivity).hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                    context.toast("没有蓝牙连接权限")
               } else {
                    getDeviceType(it.bluetoothClass.majorDeviceClass, icon)

                    tvName.text = it.name ?: "无名"

                    //蓝牙设备绑定状态判断
                    val tvState = holder.binding.tvBondState
                    tvState.text = when (it.bondState) {
                         10 -> "未配对"
                         11 -> "正在配对..."
                         12 -> "已配对"
                         else -> "未配对"
                    }
                    //添加item点击事件
                    holder.itemView.setOnClickListener {
                         listener(position)
                    }
               }
          }
     }

     override fun getItemCount(): Int = data?.size ?: 0

     /**
      * 根据类型设置图标
      * @param type 类型码
      * @param icon 图标
      */
     private fun getDeviceType(type: Int, icon: ImageView) {
          when (type) {
               AUDIO_VIDEO_HEADPHONES,//耳机
               AUDIO_VIDEO_WEARABLE_HEADSET,//穿戴式耳机
               AUDIO_VIDEO_HANDSFREE,//蓝牙耳机
               AUDIO_VIDEO //音频设备
                    -> icon.setImageResource(R.mipmap.icon_headset)
               COMPUTER //电脑
                    -> icon.setImageResource(R.mipmap.icon_computer)
               PHONE //手机
                    -> icon.setImageResource(R.mipmap.icon_phone)
               HEALTH //健康类设备
                    -> icon.setImageResource(R.mipmap.icon_health)
               AUDIO_VIDEO_CAMCORDER, //照相机录像机
               AUDIO_VIDEO_VCR //录像机
                    -> icon.setImageResource(R.mipmap.icon_vcr)
               AUDIO_VIDEO_CAR_AUDIO //车载设备
                    -> icon.setImageResource(R.mipmap.icon_car)
               AUDIO_VIDEO_LOUDSPEAKER //扬声器
                    -> icon.setImageResource(R.mipmap.icon_loudspeaker)
               AUDIO_VIDEO_MICROPHONE //麦克风
                    -> icon.setImageResource(R.mipmap.icon_microphone)
               AUDIO_VIDEO_PORTABLE_AUDIO //打印机
                    -> icon.setImageResource(R.mipmap.icon_printer)
               AUDIO_VIDEO_SET_TOP_BOX //音频视频机顶盒
                    -> icon.setImageResource(R.mipmap.icon_top_box)
               AUDIO_VIDEO_VIDEO_CONFERENCING //音频视频视频会议
                    -> icon.setImageResource(R.mipmap.icon_meeting)
               AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER //显示器和扬声器
                    -> icon.setImageResource(R.mipmap.icon_tv)
               AUDIO_VIDEO_VIDEO_GAMING_TOY //游戏
                    -> icon.setImageResource(R.mipmap.icon_game)
               AUDIO_VIDEO_VIDEO_MONITOR //可穿戴设备
                    -> icon.setImageResource(R.mipmap.icon_wearable_devices)
               else -> icon.setImageResource(R.mipmap.icon_bluetooth)
          }
     }

     /**
      * 刷新适配器
      */
     @SuppressLint("NotifyDataSetChanged")
     fun changeBondDevice() {
          notifyDataSetChanged()
     }

     class ViewHolder(val binding: ItemDeviceListBinding): RecyclerView.ViewHolder(binding.root)
}
