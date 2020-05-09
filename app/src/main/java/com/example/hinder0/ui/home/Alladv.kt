package com.example.hinder0.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.hinder0.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Alladv : AppCompatActivity() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    //UI elements
    private var tvAddress: TextView? = null
    private var tvCost: TextView? = null
    private var tvRooms: TextView? = null
    private var tvArea: TextView? = null
    private var tvFloor: TextView? = null
    private var tvFio: TextView? = null
    private var tvPhone: TextView? = null
    private var tvEmail: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alladv)
        val actionBar = supportActionBar
        actionBar!!.title = "New Activity"
        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        //initialise()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Adv")
        mAuth = FirebaseAuth.getInstance()
        tvAddress = findViewById<View>(R.id.advaddress) as TextView
        tvCost = findViewById<View>(R.id.advcost) as TextView
        tvRooms = findViewById<View>(R.id.advrooms) as TextView
        tvArea = findViewById<View>(R.id.advarea) as TextView
        tvFloor = findViewById<View>(R.id.advfloor) as TextView
        tvFio = findViewById<View>(R.id.advname) as TextView
        tvPhone = findViewById<View>(R.id.advphone) as TextView
        tvEmail = findViewById<View>(R.id.advmail) as TextView
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onStart() {
        super.onStart()
        val mUser = mAuth!!.currentUser
        var mUserReference = mDatabaseReference!!.child("null")
        if (mUser != null) {
            tvEmail!!.text = mUser.email
        }
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvAddress!!.text = snapshot.child("Address").value as String
                tvCost!!.text = (snapshot.child("Cost").value as Long).toString()
                tvRooms!!.text = (snapshot.child("Rooms").value as Long).toString()
                tvArea!!.text = (snapshot.child("Area").value as Long).toString()
                tvFloor!!.text = (snapshot.child("Floor").value as Long).toString()

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvFio!!.text = snapshot.child("firstName").value as String
                tvPhone!!.text = snapshot.child("phone").value as String
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }


}
