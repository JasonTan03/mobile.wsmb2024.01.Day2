package mobile.wsmb2024.X01.kongsikeretarider.VM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class SelectVM:ViewModel() {
    private val dB = Firebase.firestore
    var rideID by mutableStateOf("")



//    fun getDriverUserData(userId: String){
//        dB.collection("Ride").document(userId)
//            .get()
//            .addOnSuccessListener {ride ->
//                driverData = ride.toObject<User>()!!
//            }
//    }

}