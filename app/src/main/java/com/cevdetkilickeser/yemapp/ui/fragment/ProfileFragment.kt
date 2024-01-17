package com.cevdetkilickeser.yemapp.ui.fragment

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.data.entity.UserInfo
import com.cevdetkilickeser.yemapp.databinding.FragmentProfileBinding
import com.cevdetkilickeser.yemapp.utils.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore
    private lateinit var galleryPermission: String
    private var choosenImage : Uri? = null
    private var choosenBitmap : Bitmap? = null
    private lateinit var user: String
    private lateinit var userInfo: UserInfo
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = User.user

        galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            READ_MEDIA_IMAGES
        }else{
            READ_EXTERNAL_STORAGE
        }

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)

        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        binding.profileFragment = this

        database.collection("UserInfo").document(user).get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                userInfo = documentSnapshot.toObject(UserInfo::class.java)!!
                Picasso.get().load(userInfo.image).into(binding.ivProfile)
            }else{
                userInfo = UserInfo("","","","","")
                val toast = R.string.entry_info
                Toast.makeText(requireContext(),toast,Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = View.INVISIBLE
            binding.userInfo = userInfo
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.INVISIBLE
        }
        return binding.root
    }

    fun onClickButtonUpdate(name:String, addres:String, district:String, city:String){
        val referance = storage.reference
        val imageReferance = referance.child("profile_photos").child(user)
        progressBar.visibility = View.VISIBLE

        if (choosenImage != null){
            imageReferance.putFile(choosenImage!!).addOnSuccessListener { taskSnapshot ->
                val loadedImageReferance = storage.reference.child("profile_photos").child(user)
                loadedImageReferance.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()

                    userInfo = UserInfo(downloadUrl,name,addres,district,city)

                    updateDatabase()

                }.addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }else{
            userInfo.name = name
            userInfo.address = addres
            userInfo.district = district
            userInfo.city = city

            updateDatabase()
        }
    }

    fun onClickImage(){
        if (ContextCompat.checkSelfPermission(requireContext(), galleryPermission) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(galleryPermission),1)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,2)
        }
    }

    fun updateDatabase(){
        database.collection("UserInfo").document(user).set(userInfo).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val toast = R.string.infos_updated
                Toast.makeText(requireContext(),toast,Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
        }
        progressBar.visibility = View.INVISIBLE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED){
                val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            choosenImage = data.data
            if (choosenImage != null){
                val source = ImageDecoder.createSource(requireActivity().contentResolver,choosenImage!!)
                choosenBitmap = ImageDecoder.decodeBitmap(source)
                binding.ivProfile.setImageBitmap(choosenBitmap)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
