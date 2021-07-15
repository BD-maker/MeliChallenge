package com.brunodiaz.melichallenge.data.repository

import com.brunodiaz.melichallenge.data.model.ItemsResponse
import com.brunodiaz.melichallenge.data.network.MeliAPI
import com.brunodiaz.melichallenge.other.Resource
import javax.inject.Inject


interface ResultsRepository{
    suspend fun searchItems(name:String) : Resource<ItemsResponse>
}


// En este caso solo tendremos una sola fuente de datos, la llamada a la API

class ResultsRepositoryImpl @Inject constructor(
    private val meliAPI: MeliAPI
):ResultsRepository{

    override suspend fun searchItems(name:String): Resource<ItemsResponse> {
        return try{
            val response = meliAPI.queryItemsByName(name)
            if( response.isSuccessful ){
                response.body()?.let{
                    return@let Resource.success(it)
                } ?: Resource.error("Error Desconocido", null)
            }else{
                Resource.error("Error Desconocido", null)
            }
        }catch (e : Exception){
            Resource.error("No hay conexión a Internet", null)
        }
    }
}