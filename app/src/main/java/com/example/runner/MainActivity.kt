package com.example.runner

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Click listener
        val clickListener = View.OnClickListener { view ->
            when(view.id){
                R.id.sendRunnerBtn -> Toast.makeText(applicationContext, "Sending", Toast.LENGTH_SHORT).show() //put function for send runner button.
                R.id.addEmployeeBtn -> Toast.makeText(applicationContext, "Adding", Toast.LENGTH_SHORT).show() //put function for add employee button.
                R.id.showFullListBtn -> Toast.makeText(applicationContext, "Showing", Toast.LENGTH_SHORT).show() //put function for show full list button.
                R.id.searchBtn -> Toast.makeText(applicationContext, "Searching", Toast.LENGTH_SHORT).show() //put function for search button.
            }
        }

        //Buttons Defined.
        val sendRunnerBtn = findViewById<CardView>(R.id.sendRunnerBtn)
        val addEmployeeBtn = findViewById<Button>(R.id.addEmployeeBtn)
        val showFullListBtn = findViewById<Button>(R.id.showFullListBtn)
        val searchBtn = findViewById<Button>(R.id.searchBtn)

        //set listener
        sendRunnerBtn.setOnClickListener(clickListener)
        addEmployeeBtn.setOnClickListener(clickListener)
        showFullListBtn.setOnClickListener(clickListener)
        searchBtn.setOnClickListener(clickListener)



    }


    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
            R.id.mainMenuSettings -> {
                //add what to do on Settings klick.
                Toast.makeText(applicationContext, "Klicked Settings", Toast.LENGTH_SHORT).show()
                true
            }
           R.id.mainMenuOptions -> {
               //Add what to do on burgerMenu klick.
               Toast.makeText(applicationContext, "Klicked Settings", Toast.LENGTH_SHORT).show()
               true
           }

           else -> super.onOptionsItemSelected(item)
        }
    }


}