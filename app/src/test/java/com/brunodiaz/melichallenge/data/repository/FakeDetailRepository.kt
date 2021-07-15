package com.brunodiaz.melichallenge.data.repository

import com.brunodiaz.melichallenge.data.model.*
import com.brunodiaz.melichallenge.other.Resource

// para probar viewmodel

class FakeDetailRepository : DetailRepository {

    private var shouldReturnNetworkError = false
    private var shouldReturnemptyList = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }


    override suspend fun getItemDetail(id: String): Resource<Item> {
        return if( shouldReturnNetworkError ) {
            Resource.error("Error", null)
        }else{
            Resource.success(getFakeItem())
        }
    }

    override suspend fun getItemDescription(id: String): Resource<Description> {
        return if( shouldReturnNetworkError ) {
            Resource.error("Error", null)
        }else{
            Resource.success(getFakeDescription())
        }
    }


}