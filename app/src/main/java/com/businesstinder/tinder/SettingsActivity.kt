package com.businesstinder.tinder

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private var mNameField: EditText? = null
    private var mPhoneField: EditText? = null
    private var mBack: Button? = null
    private var mConfirm: Button? = null
    private var mProfileImage: ImageView? = null
    private var mAuth: FirebaseAuth? = null
    private var mUserDatabase: DatabaseReference? = null
    private var userId: String? = null
    private var name: String? = null
    private var phone: String? = null
    private var profileImageUrl: String? = null
    private var userSex: String? = null
    private var resultUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        mNameField = findViewById<View>(R.id.name) as EditText
        mPhoneField = findViewById<View>(R.id.phone) as EditText
        mProfileImage = findViewById<View>(R.id.profileImage) as ImageView
        mBack = findViewById<View>(R.id.back) as Button
        mConfirm = findViewById<View>(R.id.confirm) as Button
        mAuth = FirebaseAuth.getInstance()
        userId = mAuth?.getCurrentUser()!!.uid
        mUserDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        userInfo
        mProfileImage!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        mConfirm!!.setOnClickListener { saveUserInformation() }
        mBack!!.setOnClickListener(View.OnClickListener {
            finish()
            return@OnClickListener
        })
    }

    private val userInfo: Unit
        private get() {
            mUserDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.childrenCount > 0) {
                        val map = dataSnapshot.value as Map<String, Any?>?
                        if (map!!["name"] != null) {
                            name = map["name"].toString()
                            mNameField!!.setText(name)
                        }
                        if (map["phone"] != null) {
                            phone = map["phone"].toString()
                            mPhoneField!!.setText(phone)
                        }
                        if (map["sex"] != null) {
                            userSex = map["sex"].toString()
                        }
                        Glide.clear(mProfileImage)
                        if (map["profileImageUrl"] != null) {
                            profileImageUrl = map["profileImageUrl"].toString()
                            when (profileImageUrl) {
                                "default" -> Glide.with(application).load(R.mipmap.ic_launcher).into(mProfileImage)
                                else -> Glide.with(application).load(profileImageUrl).into(mProfileImage)
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

    private fun saveUserInformation() {
        name = mNameField!!.text.toString()
        phone = mPhoneField!!.text.toString()
        val userInfo: MutableMap<*, *> = HashMap<Any?, Any?>()
        userInfo.set("name", name)
        userInfo.set("phone", phone)
        mUserDatabase!!.updateChildren(userInfo as MutableMap<String, Any>?)
        if (resultUri != null) {
            val filepath = FirebaseStorage.getInstance().reference.child("profileImages").child(userId!!)
            var bitmap: Bitmap? = null
            try {
                bitmap = MediaStore.Images.Media.getBitmap(application.contentResolver, resultUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val baos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, baos)
            val data = baos.toByteArray()
            val uploadTask = filepath.putBytes(data)
            uploadTask.addOnFailureListener { finish() }
            uploadTask.addOnSuccessListener(OnSuccessListener { taskSnapshot ->
                val downloadUrl = taskSnapshot.downloadUrl
                val userInfo: MutableMap<*, *> = HashMap<Any?, Any?>()
                userInfo.set("profileImageUrl", downloadUrl.toString())
                mUserDatabase!!.updateChildren(userInfo as MutableMap<String, Any>?)
                finish()
                return@OnSuccessListener
            })
        } else {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imageUri = data.data
            resultUri = imageUri
            mProfileImage!!.setImageURI(resultUri)
        }
    }
}

operator fun <K, V> MutableMap<K, V>.set(v: String, value: String?) {

}
