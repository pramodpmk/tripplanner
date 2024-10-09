package com.example.composeapp.ui.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.UiState
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.search.SearchRepository
import com.example.composeapp.data.search.SearchedTripType
import com.example.composeapp.data.search.SelectionChipModel
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.AppUtils
import com.example.composeapp.utils.FireStoreDataHelper
import com.example.composeapp.utils.LoggerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val repository: SearchRepository
) : ViewModel() {

    private val _searchListFlow = MutableStateFlow<MutableList<SelectionChipModel>>(arrayListOf())
    val searchListFlow = _searchListFlow.asStateFlow()
    private val _numOfDaysFlow = MutableStateFlow<List<SelectionChipModel>>(listOf())
    val numOfDaysFlow = _numOfDaysFlow.asStateFlow()
    private val _searchQuery = MutableSharedFlow<UiState<String>>()
    val searchQuery = _searchQuery.asSharedFlow()

    /**
     * Load Spiritual travel packages list
     */
    fun provideTripTypeList() {
        viewModelScope.launch {
            launch {
                val resultTripType = tripTypeList()
                _searchListFlow.value = resultTripType
            }
            launch {
                val resultTripType = numberOfDaysList()
                _numOfDaysFlow.value = resultTripType
            }
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

    /**
     * Return the list of search type in result
     */
    private fun numberOfDaysList() = listOf(
        SelectionChipModel("1 Day"),
        SelectionChipModel("2 Day 3 Night"),
        SelectionChipModel("3 Day 4 Night"),
        SelectionChipModel("5 Day 6 Night"),
        SelectionChipModel("10 Day 11 Night"),
    )

    fun planTrip(
        numDays: String,
        place: String,
        tripType: String
    ) {
        LoggerUtils.traceLog("planTrip Entry ")
        LoggerUtils.traceLog("numDays:$numDays place:$place tripType:$tripType")
        viewModelScope.launch {
            _searchQuery.emit(UiState.Loading)
            val state = if (numDays.trim().isEmpty()) {
                UiState.Error(1, "Select number of days")
            } else if (tripType.trim().isEmpty()) {
                UiState.Error(1, "Select type of trip")
            } else if (place.trim().isEmpty()) {
                UiState.Error(1, "Enter place for trip")
            } else {
                tripType.toIntOrNull()?.let {
                    if (AppUtils.tripTypeDescription(it, place, numDays).isNotEmpty()) {
                        UiState.Success(
                            AppUtils.tripTypeDescription(it, place, numDays)
                        )
                    } else {
                        UiState.Error(1, "Some error occurred")
                    }
                } ?: kotlin.run {
                    UiState.Error(1, "Some error occurred")
                }
            }
            _searchQuery.emit(state)
        }
    }
}
