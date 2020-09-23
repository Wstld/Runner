package com.example.runner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_send_runner.*
import kotlinx.android.synthetic.main.fragment_display_squad.*

class SendRunnerActivity : AppCompatActivity() {
    val squadFrag = PickSquad()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_runner)
        fragmentHandler(squadFrag)
    }
    fun initRecycler(data:List<Employee>,recyclerView: RecyclerView){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SendRunnerActivity)
            val recyclerAdapter = SquadListRecyclerAdapter()
            recyclerAdapter.setData(data)
            adapter = recyclerAdapter
        }
    }
    fun fragmentHandler(fragment:Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragmentHolder = manager.findFragmentById(R.id.fragmenHolder)
        val fragmentHolderId = R.id.fragmenHolder
        if(fragmentHolder==null){
            transaction.add(fragmentHolderId,fragment)
            transaction.commit()
        }
        else{
            transaction.replace(fragmentHolderId,fragment)
            transaction.commit()
        }

    }
    fun selectedSquadHandler(btnId:Int){
        when(btnId){
            R.id.squadOneBtn -> {
                //skickar med vilken knapp som tryckts och uppdaterar fragmentet.
                val listFragment = DisplaySquadFragment.newInstance(btnId)
                fragmentHandler(listFragment)
            }
            R.id.squadTwoBtn -> {
                val listFragment = DisplaySquadFragment.newInstance(btnId)
                fragmentHandler(listFragment)
            }
            R.id.squadThreeBtn -> {
                val listFragment = DisplaySquadFragment.newInstance(btnId)
                fragmentHandler(listFragment)
            }
            R.id.squadFourBtn -> {
                val listFragment = DisplaySquadFragment.newInstance(btnId)
                fragmentHandler(listFragment)
            }
        }
    }
}