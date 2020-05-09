package com.example.hinder0.ui.newpost

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class NewPostFragment : Fragment() {


    private var etAddress: EditText? = null
    private var etCost: EditText? = null
    private var etRooms: EditText? = null
    private var etArea: EditText? = null
    private var etFloor: EditText? = null
    private var etDescr: EditText? = null
    private var btnSend: Button? = null

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var address: String? = null
    private var cost: Int? = null
    private var rooms: Int? = null
    private var area: Int? = null
    private var floor: Int? = null
    private var descr: String? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(com.example.hinder0.R.layout.fragment_newpost, container, false)
        etAddress = root.findViewById<View>(com.example.hinder0.R.id.address) as EditText
        etCost = root.findViewById<View>(com.example.hinder0.R.id.cost) as EditText
        etRooms = root.findViewById<View>(com.example.hinder0.R.id.rooms) as EditText
        etArea = root.findViewById<View>(com.example.hinder0.R.id.area) as EditText
        etFloor = root.findViewById<View>(com.example.hinder0.R.id.floor) as EditText
        etDescr = root.findViewById<View>(com.example.hinder0.R.id.descr) as EditText
        btnSend = root.findViewById<View>(com.example.hinder0.R.id.send) as Button
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Adv")
        mAuth = FirebaseAuth.getInstance()
        btnSend!!.setOnClickListener { post() }
        return root
    }

    private fun post() {
        address = etAddress?.text.toString()
        cost = Integer.parseInt(etCost?.text.toString())
        rooms = Integer.parseInt(etRooms?.text.toString())
        area = Integer.parseInt(etArea?.text.toString())
        floor = Integer.parseInt(etFloor?.text.toString())
        descr = etDescr?.text.toString()

        if (!TextUtils.isEmpty(address) && cost != null
            && rooms != null && area != null && floor != null
        ) {
            val userId = mAuth!!.currentUser!!.uid
            var currReference = mDatabase!!.reference!!.child("Adv")
            var currId: String? = null
            mDatabaseReference!!.child("CurrId").child("total").addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        currId = snapshot.getValue() as String?
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                });
            Toast.makeText(activity, currId, Toast.LENGTH_SHORT).show()

            mDatabaseReference!!.child("$currId").child("Address").setValue(address)
            mDatabaseReference!!.child("$currId").child("Cost").setValue(cost)
            mDatabaseReference!!.child("$currId").child("Rooms").setValue(rooms)
            mDatabaseReference!!.child("$currId").child("Area").setValue(area)
            mDatabaseReference!!.child("$currId").child("Floor").setValue(floor)
            if(!TextUtils.isEmpty(descr)) {
                mDatabaseReference!!.child("$currId").child("Descr").setValue(descr)
            }
            /*currReference = mDatabase!!.reference!!.child("Users")
            currReference.child("CurrId").addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        currOwnposts =  snapshot.child("nOwnPosts").value as String
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                });*/
            mDatabaseReference = mDatabase!!.reference!!.child("Users")
            mDatabaseReference!!.child(userId).child("ownPosts").child("$currId").setValue(currId)

            etAddress?.setText("")
            etCost?.setText("")
            etRooms?.setText("")
            etArea?.setText("")
            etFloor?.setText("")
            etDescr?.setText("")
            Toast.makeText(activity, "Posted", Toast.LENGTH_SHORT).show()
        }else
        {
            //Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

}
