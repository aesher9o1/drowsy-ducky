package com.howdy.buddy.vr.constants

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LoginUser(
    var isAuth: Boolean = false,
    var joinedIn: String = "",
    var userType: String = "Freebie"
)