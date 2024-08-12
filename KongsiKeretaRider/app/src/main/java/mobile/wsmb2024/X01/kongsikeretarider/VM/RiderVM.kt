package mobile.wsmb2024.X01.kongsikeretarider.VM

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import mobile.wsmb2024.X01.kongsikeretarider.Navigate
import okhttp3.internal.filterList
import java.util.UUID

class RiderVM:ViewModel() {
    private val auth = Firebase.auth
    private val dB = Firebase.firestore
    val userId = auth.currentUser?.uid

    var isLoading by mutableStateOf(false)
    var userData by mutableStateOf(User())

    //FOr Login
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var confirmPass by mutableStateOf("")

    //For Resister
    var ic by mutableStateOf("")
    var name by mutableStateOf("")
    var isMale by mutableStateOf(false)
    var phone by mutableStateOf("")
    var address by mutableStateOf("")
    var userImg by mutableStateOf("")

    var newName by mutableStateOf("")


    var rideList by mutableStateOf(ArrayList<Ride>())
    var cancelrideList by mutableStateOf(ArrayList<Ride>())
    var endedrideList by mutableStateOf(ArrayList<Ride>())


    var rideData by mutableStateOf(Ride())
    var driverData by mutableStateOf(User())
    var totalFare by mutableStateOf(0.0)
    var isShow by mutableStateOf(false)

