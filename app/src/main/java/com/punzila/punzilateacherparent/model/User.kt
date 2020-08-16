package com.punzila.punzilateacherparent.model

data class User(
    var userID: String = "",
    var profilePictureURL: String = "",
    var phoneNumber: String = "",
    var active: Boolean = false,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var userName: String = ""
)