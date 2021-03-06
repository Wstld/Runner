package com.example.runner

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.RatingCompat
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.StyleRes
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.databinding.ActivitySendRunnerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_send_runner.*
import kotlinx.android.synthetic.main.fragment_display_squad.*
import java.text.FieldPosition

class SendRunnerActivity : AppCompatActivity(),SquadViewHolder.RecyclerViewItemClick{
    //init picksquad fragment
    val squadFrag = PickSquad()
    lateinit var binding: ActivitySendRunnerBinding
    val recyclerAdapter = SquadListRecyclerAdapter(this@SendRunnerActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendRunnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentHandler(squadFrag)

    }

    //Send runner functionality, updates employee.lastrun
    override fun onItemClick(employee: Employee,position: Int) {
        val goToMainActivity = Intent(this,MainActivity::class.java)
        MaterialAlertDialogBuilder(this)
            .setTitle("Skicka Löpare")
            .setMessage("Är du säker på att du vill skicka ${employee.name}")
            .setNegativeButton("Nej"){dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Ja"){dialog,which ->
                employee.setDate(employee.getDate())
                startActivity(goToMainActivity)
                Toast.makeText(this, "${employee.name} har skickats", Toast.LENGTH_SHORT).show()
            }
            .show()
    }
    //recyclerview for DisplaySquadFragment.
    fun initRecycler(data:List<Employee>,recyclerView: RecyclerView){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SendRunnerActivity)
            val spacing = RecyclerViewSpacing(30)
            addItemDecoration(spacing)
            recyclerAdapter.setData(data)
            adapter = recyclerAdapter
        }
    }


    fun fragmentHandler(fragment:Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragmentHolder = manager.findFragmentById(binding.fragmenHolder.id)
        if(fragmentHolder==null){
            transaction.add(binding.fragmenHolder.id,fragment)
            transaction.commit()
        }
        else{
            transaction.replace(binding.fragmenHolder.id,fragment)
            transaction.commit()
        }

    }
    //Handles clicked squad in SquadFragment and forward value to DisplaySquadFragment.
    fun selectedSquadHandler(btnId:Int){
                val listFragment = DisplaySquadFragment.newInstance(btnId)
                fragmentHandler(listFragment)
    }

    //Add extra employee to displayed list .
    fun displayMoreEmployees(){
        // list of employee id's.
        val listOfEmployees = mutableListOf<String>()
        DataSource.data.forEach{listOfEmployees.add(it.id.toString())}

        //Default list of False values according to amount of employees.
        val itemsCheckd = mutableListOf<Boolean>()
        if(itemsCheckd.isEmpty()){
            repeat(listOfEmployees.count()){itemsCheckd.add(false)}
        }

        MaterialAlertDialogBuilder(this)
            .setTitle("Vilka vill du lägga till?")
            .setMultiChoiceItems(
                listOfEmployees.toTypedArray(),
                itemsCheckd.toBooleanArray())
                { dialog, which, isChecked ->
                    //updates previous false values in itemschecked.
                    itemsCheckd[which] = isChecked
                }
            .setPositiveButton("Lägg till"){dialog,which ->
                val list = mutableListOf<Int>()
                //adds id of checked employees to list.
                itemsCheckd.forEachIndexed { index, b -> if (b){list.add(listOfEmployees[index].toInt())} }
                //finds these employees by id and adds to adapter and notifies data change
                list.forEach { itemsCheckd -> DataSource.data.forEach{  if (it.id == itemsCheckd){recyclerAdapter.addEmployeeToList(it)} } }
                recyclerAdapter.notifyDataSetChanged()
                dialog.cancel()

            }
            .setNegativeButton("Stäng"){dialog,which->
                dialog.cancel()
            }

            .show()

    }


}