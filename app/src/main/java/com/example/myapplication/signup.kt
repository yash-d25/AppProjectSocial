package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignupBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import kotlin.properties.Delegates


class signup : AppCompatActivity() {
    companion object{
        val key1 = "Userid"
        val key2 = "Receiverid"
        val key26 = "Photo"
    }

    lateinit var binding:ActivitySignupBinding
    lateinit var database : DatabaseReference

        lateinit var array : ArrayList<String>
        lateinit var database1 : DatabaseReference
        lateinit var age :String


    lateinit var storageReference: StorageReference
    lateinit var user : String
    lateinit var bitdh : Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        actionBar?.hide()
        binding=ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        database=FirebaseDatabase.getInstance().getReference("Users")
        val name=intent.getStringExtra(Signin.key25).toString()
        storageReference=FirebaseStorage.getInstance().getReference("images/"+name+".jpeg")
        val localfile= File.createTempFile("Tempfile","")
        storageReference.getFile(localfile).addOnSuccessListener {
            val bit=BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imageid.setImageBitmap(bit)

        }
        array=ArrayList<String>()
        var str=""
        database.child(name).get().addOnSuccessListener{
            str=it.child("name").value.toString()
            binding.name.text="Welcome "+str+","

        }


        database=FirebaseDatabase.getInstance().getReference("Users")
        database.get().addOnSuccessListener {
            var current=0

          


                Toast.makeText(this, "data found", Toast.LENGTH_SHORT)
               binding.person.text=it.key!!
            for(user in it.children){
                binding.bio.text=user.key!!
                val str=user.key!!
                array.add(str)
            }
            var listsize=array.size
            if(array[current]==name){
                current=(current+1)%listsize
            }

            var namedh=array[current]
            var username=""
            var info=""
            var instalink=""
            var twitterlink=""
            database=FirebaseDatabase.getInstance().getReference("Users")
            database1=FirebaseDatabase.getInstance().getReference("about")
            database.child(namedh).get().addOnSuccessListener{
                username=it.child("name").value.toString()
                age=it.child("age").value.toString()
                binding.person.text=username+","+age


            }
            database1.child(namedh).get().addOnSuccessListener{
                info=it.child("user_description").value.toString()
                instalink=it.child("instagram").value.toString()
                twitterlink=it.child("twitter").value.toString()
                binding.bio.text=info
                binding.social.text="Connect with "+username + "!"
                binding.insta.setOnClickListener{
                    val intent=Intent(Intent.ACTION_VIEW)
                    var url=instalink
                    intent.data=Uri.parse(url)
                    startActivity(intent)
                }
                binding.twitter.setOnClickListener{
                    val intent=Intent(Intent.ACTION_VIEW)
                    var url=twitterlink
                    intent.data=Uri.parse(url)
                    startActivity(intent)
                }
            }


            storageReference=FirebaseStorage.getInstance().getReference("images/"+namedh+".jpeg")
            var localfiledh= File.createTempFile("Tempfile","")
            storageReference.getFile(localfile).addOnSuccessListener {
                bitdh=BitmapFactory.decodeFile(localfile.absolutePath)
                binding.imgview.setImageBitmap(bitdh)

            }.addOnFailureListener{
                Toast.makeText(this,"Some Error Occured!",Toast.LENGTH_SHORT)
            }
            binding.textmessage.setOnClickListener{
                val intent2 = Intent(applicationContext,messages::class.java)

                intent2.putExtra(key2,username)
                intent2.putExtra(key1,name)
                intent2.putExtra(key26,namedh)


                startActivity(intent2)
            }


            binding.right.setOnClickListener(){
                current=(current+1)%listsize
                if(array[current]==name){
                    current=(current+1)%listsize
                }
                binding.screen.alpha= 1F
                binding.imgview.alpha= 0F
                binding.person.text="..."
                binding.right.isClickable=false
                binding.left.isClickable=false

                namedh=array[current]
                database=FirebaseDatabase.getInstance().getReference("Users")
                database.child(namedh).get().addOnSuccessListener{
                    username=it.child("name").value.toString()
                    age=it.child("age").value.toString()
                    binding.social.text="Connect with "+username + "!"
                    binding.person.text=username+ ","+age

                }
                database1=FirebaseDatabase.getInstance().getReference("about")
                database1.child(namedh).get().addOnSuccessListener{
                    info=it.child("user_description").value.toString()
                    instalink=it.child("instagram").value.toString()
                    twitterlink=it.child("twitter").value.toString()
                    binding.bio.text=info

                    binding.insta.setOnClickListener{
                        val intent=Intent(Intent.ACTION_VIEW)
                        var url=instalink
                        intent.data=Uri.parse(url)
                        startActivity(intent)
                    }
                    binding.twitter.setOnClickListener{
                        val intent=Intent(Intent.ACTION_VIEW)
                        var url=twitterlink
                        intent.data=Uri.parse(url)
                        startActivity(intent)
                    }
                }


                storageReference=FirebaseStorage.getInstance().getReference("images/"+namedh+".jpeg")
                localfiledh= File.createTempFile("Tempfile","")
                storageReference.getFile(localfile).addOnSuccessListener {
                    bitdh=BitmapFactory.decodeFile(localfile.absolutePath)
                    binding.screen.alpha=0F
                    binding.imgview.alpha=1F
                    binding.imgview.setImageBitmap(bitdh)
                    binding.right.isClickable=true
                    binding.left.isClickable=true


                }.addOnFailureListener{
                    Toast.makeText(this,"Some Error Occured!",Toast.LENGTH_SHORT)
                }


            }
            binding.left.setOnClickListener{
                current=(current-1+listsize)%listsize
                if(array[current]==name){
                    current=(current-1+listsize)%listsize
                }
                binding.screen.alpha= 1F
                binding.imgview.alpha= 0F
                binding.person.text="..."
                binding.right.isClickable=false
                binding.left.isClickable=false

                namedh=array[current]
                database=FirebaseDatabase.getInstance().getReference("Users")
                database.child(namedh).get().addOnSuccessListener{
                    username=it.child("name").value.toString()
                    age=it.child("age").value.toString()
                    binding.person.text=username + "," + age
                    binding.social.text="Connect with "+username + "!"


                }
                database1=FirebaseDatabase.getInstance().getReference("about")
                database1.child(namedh).get().addOnSuccessListener{
                    info=it.child("user_description").value.toString()
                    instalink=it.child("instagram").value.toString()
                    twitterlink=it.child("twitter").value.toString()
                    binding.bio.text=info
                    binding.insta.setOnClickListener{
                        val intent=Intent(Intent.ACTION_VIEW)
                        var url=instalink
                        intent.data=Uri.parse(url)
                        startActivity(intent)
                    }
                    binding.twitter.setOnClickListener{
                        val intent=Intent(Intent.ACTION_VIEW)
                        var url=twitterlink
                        intent.data=Uri.parse(url)
                        startActivity(intent)
                    }
                }


                storageReference=FirebaseStorage.getInstance().getReference("images/"+namedh+".jpeg")
                localfiledh= File.createTempFile("Tempfile","")
                storageReference.getFile(localfile).addOnSuccessListener {
                    bitdh=BitmapFactory.decodeFile(localfile.absolutePath)
                    binding.screen.alpha=0F
                    binding.imgview.alpha=1F


                    binding.imgview.setImageBitmap(bitdh)
                    binding.right.isClickable=true
                    binding.left.isClickable=true



                }.addOnFailureListener{
                    Toast.makeText(this,"Some Error Occured!",Toast.LENGTH_SHORT)
                }
            }

            }
        binding.button4.setOnClickListener{
            val intent=Intent(this,Signin::class.java)
            startActivity(intent)
            finish()
        }



}}