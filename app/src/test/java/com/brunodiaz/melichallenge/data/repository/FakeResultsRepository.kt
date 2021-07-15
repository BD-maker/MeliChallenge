package com.brunodiaz.melichallenge.data.repository

import com.brunodiaz.melichallenge.data.model.Item
import com.brunodiaz.melichallenge.data.model.ItemsResponse
import com.brunodiaz.melichallenge.data.model.Page
import com.brunodiaz.melichallenge.data.model.getFakeItemList
import com.brunodiaz.melichallenge.other.Resource

// para probar viewmodel

class FakeResultsRepository : ResultsRepository {

    private var shouldReturnNetworkError = false
    private var shouldReturnemptyList = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    fun setShouldReturnEmptyList(value: Boolean){
        shouldReturnemptyList = value
    }

    override suspend fun searchItems(name: String): Resource<ItemsResponse> {
        return if( shouldReturnNetworkError ) {
            Resource.error("Error", null)
        }else if( shouldReturnemptyList){
            Resource.empty("",ItemsResponse("", Page(0,0), listOf()))
        }else{
            Resource.success(getFakeItemList())
        }
    }
}