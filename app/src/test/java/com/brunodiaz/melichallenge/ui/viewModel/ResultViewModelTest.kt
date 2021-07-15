package com.brunodiaz.melichallenge.ui.viewModel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.brunodiaz.melichallenge.MainCoroutineRule
import com.brunodiaz.melichallenge.data.model.ItemsResponse
import com.brunodiaz.melichallenge.data.model.Page
import com.brunodiaz.melichallenge.data.repository.FakeResultsRepository
import com.brunodiaz.melichallenge.domain.*
import com.brunodiaz.melichallenge.getOrAwaitValueTest
import com.google.common.truth.ExpectFailure.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.brunodiaz.melichallenge.other.Event
import com.brunodiaz.melichallenge.other.Resource
import com.brunodiaz.melichallenge.other.Status
import junit.framework.TestCase.assertEquals
import retrofit2.http.GET

@ExperimentalCoroutinesApi
class ResultViewModelTest {

    private lateinit var viewModel: ResultsViewModel
    private lateinit var getItemResultsUseCase: GetItemListUseCaseImpl

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        getItemResultsUseCase = GetItemListUseCaseImpl(FakeResultsRepository())
        viewModel = ResultsViewModel(getItemResultsUseCase)
    }



    @Test
    fun insertResponse_return_success(){

        viewModel.getItemList("dummy")

        val value = viewModel.items.getOrAwaitValueTest()

        assertEquals(value.getContentIfNotHandled()?.status, Status.SUCCESS)
    }

}



