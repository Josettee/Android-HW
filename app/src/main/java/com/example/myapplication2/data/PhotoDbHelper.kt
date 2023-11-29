package com.example.myapplication2.data


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.myapplication2.data.model.PhotoEntry


// 构造SQL语句
private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${PhotoEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${PhotoEntry.COLUMN_NAME_URL} TEXT," +
            "${PhotoEntry.COLUMN_NAME_IMAGE} BLOB)"

private const val SQL_CREATE_INDEX =
    "CREATE INDEX url_index ON ${PhotoEntry.TABLE_NAME} (${PhotoEntry.COLUMN_NAME_URL})"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${PhotoEntry.TABLE_NAME}"

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "Photo.db"


class PhotoDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //创建数据库
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_INDEX)
    }

    //数据库的版本更新
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    //companion object {
       // const val DATABASE_VERSION = 1
        //const val DATABASE_NAME = "Photo.db"
    //}
}