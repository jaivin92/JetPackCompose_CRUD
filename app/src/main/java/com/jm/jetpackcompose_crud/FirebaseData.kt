package com.jm.jetpackcompose_crud

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jm.jetpackcompose_crud.model.RegistrationModel

class FirebaseData :ViewModel(){

    init {
        database()
    }

    fun database(){
        databaseReference.addListenerForSingleValueEvent(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ((index,data )in snapshot.children.withIndex()  ){
                    val data1 = data.getValue<RegistrationModel>(RegistrationModel::class.java)
                    userList.add(index = index, RegistrationModel(data1?.email, data1?.password,data1?.dob ))
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}