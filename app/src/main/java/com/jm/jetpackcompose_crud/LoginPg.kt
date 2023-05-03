package com.jm.jetpackcompose_crud

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jm.jetpackcompose_crud.model.RegistrationModel
import com.jm.jetpackcompose_crud.ui.theme.Shapes

@Composable
fun LoginPg(navController: NavController) {
    Login2(navController)
    database()
}

@Composable
fun Login2(navController: NavController) {

    val passwordFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Column(
        Modifier
            .navigationBarsPadding()
            .padding(35.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.unnamed),
            contentDescription = null,
            Modifier.size(80.dp),
            tint = Color.Blue
        )
        TextInput2(InputType2.Name, keyboardActions = KeyboardActions(onNext = {
            passwordFocusRequester.requestFocus()
        }), editValue = true)
        TextInput2(InputType2.Password, keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }), focusRequester = passwordFocusRequester, editValue = false)
        val context = LocalContext.current
        Button(onClick = {
            if (isEmail.isNotBlank() && isPassword.isNotBlank()) {
                userList.forEach { udata ->

                        if (udata.email.equals(isEmail)) {
                            if (udata.password.equals(isPassword)) {
                                navController.navigate("dashboard") {
                                    popUpTo(navController.graph.startDestinationId)
                                }
                            }
                        }

                }
            } else {
                Toast.makeText(context, "Please Fill Empty field", Toast.LENGTH_SHORT).show()
            }


//            databaseReference.addListenerForSingleValueEvent(object  : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    //TODO("Not yet implemented")
//                    //val data = snapshot.getValue<RegistrationModel>(RegistrationModel::class.java)
//                    //val  ListLogin : List<RegistrationModel>
//
//                    for (data in snapshot.children  ){
//                        val data1 = data.getValue<RegistrationModel>(RegistrationModel::class.java)
//                    //Log.e("TAG", "Login: "+  data1 + " => "  + data + " <= " + snapshot.getValue())
//                        if (isEmail.isNotBlank() && isPassword.isNotBlank() ){
//                            if (data1?.email.equals(isEmail)){
//                                if (data1?.password.equals(isPassword)){
//                                    navController.navigate("dashboard"){
//                                        popUpTo(navController.graph.startDestinationId)
//                                        launchSingleTop = true
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    //TODO("Not yet implemented")
//                }
//
//            })

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Login", Modifier.padding(vertical = 8.dp))
        }
        Divider(
            color = Color.Black.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account? ", color = Color.Black)
            TextButton(onClick = {
                navController.navigate("registration") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }) {
                Text(text = "SIGN UP")

            }
        }

    }
}

sealed class InputType2(
    val label: String,
    val icon: ImageVector,
    val keyBoardOption: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Name : InputType2(
        label = "Email",
        icon = Icons.Default.Person,
        keyBoardOption = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password : InputType2(
        label = "Password", icon = Icons.Default.Lock, keyBoardOption = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ), visualTransformation = PasswordVisualTransformation()
    )
}


@Composable
fun TextInput2(
    inputType: InputType2,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    editValue: Boolean
) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = {
            value = it
            if (editValue) {
                isEmail = value
            } else {
                isPassword = value
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label) },
        shape = Shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        visualTransformation = inputType.visualTransformation,
        keyboardOptions = inputType.keyBoardOption,
        keyboardActions = keyboardActions
    )
}


fun database() {
   // userList.clear()
    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for ((index, data) in snapshot.children.withIndex()) {
                val data1 = data.getValue<RegistrationModel>(RegistrationModel::class.java)
                userList.add(
                    index = index,
                    RegistrationModel(data1?.email, data1?.password, data1?.dob)
                )
            }

        }

        override fun onCancelled(error: DatabaseError) {

        }

    })
}
