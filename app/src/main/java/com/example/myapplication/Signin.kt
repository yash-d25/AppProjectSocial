package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivitySigninBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.checkerframework.checker.units.qual.Length

class Signin : AppCompatActivity() {
    lateinit var binding: ActivitySigninBinding
    lateinit var database: DatabaseReference
    companion object{
        const val key25="com.example.myapplication.databinding.ActivitySigni.key"
        const val id="com.example.myapplication.databinding.Activity.key"


    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        binding=ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val id=binding.unique1.text.toString()
            val password=binding.password1.text.toString()
            if(id.trim().isEmpty()){
                binding.unique1.error="Required"
            }
            if(password.trim().isEmpty()){
                binding.password1.error="Required"
            }
            if(id.isNotEmpty() && password.isNotEmpty()){
            database=FirebaseDatabase.getInstance().getReference("Users")
            database.child(id).get().addOnSuccessListener{
                if(it.exists()){
                    val pass=it.child("password").value
                    if(pass==password){
                        val text=binding.unique1.text.toString()
                        Toast.makeText(this,"User found!",Toast.LENGTH_SHORT).show()

                        val intent=Intent(applicationContext,signup::class.java)
                        intent.putExtra(key25,text)
                        startActivity(intent)
                        finish()




                    }
                    else{
                        Toast.makeText(this,"Incorrect password!",Toast.LENGTH_SHORT).show()
                        binding.password1.text?.clear()
                    }

                }
                else{
                    Toast.makeText(this,"Please sign up first !",Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{
                Toast.makeText(this,"Some error occured",Toast.LENGTH_LONG).show()


            }

            }


        }

    }
}