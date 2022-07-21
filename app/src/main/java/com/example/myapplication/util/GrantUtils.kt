package com.example.myapplication.util

import android.app.Activity
import android.content.pm.PackageManager
/**
 * Created by Samira Salah
 */
class PermissionsGrantResult constructor(
    val result: Result = Result.GRANTED,
    val deniedPermissions: Array<String> = emptyArray()
) {

    enum class Result {
        GRANTED,
        PARTIALLY_GRANTED,
        COMPLETELY_DENIED
    }

    val isGranted: Boolean
        get() = result == Result.GRANTED

    val isNotGranted: Boolean
        get() = isPartiallyGranted || isCompletelyDenied

    val isPartiallyGranted: Boolean
        get() = result == Result.PARTIALLY_GRANTED

    val isCompletelyDenied: Boolean
        get() = result == Result.COMPLETELY_DENIED

    fun shouldShowRationale(activity: Activity): Boolean {
        return getRationalePermissions(activity).isNotEmpty()
    }

    fun getRationalePermissions(activity: Activity): Array<String> {
        return when {
            deniedPermissions.isEmpty() -> emptyArray()
            else -> activity.getRationalePermissions(deniedPermissions)
        }
    }

    fun getPermanentlyDeniedPermissions(activity: Activity): Array<String> {
        return when {
            deniedPermissions.isEmpty() -> emptyArray()
            else -> activity.getPermanentlyDeniedPermissions(deniedPermissions)
        }
    }
}

fun isRequestedPermissionGranted(
    permissions: Array<String>,
    grantResults: IntArray,
    requestedPermissions: Array<String>
): Boolean {
    if (
        permissions.size == requestedPermissions.size &&
        grantResults.size == requestedPermissions.size
    ) {
        for (i in requestedPermissions.indices) {
            if (
                permissions[i] != requestedPermissions[i] ||
                grantResults[i] != PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }
    return false
}

fun isRequestedPermissionsGranted(
    grantResults: IntArray,
    requestedPermissions: Array<String>
): PermissionsGrantResult {
    if (grantResults.size == requestedPermissions.size) {
        val deniedPermission = mutableListOf<String>()
        for (i in requestedPermissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermission.add(
                    requestedPermissions[i]
                )
            }
        }

        val result = when (deniedPermission.size) {
            requestedPermissions.size -> PermissionsGrantResult.Result.COMPLETELY_DENIED
            in 1 until requestedPermissions.size -> PermissionsGrantResult.Result.PARTIALLY_GRANTED
            else -> PermissionsGrantResult.Result.GRANTED
        }
        return PermissionsGrantResult(
            result = result,
            deniedPermissions = deniedPermission.toTypedArray()
        )
    }

    return PermissionsGrantResult(
        result = PermissionsGrantResult.Result.COMPLETELY_DENIED
    )
}