package com.brunodiaz.melichallenge.ui.view

import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brunodiaz.R
import com.brunodiaz.melichallenge.ui.tools.SearchViewActionExtension
import com.google.common.truth.Truth.assertThat
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith



class SearchFragmentTest {

    @Test
    fun testSearchFragmentSlogan() {
        // The "fragmentArgs" and "factory" arguments are optional.
        val fragmentArgs = Bundle().apply {
            putInt("selectedListItem", 0)
        }
        val scenario = launchFragmentInContainer<SearchFragment>(
            fragmentArgs, R.style.Theme_AppCompat_DayNight_NoActionBar)
        onView(withId(R.id.tvSlogan)).check(matches(withText("Realiza una busqueda, ¡lo que querés te esta esperando!")))
    }

    @Test
    fun testNavigationToResultsScreen() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val fragmentArgs = Bundle().apply {
            putInt("selectedListItem", 0)
        }
        val titleScenario = launchFragmentInContainer<SearchFragment>(fragmentArgs, R.style.Theme_AppCompat_DayNight_NoActionBar)

        titleScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.navigation_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        // Verify that performing a click changes the NavController’s state
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.svFind))))
            .perform(typeText("search")).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        assertThat(navController.currentDestination).isEqualTo(R.id.resultsFragment)
    }
}

