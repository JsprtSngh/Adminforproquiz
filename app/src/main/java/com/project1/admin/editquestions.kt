package com.project1.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class editquestions : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databaseReference = FirebaseDatabase.getInstance().getReference("Questions");
        setContentView(R.layout.editquestiondialog)//check box
        val a = findViewById<CheckBox>(R.id.correcta)
        val b = findViewById<CheckBox>(R.id.correctb)
        val c = findViewById<CheckBox>(R.id.correctc)
        val dcorrect = findViewById<CheckBox>(R.id.correctd)
        var position = intent.getIntExtra("position",7000)
        findViewById<EditText>(R.id.editquestion).setText(ques.elementAt(position).question)
        findViewById<EditText>(R.id.edita).setText(ques.elementAt(position).a)
        findViewById<EditText>(R.id.editb).setText(ques.elementAt(position).b)
        findViewById<EditText>(R.id.editc).setText(ques.elementAt(position).c)
        findViewById<EditText>(R.id.editd).setText(ques.elementAt(position).d)
        when(ques.elementAt(position).correctanswer){
            ques.elementAt(position).a->a.isChecked= true
            ques.elementAt(position).b->b.isChecked= true
            ques.elementAt(position).c->c.isChecked= true
            ques.elementAt(position).d->dcorrect.isChecked= true
        }

        //one at a time
        a.setOnClickListener(View.OnClickListener {
            b.isChecked = false
            c.isChecked = false
            dcorrect.isChecked = false
        })
        b.setOnClickListener(View.OnClickListener {
            a.isChecked = false
            c.isChecked = false
            dcorrect.isChecked = false
        })
        c.setOnClickListener(View.OnClickListener {
            b.isChecked = false
            a.isChecked = false
            dcorrect.isChecked = false
        })
        dcorrect.setOnClickListener(View.OnClickListener {
            b.isChecked = false
            c.isChecked = false
            a.isChecked = false
        })


        findViewById<Button>(R.id.edit_button).setOnClickListener {
            var correct = "Answer "
            if(a.isChecked){
                correct =findViewById<EditText>(R.id.edita).text.toString()
            }
            else if(b.isChecked){
                correct =findViewById<EditText>(R.id.editb).text.toString()
            }
            else if(c.isChecked){
                correct =findViewById<EditText>(R.id.editc).text.toString()
            }
            else if (dcorrect.isChecked)
            {
                correct =findViewById<EditText>(R.id.editd).text.toString()
            }
            val q = quest(findViewById<EditText>(R.id.editquestion).text.toString(),
                findViewById<EditText>(R.id.edita).text.toString(),
                findViewById<EditText>(R.id.editb).text.toString(),
                findViewById<EditText>(R.id.editc).text.toString(),
                findViewById<EditText>(R.id.editd).text.toString(),
                correct)
            databaseReference.child(ids.elementAt(position)).setValue(q)
            finish()
        }

//                list.elementAt(position).question = q.question
//                list.elementAt(position).a = q.a
//                list.elementAt(position).b = q.b
//                list.elementAt(position).c = q.c
//                list.elementAt(position).d = q.d
//                list.elementAt(position).correctanswer = q.correctanswer
//                notifyDataSetChanged()

    }
}