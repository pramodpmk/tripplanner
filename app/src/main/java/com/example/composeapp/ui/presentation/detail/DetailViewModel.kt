package com.example.composeapp.ui.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.UiState
import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.data.login.ChatGptSettings
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.user.UserDetails
import com.example.composeapp.ui.presentation.detail.domain.LoadGptSettingsUseCase
import com.example.composeapp.ui.presentation.detail.domain.LoadSearchResultUseCase
import com.example.composeapp.ui.presentation.detail.domain.LoadTripDetailsUseCase
import com.example.composeapp.ui.presentation.detail.domain.SaveTripDetailsUseCase
import com.example.composeapp.ui.presentation.home.domain.UserDetailsUseCase
import com.example.composeapp.ui.presentation.profile.domain.UpdateUserDetailsUseCase
import com.example.composeapp.utils.AdmobUtils
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.LoggerUtils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val loadSearchResultUseCase: LoadSearchResultUseCase,
    val userDetailsUseCase: UserDetailsUseCase,
    val updateUserDetailsUseCase: UpdateUserDetailsUseCase,
    val chatGptSettingsUseCase: LoadGptSettingsUseCase,
    val saveTripDetailsUseCase: SaveTripDetailsUseCase,
    val loadTripDetailsUseCase: LoadTripDetailsUseCase,
    val admobUtils: AdmobUtils
) : ViewModel() {

    private val _searchState = MutableStateFlow<UiState<DetailModel>>(UiState.Loading)
    val searchState = _searchState.asStateFlow()
    private val _userDetailsFlow = MutableStateFlow<UiState<UserDetails>>(UiState.Idle)
    var userDetailsFlow = _userDetailsFlow.asStateFlow()
    private val _updateDetailsFlow = MutableStateFlow<UiState<String>>(UiState.Idle)
    var updateDetailsFlow = _updateDetailsFlow.asStateFlow()
    private var userDetails: UserDetails? = null
    private var chatGptSettings: ChatGptSettings? = null
    var searchKey = ""
    var searchType = ""
    var tripId = ""
    var sourcePage = ""

    init {
        loadUserDetails()
    }

    /**
     * Load chat gpt settings for start fetching result
     */
    fun prepareSearchResults() {
        viewModelScope.launch {
            _searchState.value = UiState.Loading
            chatGptSettingsUseCase().collectLatest { user ->
                when (user) {
                    is DataState.Success -> {
                        LoggerUtils.traceLog("loadChatGptSettings>>> success")
                        chatGptSettings = user.data
                        if (tripId != "" && tripId != "{tripId}") {
                            loadTripDetails()
                        } else {
                            loadSearchResult(user.data)
                        }
                    }

                    is DataState.Error -> {
                        LoggerUtils.traceLog("loadChatGptSettings>>> fail")
                        _searchState.value = UiState.Error(
                            1, "Failed to setup search prompt"
                        )
                    }
                }
            }
        }
    }

    private fun loadSearchResult(item: ChatGptSettings) {
        viewModelScope.launch {
            loadSearchResultUseCase(
                searchKey = searchKey,
                chatGptSettings = item
            ).distinctUntilChanged().collectLatest { result ->
                when (result) {
                    is DataState.Success -> {
                        val json = Gson().toJson(result.data)
                        LoggerUtils.traceLog("loadSearchResult>>>${json}")
                        _searchState.value = UiState.Success(result.data)
                        saveTripDetails(result.data)
                    }

                    is DataState.Error -> {
                        _searchState.value = UiState.Error(
                            errorType = result.errorCode ?: 1,
                            message = result.message ?: ""
                        )
                    }
                }
            }
        }
    }

    /**
     * Reload with a new trip plan
     */
    fun rePlanTrip() {
        tripId = ""
        sourcePage = AppConstants.SourcePage.SRC_SEARCH
        loadUserDetails()
    }

    private fun loadTripDetails() {
        viewModelScope.launch {
            loadTripDetailsUseCase(
                id = tripId
            ).distinctUntilChanged().collectLatest { result ->
                when (result) {
                    is DataState.Success -> {
                        val json = Gson().toJson(result.data)
                        LoggerUtils.traceLog("loadTripDetails>>>${json}")
                        _searchState.value = UiState.Success(result.data)
                    }

                    is DataState.Error -> {
                        _searchState.value = UiState.Error(
                            errorType = result.errorCode ?: 1,
                            message = result.message ?: ""
                        )
                    }
                }
            }
        }
    }

    /**
     * Save trip details to firebase
     */
    private fun saveTripDetails(detailModel: DetailModel) {
        viewModelScope.launch {
            saveTripDetailsUseCase(
                detailModel,
                searchType
            ).distinctUntilChanged().collectLatest {
                // Do nothing
            }
        }
    }

    /**
     * Load present user details
     */
    private fun loadUserDetails() {
        viewModelScope.launch {
            _userDetailsFlow.value = UiState.Loading
            userDetailsUseCase().collectLatest { user ->
                when (user) {
                    is DataState.Success -> {
                        LoggerUtils.traceLog(
                            "loadUserDetails>>> detailCount${user.data.detailPageViewCount}"
                        )
                        LoggerUtils.traceLog(
                            "loadUserDetails>>>aiDetailCount${user.data.aiDetailPageViewCount}"
                        )
                        userDetails = user.data
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
     * Update user details
     */
    fun updateUserDetails() {
        viewModelScope.launch {
            userDetails?.let { userItem ->
                LoggerUtils.traceLog("sourcePage -> ${sourcePage}")
                if (sourcePage == AppConstants.SourcePage.SRC_SEARCH) {
                    userItem.aiDetailPageViewCount = userItem.aiDetailPageViewCount + 1
                } else {
                    userItem.detailPageViewCount = userItem.detailPageViewCount + 1
                }
                updateUserDetailsUseCase(userItem).collectLatest {
                    when (it) {
                        is DataState.Success -> {
                            LoggerUtils.traceLog("updateUserDetails -> success")
                            _updateDetailsFlow.value = UiState.Success(
                                ""
                            )
                        }

                        is DataState.Error -> {
                            LoggerUtils.traceLog("updateUserDetails -> error")
                            _updateDetailsFlow.value = UiState.Error(
                                1,
                                it.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Check the eligibility to load details wihout advertisement
     */
    fun checkForAdd(data: UserDetails): Boolean {
        return if (
            sourcePage == AppConstants.SourcePage.SRC_SEARCH &&
            data.aiDetailPageViewCount > 3
        ) {
            true
        } else {
            data.detailPageViewCount > 5 && data.detailPageViewCount % 2 == 0
        }
    }

}