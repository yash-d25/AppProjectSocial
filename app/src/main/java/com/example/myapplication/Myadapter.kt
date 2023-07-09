package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.Message1Binding
import com.example.myapplication.databinding.Text2Binding
import com.google.firebase.database.core.Context

class Myadapter(val contextmessage : android.content.Context, val list : ArrayList<Message>, private val UserName : String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var message_by_person=1
    var message_by_user=2
    override fun getItemViewType(position: Int): Int {
        if(list[position].userid == UserName){
            return message_by_user
        }
        else{
            return message_by_person
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==message_by_user){
            return MyUser(LayoutInflater.from(contextmessage).inflate(R.layout.message1,parent,false))
        }
        else{
            return User_to_send(LayoutInflater.from(contextmessage).inflate(R.layout.text2,parent,false))
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == message_by_user){
            var viewholder=holder as MyUser
            viewholder.binding.textViewY.text= list[position].str2
        }
        else{
            var user_to_send = holder as User_to_send
            user_to_send.binding.textView5.text= list[position].str2
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class User_to_send (itemView2 : View) : RecyclerView.ViewHolder(itemView2){
        var binding = Text2Binding.bind(itemView2)

}
    inner class MyUser(itemView : View) : RecyclerView.ViewHolder(itemView){
        var binding = Message1Binding.bind(itemView)
    }


}