package com.jm.jetpackcompose_crud

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jm.jetpackcompose_crud.component.TopBar
import com.jm.jetpackcompose_crud.model.RegistrationModel
import com.jm.jetpackcompose_crud.ui.theme.JetPackCompose_CRUDTheme
import com.jm.jetpackcompose_crud.ui.theme.Shapes
import java.util.*
var date : String = ""
@Composable
fun RegistationPg(navController: NavController){

    Scaffold(
        Modifier.statusBarsPadding(),

        topBar ={
            TopBar(screenName = "Registration" , navController = navController,  destination = "login")
        },
        content = {it
            Body(navController = navController)
        }
    )

}

@Composable
fun Body (navController: NavController){
    Column(
        Modifier
            .navigationBarsPadding()
            .padding(35.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextInput3(InputType3.Name, keyboardActions = KeyboardActions( onNext = {
            // passwordFocusRequester.requestFocus()
        } ), editValue = true)
        TextInput3(InputType3.Password, keyboardActions = KeyboardActions (onDone ={
            // focusManager.clearFocus()
        }), //focusRequester = //passwordFocusRequester,
            editValue = false)

        MyDatePicker()


        val context = LocalContext.current
        Button(onClick = {
           // Log.e("TAG", "Login: "+ isEmail + " ** " + isPassword    )
           SaveData(navController = navController, context)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Login", Modifier.padding(vertical = 8.dp))
        }

        Button(onClick = {
            Log.e("TAG", "Login: "+ isEmail + " ** " + isPassword    )
            navController.navigate("registration"){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Login", Modifier.padding(vertical = 8.dp))
        }
    }
}

fun DataValidate () :Boolean{
    var validate :Boolean = false
    if(isEmail.isNotBlank() && isPassword.isNotBlank() && date.isNotBlank()){
        validate = true
    }
    return  validate
}


fun ClearData () {
    isEmail = ""
    isPassword = ""
    date = ""
}

fun  SaveData(navController :NavController, context :Context ) {
    if (DataValidate()){

        val key :String = databaseReference.push().key.toString()
        databaseReference.child(key).setValue(RegistrationModel(isEmail, isPassword, date ))
        ClearData()
        Log.e("TAG", "Login: "+ isEmail + " ** " + isPassword    )
        navController.navigate("login"){
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }

//    databaseReference.addValueEventListener(object :ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            val key :String = databaseReference.push().key.toString()
//            databaseReference.child(key).setValue(RegistrationModel(isEmail, isPassword, date ))
//            ClearData()
//            Log.e("TAG", "Login: "+ isEmail + " ** " + isPassword    )
//            navController.navigate("login"){
//                popUpTo(navController.graph.startDestinationId)
//                launchSingleTop = true
//            }
//
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            Toast.makeText(context , error.message, Toast.LENGTH_SHORT).show()
//        }
//
//    })
    } else {
        Toast.makeText(context ,"Fill Data", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun MyDatePicker() {
    val context  = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )
    Column() {
        Button(onClick = {mDatePickerDialog.show()}) {
            var data :String = "Select Your BirthDate"
                if (mDate.value.isNotBlank()){
                    data = mDate.value
                    date = mDate.value
                }

            Text(text = data)
        }
    }    
}

sealed class InputType3(
    val label: String,
    val icon: ImageVector,
    val keyBoardOption: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Name : InputType3(
        label = "Email",
        icon = Icons.Default.Person,
        keyBoardOption = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password : InputType3(
        label = "Password", icon = Icons.Default.Lock, keyBoardOption = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ), visualTransformation = PasswordVisualTransformation()
    )
}



@Composable
fun TextInput3(inputType: InputType3, focusRequester: FocusRequester? = null, keyboardActions: KeyboardActions, editValue:Boolean ) {
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
