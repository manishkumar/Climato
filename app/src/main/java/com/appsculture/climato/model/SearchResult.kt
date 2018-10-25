package com.appsculture.climato.model

import com.google.gson.annotations.Expose


data class SearchResult(
    @Expose
    var cod: String,
    @Expose
    var message: Float,
    @Expose
    var cnt: Int,
    @Expose
    var list: List<Forecast>,
    @Expose
    var city: City
)

data class City(
    @Expose
    var id: Int,
    @Expose
    var name: String,
    @Expose
    var country: String
)