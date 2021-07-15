package com.brunodiaz.melichallenge.ui.tools

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.brunodiaz.R
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest{

    private lateinit var resourceComparer : ResourceComparer

    @Before
    fun setup(){
        resourceComparer = ResourceComparer()
    }

    @After
    fun teardown(){
        // no es necesario pero podria usarse por ej para room, para cerrarla por cada caso
    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "MeLiChallenge")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDiferentAsGivenString_returnsFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Hello")
        assertThat(result).isFalse()
    }
}