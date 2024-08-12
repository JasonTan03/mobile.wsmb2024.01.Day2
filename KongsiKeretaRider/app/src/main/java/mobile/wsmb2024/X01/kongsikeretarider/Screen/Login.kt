package mobile.wsmb2024.X01.kongsikeretarider.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mobile.wsmb2024.X01.kongsikeretarider.Navigate
import mobile.wsmb2024.X01.kongsikeretarider.VM.RiderVM


@Composable
fun Login(navController: NavController, login : RiderVM = viewModel()){
    val ctx = LocalContext.current
    Column(modifier = Modifier.background(Color(0xFF508D4E))){
        Column (modifier = Modifier
            .fillMaxHeight(.3f)
            .fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Text(text = "Kongsi Kereta",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 50.sp)
        }
        Column(modifier = Modifier
            .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            .background(Color.White)) {

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = login.email, onValueChange = { login.email = it },
                    label = { Text(text = "Email") },
                    modifier = Modifier.width(400.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedTextField(value = login.password, onValueChange = { login.password = it },
                    label = { Text(text = "Password") },
                    modifier = Modifier.width(400.dp),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.padding(12.dp))
                Button(
                    onClick = { login.isLoading = true
                        login.Login(ctx, navController) },
                    modifier = Modifier.width(400.dp),
                    shape = RoundedCornerShape(7.dp)
                )
                {
                    if(login.isLoading){
                        CircularProgressIndicator(modifier = Modifier.size(20.dp),
                            color = Color.White)
                    }else {
                        Text(text = "Log in")
                    }
                }
                Spacer(modifier = Modifier.padding(12.dp))
                Row {
                    Text(text = "New user? ")
                    Text(
                        text = "Sign up. ",
                        modifier = Modifier.clickable { navController.navigate(Navigate.Register.name) },
                        color = Color(0xFF674188)
                    )
                }
            }
        }
    }
}