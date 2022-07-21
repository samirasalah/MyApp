package com.example.myapplication.data.di.exception

import java.lang.Exception


/**
 * Created by Samira Salah
 */
open class ProjectApiCauseException(
    val localMessage: String? = null,
    val data: Exception? = null,
    cause: Throwable? = null,
    val recoverySuggestion: String? = null
) : kotlin.Exception(data?.message?.toString() ?: cause?.message ?: localMessage, cause)