package com.brunodiaz.melichallenge.data.network

import com.brunodiaz.melichallenge.data.model.Description
import com.brunodiaz.melichallenge.data.model.Item
import com.brunodiaz.melichallenge.data.model.ItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliAPI {
    @GET("sites/MLA/search?")
    suspend fun queryItemsByName(@Query("q") name: String): Response<ItemsResponse>

    @GET("items/{id}")
    suspend fun queryItemDetail(@Path("id") itemId: String): Response<Item>

    @GET("items/{id}/description")
    suspend fun queryItemDescription(@Path("id") itemId: String): Response<Description>
}