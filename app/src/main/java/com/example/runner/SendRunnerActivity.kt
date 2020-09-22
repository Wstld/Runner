package com.example.runner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_send_runner.*

class SendRunnerActivity : AppCompatActivity() {
    val squadFrag = PickSquad()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_runner)
        fragmentHandler(squadFrag)
    }
    fun fragmentHandler(fragment:Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragmentHolder = manager.findFragmentById(R.id.fragmenHolder)
        val fragmentHolderId = R.id.fragmenHolder
        if(fragmentHolder==null){
            transaction.add(fragmentHolderId,fragment)
            transaction.commit()}else{
            transaction.replace(fragmentHolderId,fragment)
            transaction.commit()
        }

    }
    fun selectedSquadHandler(btnId:Int){
        when(btnId){
            R.id.squadOneBtn -> {
                val listFragment = DisplaySquadFragment.newInstance("yes","no")
                fragmentHandler(listFragment)
            }
        }
    }
}