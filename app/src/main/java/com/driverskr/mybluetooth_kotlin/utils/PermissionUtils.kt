package com.driverskr.mybluetooth_kotlin.utils

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 * @Author: driverSkr
 * @Time: 2023/11/24 17:50
 * @Description: 权限请求工具类$
 */
class PermissionUtils(private val activity: AppCompatActivity) {

    private lateinit var permissionCallback: (Boolean) -> Unit

    /**
     * 请求权限-立即launch
     */
    fun requestPermissions(permissions: Array<String>, callback: (Boolean) -> Unit) {
        permissionCallback = callback

        if (checkPermissions(permissions)) {
            // 权限已经授予
            permissionCallback(true)
        } else {
            // 请求权限
            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                val allPermissionsGranted = result.all { it.value }
                permissionCallback(allPermissionsGranted)
            }.launch(permissions)
        }
    }

    /**
     * 请求权限-不立即launch
     */
    fun requestPermissionsNotLaunch(callback: (Boolean) -> Unit): ActivityResultLauncher<Array<String>> {
        permissionCallback = callback
        // 请求权限
        return activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val allPermissionsGranted = result.all { it.value }
            permissionCallback(allPermissionsGranted)
        }
    }

    /**
     * 检查权限-批量
     */
    private fun checkPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * 是否有某个权限-单个
     */
    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }
}