package com.example.myapplication.util

import android.os.Environment
import com.example.myapplication.App


val PathAvatar=App.instance.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()+"/" +"avatar.jpeg"

