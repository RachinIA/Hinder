package com.example.hinder0

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Wishlist : Fragment() {

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wishlist, container, false)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Adv")
        mAuth = FirebaseAuth.getInstance()
        tvAddress = root.findViewById<View>(R.id.advaddress) as TextView
        tvCost = root.findViewById<View>(R.id.advcost) as TextView
        tvRooms = root.findViewById<View>(R.id.advrooms) as TextView
        tvArea = root.findViewById<View>(R.id.advarea) as TextView
        tvFloor = root.findViewById<View>(R.id.advfloor) as TextView
        tvFio = root.findViewById<View>(R.id.advname) as TextView
        tvPhone = root.findViewById<View>(R.id.advphone) as TextView
        tvEmail = root.findViewById<View>(R.id.advmail) as TextView
        return root
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
