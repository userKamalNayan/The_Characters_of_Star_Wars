package com.kamalnayan.knstarwars.util.layoutmanager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** @Author Kamal Nayan
Created on: 10/01/24
 *
 * Was facing crashes with [GridLayoutManager], hence
 * used this class to disable some functionalities
 **/
class ExtendedGridLayoutManager : GridLayoutManager {

    constructor(context: Context, spanCount: Int) : super(context, spanCount)

    constructor(
        context: Context, attrs: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(
        context: Context, spanCount: Int,
        orientation: Int,
        reverseLayout: Boolean
    ) : super(context, spanCount, orientation, reverseLayout)


    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}