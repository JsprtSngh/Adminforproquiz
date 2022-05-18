package com.project1.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
data class  quest(var question: String , var a: String, var b : String, var c: String, var d: String, var correctanswer: String){

}
var ids = mutableSetOf<String>()
var ques = mutableSetOf<quest>()
val database = Firebase.database
val myRef = database.getReference("Questions")
lateinit var  progress :ProgressBar
class questions : AppCompatActivity() {

   var lastposition:Int =0
    lateinit var recyclerView :RecyclerView
    lateinit var adapter :savedquestionsadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        var l = LinearLayoutManager(this)
        recyclerView.layoutManager = l
        progress = findViewById(R.id.progressBar)
        progress.visibility = View.VISIBLE

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastposition =l.findFirstCompletelyVisibleItemPosition()

            }
        })
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (messageSnapshot in snapshot.children) {
                    ids.add(messageSnapshot.key.toString())
                    var q = quest(messageSnapshot.child("question").value.toString(),
                        messageSnapshot.child("a").value.toString(),
                        messageSnapshot.child("b").value.toString(),
                        messageSnapshot.child("c").value.toString(),
                        messageSnapshot.child("d").value.toString(),
                        messageSnapshot.child("correctanswer").value.toString()
                    )
                    ques.add(q)

                }

                adapter = savedquestionsadapter( this@questions)
                progress.visibility = View.GONE
                recyclerView.adapter = adapter

                Log.e("Value",snapshot.toString())
                Log.e("Value",ids.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Value", "Failed to read value.", error.toException())
            }

        })
        findViewById<FloatingActionButton>(R.id.floatingbutton_addquestions).setOnClickListener {
            val i = Intent(this, addquestions::class.java)
            startActivity(i)
        }

    }

    override fun onResume() {
        super.onResume()
        ques.clear()

        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (messageSnapshot in snapshot.children) {
                    ids.add(messageSnapshot.key.toString())
                    var q = quest(messageSnapshot.child("question").value.toString(),
                        messageSnapshot.child("a").value.toString(),
                        messageSnapshot.child("b").value.toString(),
                        messageSnapshot.child("c").value.toString(),
                        messageSnapshot.child("d").value.toString(),
                        messageSnapshot.child("correctanswer").value.toString()
                    )
                    ques.add(q)

                }
//
                adapter = savedquestionsadapter( this@questions)

                recyclerView.adapter = adapter
                recyclerView.scrollToPosition(lastposition)
                Log.e("Value",snapshot.toString())
                Log.e("Value", ques.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Value", "Failed to read value.", error.toException())
            }

        })
    }

}