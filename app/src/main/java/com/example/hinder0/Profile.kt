package com.example.hinder0

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.IOException


class Profile : Fragment(), View.OnClickListener {
    val GALLERY_REQUEST = 1
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    //UI elements
    private var tvFio: TextView? = null
    private var tvPhone: TextView? = null
    private var tvEmail: TextView? = null

    private lateinit var imag: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        imag = root.findViewById(R.id.profile_image);
        imag.setOnClickListener(this)
        //initialise()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        tvFio = root.findViewById<View>(R.id.fio_view) as TextView
        tvPhone = root.findViewById<View>(R.id.number_view) as TextView
        tvEmail = root.findViewById<View>(R.id.mail_view) as TextView
        return root
    }
    override fun onStart() {
        super.onStart()
        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        tvEmail!!.text = mUser.email
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvFio!!.text = snapshot.child("firstName").value as String
                tvPhone!!.text = snapshot.child("phone").value as String
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_options, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    //handle item clicks of menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get item id to handle item clicks
        val id = item!!.itemId
        //handle item clicks
        if (id == R.id.action_settings){
            //do your action here, im just showing toast
            Toast.makeText(activity, "Settings", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.profile_image ->
            {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                photoPickerIntent.action = Intent.ACTION_GET_CONTENT;
                startActivityForResult(Intent.createChooser(photoPickerIntent,
                    "Select Picture"), GALLERY_REQUEST)
            }

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        val bitmap: Bitmap? = null
        val imageView = requireView().findViewById(R.id.profile_image) as ImageView
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                val selectedImage: Uri? = imageReturnedIntent?.data
                try {
                    var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                    val matrix = Matrix()
                    matrix.postRotate(-90F)
                    bitmap = Bitmap.createBitmap(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        matrix,
                        true
                    )
                    imageView.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                //imageView.setImageBitmap(bitmap)
            }
        }
    }
   /* private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        tvFio = requireView().findViewById<View>(R.id.fio_view) as TextView
        tvPhone = requireView().findViewById<View>(R.id.number_view) as TextView
        tvEmail = requireView().findViewById<View>(R.id.mail_view) as TextView
    }*/

}