    fun CreateAcc(ctx: android.content.Context, navController: NavController){
        if(email.isNotEmpty() && password.isNotEmpty()&&confirmPass.isNotEmpty()&&ic.isNotEmpty()
            &&name.isNotEmpty() && phone.isNotEmpty()) {
            if(password==confirmPass) {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    val user = auth.uid?.let { it1 ->
                        User(
                            it1,
                            email,
                            password,
                            ic,
                            name,
                            isMale,
                            userImg,
                            phone,
                            address,
                            Driver = false,
                        )
                    }
                    if (user != null) {
                        auth.uid?.let { it1 ->
                            dB.collection("User").document(it1).set(user).addOnSuccessListener {
                                Toast.makeText(ctx, "Sign up success!", Toast.LENGTH_SHORT).show()
                                isLoading = false
                                Logout(navController)
                            }.addOnFailureListener {
                                Toast.makeText(
                                    ctx,
                                    "Invalid email or email taken!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }else{
                isLoading = false

                Toast.makeText(ctx, "Both password must match!", Toast.LENGTH_SHORT).show()

            }
        }else{
            isLoading = false
            Toast.makeText(ctx, "Field cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }
    fun Login(ctx: android.content.Context, navController: NavController){
        if(email.isNotEmpty()&&password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                isLoading = false
                val userId = auth.currentUser?.uid
                userId?.let { it1 -> getUserData(it1, true) }
                if(!userData.Driver){
                    Toast.makeText(ctx, "Welcome!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Navigate.Home.name)
                }else{
                    Toast.makeText(ctx, "Driver cannot access this app.", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                }
            }.addOnFailureListener{
                isLoading = false
                Toast.makeText(ctx, "Invalid email or password. Please try again.", Toast.LENGTH_SHORT).show()

            }
        }else{
            isLoading = false
            Toast.makeText(ctx, "Field cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }
    fun Logout(navController: NavController){
        auth.signOut()
        navController.navigate(Navigate.Login.name)
    }
    fun UploadImg(uri: Uri, edit:Boolean = false, ctx: Context){
        val storage = Firebase.storage
        val storageRef = storage.reference
        val uid = UUID.randomUUID()
        val imgeRef = storageRef.child("pfp/$uid.jpg")
        imgeRef.putFile(uri).addOnSuccessListener {task ->
            task.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    userImg = uri.toString()
                if(edit)
                    updateWithImg(ctx)
            }
        }
    }
    fun getUserData(userId: String, current:Boolean){
        dB.collection("User").document(userId).get()
            .addOnSuccessListener { userDa ->
                if(userDa.exists()){
                    if(current)
                        userData = userDa.toObject<User>()!!
                    else
                        driverData = userDa.toObject<User>()!!
                }
            }
    }
    fun getDriverUserData(userId: String){
        dB.collection("User").document(userId).get()
            .addOnSuccessListener { userData ->
                if(userData.exists()){
                    driverData = userData.toObject<User>()!!
                }
            }
    }

    fun getRide(){
        var count = 0
        dB.collection("Ride").get()
            .addOnSuccessListener {documents ->
                for(document in documents){
                    rideList.add(document.toObject<Ride>())
                    rideList[count].rideID = document.id
                    count++
                }
            }
    }
    fun getRideById(rideId: String){
        dB.collection("Ride").document(rideId)
            .get()
            .addOnSuccessListener {ride ->
                rideData = ride.toObject<Ride>()!!
            }
    }
    fun getJoinedRidebyContain(userId: String) {
        var count = 0
        dB.collection("Ride").whereArrayContains("riderRef", userId)
            .get()
            .addOnSuccessListener {documents ->
                for(document in documents){
                    rideList.add(document.toObject<Ride>())
                    rideList[count].rideID = document.id
                    count++
                    if(document.toObject<Ride>().isEnded){

                    }
                }
            }
    }
    fun getEndedRide(list: ArrayList<Ride>){
        var count = 0
        list.forEach{ride ->
            if(ride.isEnded){
                endedrideList.add(ride)
                endedrideList[count].rideID = list[count].rideID
                count++
            }
        }
    }

    fun getCancelRidebyContain(userId: String) {
        var count = 0
        dB.collection("Ride").whereArrayContains("cancelledriderRef", userId)
            .get()
            .addOnSuccessListener {documents ->
                for(document in documents){
                    cancelrideList.add(document.toObject<Ride>())
                    cancelrideList[count].rideID = document.id
                    count++
                }
            }
    }


    fun joinRide(rideId: String, userId: String, ctx: Context){
        if(rideData.currentCap>=rideData.car.maxCap) {
                Toast.makeText(ctx, "Cannot join ride. Ride already full.", Toast.LENGTH_SHORT).show()

        }else{
            if (rideData.riderRef.contains(userId) != true) {
                if (rideData.cancelledriderRef.contains(userId)) {
                    rideData.cancelledriderRef.remove(userId)
                }
                rideData.currentCap+1
                val cap = rideData.currentCap+1
                rideData.riderRef.add(userId)
                val join = mapOf<String, Any>(
                    "riderRef" to rideData.riderRef,
                    "cancelledriderRef" to rideData.cancelledriderRef,
                    "currentCap" to cap
                )
                dB.collection("Ride").document(rideId).update(join).addOnSuccessListener {
                    Toast.makeText(ctx, "Joined Ride!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(ctx, "You already joined this ride.", Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun cancelRide(rideId: String, userId: String, ctx: Context){
        if(rideData.riderRef.contains(userId)) {
            rideData.riderRef.remove(userId)
            rideData.cancelledriderRef.add(userId)
            rideData.currentCap-1
            val cap = rideData.currentCap-1
            val cancel = mapOf<String, Any>(
                "riderRef" to rideData.riderRef,
                "cancelledriderRef" to rideData.cancelledriderRef,
                "currentCap" to cap
            )

            dB.collection("Ride").document(rideId).update(cancel).addOnSuccessListener {
                Toast.makeText(ctx, "Ride Cancelled!", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(ctx, "You have yet to join this ride, cannot cancel.", Toast.LENGTH_SHORT).show()
        }

    }

    fun calfare(){
        rideList.forEach{ ride ->
            totalFare += ride.fare
        }
    }
    fun update(id: String, ctx: Context){
        val updates = hashMapOf(
            "name" to newName
        )
        dB.collection("User").document(id).update(updates as Map<String, Any>).addOnSuccessListener {
            Toast.makeText(ctx, "Profile Updated!", Toast.LENGTH_SHORT).show()
        }
    }
    fun updateWithImg(ctx: Context){
        val updates = hashMapOf(
            "name" to newName,
            "userImg" to userImg
        )
        userId?.let {
            dB.collection("User").document(it).update(updates as Map<String, Any>).addOnSuccessListener {
                Toast.makeText(ctx, "Profile Updated!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}