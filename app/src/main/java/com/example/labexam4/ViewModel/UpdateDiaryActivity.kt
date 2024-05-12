package com.example.labexam4.ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.labexam4.Diary
import com.example.labexam4.DiaryDatabaseHelper
import com.example.labexam4.R
import com.example.labexam4.databinding.ActivityUpdateDiaryBinding

class UpdateDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDiaryBinding
    private lateinit var db: DiaryDatabaseHelper
    private var friendId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DiaryDatabaseHelper(this)

        friendId = intent.getIntExtra("diary_id", -1)
        if(friendId == -1){
            finish()
            return
        }

        val diary = db.getFriendById(friendId)
        binding.updateNameEditText.setText(diary.name)
        binding.updateTelephoneEditText.setText(diary.telephone)
        binding.updateAddressEditText.setText(diary.address)
        binding.updateBirthdayEditText.setText(diary.birthday)

        binding.updateSaveButton.setOnClickListener{
            val newName = binding.updateNameEditText.text.toString()
            val newTelephone = binding.updateTelephoneEditText.text.toString()
            val newAddress = binding.updateAddressEditText.text.toString()
            val newBirthday = binding.updateBirthdayEditText.text.toString()
            val updatedFriend = Diary(friendId, newName, newTelephone, newAddress, newBirthday)
            db.updateFriend(updatedFriend)
            finish()
            Toast.makeText(this,"Changes Saved", Toast.LENGTH_SHORT).show()
        }

    }
}