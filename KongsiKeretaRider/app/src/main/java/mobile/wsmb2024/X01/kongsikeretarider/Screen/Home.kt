package mobile.wsmb2024.X01.kongsikeretarider.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import mobile.wsmb2024.X01.kongsikeretarider.Navigate
import mobile.wsmb2024.X01.kongsikeretarider.VM.RiderVM
import mobile.wsmb2024.X01.kongsikeretarider.VM.SelectVM

@Composable
fun Home(navController: NavController, view: RiderVM = viewModel(), selectVM: SelectVM){
    val auth = Firebase.auth
    val userID = auth.currentUser?.uid
    if(userID != null){
        LaunchedEffect(userID) {
            view.getUserData(userID, true)
            view.getRide()
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
                Text(text = "Home",
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFEEEEEE))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Text(text = "Welcome, ", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(text = view.userData.name, fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF7743DB), fontSize = 24.sp)

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "All ride", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { navController.navigate(Navigate.Home.name) }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh")
                    }
                }
                view.rideList.forEach { ride ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(178.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            selectVM.rideID = ride.rideID
                            navController.navigate(Navigate.RideDetail.name)
                        }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .fillMaxHeight()
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            AsyncImage(
                                model = ride.car.carImg.toUri(),
                                contentDescription = "Car Image",
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(4.dp)
                        ) {
                            Row {
                                Column {
                                    Text(text = "Destination")
                                    Text(
                                        text = ride.destination,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Column {
                                    Text(text = "Origin")
                                    Text(
                                        text = ride.origin,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(2.dp))
                            Divider()
                            Spacer(modifier = Modifier.padding(2.dp))

                            Row {
                                Column {
                                    Text(text = "Car Model")
                                    Text(
                                        text = ride.car.brand,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = ride.car.model,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Column {
                                    Text(text = "Car Plate")
                                    Text(
                                        text = ride.car.plateNo,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(2.dp))

                            Divider()
                            Spacer(modifier = Modifier.padding(2.dp))


                            Row {
                                Column {
                                    Text(text = "Pick Up Date")
                                    Text(
                                        text = ride.date,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = ride.time,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Column {
                                    Text(text = "Car Fare")
                                    Text(
                                        text = ride.fare.toString(),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}