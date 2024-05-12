package com.example.labexam4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labexam4.ViewModel.AddDiaryActivity
import com.example.labexam4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : DiaryDatabaseHelper
    private lateinit var diaryAdapter : DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DiaryDatabaseHelper(this)
        diaryAdapter = DiaryAdapter(db.getAllFriends(),this)

        binding.diaryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.diaryRecyclerView.adapter = diaryAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddDiaryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()
        diaryAdapter.refreshData(db.getAllFriends())
    }
}
