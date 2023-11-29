package com.example.myapplication2




import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.example.myapplication2.data.PhotoDbHelper
import com.example.myapplication2.data.model.Photo
import com.example.myapplication2.data.model.PhotoEntry
import com.example.myapplication2.data.model.Photos
import com.example.myapplication2.data.photoService
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainHandler(
    private val ref: WeakReference<MainActivity>,
    private val executor: ExecutorService = Executors.newFixedThreadPool(20)
) :
    Handler(Looper.getMainLooper()) {
    override fun handleMessage(msg: Message) {

        val activity: MainActivity = ref.get() ?: return

        when (msg.what) {
            //下载渲染图片
            MainActivity.StatusCode.OK -> {
                val photos: List<Photo> = (msg.obj as Photos).photos
                Thread {
                    for (photo in photos) {
                        executor.execute {
                            loadImage(photo, activity.dbHelper)
                        }
                    }
                }.start()
            }
            //  渲染出一张图片到 RecyclerView 中
            MainActivity.StatusCode.IMG_OK -> {
                val bitmap = msg.obj as Bitmap
                activity.adapter.add(bitmap)
            }
            //如果失败了，获取的photo列表失败，返回错误代码
            MainActivity.StatusCode.FAILED -> {
                Toast.makeText(activity, activity.getString(R.string.fail_note), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun fetchRemoteImage(url: String, dbHelper: PhotoDbHelper) {
        try {
            val bytes = photoService.downloadPhoto(url).execute().body()?.bytes() ?: return
            //从网络下载

            sendMessage(
                //通知主线程进行渲染
                Message.obtain(
                    null,
                    MainActivity.StatusCode.IMG_OK,
                    BitmapFactory.decodeStream(bytes.inputStream())
                )
            )

            //写入数据库
            val writeDb = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(PhotoEntry.COLUMN_NAME_URL, url)
                put(PhotoEntry.COLUMN_NAME_IMAGE, bytes)
            }
            writeDb?.insert(PhotoEntry.TABLE_NAME, null, values)
        } catch (ex: Exception) {
            return
        }
    }

    private fun loadImage(photo: Photo, dbHelper: PhotoDbHelper) {
        val readDb = dbHelper.readableDatabase
        val cursor = readDb.query(
            PhotoEntry.TABLE_NAME,
            arrayOf(PhotoEntry.COLUMN_NAME_IMAGE),
            "${PhotoEntry.COLUMN_NAME_URL} = ?",
            arrayOf(photo.imgSrc),
            null,
            null,
            null
        )
        if (cursor.count != 0) {
            //查询本地数据库
            Log.i(TAG, "Fetch image from database: ${photo.imgSrc}")
            val bytes: ByteArray
            with(cursor) {
                cursor.moveToNext()
                bytes = getBlob(0)
            }
            sendMessage(
                Message.obtain(
                    null,
                    MainActivity.StatusCode.IMG_OK,
                    BitmapFactory.decodeStream(bytes.inputStream())
                )
            )
        } else {
            //本地数据库不存在，需要下载
            Log.i(TAG, "Fetch image from url: ${photo.imgSrc}")
            fetchRemoteImage(photo.imgSrc, dbHelper)
        }
        cursor.close()
    }
}
