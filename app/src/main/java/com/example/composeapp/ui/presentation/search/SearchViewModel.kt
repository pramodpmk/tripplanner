package com.example.composeapp.ui.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.search.SearchRepository
import com.example.composeapp.data.search.SearchedTripType
import com.example.composeapp.data.search.SelectionChipModel
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.AppUtils
import com.example.composeapp.utils.FireStoreDataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val repository: SearchRepository
) : ViewModel() {

    private val _searchListFlow = MutableStateFlow<MutableList<SelectionChipModel>>(arrayListOf())
    val searchListFlow = _searchListFlow.asStateFlow()

    /**
     * Load Spiritual travel packages list
     */
    fun provideTripTypeList() {
        viewModelScope.launch {
            val resultTripType = tripTypeList()
            _searchListFlow.value = resultTripType
        }
    }

    /**
     * Return the list of search type in result
     */
    private fun tripTypeList() = mutableListOf(
        SelectionChipModel(
            title = AppConstants.TripDescription.SOLO,
            id = AppConstants.TripType.SOLO.toString()
        ),
        SelectionChipModel(
            title = AppConstants.TripDescription.HONEY_MOON,
            id = AppConstants.TripType.HONEY_MOON.toString()
        ),
        SelectionChipModel(
            title = AppConstants.TripDescription.GROUP,
            id = AppConstants.TripType.GROUP.toString()
        ),
        SelectionChipModel(
            title = AppConstants.TripDescription.SPIRITUAL,
            id = AppConstants.TripType.SPIRITUAL.toString()
        ),
        SelectionChipModel(
            title = AppConstants.TripDescription.ROAD_TRIP,
            id = AppConstants.TripType.ROAD_TRIP.toString()
        ),
        SelectionChipModel(
            title = AppConstants.TripDescription.BIKE_TRIP,
            id = AppConstants.TripType.BIKE_TRIP.toString()
        )
    )
}