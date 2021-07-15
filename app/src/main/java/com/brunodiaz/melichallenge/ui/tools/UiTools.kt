package com.brunodiaz.melichallenge.ui.tools

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


class UiTools {

    companion object {
        fun hideKeyboard(activity: Activity) {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // Check if no view has focus
            val currentFocusedView = activity.currentFocus
            currentFocusedView?.let {
                inputMethodManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

        fun closeSoftKeyboard(context: Context, v: View) {
            val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            iMm.hideSoftInputFromWindow(v.windowToken, 0)
            v.clearFocus()
        }
    }
}