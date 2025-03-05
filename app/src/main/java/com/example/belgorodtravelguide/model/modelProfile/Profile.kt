package com.example.belgorodtravelguide.model.modelProfile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    var profileId: Long = 0L,

    @ColumnInfo(name = "name_user")
    var nameUser: String = "",

    @ColumnInfo(name ="money")
    var money: Int,

    @ColumnInfo(name = "about_me")
    var aboutMe: String = "",

    @ColumnInfo(name = "birthday")
    var birthday: String = "",

    @ColumnInfo(name = "city")
    var city: String = "",

    @ColumnInfo(name = "status")
    var status: String = "",

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "social_network")
    var socialNetwork: String = "",

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String = "",

    @ColumnInfo(name = "rating")
    var rating: Float = 2f
)