package com.example.mysololife.contentsList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R

class ContentsRvAdaptor(val context : Context, var item : ArrayList<ContentModel>) : RecyclerView.Adapter<ContentsRvAdaptor.ViewHolder>(){
    //recyclerview item클릭방법
    interface ItemClick {
        fun onClick(view : View, position: Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentsRvAdaptor.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentsRvAdaptor.ViewHolder, position: Int) {
        if(itemClick != null) {
            holder.itemView.setOnClickListener { v->
                itemClick?.onClick(v, position)
            }
        }

        holder.bindItem(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }


    inner class ViewHolder( itemview: View) : RecyclerView.ViewHolder(itemview){
        fun bindItem(item: ContentModel){
            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)

            contentTitle.text = item.title

            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)
        }

    }

}