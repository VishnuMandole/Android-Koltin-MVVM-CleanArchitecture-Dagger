package com.marvel.marvelapplication.util

import android.content.Context
import android.widget.Toast

object StringExtensions {
    fun String.toast(context: Context) = Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}