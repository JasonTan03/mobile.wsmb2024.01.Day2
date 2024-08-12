package mobile.wsmb2024.X01.kongsikeretarider.VM

import java.lang.reflect.Array

data class User(
    val userID:String = "",
    val email: String = "",
    val password: String = "",
    val ic: String = "",
    val name: String = "",
    val isMale: Boolean = false,
    val userImg: String = "",
    val phone: String = "",
    val address: String = "",
    val Driver: Boolean = false,
    val joinedRide: ArrayList<String> = ArrayList(),
    val car: Car = Car(),
    var cancelledRide:  ArrayList<String> = ArrayList(),
)
