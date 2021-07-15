package com.brunodiaz.melichallenge.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario.launch
import com.brunodiaz.melichallenge.MainCoroutineRule
import com.brunodiaz.melichallenge.data.repository.FakeDetailRepository
import com.brunodiaz.melichallenge.data.repository.FakeResultsRepository
import com.brunodiaz.melichallenge.getOrAwaitValueTest
import com.brunodiaz.melichallenge.other.Status
import com.brunodiaz.melichallenge.ui.viewModel.ResultsViewModel
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetItemDetailUseCaseTest {


    private lateinit var  repository: FakeDetailRepository
    private lateinit var  getDetailItemsUseCase: GetItemDetailUseCaseImpl

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        repository = FakeDetailRepository()
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun search_return_success(){
        runBlockingTest {
            getDetailItemsUseCase = GetItemDetailUseCaseImpl(repository)
            val response = getDetailItemsUseCase.getItemData("dummy")
            
            assertEquals(response.status, Status.SUCCESS)
        }
    }

    @Test
    fun search_return_error(){
        runBlockingTest {
            repository.setShouldReturnNetworkError(true)
            getDetailItemsUseCase = GetItemDetailUseCaseImpl(repository)
            val response = getDetailItemsUseCase.getItemData("dummy")

            assertEquals(response.status, Status.ERROR)
        }
    }
}