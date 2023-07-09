package com.example.myapplication

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager.OnActivityResultListener
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.checkerframework.checker.units.qual.h
import kotlin.contracts.contract
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var arraylist=ArrayList<String>()
    var current_index=0
    lateinit var Name: String
    lateinit var age:String
    lateinit var password:String
    lateinit var idunique :String
    lateinit var database :DatabaseReference
    lateinit var profile :Uri
    lateinit var storage : StorageReference
    companion object{
        const val key1="com.example.myapplication.key1"
        const val key2="com.example.myapplication.key2"
        const val key3="com.example.myapplication.key3"
        const val key4="com.example.myapplication.key4"
        var h=false
        var image = false
        var check=0
        var int2=false


    }


    private val picture=registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.imageview.setImageURI(it)
        profile=it!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        actionBar?.hide()
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.imageview.setOnClickListener {
            image=true
            picture.launch("image/*")
        }
        binding.intentsignin.setOnClickListener{
            val intent=Intent(this,Signin::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            check=0
            int2=false
            idunique = binding.unique.text.toString()
            Name = binding.name.text.toString()
            age = binding.age.text.toString()
            password = binding.password.text.toString()
            int2=try{
                age.toInt()
                if(age.toInt()>=18) {true}
                else  {false}
            }
            catch (ex: NumberFormatException){
                false
            }
            if(!int2){
                binding.age.text?.clear()
                Toast.makeText(this,"Please enter valid Age above 18!",Toast.LENGTH_SHORT).show()
            }
            else{
                check= check+1
            }

            if(!image){
                Toast.makeText(this,"Please select a profile photo!",Toast.LENGTH_SHORT).show()
            }
            else{
                check= check+1
            }
            if(binding.unique.text.toString()=="") {
                binding.unique.error = "Field is required!"

            }
            else{
                check=check+1
            }
            if(binding.name.text.toString()==""){
                binding.name.error="Field is required!"
            }
            else{
                check=check+1
            }
            if(binding.age.text.toString()==""){
                binding.age.error="Field is required!"
            }
            else{
                check=check+1
            }
            if(binding.password.text.toString()==""){
                binding.password.error="Field is required!"
            }
            else{
                check= check+1
            }
            if(check<6){
                Toast.makeText(this,"Please enter all the required info!",Toast.LENGTH_SHORT).show()
            }
            else {

                database = FirebaseDatabase.getInstance().getReference("Users")


                val filename = "images/" + idunique + ".jpeg"
                val User_val = user_data(idunique, Name, password, age)
                var arrayuser = ArrayList<String>()
                database = FirebaseDatabase.getInstance().getReference("Users")
                database.get().addOnSuccessListener {
                    if (it.exists()) {
                        for (user in it.children) {
                            arrayuser.add(user.key!!)
                            if (user.key!! == idunique) {
                                h = true
                                break
                            }
                        }

                        if (h) {

                            binding.unique.error = "ID must be unique!"
                            binding.unique.text?.clear()
                            h = false

                            Toast.makeText(
                                applicationContext,
                                "User ID is not unique!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (!h) {

                            database.child(idunique)
                            database.child(idunique).setValue(User_val)

                            storage = FirebaseStorage.getInstance().getReference(filename)
                            storage.putFile(profile)


//
//
                            intent = Intent(applicationContext, MainActivity2::class.java)
                            intent.putExtra(key1, Name)
                            intent.putExtra(key2, idunique)
                            intent.putExtra(key3, age)
                            intent.putExtra(key4, password)
                            startActivity(intent)
                            finish()


                        }


                    }
                }
            }



            }
        }






}
