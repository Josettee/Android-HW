package com.example.myapplication2



import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.data.PhotoDbHelper
import com.example.myapplication2.data.photoService
import java.lang.ref.WeakReference

const val TAG = "NASA Curiosity"
//获取apiKey  https://api.nasa.gov/
const val apiKey = "iwfrxQFMfhbH0Qyh2AIsrMCjJdOTa8EfPYf1clbc"

class MainActivity : AppCompatActivity() {

    //数据库
    val dbHelper = PhotoDbHelper(this)
    val adapter = RecyclerViewAdapter()

    object StatusCode {
        const val OK = 200
        const val FAILED = 500
        const val IMG_OK = 201
    }

    //关闭数据库
    //由于在数据库关闭时，调用 getWritableDatabase() 和 getReadableDatabase() 的成本比较高，
    // 因此只要有可能需要访问数据库，就应保持数据库连接处于打开状态。
    // 通常情况下，最好在发出调用的 Activity 的 onDestroy() 中关闭数据库。
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 布局文件引入
        setContentView(R.layout.activity_main)

        val photosView: RecyclerView = findViewById(R.id.photos_view)
        //使用 GridLayoutManager 将图片排列到二维网格中
        photosView.layoutManager = GridLayoutManager(this, 3)
        photosView.adapter = adapter
        photosView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        photosView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val handler = MainHandler(WeakReference(this))

        //多线程+
        Thread {
            // 通过PhotoService获取到json格式的原始数据
            // retrofit的响应主体
            val resp = photoService.allPhotos(sol = 1000, apiKey).execute().body()
            if (resp == null) {
                handler.sendMessage(Message.obtain(null, StatusCode.FAILED))

            } else {
                handler.sendMessage(Message.obtain(null, StatusCode.OK, resp))
            }
        }.start();
    }
}
