package com.example.composeapp.ui.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.search.SearchRepository
import com.example.composeapp.data.search.SearchedTripType
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

    val dataHelper: FireStoreDataHelper = FireStoreDataHelper()

    private val spiritual = mutableListOf<TripItemModel>()
    private val _searchListFlow = MutableStateFlow<MutableList<SearchedTripType>>(arrayListOf())
    val searchListFlow = _searchListFlow.asStateFlow()

    /**
     * Load Spiritual travel packages list
     */
    fun searchAction(text: String) {
        viewModelScope.launch {
            val resultTripType = tripTypeList(text)
            _searchListFlow.value = resultTripType


//            dataHelper.getTripItems()


//            val searchQuery = text // TODO : Build query string from input text
//            val resp = repository.search(searchQuery)
//            when(resp) {
//                is DataState.Success -> {
//                    // Api success
//                    println("Search success : >>> ${resp.data}")
//                }
//                is DataState.Error -> {
//                    // Api failure
//                }
//            }
        }
    }

    /**
     * Return the list of search type in result
     */
    private fun tripTypeList(text: String) = mutableListOf(
        SearchedTripType(
            title = AppUtils.tripTypeDescription(
                AppConstants.TripType.SOLO,
                text
            ),
            tripType = AppConstants.TripType.SOLO
        ),
        SearchedTripType(
            title = AppUtils.tripTypeDescription(
                AppConstants.TripType.HONEY_MOON,
                text
            ),
            tripType = AppConstants.TripType.HONEY_MOON
        ),
        SearchedTripType(
            title = AppUtils.tripTypeDescription(
                AppConstants.TripType.GROUP,
                text
            ),
            tripType = AppConstants.TripType.GROUP
        ),
        SearchedTripType(
            title = AppUtils.tripTypeDescription(
                AppConstants.TripType.SPIRITUAL,
                text
            ),
            tripType = AppConstants.TripType.SPIRITUAL
        ),
        SearchedTripType(
            title = AppUtils.tripTypeDescription(
                AppConstants.TripType.ROAD_TRIP,
                text
            ),
            tripType = AppConstants.TripType.ROAD_TRIP
        ),
        SearchedTripType(
            title = AppUtils.tripTypeDescription(
                AppConstants.TripType.BIKE_TRIP,
                text
            ),
            tripType = AppConstants.TripType.BIKE_TRIP
        )
    )
}