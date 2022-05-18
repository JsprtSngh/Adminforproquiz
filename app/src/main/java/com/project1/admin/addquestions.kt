package com.project1.admin

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addquestions: AppCompatActivity() {
    lateinit var submit : Button;
    lateinit var question: EditText;
    lateinit var a: EditText;
    lateinit var b: EditText;
    lateinit var c: EditText;
    lateinit var d: EditText;
    lateinit var acorrect: CheckBox;
    lateinit var bcorrect: CheckBox;
    lateinit var ccorrect: CheckBox;
    lateinit var dcorrect: CheckBox;
    lateinit var databaseReference: DatabaseReference;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addquestions)
        submit = findViewById(R.id.add_submit_question)
        question = findViewById(R.id.add_question)
        a = findViewById(R.id.add_a)
        b = findViewById(R.id.add_b)
        c = findViewById(R.id.add_c)
        d = findViewById(R.id.add_d)
        acorrect = findViewById(R.id.acorrect)
        bcorrect = findViewById(R.id.bcorrect)
        ccorrect = findViewById(R.id.ccorrect)
        dcorrect = findViewById(R.id.dcorrect)
        databaseReference = FirebaseDatabase.getInstance().getReference("Questions");

        submit.setOnClickListener(View.OnClickListener {
            val ex = makedataclass()
            if (ex.correctanswer =="Answer not given"){
                val alert = AlertDialog.Builder(this).setTitle("Alert").setMessage("Please add the correct answer and try again..")
                    .setNegativeButton("Okay",{ d: DialogInterface, i:Int ->
                    })
                alert.show()
            }
            else if (question.text.toString() ==""){
                val alert = AlertDialog.Builder(this).setTitle("Alert").setMessage("Please enter question in the question field..")
                    .setNegativeButton("Okay",{ d: DialogInterface, i:Int ->
                    })
                alert.show()
            }
            else if (question.text.toString() ==""||b.text.toString()==""||c.text.toString()==""||d.text.toString()==""){
                val alert = AlertDialog.Builder(this).setTitle("Alert").setMessage("Please enter all options..")
                    .setNegativeButton("Okay",{ d: DialogInterface, i:Int ->
                    })
                alert.show()
            }
            else{
                databaseReference.push().setValue(ex)
                val jk =databaseReference.key
                val snack : Snackbar = Snackbar.make(findViewById(R.id.addquestionlayout),"The above question is added ...", Snackbar.LENGTH_LONG)
                snack.show();
                empty()

            }})

        acorrect.setOnClickListener(View.OnClickListener {
            bcorrect.isChecked = false
            ccorrect.isChecked = false
            dcorrect.isChecked = false
        })
        bcorrect.setOnClickListener(View.OnClickListener {
            acorrect.isChecked = false
            ccorrect.isChecked = false
            dcorrect.isChecked = false
        })
        ccorrect.setOnClickListener(View.OnClickListener {
            bcorrect.isChecked = false
            acorrect.isChecked = false
            dcorrect.isChecked = false
        })
        dcorrect.setOnClickListener(View.OnClickListener {
            bcorrect.isChecked = false
            ccorrect.isChecked = false
            acorrect.isChecked = false
        })

    }
    fun makedataclass ():quest{

        var correct = "Answer not given"
        if(acorrect.isChecked){
            correct =a.text.toString()
        }
        else if(bcorrect.isChecked){
            correct =b.text.toString()
        }
        else if(ccorrect.isChecked){
            correct =c.text.toString()
        }
        else if (dcorrect.isChecked){
            correct =d.text.toString()
        }
        val q  = quest(question.text.toString(),a.text.toString(), b.text.toString(),c.text.toString(),d.text.toString(),correct)
        return q;
    }
    fun empty() {
        a.text.clear()
        b.text.clear()
        c.text.clear()
        d.text.clear()
        question.text.clear()
        acorrect.isChecked = false
        bcorrect.isChecked = false
        ccorrect.isChecked = false
        dcorrect.isChecked = false

    }
}