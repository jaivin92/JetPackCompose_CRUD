package com.jm.jetpackcompose_crud

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase
import com.jm.jetpackcompose_crud.model.RegistrationModel
import com.jm.jetpackcompose_crud.ui.theme.JetPackCompose_CRUDTheme
import com.jm.jetpackcompose_crud.ui.theme.Shapes

var isEmail:String = ""
var isPassword:String = ""
var userList  = mutableListOf<RegistrationModel>()
val firebaseDatabase = FirebaseDatabase.getInstance()
val databaseReference = firebaseDatabase.getReference("EmployeeInfo")


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackCompose_CRUDTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    //Greeting("Android")
                    //Login()
                    DefaultPreview()
                    //PageList()
                }
            }
        }
    }
}

@Composable
fun PageList(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login" , content =  { LoginPg(navController = navController )})
        composable("registration" , content =  { RegistationPg(navController = navController )})
        composable("dashboard" , content =  { Dashboard(navController = navController )})

    } )
}


@Composable
fun Login() {

    val passwordFocusRequester = remember {FocusRequester()}
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
        TextInput(InputType.Name, keyboardActions = KeyboardActions( onNext = {
            passwordFocusRequester.requestFocus()
        } ), editValue = false)
        TextInput(InputType.Password, keyboardActions = KeyboardActions (onDone ={
            focusManager.clearFocus()
        }), focusRequester = passwordFocusRequester, editValue = true)

        Button(onClick = { Log.e("TAG", "Login: "+ isEmail + " ** " + isPassword    ) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Login", Modifier.padding(vertical = 8.dp))
        }
        Divider(
            color = Color.Black.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account? ", color = Color.Black)
            TextButton(onClick = {}) {
                Text(text = "SIGN UP")

            }
        }

    }
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyBoardOption: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Name : InputType(
        label = "Email",
        icon = Icons.Default.Person,
        keyBoardOption = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password : InputType(
        label = "Password", icon = Icons.Default.Lock, keyBoardOption = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ), visualTransformation = PasswordVisualTransformation()
    )
}



@Composable
fun TextInput(inputType: InputType , focusRequester: FocusRequester? = null, keyboardActions: KeyboardActions,  editValue:Boolean ) {
    var value by remember { mutableStateOf("") }

    TextField(value = value,
        onValueChange = { value = it
                                if(editValue){
                                    isEmail = value
                                } else {
                                    isPassword = value
                                }
                        },
        modifier = Modifier
            .fillMaxWidth()
            .focusOrder(focusRequester ?: FocusRequester()),
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
        keyboardOptions =inputType.keyBoardOption,
        keyboardActions = keyboardActions
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetPackCompose_CRUDTheme {
//        Greeting("Android")
//        Login()
            PageList()
    }
}