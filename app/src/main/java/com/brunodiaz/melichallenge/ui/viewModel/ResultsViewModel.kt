package com.brunodiaz.melichallenge.ui.viewModel

import android.os.Bundle
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.brunodiaz.melichallenge.data.model.ItemInfo
import com.brunodiaz.melichallenge.data.model.ItemsResponse
import com.brunodiaz.melichallenge.data.repository.ResultsRepository
import com.brunodiaz.melichallenge.domain.GetItemDetailUseCase
import com.brunodiaz.melichallenge.domain.GetItemsListUseCase
import com.brunodiaz.melichallenge.other.Event
import com.brunodiaz.melichallenge.other.Resource
import com.brunodiaz.melichallenge.other.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ResultsViewModel @ViewModelInject constructor(
    private val getItemsListUseCase: GetItemsListUseCase,
) : ViewModel() {

    // Lista de items
    private val _items = MutableLiveData<Event<Resource<ItemsResponse>>>()
    val items: LiveData<Event<Resource<ItemsResponse>>> = _items

    fun getItemList(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var response = getItemsListUseCase.getItems(name)
            if (response.data?.paging?.total == 0) {
                _items.postValue(Event(Resource(Status.EMPTY, null, null)))
            } else {
                _items.postValue(Event(response))
            }
        }
    }

}

