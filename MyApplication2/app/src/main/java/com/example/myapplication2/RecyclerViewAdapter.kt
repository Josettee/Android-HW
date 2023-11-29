package com.example.myapplication2


import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


// RecyclerView 可以轻松高效地显示大量数据  RecyclerView 库会根据需要动态创建元素
// Adapter 是一个控制器对象，从模型层获取数据，然后提供给 RecyclerView 显示
class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val photos = ArrayList<Bitmap>()

    //确定布局后，需要实现 Adapter 和 ViewHolder
    // 这两个类配合使用，共同定义数据的显示方式  ViewHolder 是包含列表中各列表项的布局的 View 的封装容器
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.imageView)
        }
    }

    // 创建 ViewHolder 和要显示的视图
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_item, viewGroup, false)

        return ViewHolder(view)
    }

    // 寻找数据并绑定到 ViewHolder 的视图
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val photo = photos[position]
        viewHolder.imageView.setImageBitmap(photo)
    }

    // 包含的对象数量
    override fun getItemCount() = photos.size

    //添加占位图
    fun add(bitmap: Bitmap) = synchronized(Unit) {
        photos.add(bitmap)
        notifyItemInserted(itemCount)
    }
}
