package com.ds.qqpega.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseClass {

     lateinit var dbRef: DatabaseReference
     lateinit var dbInstance: FirebaseFirestore

}