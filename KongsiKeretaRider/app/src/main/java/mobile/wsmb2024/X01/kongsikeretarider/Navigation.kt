package mobile.wsmb2024.X01.kongsikeretarider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import mobile.wsmb2024.X01.kongsikeretarider.Screen.Edit
import mobile.wsmb2024.X01.kongsikeretarider.Screen.Home
import mobile.wsmb2024.X01.kongsikeretarider.Screen.Login
import mobile.wsmb2024.X01.kongsikeretarider.Screen.Profile
import mobile.wsmb2024.X01.kongsikeretarider.Screen.Record
import mobile.wsmb2024.X01.kongsikeretarider.Screen.Register
import mobile.wsmb2024.X01.kongsikeretarider.Screen.RideDetail
import mobile.wsmb2024.X01.kongsikeretarider.VM.SelectVM

enum class Navigate{
    Home,
    Login,
    Register,
    Profile,
    Record,
    RideDetail,
    Edit
}
@Composable
fun Navigation(select:SelectVM = viewModel()){
    val auth = Firebase.auth
    val userId = auth.currentUser?.uid
    val navController = rememberNavController()
    var startDes = Navigate.Login.name
    if(userId!=null){
        startDes = Navigate.Home.name
    }
    NavHost(
        startDestination = startDes,
        navController = navController
    ){
        composable(route = Navigate.Home.name){
            Home(navController, selectVM = select)
        }
        composable(route = Navigate.Login.name){
            Login(navController)
        }
        composable(route = Navigate.Register.name){
            Register(navController)
        }
        composable(route = Navigate.Profile.name){
            Profile(navController)
        }
        composable(route = Navigate.Record.name){
            Record(navController, selectVM = select)
        }
        composable(route = Navigate.RideDetail.name){
            RideDetail(navController, selectVM = select)
        }
        composable(route = Navigate.Edit.name){
            Edit(navController)
        }
    }
}