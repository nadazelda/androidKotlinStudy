package com.example.mysololife.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef

class BookmarkRvAdaptor(val context : Context, var item : ArrayList<ContentModel>, var keylist : ArrayList<String>, var bookmarkList : MutableList<String> )
    : RecyclerView.Adapter<BookmarkRvAdaptor.ViewHolder>(){
    //recyclerview item클릭방법
//    interface ItemClick {
//        fun onClick(view : View, position: Int)
//    }
//    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkRvAdaptor.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookmarkRvAdaptor.ViewHolder, position: Int) {
//        if(itemClick != null) {
//            holder.itemView.setOnClickListener { v->
//                itemClick?.onClick(v, position)
//            }
//        }
        holder.bindItem(item[position], keylist[position]);
    }

    override fun getItemCount(): Int {
        return item.size
    }


    inner class ViewHolder( itemview: View) : RecyclerView.ViewHolder(itemview){
        fun bindItem(item: ContentModel, key: String){
            itemView.setOnClickListener() {
                var intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url", item.imageUrl)
                //이미지를 누르면 웹뷰를 호출
                itemView.context.startActivity(intent)
            }

            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea= itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if( bookmarkList.contains(key) ) {
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            }else{
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }

            contentTitle.text = item.title

            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }

}