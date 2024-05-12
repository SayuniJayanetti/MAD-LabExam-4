package com.example.labexam4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam4.ViewModel.UpdateDiaryActivity

class DiaryAdapter(private var originalFriendsList: List<Diary>, private val context: Context) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>(), Filterable {

    private val db: DiaryDatabaseHelper = DiaryDatabaseHelper(context)
    private var filteredFriendsList: List<Diary> = originalFriendsList

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val telephoneTextView: TextView = itemView.findViewById(R.id.telephoneTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        val birthdayTextView: TextView = itemView.findViewById(R.id.birthdayTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_item, parent, false)
        return DiaryViewHolder(view)
    }

    override fun getItemCount(): Int = filteredFriendsList.size

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val diary = filteredFriendsList[position]
        holder.nameTextView.text = diary.name
        holder.telephoneTextView.text = diary.telephone
        holder.addressTextView.text = diary.address
        holder.birthdayTextView.text = diary.birthday

        holder.updateButton.setOnClickListener {
            val intent = Intent(context, UpdateDiaryActivity::class.java).apply {
                putExtra("diary_id", diary.id)
            }
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteFriend(diary.id)
            refreshData(db.getAllFriends())
            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes: List<Diary>, searchQuery: String? = null) {
        originalFriendsList = newNotes
        filteredFriendsList = if (!searchQuery.isNullOrEmpty()) {
            originalFriendsList.filter { it.name.contains(searchQuery, ignoreCase = true) }
        } else {
            originalFriendsList
        }
        notifyDataSetChanged()
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Diary>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(originalFriendsList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (friend in originalFriendsList) {
                        if (friend.name.toLowerCase().contains(filterPattern)) {
                            filteredList.add(friend)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredFriendsList = results?.values as List<Diary>
                notifyDataSetChanged()
            }
        }
    }
}
