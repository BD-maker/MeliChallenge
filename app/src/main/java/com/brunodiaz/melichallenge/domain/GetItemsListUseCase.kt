package com.brunodiaz.melichallenge.domain

import com.brunodiaz.melichallenge.data.model.ItemsResponse
import com.brunodiaz.melichallenge.data.repository.ResultsRepository
import com.brunodiaz.melichallenge.other.Resource
import javax.inject.Inject


/*
        Caso de uso para buscar productos por nombre
 */

interface GetItemsListUseCase {
    suspend fun getItems(name: String): Resource<ItemsResponse>
}

class GetItemListUseCaseImpl @Inject constructor(
    private val repository: ResultsRepository,
) : GetItemsListUseCase {

    override suspend fun getItems(name: String): Resource<ItemsResponse> {
        return repository.searchItems(name)
    }
}



