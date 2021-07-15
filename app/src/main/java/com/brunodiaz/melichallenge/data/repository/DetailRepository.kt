package com.brunodiaz.melichallenge.data.repository

import com.brunodiaz.melichallenge.data.model.Description
import com.brunodiaz.melichallenge.data.model.Item
import com.brunodiaz.melichallenge.data.network.MeliAPI
import com.brunodiaz.melichallenge.other.Resource
import javax.inject.Inject


interface DetailRepository {
    suspend fun getItemDetail(id: String): Resource<Item>
    suspend fun getItemDescription(id: String): Resource<Description>
}

class DetailRepositoryImpl @Inject constructor(
    private val meliAPI: MeliAPI,
) : DetailRepository {

    // En este caso solo tendremos una sola fuente de datos, la llamada a la API
    override suspend fun getItemDetail(id: String): Resource<Item> {
        return try {
            val response = meliAPI.queryItemDetail(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error Desconocido", null)
            } else {
                Resource.error("Error Desconocido", null)
            }
        } catch (e: Exception) {
            Resource.error("No hay conexión a Internet", null)
        }
    }

    override suspend fun getItemDescription(id: String): Resource<Description> {
        return try {
            val response = meliAPI.queryItemDescription(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error Desconocido", null)
            } else {
                Resource.error("Error Desconocido", null)
            }
        } catch (e: Exception) {
            Resource.error("No hay conexión a Internet", null)
        }
    }
}