package com.example.labexam4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DiaryDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {


    companion object {
        //all the constants
        private const val DATABASE_NAME = "diaryapp.dp"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allfriends"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_TELEPHONE = "telephone"
        private const val COLUMN_ADDRESS = "address"
        private const val COLUMN_BIRTHDAY = "birthday"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_TELEPHONE TEXT, $COLUMN_ADDRESS TEXT, $COLUMN_BIRTHDAY TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertFriend(note: Diary) {
        val db = writableDatabase  //it can be modified
        val values = ContentValues().apply {
            put(COLUMN_NAME, note.name)
            put(COLUMN_TELEPHONE, note.telephone)
            put(COLUMN_ADDRESS, note.address)
            put(COLUMN_BIRTHDAY, note.birthday)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllFriends(): List<Diary> {
        val friendsList = mutableListOf<Diary>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)


        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val telephone= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEPHONE))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
            val birthday = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDAY))

            val diary = Diary(id, name, telephone, address, birthday)
            friendsList.add(diary)
        }
        cursor.close()
        db.close()
        return friendsList
    }

    fun updateFriend(diary: Diary){

        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_NAME,diary.name)
            put(COLUMN_TELEPHONE,diary.telephone)
            put(COLUMN_ADDRESS,diary.address)
            put(COLUMN_BIRTHDAY,diary.birthday)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(diary.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()

    }

    fun getFriendById(friendId: Int): Diary{

        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $friendId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val telephone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEPHONE))
        val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
        val birthday = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDAY))

        cursor.close()
        db.close()
        return Diary(id,name,telephone,address,birthday)

    }

    fun deleteFriend(friendId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(friendId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}