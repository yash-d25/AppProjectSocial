package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMain2Binding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.checkerframework.checker.units.qual.Length




class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityMain2Binding
    lateinit var database2 : DatabaseReference
    lateinit var storage2 : FirebaseStorage
    lateinit var user_about : user_info
    override fun onCreate(savedInstanceState: Bundle?) {
        actionBar?.hide()
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val name=intent.getStringExtra(MainActivity.key1)

        binding.textView2.text="Welcome "+name.toString()+" !"


        binding.sign.setOnClickListener{
            var filename2=intent.getStringExtra(MainActivity.key2).toString()
            var instagram = "https://www.instagram.com/"+binding.instagram.text.toString()
            var twitter ="https://twitter.com/"+binding.twitter.text.toString()
            var about2 = binding.bio.text.toString()
            val user_about=user_info(instagram,twitter,about2)
            if(binding.instagram.text.toString().trim().isEmpty()){
                binding.instagram.error="Please enter your instagram ID"
            }
            if(binding.twitter.text.toString().trim().isEmpty()){
                binding.twitter.error="Please enter your Twitter ID"
            }
            if(binding.bio.text.toString().trim().isEmpty()){
                binding.bio.error="Please enter your Bio"
            }
            if((binding.checkBox3.isChecked==false)){
                Toast.makeText(this,"Please accept the check box",Toast.LENGTH_SHORT).show()

            }
            else {
                database2 = FirebaseDatabase.getInstance().getReference("about")

                database2.child(filename2)
                database2.child(filename2).setValue(user_about)
                Toast.makeText(this,"User Registered!",Toast.LENGTH_SHORT)
                val intent= Intent(this,Signin::class.java)
                startActivity(intent)
                finish()

            }

        }




    }
}