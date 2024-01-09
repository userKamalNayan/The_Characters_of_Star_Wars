package com.kamalnayan.commons.binding_adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

/** @Author Kamal Nayan
Created on: 09/01/24
 **/

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}