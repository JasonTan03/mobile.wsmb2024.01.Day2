package mobile.wsmb2024.X01.kongsikeretarider.Screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import mobile.wsmb2024.X01.kongsikeretarider.Navigate
import mobile.wsmb2024.X01.kongsikeretarider.VM.RiderVM
import mobile.wsmb2024.X01.kongsikeretarider.VM.SelectVM

@Composable
fun RideDetail(navController: NavController, detail: RiderVM = viewModel(), selectVM: SelectVM) {
    val ctx = LocalContext.current
    val auth = Firebase.auth
    val userID = auth.currentUser?.uid
    if(userID != null){
        LaunchedEffect(userID) {
            detail.getUserData(userID, true)
            detail.getRideById(selectVM.rideID)
        }
    }
    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF508D4E))
                .height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "Ride Detail",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF508D4E))
                .height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                IconButton(onClick = { navController.navigate(Navigate.Profile.name) },
                ) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "profile",
                        modifier = Modifier.size(50.dp))
                }
                Spacer(modifier = Modifier.padding(30.dp))
                IconButton(onClick = { navController.navigate(Navigate.Home.name)}) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "home",
                        modifier = Modifier.size(50.dp))

                }
                Spacer(modifier = Modifier.padding(30.dp))

                IconButton(onClick = { navController.navigate(Navigate.Record.name) }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "view record",
                        modifier = Modifier.size(50.dp))
                }
            }
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.padding(24.dp)) {
//                Row(verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                    ,modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .shadow(4.dp, RoundedCornerShape(15.dp))
//                        .clip(RoundedCornerShape(15.dp))
//                        .border(1.dp, Color(0xFFD1E9F6), RoundedCornerShape(15.dp))
//                        .background(Color(0xFFF7F9F2))
//
//                ){
//                    Text(text = "Status "
//                        , fontWeight = FontWeight.Bold
//                        , fontSize = 20.sp)
//
//                    Text(text = if (detail.rideData.isEnded) "Ongoing" else "Ended"
//                        , fontWeight = FontWeight.ExtraBold
//                        , fontSize = 20.sp
//                        , color = Color(0xFF7743DB))
//
//                }
                Spacer(modifier = Modifier.padding(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                    ,modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .shadow(4.dp, RoundedCornerShape(15.dp))
                        .clip(RoundedCornerShape(15.dp))
                        .border(1.dp, Color(0xFFD1E9F6), RoundedCornerShape(15.dp))
                        .background(Color(0xFFF7F9F2))

                ){
                    Column(modifier = Modifier.fillMaxWidth(.5f)) {
                        Text(text = "From"
                            , fontWeight = FontWeight.Bold)
                        Text(text = detail.rideData.origin
                        , fontWeight = FontWeight.ExtraBold
                        , fontSize = 20.sp
                        , color = Color(0xFF7743DB))
                    }
                    Column (modifier = Modifier.fillMaxWidth(.5f)){
                        Text(text = "To"
                            , fontWeight = FontWeight.Bold)
                        Text(text = detail.rideData.destination
                            , fontWeight = FontWeight.ExtraBold
                            , fontSize = 20.sp
                            , color = Color(0xFF7743DB))
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                    ,modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .shadow(4.dp, RoundedCornerShape(15.dp))
                        .clip(RoundedCornerShape(15.dp))
                        .border(1.dp, Color(0xFFD1E9F6), RoundedCornerShape(15.dp))
                        .background(Color(0xFFF7F9F2))

                ){
                    Column(modifier = Modifier.fillMaxWidth(.5f)) {
                        AsyncImage(
                            model = detail.rideData.car.carImg, contentDescription = "car",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .border(1.dp, Color(0xFFD1E9F6), RoundedCornerShape(15.dp))
                        )
                    }
                        Column(modifier = Modifier.fillMaxWidth(),
                            Arrangement.Center) {
                            Text(text = "Car Model"
                                , fontWeight = FontWeight.Bold)
                            Text(text = detail.rideData.car.brand + " "+detail.rideData.car.model
                                , fontWeight = FontWeight.ExtraBold
                                , fontSize = 20.sp
                                , color = Color(0xFF7743DB))
                            Spacer(modifier = Modifier.padding(4.dp))
                            Divider()
                            Spacer(modifier = Modifier.padding(4.dp))

                            Text(text = "Plate Number"
                                , fontWeight = FontWeight.Bold)
                            Text(text = detail.rideData.car.plateNo
                                , fontWeight = FontWeight.ExtraBold
                                , fontSize = 20.sp
                                , color = Color(0xFF7743DB))
                        }
                    }

                Spacer(modifier = Modifier.padding(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                    ,modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .shadow(4.dp, RoundedCornerShape(15.dp))
                        .clip(RoundedCornerShape(15.dp))
                        .border(1.dp, Color(0xFFD1E9F6), RoundedCornerShape(15.dp))
                        .background(Color(0xFFF7F9F2))

                ){
                    Column(modifier = Modifier.fillMaxWidth(.5f)) {
                        Text(text = "Pickup Date"
                            , fontWeight = FontWeight.Bold)
                        Text(text = detail.rideData.date
                            , fontWeight = FontWeight.ExtraBold
                            , fontSize = 20.sp
                            , color = Color(0xFF7743DB))
                        Text(text = detail.rideData.time
                            , fontWeight = FontWeight.ExtraBold
                            , fontSize = 20.sp
                            , color = Color(0xFF7743DB))

                    }
                    Column(modifier = Modifier.fillMaxWidth(.5f)) {
                        Text(text = "Fare"
                            , fontWeight = FontWeight.Bold)
                        Text(text = "RM"+detail.rideData.fare
                            , fontWeight = FontWeight.ExtraBold
                            , fontSize = 20.sp
                            , color = Color(0xFF7743DB))
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
                var view by remember{mutableStateOf(false)}
                Button(onClick = {
                    detail.getDriverUserData(detail.rideData.driverRef)
                    view = !view
                },
                    modifier = Modifier.width(400.dp),
                    shape = RoundedCornerShape(7.dp)){
                    Text(text = "View Driver")

                }
                if(view) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center, modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .shadow(4.dp, RoundedCornerShape(15.dp))
                            .clip(RoundedCornerShape(15.dp))
                            .border(1.dp, Color(0xFFD1E9F6), RoundedCornerShape(15.dp))
                            .background(Color(0xFFF7F9F2))

                    ) {
                        Column(modifier = Modifier.fillMaxWidth(.5f)) {
                            AsyncImage(
                                model = detail.driverData.userImg, contentDescription = "pfp",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(160.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .border(1.dp, Color(0xFFD1E9F6), RoundedCornerShape(15.dp))
                            )
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Driver"
                                , fontWeight = FontWeight.Bold)
                            Text(text =  detail.driverData.name
                                , fontWeight = FontWeight.ExtraBold
                                , fontSize = 20.sp
                                , color = Color(0xFF7743DB))
                            Text(text = "Phone Number"
                                , fontWeight = FontWeight.Bold)
                            Text(text =  detail.driverData.phone
                                , fontWeight = FontWeight.ExtraBold
                                , fontSize = 20.sp
                                , color = Color(0xFF7743DB))
                            Text(text = "Gender"
                                , fontWeight = FontWeight.Bold)
                            Text(text =  if(detail.driverData.isMale)"Male" else "Female"
                                , fontWeight = FontWeight.ExtraBold
                                , fontSize = 20.sp
                                , color = Color(0xFF7743DB))
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                }

                    Button(
                        onClick = {
                            detail.joinRide(selectVM.rideID, detail.userData.userID, ctx)
                        },
                        modifier = Modifier.width(400.dp),
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Column {
                            Text(text = "Join Ride")
                        }

                    }
                    Button(
                        onClick = {
                                  detail.isShow = true
                        },
                        modifier = Modifier.width(400.dp),
                        shape = RoundedCornerShape(7.dp),
                    ){
                        Column {
                            Text(text = "Cancel Ride")
                        }
                    }
                if(detail.isShow) {
                    Alert(detail, selectVM, ctx)
                }
                }
            }
    }

}

@Composable
fun Alert (detail: RiderVM, selectVM: SelectVM, ctx: Context){
    AlertDialog(
        onDismissRequest = { detail.isShow=false },
        confirmButton = {
            TextButton(onClick = { detail.isShow=false
                detail.cancelRide(selectVM.rideID, detail.userData.userID, ctx) }) {
                Text(text = "Confirm")
            }

        },
        dismissButton = {
            TextButton(onClick = { detail.isShow=false }) {
                Text(text = "Dismiss")
            }
        },
        title = {
            Text(text = "Cancel Ride?")
        },
        text = {
            Text(text = "Are you sure to cancel this ride?")
        },
        )

}