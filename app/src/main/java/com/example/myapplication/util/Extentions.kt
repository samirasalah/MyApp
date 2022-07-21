package com.example.myapplication.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.myapplication.App
import com.example.myapplication.R


/**
 * Created by Samira Salah
 */
fun getAppString(id: Int, vararg format: Any): String {
    return App.instance.getString(id, *format)
}

fun hasNetworkConnection(): Boolean {
    val cm: ConnectivityManager? =
        App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    if (cm != null) {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            if (nc != null) {
                return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
        }
    }
    return false
}
var progressDialog: Dialog? = null
fun Context.onProgress(inProgress: Boolean) {
    try {
        if (inProgress) {

            if (progressDialog == null) {

                progressDialog = Dialog(this, R.style.AppThemeDialog)
                    .apply {
                        setCancelable(false)
                        setContentView(R.layout.progress_bar)
                        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        show()
                    }
            } else {
                progressDialog?.show()
            }

        } else {
            progressDialog?.dismiss()
            progressDialog = null
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
fun isApiLevel(level: Int) = Build.VERSION.SDK_INT >= level

fun isApiLevel(from: Int, toInclusive: Int = Build.VERSION_CODES.S): Boolean {
    return Build.VERSION.SDK_INT in from..toInclusive
}

fun isAnagram(s1:String, s2:String):Boolean{
    if ( s1.length!=s2.length) return false
    //val isSame=s1.map { c1->s2.filter { c2-> c1==c2 }.length }.toList()
    val occurenceS1=s1.map { c1-> s1.filter { c2-> c1==c2 }.length }
    val occurenceS2=s2.map { c1-> s2.filter { c2-> c1==c2 }.length }
    return (occurenceS1==occurenceS2 )
    //return !(isSame.contains(false) || s1.length!=s2.length)

}
