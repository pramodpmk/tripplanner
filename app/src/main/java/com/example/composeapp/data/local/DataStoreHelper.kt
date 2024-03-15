package com.example.composeapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton
import androidx.datastore.preferences.core.Preferences

private var SETTINGS_NAME: String = "com.travel.tripplanner_preferences"
private var SECONDARY_NAME: String = "com.travel.tripplanner_preferences_secondary"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTINGS_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, SETTINGS_NAME))
    })

val Context.secondaryStore: DataStore<Preferences> by preferencesDataStore(
    name = SECONDARY_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, SECONDARY_NAME))
    })

@Singleton
class DataStoreHelper @Inject constructor(@ApplicationContext context: Context) {

    var mContext: Context = context

    private var token = ""
    private var refreshToken = ""


    private val KEY_TOKEN = stringPreferencesKey("chatgpt_token")
    private val KEY_SETTINGS_MESSAGE = stringPreferencesKey("chatgpt_settings_message")
    private val KEY_TOKEN_LIMIT = stringPreferencesKey("chatgpt_token_limit")
    private val KEY_VENDOR = stringPreferencesKey("key_vendor")
    private val KEY_UUID = stringPreferencesKey("key_uuid")
    private val KEY_DEVICE_SIGNATURE = stringPreferencesKey("key_device_signature")
    private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    private val LOCATION_ID = stringPreferencesKey("location_id")
    private val KEY_ACCOUNT_TYPE = stringPreferencesKey("account_type")
    private val KEY_LENS_LEAD_ID = stringPreferencesKey("lens_lead_id")

    /**
     * Get saved value of token from DataStore
     * @return String
     */
    suspend fun getSavedGptToken(): String {
        token = if (token == "") {
            val tokenFlow: Flow<String> = mContext.dataStore.data.map { preferences ->
                // No type safety.
                preferences[KEY_TOKEN] ?: ""
            }
            tokenFlow.first() ?: ""
        } else {
            token
        }
        return token
    }

    /**
     * Get saved value of settings message from DataStore
     * @return String
     */
    suspend fun getSavedSettingsMessage(): String {
        token = if (token == "") {
            val tokenFlow: Flow<String> = mContext.dataStore.data.map { preferences ->
                // No type safety.
                preferences[KEY_SETTINGS_MESSAGE] ?: ""
            }
            tokenFlow.first() ?: ""
        } else {
            token
        }
        return token
    }

    /**
     * Get saved value of refresh token from DataStore
     * @return String
     */
    suspend fun getRefreshToken(): String {
        refreshToken = if (refreshToken == "") {
            val tokenFlow: Flow<String> = mContext.dataStore.data.map { preferences ->
                // No type safety.
                preferences[KEY_REFRESH_TOKEN] ?: ""
            }
            tokenFlow.first() ?: ""
        } else {
            refreshToken
        }
        return refreshToken
    }

    /**
     * Save token in DataStore
     * @param text : String
     */
    suspend fun saveToken(text: String) {
        println("Saving token...")
        token = text
        mContext.dataStore.edit { settings ->
            settings[KEY_TOKEN] = text
        }
    }


    /**
     * Save refresh token in DataStore
     * @param text : String
     */
    suspend fun saveRefreshToken(text: String) {
        println("Saving Refresh token...")
        refreshToken = text
        mContext.dataStore.edit { settings ->
            settings[KEY_REFRESH_TOKEN] = text
        }
    }

    /**
     * Save account type of user
     */
    suspend fun saveAccountType(accountType:String){
        mContext.dataStore.edit { settings ->
            settings[KEY_ACCOUNT_TYPE] = accountType
        }
    }

    /**
     * Save lead id for resume registration
     */
    suspend fun saveLeadId(leadId: String) {
        mContext.secondaryStore.edit { settings ->
            settings[KEY_LENS_LEAD_ID] = leadId
        }
    }

    /**
     * Get lead id for resume registration
     */
    fun getLensLeadId(): String {
        var value = ""
        runBlocking {
            value = mContext.secondaryStore.data.map { preferences ->
                preferences[KEY_LENS_LEAD_ID]
            }.first() ?: value
        }
        return value
    }

