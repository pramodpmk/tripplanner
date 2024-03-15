package com.example.composeapp.utils

import android.util.Patterns
import com.example.composeapp.data.home.TripItemModel
import java.util.Collections

object AppUtils {

    /**
     * Return the string text for trip types
     */
    fun tripTypeDescription(
        type: Int,
        text: String
    ) = when(type) {
        AppConstants.TripType.SPIRITUAL -> {
            AppConstants.TripDescription.SPIRITUAL + " to " + text
        }
        AppConstants.TripType.SOLO -> {
            AppConstants.TripDescription.SOLO + " to " + text
        }
        AppConstants.TripType.HONEY_MOON -> {
            AppConstants.TripDescription.HONEY_MOON + " to " + text
        }
        AppConstants.TripType.GROUP -> {
            AppConstants.TripDescription.GROUP + " to " + text
        }
        AppConstants.TripType.ROAD_TRIP -> {
            AppConstants.TripDescription.ROAD_TRIP + " to " + text
        }
        AppConstants.TripType.BIKE_TRIP -> {
            AppConstants.TripDescription.BIKE_TRIP + " to " + text
        }
        else -> {
            ""
        }
    }

    /**
     * Get the list for multi row widget
     */
    fun multiRowList(
        itemList: MutableList<TripItemModel>
    ): MutableList<MutableList<TripItemModel>> {
        val list: MutableList<MutableList<TripItemModel>> = mutableListOf()
        val tempList: ArrayList<TripItemModel> = arrayListOf()
        itemList.forEachIndexed { index, item ->
            tempList.add(item)
            if (tempList.size == 2 || index == itemList.size - 1) {
                val dummyList = mutableListOf<TripItemModel>()
                dummyList.addAll(tempList)
                list.add(dummyList)
                tempList.clear()
            }
        }
        return list
    }

    fun String.isValidEmail(): Boolean {
        val emailRegex = Patterns.EMAIL_ADDRESS.toRegex()
        return emailRegex.matches(this)
    }

}