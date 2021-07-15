package com.brunodiaz.melichallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.brunodiaz.melichallenge.ui.view.DetailFragment
import com.brunodiaz.melichallenge.ui.view.ResultsFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        hiltRule.inject()

    }

    @After
    fun teardown() {

    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ResultsFragment> {

        }
    }
}