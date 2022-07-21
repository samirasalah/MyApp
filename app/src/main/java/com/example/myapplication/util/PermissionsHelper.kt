package com.example.myapplication.util

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Samira Salah
 */
class PermissionsHelper(
    lifecycle: Lifecycle,
    var permissions: Array<String> = emptyArray(),
    private val onPermissionsResult: (grantResult: PermissionsGrantResult) -> Unit
) : DefaultLifecycleObserver {

    private var resultLauncher: ActivityResultLauncher<Array<String>>? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        resultLauncher = when (owner) {
            is ComponentActivity -> {
                owner.registerForActivityResult(
                    RequestPermissions(), onPermissionsResult
                )
            }
            is Fragment -> {
                owner.registerForActivityResult(
                    RequestPermissions(), onPermissionsResult
                )
            }
            else -> null
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }

    fun requestPermissions(requestedPermissions: Array<String> = permissions) {
        permissions = requestedPermissions
        resultLauncher?.launch(permissions)
    }
}