package com.example.myapplication2.data.model


import android.provider.BaseColumns

//  为了多线程中操作数据库系统的安全性及简洁性   这里使用 android.provider 中的 BaseColumns 统一管理
object PhotoEntry : BaseColumns {
    const val TABLE_NAME = "photo_cache"
    const val COLUMN_NAME_URL = "url"
    const val COLUMN_NAME_IMAGE = "image"
}