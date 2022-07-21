package com.example.myapplication.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

/**
 * Created by Samira Salah
 */
class RequestPermissions : ActivityResultContract<Array<String>, PermissionsGrantResult>() {

    override fun createIntent(context: Context, input: Array<String>): Intent {
        return Intent(ActivityResultContracts.RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS)
            .putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): PermissionsGrantResult {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return PermissionsGrantResult(
                result = PermissionsGrantResult.Result.COMPLETELY_DENIED
            )
        }

        val grantResults =
            intent.getIntArrayExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS)
        val requestedPermissions =
            intent.getStringArrayExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS)
        if (grantResults == null || requestedPermissions == null) {
            return PermissionsGrantResult(
                result = PermissionsGrantResult.Result.COMPLETELY_DENIED
            )
        }

        return isRequestedPermissionsGranted(
            grantResults = grantResults,
            requestedPermissions = requestedPermissions
        )
    }

    override fun getSynchronousResult(
        context: Context,
        input: Array<String>
    ): SynchronousResult<PermissionsGrantResult>? {
        if (input.isEmpty()) {
            return SynchronousResult(
                PermissionsGrantResult(
                    result = PermissionsGrantResult.Result.COMPLETELY_DENIED
                )
            )
        }

        return if (context.isPermissionsGranted(input).isGranted) {
            SynchronousResult(
                PermissionsGrantResult(
                    result = PermissionsGrantResult.Result.GRANTED
                )
            )
        } else null
    }
}