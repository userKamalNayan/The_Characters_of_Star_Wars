package com.kamalnayan.commons.extension

import android.content.Context
import android.widget.Toast

/** @Author Kamal Nayan
Created on: 11/01/24
 **/

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
 Toast.makeText(this, message, duration).show()
}