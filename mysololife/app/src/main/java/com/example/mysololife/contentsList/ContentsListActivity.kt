package com.example.mysololife.contentsList

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue


class ContentsListActivity : AppCompatActivity() {
    lateinit var myRef : DatabaseReference
    var bookmarkIdList = mutableListOf<String>()
    lateinit var rvAdaptor : ContentsRvAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)
        var rv: RecyclerView = findViewById(R.id.rv)
        var items = ArrayList<ContentModel>()

        var keyList = ArrayList<String>()
        rvAdaptor = ContentsRvAdaptor(baseContext, items,keyList,bookmarkIdList)

        // Write a message to the database
        val database = Firebase.database

        //파라미터를 받습니다
        myRef = database.getReference(intent.getStringExtra("category").toString())

        
        var postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    //firebase와 같은 형태의 ㅁmodel 클래스에 맞게끔 매
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    keyList.add(dataModel.key.toString())
                }
                //비동기 방식이기 때문에 해당 함수가 실행된 이후 어답터를 갱신해준다
                rvAdaptor.notifyDataSetChanged()
                Log.d("ContentListActivity", items.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }

        //fire base database 접속
        myRef.addValueEventListener(postListener)
        rv.adapter = rvAdaptor
        rv.layoutManager = GridLayoutManager(this, 2) //가로 2개 배치
        getBookmark()

//        rvAdaptor.itemClick = object : ContentsRvAdaptor.ItemClick {
//            override fun onClick(view: View, position: Int) {
//                Toast.makeText(baseContext, items[position].title, Toast.LENGTH_SHORT).show()
//
//                var intent = Intent(this@ContentsListActivity, ContentShowActivity::class.java)
//                //이미지를 누르면 웹뷰를 호출
//                intent.putExtra("url", items[position].imageUrl)
//                startActivity(intent)
//            }
//
//        }
    }

    private fun getBookmark(){
        var postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookmarkIdList.clear()
                for (dataModel in dataSnapshot.children) {
                    //Log.d("ContentListActivity getBookmark",dataModel.toString())
                    bookmarkIdList.add(dataModel.key.toString())
                }
                //비동기 방식이기 때문에 해당 함수가 실행된 이후 어답터를 갱신해준다
                rvAdaptor.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)
    }
}

//items.add(ContentModel("title1", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FblYPPY%2Fbtq66v0S4wu%2FRmuhpkXUO4FOcrlOmVG4G1%2Fimg.png", "https://philosopher-chan.tistory.com/1235?category=941578"))
//items.add(ContentModel("title2", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FznKK4%2Fbtq665AUWem%2FRUawPn5Wwb4cQ8BetEwN40%2Fimg.png", "https://philosopher-chan.tistory.com/1236?category=941578"))
//items.add(ContentModel("title3", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbtig9C%2Fbtq65UGxyWI%2FPRBIGUKJ4rjMkI7KTGrxtK%2Fimg.png", "https://philosopher-chan.tistory.com/1237?category=941578"))
//items.add(ContentModel("title4", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOYyBM%2Fbtq67Or43WW%2F17lZ3tKajnNwGPSCLtfnE1%2Fimg.png", "https://philosopher-chan.tistory.com/1238?category=941578"))
//items.add(ContentModel("title5", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fekn5wI%2Fbtq66UlN4bC%2F8NEzlyot7HT4PcjbdYAINk%2Fimg.png", "https://philosopher-chan.tistory.com/1239?category=941578"))
//items.add(ContentModel("title6", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F123LP%2Fbtq65qy4hAd%2F6dgpC13wgrdsnHigepoVT1%2Fimg.png", "https://philosopher-chan.tistory.com/1240?category=941578"))
//items.add(ContentModel("title7", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fl2KC3%2Fbtq64lkUJIN%2FeSwUPyQOddzcj6OAkPKZuk%2Fimg.png","https://philosopher-chan.tistory.com/1241?category=941578"))
//items.add(ContentModel("title8", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmBh5u%2Fbtq651yYxop%2FX3idRXeJ0VQEoT1d6Hln30%2Fimg.png", "https://philosopher-chan.tistory.com/1242?category=941578"))
//items.add(ContentModel("title9", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlOnja%2Fbtq69Tmp7X4%2FoUvdIEteFbq4Z0ZtgCd4p0%2Fimg.png", "https://philosopher-chan.tistory.com/1243?category=941578"))
//
//items.forEach{
//    item-> myRef.push().setValue(item)
//}
