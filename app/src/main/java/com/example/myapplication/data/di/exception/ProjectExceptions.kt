package com.example.myapplication.data.di.exception

import com.example.myapplication.R
import com.example.myapplication.util.getAppString


/**
 * Created by Samira Salah
 */
open class ProjectExceptions {
    companion object{
        fun networkException() = UnknownHostException()
        fun anyException(gson:Any) = AnyException(gson)
    }

    class UnknownHostException() : ProjectApiCauseException(
        getAppString(R.string.unknow_host_message),
    )
    class AnyException(val gson:Any) : ProjectApiCauseException(
        getAppString(R.string.any_api_axception_message),
    )
}