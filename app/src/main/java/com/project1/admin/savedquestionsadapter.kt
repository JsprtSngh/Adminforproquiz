package com.project1.admin

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class savedquestionsadapter( val context: Context) : RecyclerView.Adapter<savedquestionsadapter.Viewholder>() {
    val databaseReference = FirebaseDatabase.getInstance().getReference("Questions");
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question = itemView.findViewById<TextView>(R.id.q)
        val a = itemView.findViewById<TextView>(R.id.a)
        val b = itemView.findViewById<TextView>(R.id.b)
        val c = itemView.findViewById<TextView>(R.id.c)
        val d = itemView.findViewById<TextView>(R.id.d)
        val correct = itemView.findViewById<TextView>(R.id.correctoption)
        val edit = itemView.findViewById<ImageView>(R.id.editquestions_button)
        val delete = itemView.findViewById<ImageView>(R.id.deletequestion_button)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): savedquestionsadapter.Viewholder {
        return Viewholder(LayoutInflater.from(parent.context).inflate(R.layout.questionsdetails,parent,false))
    }

    override fun onBindViewHolder(holder: savedquestionsadapter.Viewholder, position: Int) {
        holder.question.setText("Q"+(position+1)+": "+ques.elementAt(position).question)
        holder.a.setText(ques.elementAt(position).a)
        holder.b.setText(ques.elementAt(position).b)
        holder.c.setText(ques.elementAt(position).c)
        holder.d.setText(ques.elementAt(position).d)
        holder.correct.setText("Correct answer :"+ques.elementAt(position).correctanswer)
        holder.edit.setOnClickListener {
//            val d = Dialog(context)
//            d.setContentView(R.layout.editquestiondialog)
//            d.findViewById<EditText>(R.id.editquestion).setText(ques.elementAt(position).question)
//            d.findViewById<EditText>(R.id.edita).setText(ques.elementAt(position).a)
//            d.findViewById<EditText>(R.id.editb).setText(ques.elementAt(position).b)
//            d.findViewById<EditText>(R.id.editc).setText(ques.elementAt(position).c)
//            d.findViewById<EditText>(R.id.editd).setText(ques.elementAt(position).d)
            val i = Intent(context,editquestions::class.java)
            i.putExtra("position",position)
            context.startActivity(i)


//            d.findViewById<Button>(R.id.edit_button).setOnClickListener {
//
//                Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
//
//
//                //check box
//                val a = d.findViewById<CheckBox>(R.id.correcta)
//                val b = d.findViewById<CheckBox>(R.id.correctb)
//                val c = d.findViewById<CheckBox>(R.id.correctc)
//                val dcorrect = d.findViewById<CheckBox>(R.id.correctd)
//
//                when(ques.elementAt(position).correctanswer){
//                    ques.elementAt(position).a->a.isChecked= true
//                    ques.elementAt(position).b->b.isChecked= true
//                    ques.elementAt(position).c->c.isChecked= true
//                    ques.elementAt(position).d->dcorrect.isChecked= true
//                }
//
//                //one at a time
//                a.setOnClickListener(View.OnClickListener {
//                    b.isChecked = false
//                    c.isChecked = false
//                    dcorrect.isChecked = false
//                })
//                b.setOnClickListener(View.OnClickListener {
//                    a.isChecked = false
//                    c.isChecked = false
//                    dcorrect.isChecked = false
//                })
//                c.setOnClickListener(View.OnClickListener {
//                    b.isChecked = false
//                    a.isChecked = false
//                    dcorrect.isChecked = false
//                })
//                dcorrect.setOnClickListener(View.OnClickListener {
//                    b.isChecked = false
//                    c.isChecked = false
//                    a.isChecked = false
//                })
//                var correct = "Answer "
//                if(a.isChecked){
//                    correct =d.findViewById<EditText>(R.id.edita).text.toString()
//                }
//                else if(b.isChecked){
//                    correct =d.findViewById<EditText>(R.id.editb).text.toString()
//                }
//                else if(c.isChecked){
//                    correct =d.findViewById<EditText>(R.id.editc).text.toString()
//                }
//                else if (dcorrect.isChecked){
//                    correct =d.findViewById<EditText>(R.id.editd).text.toString()
//                }
//                val q = quest(d.findViewById<EditText>(R.id.editquestion).text.toString(),
//                    d.findViewById<EditText>(R.id.edita).text.toString(),
//                    d.findViewById<EditText>(R.id.editb).text.toString(),
//                    d.findViewById<EditText>(R.id.editc).text.toString(),
//                    d.findViewById<EditText>(R.id.editd).text.toString(),
//                    correct)
//
//                ques.elementAt(position).question = q.question
//                ques.elementAt(position).a = q.a
//                ques.elementAt(position).b = q.b
//                ques.elementAt(position).c = q.c
//                ques.elementAt(position).d = q.d
//                ques.elementAt(position).correctanswer = q.correctanswer
//                notifyDataSetChanged()
//                databaseReference.child(ids.elementAt(position)).setValue(q)
//                reload()
//                d.dismiss()
//            }
//            d.show()
        }
        holder.delete.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                .setTitle("Delete Question")
                .setMessage("Do you want delete this question?")
            builder.setPositiveButton("Yes",{ d: DialogInterface, i:Int->
                databaseReference.child(ids.elementAt(position)).removeValue()
                ques.remove(ques.elementAt(position))
                notifyDataSetChanged()

            })
            builder.setNegativeButton("No"){ d: DialogInterface, i:Int->

            }
            builder.show()
        }

    }
//    fun reload(){
//        ques.clear()
//        myRef.addValueEventListener(object: ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (messageSnapshot in snapshot.children) {
//                    ids.add(messageSnapshot.key.toString())
//                    var q = quest(messageSnapshot.child("question").value.toString(),
//                        messageSnapshot.child("a").value.toString(),
//                        messageSnapshot.child("b").value.toString(),
//                        messageSnapshot.child("c").value.toString(),
//                        messageSnapshot.child("d").value.toString(),
//                        messageSnapshot.child("correctanswer").value.toString()
//                    )
//                    ques.add(q)
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("Value", "Failed to read value.", error.toException())
//            }
//
//        })
//        notifyDataSetChanged()
//    }
    override fun getItemCount(): Int {
        return ques.size
    }
}