    /**
     * Get saved value of account type from DataStore
     * @return String
     */
    suspend fun getAccountType(): String {
        val accountType: Flow<String> = mContext.dataStore.data.map { preferences ->
            // No type safety.
            preferences[KEY_ACCOUNT_TYPE] ?: ""
        }
        return accountType.first() ?: ""
    }

    /**
     * Get saved value of vendor from DataStore
     * @return String
     */
    suspend fun getSavedVendor(): String {
        val vendorFlow: Flow<String> = mContext.dataStore.data.map { preferences ->
            // No type safety.
            preferences[KEY_VENDOR] ?: ""
        }
        return vendorFlow.first() ?: ""
    }

    suspend fun storeString(tag: String, value: String) {
        mContext.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(tag)] = value
        }
    }

    suspend fun storeGptToken(value: String) {
        mContext.dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = value
        }
    }

    suspend fun storeGptSettingsMessage(value: String) {
        mContext.dataStore.edit { preferences ->
            preferences[KEY_SETTINGS_MESSAGE] = value
        }
    }

    suspend fun storeGptTokenLimit(value: String) {
        mContext.dataStore.edit { preferences ->
            preferences[KEY_TOKEN_LIMIT] = value
        }
    }

    suspend fun storeFloat(tag: String, value: Float) {
        mContext.dataStore.edit { preferences ->
            preferences[floatPreferencesKey(tag)] = value
        }
    }

    suspend fun storeLong(tag: String, value: Long) {
        mContext.dataStore.edit { preferences ->
            preferences[longPreferencesKey(tag)] = value
        }
    }

    suspend fun storeBoolean(tag: String, value: Boolean) {
        mContext.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(tag)] = value
        }
    }

    suspend fun storeInt(tag: String, value: Int) {
        mContext.dataStore.edit { preferences ->
            preferences[intPreferencesKey(tag)] = value
        }
    }

    fun getBoolean(tag: String): Flow<Boolean?> {
        return mContext.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(tag)]
        }
    }

    fun getStoredBoolean(tag: String): Boolean {
        return getStoredBoolean(tag, false)
    }

    fun getStoredLong(tag: String): Long {
        var value = 0L
        runBlocking {
            value = mContext.dataStore.data.map { preferences ->
                preferences[longPreferencesKey(tag)]
            }.first() ?: 0L
        }
        return value
    }

    fun getStoredBoolean(tag: String, default: Boolean): Boolean {
        var value = false
        runBlocking {
            value = mContext.dataStore.data.map { preferences ->
                preferences[booleanPreferencesKey(tag)]
            }.first() ?: default
        }

        return value
    }

    /**
     * Return long datastore field with reactive updates
     */
    fun getStoredLongFlow(tag: String): Flow<Long?> {
        return mContext.dataStore.data.map { preferences ->
            preferences[longPreferencesKey(tag)]
        }
    }

    fun getStoredString(tag: String): String {
        return getStoredString(tag, "")
    }

    fun getStoredGptToken(): String {
        var value = ""
        runBlocking {
            value = mContext.dataStore.data.map { preferences ->
                preferences[KEY_TOKEN]
            }.first() ?: ""
        }
        return value
    }

    fun getStoredGptSettingsMessage(): String {
        var value = ""
        runBlocking {
            value = mContext.dataStore.data.map { preferences ->
                preferences[KEY_SETTINGS_MESSAGE]
            }.first() ?: ""
        }
        return value
    }

    fun getStoredGptLimit(): String {
        var value = ""
        runBlocking {
            value = mContext.dataStore.data.map { preferences ->
                preferences[KEY_TOKEN_LIMIT]
            }.first() ?: ""
        }
        return value
    }

    fun getStoredString(tag: String, default: String): String {
        var value = ""
        runBlocking {
            value = mContext.dataStore.data.map { preferences ->
                preferences[stringPreferencesKey(tag)]
            }.first() ?: default
        }

        return value
    }

    fun getStoredFloat(tag: String): Float {
        return getStoredFloat(tag, 0f)
    }

    fun getStoredFloat(tag: String, default: Float): Float {
        var value = 0f
        runBlocking {
            value = mContext.dataStore.data.map { preferences ->
                preferences[floatPreferencesKey(tag)]
            }.first() ?: default
        }

        return value
    }

    fun getStoredInt(tag: String): Int {
        return getStoredInt(tag, -1)
    }

    fun getStoredInt(tag: String, default: Int): Int {
        var value = -1
        runBlocking {
            value = mContext.dataStore.data.map { preferences ->
                preferences[intPreferencesKey(tag)]
            }.first() ?: default
        }

        return value
    }

    /**
     * Save vendor in DataStore
     * @param text : String
     */
    suspend fun saveVendor(text: String) {
        mContext.dataStore.edit { settings ->
            settings[KEY_VENDOR] = text
        }
    }

    /**
     * Save UUID in DataStore
     * @param text : String
     */
    suspend fun saveUUID(text: String) {
        mContext.dataStore.edit { settings ->
            settings[KEY_UUID] = text
        }
    }

    /**
     * Get saved value of UUID from DataStore
     * @return String
     */
    suspend fun getUUID(): String {
        val uuidFlow: Flow<String> = mContext.dataStore.data.map { preferences ->
            // No type safety.
            preferences[KEY_UUID] ?: ""
        }
        return uuidFlow.first()
    }

    /**
     * Save Device Signature in DataStore
     * @param text : String
     */
    suspend fun saveDeviceSignature(text: String) {
        println("Saving DeviceSignature...")
        mContext.dataStore.edit { settings ->
            settings[KEY_DEVICE_SIGNATURE] = text
        }
    }

    /**
     * Get saved value of Device Signature from DataStore
     * @return String
     */
    suspend fun getDeviceSignature(): String {
        val uuidFlow: Flow<String> = mContext.dataStore.data.map { preferences ->
            // No type safety.
            preferences[KEY_DEVICE_SIGNATURE] ?: ""
        }
        return uuidFlow.first()
    }

    /**
     * Clear all user data
     * Collecting intro state just before clearing user data
     * Saving the same state to datastore.
     */
    suspend fun clearAll() {
        val introState = getStoredBoolean(IS_FIRST_TIME)
        token = ""
        refreshToken = ""
        mContext.dataStore.edit { settings ->
            settings.clear()
        }
        saveIntroState(IS_FIRST_TIME, introState)
    }

    suspend fun saveIntroState(tag: String, value: Boolean) {
        mContext.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(tag)] = value
        }
    }

    companion object {
        const val GENDER = "gender"
        const val LOCATION_ID = "location_id"
        const val COUNTRY_LIVING_IN = "country_living_in"
        const val COUNTRY_LIVING_IN_TEXT = "country_living_in_text"
        const val PROFILE_ID = "profile_id"
        const val USER_ID = "user_id"
        const val RECOMMENDATION_ENABLE = "recommendation_enable"
        const val CURRENT_PUSH_USER = "current_push_user"
        const val IS_FIRST_TIME = "first_time_install"
        const val FiRST_TIME_INSTRUCTIONAL_UI = "first_time_instructional_ui"

        //fcm
        const val GCM_REGISTER = "gcm_register"
        const val PUSH_NOTIFICATION = "push_notification"
        const val SENT_TOKEN_TO_SERVER = "sentTokenToServer"

        const val last_launch_date = "last_launch_date"
        const val LENS_LEAD_ID = "lens_lead_id"
        const val SUCCESS_STORY_DISMISS_TIME = "success_story_dismiss_time"
        const val SUCCESS_STORY_DISMISSED = "success_story_dismissed"
        const val SUCCESS_STORY_REMOVE = "success_story_remove"
        const val SUCCESS_STORY_LAST_UPDATED = "success_story_last_updated"
        const val SUCCESS_STORY_LOCAL_LIST = "success_story_local_list"
        const val COMPLETE_SECTION_HIDE_TIME = "complete_profile_hide_timestamp"
        // Renew
        const val LAST_LAUNCH_DATE = "last_launch_date"
        const val LAST_DISPLAY_DATE = "last_display_date"
        //Package Expire data
        const val ROYAL_PACKAGE_EXPIRY_DATE = "royal_package_expiry_date"
        //Event banner
        const val EVENT_BANNER_SEARCH_FROM_MY_ACCOUNT = "event_banner_search_from_my_account"
        const val ACCOUNT_TYPE = "account_type"
    }
}