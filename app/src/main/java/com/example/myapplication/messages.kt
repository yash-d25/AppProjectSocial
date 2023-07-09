package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.myapplication.databinding.ActivityMessagesBinding
import android.util.Base64
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.graphics.createBitmap
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import kotlin.math.sign

class messages : AppCompatActivity() {
    lateinit var binding :ActivityMessagesBinding
    lateinit var yd : StorageReference
    lateinit var databasereference2 : DatabaseReference
    lateinit var list2 : ArrayList<Message>
    lateinit var hsn : Message



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        actionBar?.hide()
        binding=ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent2=intent

        var send=intent2.getStringExtra(signup.key2)
        var userid2 = intent2.getStringExtra(signup.key1)
        var id = intent2.getStringExtra(signup.key26)

        binding.textView4.text=send.toString()
        yd=FirebaseStorage.getInstance().getReference("images/"+id+".jpeg")
        var localfily= File.createTempFile("Tempfile","")
        yd.getFile(localfily).addOnSuccessListener {
            var bit=BitmapFactory.decodeFile(localfily.absolutePath)
            binding.imageView2.setImageBitmap(bit)

        }.addOnFailureListener{
            Toast.makeText(this,"Some Error Occured!", Toast.LENGTH_SHORT).show()
        }
        binding.imageButton.setOnClickListener{
            navigateUpTo(intent2)

        }
        databasereference2=FirebaseDatabase.getInstance().getReference("Message")
        list2= arrayListOf()

        databasereference2.child(userid2!!).child(id!!).addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                list2.clear()


                for(value1 in snapshot.children) {
                    var personid = value1.child("personid").value.toString()
                    var str = value1.child("str2").value.toString()
                    var yr = value1.child("userid").value.toString()
                    hsn = Message(str, yr, personid)
                    list2.add(hsn)
                }
                binding.myview.adapter=Myadapter(applicationContext,list2,userid2)
                binding.myview.layoutManager=LinearLayoutManager(this@messages)
                (binding.myview.layoutManager as LinearLayoutManager).scrollToPosition(list2.size-1)

            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Toast.makeText(this@messages,"There is some issue!",Toast.LENGTH_SHORT).show()
            }




        })

        binding.sendtext.setOnClickListener{
            var content2=binding.textview.text
            if(content2.toString()==""){
                Toast.makeText(this,"Please enter the message",Toast.LENGTH_SHORT).show()
            }
            else{
                databasereference2=FirebaseDatabase.getInstance().getReference("Message")


            val message =Message(content2.toString(),userid2.toString(),id.toString())

                var randomkey=databasereference2.child(userid2!!).child(id!!).push().key
                databasereference2=FirebaseDatabase.getInstance().getReference("Message")
                databasereference2.child(userid2!!).child(id!!).child(randomkey!!).setValue(message).addOnSuccessListener {

                    var randomkey7=databasereference2.child(id!!).child(userid2!!).push().key
                    databasereference2.child(id!!).child(userid2!!).child(randomkey7!!).setValue(message)
                    binding.textview.text?.clear()
                }



            }
        }


    }
}
