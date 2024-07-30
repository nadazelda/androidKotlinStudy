package com.example.mysololife.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mysololife.R

class BoardListAdaptor(var boardList : MutableList<BoardModel>): BaseAdapter() {
    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convert =convertView
        if( convert == null){
            convert = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item,parent,false)

        }

        var title = convert?.findViewById<TextView>(R.id.titleArea)
        var content = convert?.findViewById<TextView>(R.id.contentArea)
        var time = convert?.findViewById<TextView>(R.id.timeArea)

        title!!.text = boardList[position].title
        content!!.text = boardList[position].content
        time!!.text = boardList[position].time

        return convert!!
    }


}