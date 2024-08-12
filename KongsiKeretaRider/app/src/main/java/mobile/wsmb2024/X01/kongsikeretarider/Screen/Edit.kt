package mobile.wsmb2024.X01.kongsikeretarider.Screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.delay
import mobile.wsmb2024.X01.kongsikeretarider.Navigate
import mobile.wsmb2024.X01.kongsikeretarider.VM.RiderVM

@Composable
fun Edit(navController: NavController, edit: RiderVM = viewModel()) {
    val auth = Firebase.auth
    val userID = auth.currentUser?.uid
    if(userID != null){
        LaunchedEffect(userID) {
            edit.getUserData(userID, true)
            edit.newName = edit.userData.name
        }
    }
    val ctx = LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }
    val pfp = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uris ->
        uris?.let{
            uri = uris
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
                Text(text = "Edit Profile",
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
        Column(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Edit Photo")
                AsyncImage(
                    model = uri,
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .border(1.dp, Color.Black, RoundedCornerShape(7.dp))
                        .size(128.dp)
                        .clickable {
                            pfp.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                )
                
                OutlinedTextField(value = edit.newName, onValueChange = {edit.newName = it},
                    label = {Text(text = "Edit name")}
                    ,modifier = Modifier.width(400.dp))
                Spacer(modifier = Modifier.padding(4.dp))
                Button(onClick = {

                    if(!edit.newName.isNullOrEmpty()) {
                        if (uri.toString().isNullOrEmpty()) {
                            if (userID != null) {
                                edit.update(userID, ctx)
                            }
                        } else {
                            if (userID != null) {
                                uri?.let { it1 -> edit.UploadImg(it1, true, ctx) }
                            }
                        }
                    }else{
                        Toast.makeText(ctx, "Field cannot be empty!", Toast.LENGTH_SHORT).show()
                    }

                }
                    ,modifier = Modifier.width(400.dp),
                    shape = RoundedCornerShape(7.dp)) {
                    Text(text = "Update profile")
                }
            }
        }
    }
}
