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


class DetailViewModel @ViewModelInject constructor(
    private val getItemDetailUseCase: GetItemDetailUseCase,
) : ViewModel() {

    // Descripcion del item
    private val _itemDescription = MutableLiveData<Event<Resource<ItemInfo>>>()
    val itemDescription: LiveData<Event<Resource<ItemInfo>>> = _itemDescription


    fun getItemData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getItemDetailUseCase.getItemData(id)
            _itemDescription.postValue(Event(response))
        }
    }
}

