package com.example.labexam4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labexam4.ViewModel.AddDiaryActivity
import com.example.labexam4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DiaryDatabaseHelper
    private lateinit var diaryAdapter: DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DiaryDatabaseHelper(this)
        diaryAdapter = DiaryAdapter(db.getAllFriends(), this)

        binding.diaryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.diaryRecyclerView.adapter = diaryAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddDiaryActivity::class.java)
            startActivity(intent)
        }

        // Initialize search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                diaryAdapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val searchQuery = binding.searchView.query.toString()
        diaryAdapter.refreshData(db.getAllFriends(), searchQuery)
    }
}
