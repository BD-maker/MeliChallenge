package com.brunodiaz.melichallenge

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.brunodiaz.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

/*
        Funcion para pasar el fragment que queremos testear y adjuntarlo a nuestra Custom Main Activity

 */
@ExperimentalCoroutinesApi
inline fun <reified  T : Fragment > launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    crossinline action: Fragment.() -> Unit = {}
){
    // Creamos activity, cada app necesita su mainActivity y en ambiente de testing no existe por defecto
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId)

    ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity->

        // Creamos el fragment
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        // Le cargamos los argumentos
        fragment.arguments = fragmentArgs

        // Lanzamos el Fragment
        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        // Referencia al Fragment
        fragment.action()
    }

}
