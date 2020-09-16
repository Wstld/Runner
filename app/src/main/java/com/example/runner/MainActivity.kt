package com.example.runner

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    fun test(view: View) {
        Toast.makeText(applicationContext, "Klick fungerar", Toast.LENGTH_SHORT).show()
        return
    }
}