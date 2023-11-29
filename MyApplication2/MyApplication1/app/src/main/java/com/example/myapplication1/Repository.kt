package com.example.myapplication1


//定义全局对象Repository完成共享
object Repository {
    val methodList = StringBuilder()  // 储存log信息
    val status = HashMap<String, String>()  // 键值对储存 以便覆盖刷新
}