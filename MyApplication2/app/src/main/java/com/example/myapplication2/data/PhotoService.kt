package com.example.myapplication2.data


import com.example.myapplication2.data.model.Photos
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


 //建立连接  与互联网上的服务器通信  获取原始数据
 // 定义静态变量  创建了一个Retrofit单例，并与一个Gson格式转换器绑定
const val baseUrl = "https://api.nasa.gov/"
val photoService: PhotoService = Retrofit.Builder()
    // Retrofit 包含一个 ScalarsConverter，它支持字符串和其他基元类型，
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    // 使用 GsonConverterFactory 实例对构建器调用 addConverterFactory()
    .build()
    .create(PhotoService::class.java);
   // 使用 baseUrl() 方法添加网络服务的基础 URL 再调用 build() 创建 Retrofit 的对象

// 设置两个GET请求
interface PhotoService {
    // addPhotos 这个请求从 baseUrl/mars-photos/api/v1/rovers/curiosity/photos 中获取数据
    // 同时后面有两个参数 sol 和 apiKey
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun allPhotos(@Query("sol") sol: Int, @Query("api_key") apiKey: String): Call<Photos>

    //  这个请求从指定的URL中下载图片
    @GET
    fun downloadPhoto(@Url url: String): Call<ResponseBody>
    }
