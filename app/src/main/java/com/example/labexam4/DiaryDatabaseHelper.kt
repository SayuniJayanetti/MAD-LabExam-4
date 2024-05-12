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
            "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_TELEPHONE INTEGER, $COLUMN_ADDRESS TEXT, $COLUMN_BIRTHDAY INTEGER)"
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
}