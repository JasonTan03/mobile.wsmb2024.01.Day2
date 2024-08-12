package mobile.wsmb2024.X01.kongsikeretarider.VM

data class Ride(
    val driverRef: String ="",
    val car: Car = Car(),
    val riderRef: ArrayList<String> = ArrayList(),
    val cancelledriderRef: ArrayList<String> = ArrayList(),
    val destination: String ="",
    val origin: String ="",
    val date: String ="",
    val time: String ="",
    val fare: Double = 0.0,
    var currentCap: Int = 0,
    var rideID: String = "",
    val isEnded:Boolean = false,
)
