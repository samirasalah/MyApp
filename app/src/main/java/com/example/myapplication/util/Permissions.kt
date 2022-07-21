package com.example.myapplication.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import  com.example.myapplication.R

/**
 * Created by Samira Salah
 */
const val CAMERA_PERMISSIONS = Manifest.permission.CAMERA

@RequiresApi(Build.VERSION_CODES.S)
const val BLUETOOTH_SCAN_PERMISSIONS = Manifest.permission.BLUETOOTH_SCAN

@RequiresApi(Build.VERSION_CODES.S)
const val BLUETOOTH_CONNECT_PERMISSIONS = Manifest.permission.BLUETOOTH_CONNECT

const val ACCESS_FINE_LOCATION_PERMISSIONS = Manifest.permission.ACCESS_FINE_LOCATION
const val ACCESS_COARSE_LOCATION_PERMISSIONS = Manifest.permission.ACCESS_COARSE_LOCATION
const val WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
const val READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE

val BLUETOOTH_PERMISSIONS: Array<String>
    @SuppressLint("NewApi")
    get() {
        return when {
            isApiLevel(Build.VERSION_CODES.S) -> {
                arrayOf(
                    BLUETOOTH_SCAN_PERMISSIONS,
                    BLUETOOTH_CONNECT_PERMISSIONS,
                    ACCESS_FINE_LOCATION_PERMISSIONS,
                    ACCESS_COARSE_LOCATION_PERMISSIONS
                )
            }
            isApiLevel(Build.VERSION_CODES.Q, Build.VERSION_CODES.R) -> arrayOf(
                ACCESS_FINE_LOCATION_PERMISSIONS
            )
            else -> arrayOf(
                ACCESS_COARSE_LOCATION_PERMISSIONS,
            )
        }
    }

val SCAN_INSOLES_PERMISSIONS: Array<String>
    get() {
        return when {
            isApiLevel(Build.VERSION_CODES.S) -> {
                arrayOf(
                    CAMERA_PERMISSIONS,
                    BLUETOOTH_SCAN_PERMISSIONS,
                    BLUETOOTH_CONNECT_PERMISSIONS
                )
            }
            isApiLevel(Build.VERSION_CODES.Q, Build.VERSION_CODES.R) -> arrayOf(
                CAMERA_PERMISSIONS,
                ACCESS_FINE_LOCATION_PERMISSIONS,
            )
            else -> arrayOf(
                CAMERA_PERMISSIONS,
                ACCESS_COARSE_LOCATION_PERMISSIONS,
            )
        }
    }

fun Context.isPermissionGranted(permission: String): Boolean {
    if (!isApiLevel(Build.VERSION_CODES.M)) return true
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.isPermissionsGranted(permissions: Array<String>): PermissionsGrantResult {
    val deniedPermission = mutableListOf<String>()
    for (permission in permissions) {
        if (!isPermissionGranted(permission)) {
            deniedPermission.add(permission)
        }
    }
    val result = when (deniedPermission.size) {
        permissions.size -> PermissionsGrantResult.Result.COMPLETELY_DENIED
        in 1 until permissions.size -> PermissionsGrantResult.Result.PARTIALLY_GRANTED
        else -> PermissionsGrantResult.Result.GRANTED
    }
    return PermissionsGrantResult(
        result,
        deniedPermission.toTypedArray()
    )
}

fun Activity.shouldShowRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

fun Activity.getRationalePermissions(permissions: Array<String>): Array<String> {
    val rationalePermissions = mutableListOf<String>()
    for (permission in permissions) {
        if (
            !isPermissionGranted(permission) &&
            shouldShowRationale(permission)
        ) {
            rationalePermissions.add(permission)
        }
    }
    return rationalePermissions.toTypedArray()
}

fun Activity.getPermanentlyDeniedPermissions(permissions: Array<String>): Array<String> {
    val permanentlyDeniedPermissions = mutableListOf<String>()
    for (permission in permissions) {
        if (
            !isPermissionGranted(permission) &&
            !shouldShowRationale(permission)
        ) {
            permanentlyDeniedPermissions.add(permission)
        }
    }
    return permanentlyDeniedPermissions.toTypedArray()
}

fun showPermissionsDialog(
    activity: Activity,
    permissions: Array<String>,
    isPermanentlyDenied: Boolean = false
) {
    permissions.forEach {
        if (it != Manifest.permission.BLUETOOTH_CONNECT) {
            showAlert(
                activity,
                isPermanentlyDenied,
                getPermissionTitle(it),
                getPermissionMessage(it)
            ) {
                if (isPermanentlyDenied) {
                    val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    i.data = Uri.parse("package:" + activity.packageName)
                    activity.startActivity(i)
                }
            }
        }
    }
}

private fun showAlert(
    context: Context,
    isPermanentlyDenied: Boolean,
    permissionTitle: String,
    permissionMessage: String, callback: () -> Unit
) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)

    if (!isPermanentlyDenied)
        builder.setTitle(permissionTitle)
            .setMessage(permissionMessage)
            .setCancelable(true)
            .setPositiveButton(getAppString(R.string.common_ok)) { _, _ ->
                callback()
            }
            .setNegativeButton(getAppString(R.string.common_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
    else
        builder.setTitle(permissionTitle)
            .setMessage(permissionMessage)
            .setCancelable(false)
            .setPositiveButton("Open Setting") { _, _ ->
                callback()
            }

    val alert: AlertDialog = builder.create()
    alert.show()
}

@SuppressLint("NewApi")
private fun getPermissionTitle(permission: String): String {
    return when (permission) {
        CAMERA_PERMISSIONS ->"Autorisations de l’appareil photo"
        ACCESS_FINE_LOCATION_PERMISSIONS, ACCESS_COARSE_LOCATION_PERMISSIONS -> ""
        BLUETOOTH_SCAN_PERMISSIONS, BLUETOOTH_CONNECT_PERMISSIONS -> ""
        else -> ""
    }
}

@SuppressLint("NewApi")
private fun getPermissionMessage(permission: String): String {
    return if (isApiLevel(Build.VERSION_CODES.S)) {
        when (permission) {
            ACCESS_FINE_LOCATION_PERMISSIONS, ACCESS_COARSE_LOCATION_PERMISSIONS -> "needs_to_use_location_api_S"
            BLUETOOTH_SCAN_PERMISSIONS, BLUETOOTH_CONNECT_PERMISSIONS -> "needs_to_have_nearby_device"
            else -> ""
        }
    } else {
        when (permission) {
            CAMERA_PERMISSIONS -> "needs_to_use_camera"
            ACCESS_FINE_LOCATION_PERMISSIONS, ACCESS_COARSE_LOCATION_PERMISSIONS -> "needs_to_use_location"
            else -> ""
        }
    }
}