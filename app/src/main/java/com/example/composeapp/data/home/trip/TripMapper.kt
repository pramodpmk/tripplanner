package com.example.composeapp.data.home.trip

import com.example.composeapp.data.home.TripItemModel

object TripMapper {

    /**
     * Mapper function to map TripItemModel from fire store data
     */
    fun fromDb(
        data: MutableMap<String, Any>?
    ): TripItemModel? {
        println("Input data -> ${data.toString()}")
        return data?.let { item ->
            val days = try {
                val num = item["numOfDays"] as Long
                num.toInt()
            } catch (exception: NumberFormatException) {
                exception.printStackTrace()
                0
            } catch (exception: ClassCastException) {
                exception.printStackTrace()
                0
            }
            TripItemModel(
                tripTitle = item["tripTitle"]?.toString() ?: "",
                description = item["description"]?.toString() ?: "",
                imageUrl = item["imageUrl"]?.toString() ?: "",
                numOfDays = days
            )
        } ?: kotlin.run {
            null
        }
    }
}