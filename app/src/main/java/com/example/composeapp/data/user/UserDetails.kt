package com.example.composeapp.data.user

class UserDetails() {
    lateinit var name: String
    lateinit var email: String
    lateinit var location: String
    lateinit var authId: String
    var userType: String = USER_TYPE_FREE
    var detailPageViewCount: Int = 0
    var aiDetailPageViewCount: Int = 0

    fun stringData() = "name = ${name} " +
            "email = $email " +
            "location = $location " +
            "userType = $userType " +
            "detailPageViewCount = $detailPageViewCount " +
            "aiDetailPageViewCount = $aiDetailPageViewCount"

    companion object {
        const val USER_TYPE_FREE = "FREE USER"
        const val USER_TYPE_MONTHLY = "MONTHLY USER"
        const val USER_TYPE_YEARLY = "YEARLY USER"
        const val USER_TYPE_CUSTOM = "CUSTOM USER"
    }
}
