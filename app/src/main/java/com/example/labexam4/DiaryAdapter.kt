package com.example.labexam4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam4.ViewModel.UpdateDiaryActivity

class DiaryAdapter (private var friends: List<Diary>,context: Context) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>(){

    private val db: DiaryDatabaseHelper = DiaryDatabaseHelper(context)

    class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val telephoneTextView: TextView = itemView.findViewById(R.id.telephoneTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        val birthdayTextView: TextView = itemView.findViewById(R.id.birthdayTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_item, parent,false)
        return DiaryViewHolder(view)
    }

    override fun getItemCount(): Int = friends.size

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val diary = friends[position]
        holder.nameTextView.text = diary.name
        holder.telephoneTextView.text = diary.telephone
        holder.addressTextView.text = diary.address
        holder.birthdayTextView.text = diary.birthday

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateDiaryActivity::class.java).apply{

                putExtra("diary_id", diary.id)

            }

            holder.itemView.context.startActivity(intent)
        }


        holder.deleteButton.setOnClickListener{
            db.deleteFriend(diary.id)
            refreshData(db.getAllFriends())
            Toast.makeText(holder.itemView.context,"Note Deleted", Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshData(newNotes: List<Diary>) {
        friends = newNotes
        notifyDataSetChanged()
    }

    }