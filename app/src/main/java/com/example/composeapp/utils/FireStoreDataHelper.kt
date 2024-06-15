package com.example.composeapp.utils

import com.example.composeapp.data.detail.DetailDto
import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.data.home.trip.TripMapper
import com.example.composeapp.data.login.ChatGptSettings
import com.example.composeapp.data.login.LoginModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.user.UserDetails
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FireStoreDataHelper @Inject constructor() {

    companion object {
        const val TABLE_TRIPS = "table_trips"
        const val TABLE_USERS = "table_users"
        const val TABLE_CHATGPT = "chatgpt_config"
        const val ITEM = "table_entry"

        object Query {
            const val GET = "query_get"
            const val SAVE = "query_save"
            const val EDIT = "query_edit"
            const val DELETE = "query_delete"
            const val GET_BY_ID = "query_get_by_id"
        }

        object Field {
            const val TRIP_TYPE = "tripType"
        }
    }

    suspend fun saveTripDetails(item: DetailDto): Flow<DataState<Boolean>> {
        return callbackFlow {
            Firebase.firestore.collection(TABLE_TRIPS)
                .add(item)
                .addOnSuccessListener {
                    println("Success saving trip details")
                    trySend(
                        DataState.Success(true)
                    )
                }.addOnFailureListener {
                    println("Failure saving trip details")
                    DataState.Error(
                        false,
                        1,
                        null,
                        null,
                        "Trip details saving failed"
                    )
                }
            awaitClose {
                // Do nothing
            }
        }
    }

    suspend fun getTripDetails(id: String): Flow<DataState<DetailModel>> {
        return callbackFlow {
            LoggerUtils.traceLog("FireStoreDataHelper getTripDetails -> ${id}")
            Firebase.firestore.collection(TABLE_TRIPS)
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener {
                    LoggerUtils.traceLog("FireStoreDataHelper onComplete -> ${it.isSuccessful}")
                    LoggerUtils.traceLog("FireStoreDataHelper onComplete data -> ${it.exception}")
                    if (it.result.size() > 0) {
                        val doc = it.result.documents[0].toObject(DetailDto::class.java)
                        val item = DetailDto(
                            _id = id,
                            _title = doc?.title ?: "",
                            _location = doc?.location ?: "",
                            _dayDesc = doc?.dayDesc ?: "",
                            _description = doc?.description ?: "",
                            _tips = doc?.tips ?: arrayListOf(),
                            _days = doc?.days ?: arrayListOf(),
                            _images = doc?.images ?: arrayListOf(),
                            _tripType = doc?.tripType ?: ""
                        )
                        println("Success trip details")
                        LoggerUtils.traceLog("Data trip details -> ${doc?.title}")
                        LoggerUtils.traceLog("Data trip details -> ${doc?.location}")
                        trySend(DataState.Success(DetailDto.toModel(item)))
                    } else {
                        trySend(DataState.Error(
                            false,
                            1,
                            null,
                            null,
                            "Trip details retrieval failed"
                        ))
                    }
                }
            awaitClose {
                // Do nothing
            }
        }
    }

    suspend fun getTripItems(_tripType: Int): Flow<DataState<MutableList<TripItemModel>>> {
        return callbackFlow {
            val tripType = "{$_tripType}"
            Firebase.firestore.collection(TABLE_TRIPS)
                .limit(10)
                .whereEqualTo(Field.TRIP_TYPE, tripType)
                .get()
                .addOnSuccessListener {
                    println("Success getTripItems")
                    val resultList = arrayListOf<TripItemModel>()
                    it.documents.forEach { _doc ->
                        println("outside item = ${_doc}")
                        val doc = _doc.toObject(DetailDto::class.java)
                        val image = if (doc?.images.isNullOrEmpty()) {
                            ""
                        } else {
                            doc?.images?.get(0) ?: ""
                        }
                        val item = TripItemModel(
                            tripTitle = doc?.location ?: "",
                            description = doc?.title ?: "",
                            imageUrl = image,
                            numOfDays = doc?.days?.size ?: 0,
                            tripId = doc?.id ?: "",
                            tripCategory = doc?.tripType ?: ""
                        )
                        resultList.add(item)
                    }
                    trySend(DataState.Success(resultList))
                }.addOnFailureListener {
                    println("Failure getTripItems")
                    trySend(DataState.Error(
                        false, 1,null,
                        "", ""
                    ))
                }

            awaitClose {

            }
        }
    }

    suspend fun saveUserDetails(item: UserDetails): Flow<DataState<LoginModel>> {
        return callbackFlow {
            Firebase.firestore.collection(TABLE_USERS)
                .add(item)
                .addOnSuccessListener {
                    println("Success saving user details")
                    trySend(
                        DataState.Success(LoginModel(it.id))
                    )
                }.addOnFailureListener {
                    println("Failure saving user details")
                    DataState.Error(
                        false,
                        1,
                        null,
                        null,
                        "User details saving failed"
                    )
                }
            awaitClose {
                // Do nothing
            }
        }
    }

    suspend fun getUserDetails(id: String): Flow<DataState<UserDetails>> {
        return callbackFlow {
            LoggerUtils.traceLog("FireStoreDataHelper getUserDetails -> ${id}")
            Firebase.firestore.collection(TABLE_USERS)
                .whereEqualTo("authId", id)
                .get()
                .addOnCompleteListener {
                    LoggerUtils.traceLog("FireStoreDataHelper onComplete -> ${it.isSuccessful.toString()}")
                    LoggerUtils.traceLog("FireStoreDataHelper onComplete data -> ${it.exception}")
                    if (it.result.size() > 0) {
                        val doc = it.result.documents[0].toObject(UserDetails::class.java)
                        val item = UserDetails()
                        item.name = doc?.name ?: ""
                        item.email = doc?.email ?: ""
                        item.location = doc?.location ?: ""
                        item.userType = doc?.userType ?: UserDetails.USER_TYPE_FREE
                        item.detailPageViewCount = doc?.detailPageViewCount ?: 0
                        item.aiDetailPageViewCount = doc?.aiDetailPageViewCount ?: 0
                        println("Success user details")
                        LoggerUtils.traceLog("Data user details -> ${doc?.authId}")
                        LoggerUtils.traceLog("Data user details -> ${doc?.location}")
                        trySend(DataState.Success(item))
                    } else {
                        trySend(DataState.Error(
                            false,
                            1,
                            null,
                            null,
                            "User details retrieval failed"
                        ))
                    }
                }
            awaitClose {
                // Do nothing
            }
        }
    }

    /**
     * Update location in fire store for logged in user
     */
    suspend fun updateUserDetails(
        userId: String,
        item: UserDetails
    ): Flow<DataState<Boolean>> {
        return callbackFlow {
            LoggerUtils.traceLog(
                "updateUserDetails>>>${item.stringData()}"
            )
            val docId = getDocumentId(authId = userId) { docId ->
                Firebase.firestore.collection(TABLE_USERS)
                    .document(docId)
                    .update(
                        "name", item.name,
                        "email", item.email,
                        "location", item.location,
                        "userType", item.userType,
                        "detailPageViewCount", item.detailPageViewCount,
                        "aiDetailPageViewCount", item.aiDetailPageViewCount
                    )
                    .addOnSuccessListener {
                        println("Success saving user details")
                        trySend(
                            DataState.Success(data = true)
                        )
                    }.addOnFailureListener {
                        println("Failure saving user details")
                        DataState.Error(
                            false,
                            1,
                            null,
                            null,
                            "User details updation failed"
                        )
                    }
            }
            LoggerUtils.traceLog("getDocumentId returned value -> $docId")
            awaitClose {
                // Do nothing
            }
        }
    }

    private suspend fun getDocumentId(
        authId: String, callBack: (token: String) -> Unit
    ): String {
        return withContext(Dispatchers.IO) {
            var res = ""
            val deferred = async {
                Firebase.firestore.collection(TABLE_USERS)
                    .whereEqualTo("authId", authId)
                    .get()
                    .addOnCompleteListener {
                        if (
                            it.isSuccessful &&
                            it.result.documents.isNotEmpty()
                        ) {
                            val docId = it.result.documents[0].id
                            res = docId
                            callBack.invoke(docId)
                            LoggerUtils.traceLog("getDocumentId complete -> $res")
                        } else {
                            LoggerUtils.traceLog("getDocumentId complete -> fail")
                        }
                    }.await()
                res
            }
            deferred.await()
            res
        }
    }

    suspend fun getChatGptSettings(): Flow<DataState<ChatGptSettings>> {
        return callbackFlow {
            Firebase.firestore.collection(TABLE_CHATGPT)
                .get()
                .addOnCompleteListener {
                    if (
                        it.isSuccessful &&
                        it.result.documents.isNotEmpty()
                    ) {
                        val dat = it.result.documents[0]
                        val doc = dat.toObject(ChatGptSettings::class.java)
                        val res = ChatGptSettings(
                            doc?.api_key ?: "",
                            doc?.system_message ?: ""
                        )
                        res.token_limit = doc?.token_limit ?: ""
                        trySend(DataState.Success(res))
                        LoggerUtils.traceLog("getChatGptSettings complete -> $res")
                    } else {
                        LoggerUtils.traceLog("getChatGptSettings complete -> fail")
                        trySend(DataState.Error(
                            false, 1, null,
                            "", "Failed to load chat gpt system data"
                        ))
                    }
                }

            awaitClose {

            }
        }
    }

}
