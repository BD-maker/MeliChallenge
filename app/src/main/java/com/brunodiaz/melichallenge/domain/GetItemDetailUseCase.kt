package com.brunodiaz.melichallenge.domain

import com.brunodiaz.melichallenge.data.model.Item
import com.brunodiaz.melichallenge.data.model.ItemInfo
import com.brunodiaz.melichallenge.data.repository.DetailRepository
import com.brunodiaz.melichallenge.data.repository.ResultsRepository
import com.brunodiaz.melichallenge.other.Resource
import com.brunodiaz.melichallenge.other.Status
import javax.inject.Inject


/*
        Caso de uso para traer detalles y descripcion de un producto
 */

interface GetItemDetailUseCase{
    suspend fun getItemData(id:String) : Resource<ItemInfo>
}

class GetItemDetailUseCaseImpl @Inject constructor(
    private val repository: DetailRepository,
): GetItemDetailUseCase{

    override suspend fun getItemData(id: String): Resource<ItemInfo> {
        val itemDetail = repository.getItemDetail(id)
        val itemDescription = repository.getItemDescription(id)
        if( itemDetail.status == Status.SUCCESS && itemDescription.status == Status.SUCCESS)
            return Resource(Status.SUCCESS, ItemInfo(itemDetail.data!! , itemDescription.data!!), null)
        else
            return Resource(Status.ERROR,null, null)
    }
}
