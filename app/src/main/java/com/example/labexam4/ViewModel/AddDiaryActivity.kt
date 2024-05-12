package com.example.labexam4.ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.labexam4.Diary
import com.example.labexam4.DiaryDatabaseHelper
import com.example.labexam4.R
import com.example.labexam4.databinding.ActivityAddDiaryBinding

class AddDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDiaryBinding
    private lateinit var db: DiaryDatabaseHelper  //access database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DiaryDatabaseHelper(this)

        //data will be stored only if the save button is clicked
        binding.saveButton.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            val telephone = binding.telephoneEditText.text.toString().toIntOrNull() ?: 0
            val address = binding.addressEditText.text.toString()
            val birthday = binding.birthdayEditText.text.toString().toIntOrNull() ?: 0

            val diary = Diary(0, name, telephone, address, birthday)
            db.insertFriend(diary)
            finish()
            Toast.makeText(this, "Friend Saved", Toast.LENGTH_SHORT).show()
        }
    }
}