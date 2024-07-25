package com.example.mysololife.contentsList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R

class ContentsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)

        var rv : RecyclerView = findViewById(R.id.rv)
        var item = ArrayList<ContentModel>()
        item.add(ContentModel("title1",""))
        item.add(ContentModel("title2",""))
        item.add(ContentModel("title3",""))
        item.add(ContentModel("title4",""))
        var rvAdaptor = ContentsRvAdaptor(item)
        rv.adapter = rvAdaptor

        rv.layoutManager = GridLayoutManager(this,2) //가로 2개 배치

    }
}