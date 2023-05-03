package com.jm.jetpackcompose_crud

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jm.jetpackcompose_crud.component.TopBar
import com.jm.jetpackcompose_crud.model.RegistrationModel


@Composable
fun Dashboard(navController: NavController){
    database()

    DashboardPg(navController = navController)

}
@Composable
fun DashboardPg(navController: NavController){
    Scaffold(
        Modifier.statusBarsPadding(),
        topBar = { TopBar(screenName = "DashBoard" , navController = navController,  destination = "login") },
        content = {it
            Bodyp(navController)
        }

    )
}

@Composable
fun Bodyp (navController: NavController){

    Column(
        Modifier
            .navigationBarsPadding()
            .padding(35.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally){
        //ListShow(data = "Data")
        LazyColumn{
                items(items =userList ,itemContent = {
                    item -> ListShow(data = item)
                })
        }
    }
}


@Composable
fun ListShow (data:RegistrationModel) {
    Column{
        Box(modifier = Modifier
            .clip(RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp))
            .background(
                Color.LightGray
            )
            .width(110.dp)
            .height(110.dp)
            .padding(10.dp)
        ){
            Column(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(1.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "${data.email}", color = Color.Black, )
                Text(text = "${data.password}", color = Color.Black,)
                Text(text = "${data.dob}", color = Color.Black, )
            }
//            Text(text = "${data.email}", color = Color.Black, modifier = Modifier.align(Alignment.Center))
//            Text(text = "${data.password}", color = Color.Black, modifier = Modifier.align(Alignment.Center))
//            Text(text = "${data.dob}", color = Color.Black, modifier = Modifier.align(Alignment.Center))

        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
