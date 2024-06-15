package com.example.composeapp.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.UiState
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.user.UserDetails
import com.example.composeapp.ui.presentation.home.domain.LoadHomeTripListUseCase
import com.example.composeapp.ui.presentation.home.domain.UserDetailsUseCase
import com.example.composeapp.utils.AdmobUtils
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.LoggerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
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

    }

    fun initCalls() {
        //loadSpiritualList()
        loadHoneymoonTravelList()
        loadSoloTravelList()
        loadGroupTravelList()
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
                typeOfList = AppConstants.TripType.SPIRITUAL
            ).collectLatest { result ->
                when(result) {
                    is DataState.Success -> {
                        _spiritualFlow.value = result.data
                    }
                    is DataState.Error -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    /**
     * Load Solo travel packages list
     */
    private fun loadSoloTravelList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = AppConstants.TripType.SOLO
            ).collectLatest { result ->
                when(result) {
                    is DataState.Success -> {
                        _soloFlow.value = result.data
                    }
                    is DataState.Error -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    /**
     * Load Solo travel packages list
     */
    private fun loadHoneymoonTravelList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = AppConstants.TripType.HONEY_MOON
            ).collectLatest { result ->
                when(result) {
                    is DataState.Success -> {
                        _honeyMoonFlow.value = result.data
                    }
                    is DataState.Error -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    /**
     * Load Solo travel packages list
     */
    private fun loadGroupTravelList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = AppConstants.TripType.GROUP
            ).collectLatest { result ->
                when(result) {
                    is DataState.Success -> {
                        _groupTravelFlow.value = result.data
                    }
                    is DataState.Error -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    /**
     * Load road trip travel packages list
     */
    private fun loadRoadTripList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = AppConstants.TripType.ROAD_TRIP
            ).collectLatest { result ->
                when(result) {
                    is DataState.Success -> {
                        _roadTripFlow.value = result.data
                    }
                    is DataState.Error -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    /**
     * Load Solo travel packages list
     */
    private fun loadBikeTripList() {
        viewModelScope.launch {
            tripListUseCase(
                typeOfList = AppConstants.TripType.BIKE_TRIP
            ).collectLatest { result ->
                when(result) {
                    is DataState.Success -> {
                        _bikeTripFlow.value = result.data
                    }
                    is DataState.Error -> {
                        // Do nothing
                    }
                }
            }
        }
    }
}