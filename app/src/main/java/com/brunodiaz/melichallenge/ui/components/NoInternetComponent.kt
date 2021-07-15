package com.brunodiaz.melichallenge.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.brunodiaz.R

class NoInternetComponent(
    context:Context,
    attrs: AttributeSet
):LinearLayout(context,attrs){

    init{
        LayoutInflater.from(context).inflate(R.layout.no_internet_layout,this, true)
    }
}