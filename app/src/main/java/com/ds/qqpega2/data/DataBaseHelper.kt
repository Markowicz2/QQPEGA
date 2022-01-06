package com.ds.qqpega2.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ds.qqpega2.data.constants.DataBaseConstants

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


companion object{
    private const val DATABASE_VERSION = 1
    private const val DATABASE_NAME = "QQPega"

    private const val CREATE_TABLE = ("create table "+ DataBaseConstants.QQpega.TABLE_NAME +" (" +
            DataBaseConstants.QQpega.COLUMNS.id + " integer primary key autoincrement," +
            ")")
}



}