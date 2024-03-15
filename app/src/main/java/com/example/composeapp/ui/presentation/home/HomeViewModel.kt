package com.example.composeapp.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.UiState
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.search.SearchRepository
import com.example.composeapp.data.user.UserDetails
import com.example.composeapp.ui.presentation.home.domain.LIST_TYPE_BIKE_TRIP
import com.example.composeapp.ui.presentation.home.domain.LIST_TYPE_GROUP
import com.example.composeapp.ui.presentation.home.domain.LIST_TYPE_HONEYMOON
import com.example.composeapp.ui.presentation.home.domain.LIST_TYPE_ROAD_TRIP
import com.example.composeapp.ui.presentation.home.domain.LIST_TYPE_SOLO
import com.example.composeapp.ui.presentation.home.domain.LIST_TYPE_SPIRITUAL
import com.example.composeapp.ui.presentation.home.domain.LoadHomeTripListUseCase
import com.example.composeapp.ui.presentation.home.domain.UserDetailsUseCase
import com.example.composeapp.utils.AdmobUtils
import com.example.composeapp.utils.LoggerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val tripListUseCase: LoadHomeTripListUseCase,
    val userDetailsUseCase: UserDetailsUseCase,
    val admobUtils: AdmobUtils
) : ViewModel() {

    private val _spiritualFlow = MutableStateFlow<MutableList<TripItemModel>>(arrayListOf())
    val spiritualFlow = _spiritualFlow.asStateFlow()
    private val _soloFlow = MutableStateFlow<MutableList<TripItemModel>>(arrayListOf())
    val soloFlow = _soloFlow.asStateFlow()
    private val _honeyMoonFlow = MutableStateFlow<MutableList<TripItemModel>>(arrayListOf())
    val honeyMoonFlow = _honeyMoonFlow.asStateFlow()
    private val _groupTravelFlow = MutableStateFlow<MutableList<TripItemModel>>(arrayListOf())
    val groupTravelFlow = _groupTravelFlow.asStateFlow()
    private val _roadTripFlow = MutableStateFlow<MutableList<TripItemModel>>(arrayListOf())
    val roadTripFlow = _roadTripFlow.asStateFlow()
    private val _bikeTripFlow = MutableStateFlow<MutableList<TripItemModel>>(arrayListOf())
    val bikeTripFlow = _bikeTripFlow.asStateFlow()
    private val _userDetailsFlow = MutableStateFlow<UiState<UserDetails>>(UiState.Idle)
    var userDetailsFlow = _userDetailsFlow.asStateFlow()

    init {
        loadSpiritualList()
        loadSoloTravelList()
        loadGroupTravelList()
        loadHoneymoonTravelList()
        loadRoadTripList()
        loadBikeTripList()
    }

    /**
     * Load present user details
     */
    fun loadUserDetails() {
        viewModelScope.launch {
            _userDetailsFlow.value = UiState.Loading
            userDetailsUseCase().collectLatest { user ->
                when(user) {
                    is DataState.Success -> {
                        LoggerUtils.traceLog("loadUserDetails>>> location${user.data.location}")
                        _userDetailsFlow.value = UiState.Success(user.data)
                    }
                    is DataState.Error -> {
                        _userDetailsFlow.value = UiState.Error(
                            1,
                            user.message ?: ""
                        )
                    }
                }
            }
        }
    }

    /**
     * Load Spiritual travel packages list
     */
    private fun loadSpiritualList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = LIST_TYPE_SPIRITUAL
            ).collectLatest { list ->
                _spiritualFlow.value = list
            }
        }
    }

    /**
     * Load Solo travel packages list
     */
    private fun loadSoloTravelList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = LIST_TYPE_SOLO
            ).collectLatest { list ->
                _soloFlow.value = list
            }
        }
    }

    /**
     * Load Solo travel packages list
     */
    private fun loadHoneymoonTravelList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = LIST_TYPE_HONEYMOON
            ).collectLatest { list ->
                _honeyMoonFlow.value = list
            }
        }
    }

    /**
     * Load Solo travel packages list
     */
    private fun loadGroupTravelList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = LIST_TYPE_GROUP
            ).collectLatest { list ->
                _groupTravelFlow.value = list
            }
        }
    }

    /**
     * Load road trip travel packages list
     */
    private fun loadRoadTripList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = LIST_TYPE_ROAD_TRIP
            ).collectLatest { list ->
                _roadTripFlow.value = list
            }
        }
    }

    /**
     * Load Solo travel packages list
     */
    private fun loadBikeTripList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = LIST_TYPE_BIKE_TRIP
            ).collectLatest { list ->
                _bikeTripFlow.value = list
            }
        }
    }
}