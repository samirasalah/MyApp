package com.example.myapplication.util

import android.app.Activity
import android.net.Uri
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.github.dhaval2404.imagepicker.ImagePicker
import java.lang.ref.WeakReference
/**
 * Created by Samira Salah
 */
class ImagePickerClass(
    private val fragment: Fragment,
    private val onImagePicked: (Uri) -> Unit

) : DefaultLifecycleObserver {
    private val permissionsHelper  =
        PermissionsHelper(fragment.lifecycle) { grantResult ->
            when {
                grantResult.isGranted -> {
                    getImage()
                }
                else -> {
                    fragment?.let { context ->
                        val rationalePermissions = grantResult.getRationalePermissions(fragment.requireActivity())
                        val permanentlyDeniedPermissions =
                            grantResult.getPermanentlyDeniedPermissions(fragment.requireActivity())
                        if (rationalePermissions.isNotEmpty()) {
                            showPermissionsDialog(fragment.requireActivity(), rationalePermissions)
                        } else if (permanentlyDeniedPermissions.isNotEmpty())
                            showPermissionsDialog(
                                fragment.requireActivity(),
                                permanentlyDeniedPermissions,
                                isPermanentlyDenied = true
                            )
                    }
                }
            }
        }
    private val weakFragment = WeakReference(fragment)

    init {
        fragment.lifecycle.addObserver(this)
    }

    private val onActivityResult = weakFragment.get()
        ?.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK)
                if (result.data != null && result.data?.data != null) onImagePicked(result.data!!.data!!)
        }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getImage()
    }
    private fun getImage() {
        permissionsHelper.requestPermissions(arrayOf(
            READ_EXTERNAL_STORAGE_PERMISSION,
            WRITE_EXTERNAL_STORAGE_PERMISSION))

    }
    override fun onDestroy(owner: LifecycleOwner) {
        weakFragment.get()?.lifecycle?.removeObserver(this)
        super.onDestroy(owner)
    }

    fun pickImage() {
        choosePhotoFromCamera()
    }

    private fun choosePhotoFromGallary() {
        val img = ImagePicker.with(fragment)
        img.galleryOnly()

        img.createIntent {
            onActivityResult?.launch(it)
        }

    }
    private fun choosePhotoFromCamera() {
        val img = ImagePicker.with(fragment)
        img.cameraOnly()
            .compress(300)
            .saveDir(Environment.getExternalStorageDirectory())

        img.createIntent {
            onActivityResult?.launch(it)
        }

    }

}