package com.example.runner

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runner.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Create Data (the list)
        val MAINLIST = DataSource



        //orientation logic.
        val clickListener = View.OnClickListener { view ->
            when(view){
                binding.sendRunnerBtn -> {
                    val goToSendRunner = Intent(this,SendRunnerActivity::class.java)
                    startActivity(goToSendRunner)
                }
                binding.addEmployeeBtn -> {
                    val goToAddEmployeeActivity = Intent(this,AddEmployeeActivity::class.java)
                    startActivity(goToAddEmployeeActivity)
                }
                binding.showFullListBtn -> {
                    val goToMainList = Intent(this,ListMain::class.java)
                    startActivity(goToMainList)
                }

            }
        }

        //set listener
        binding.sendRunnerBtn.setOnClickListener(clickListener)
        binding.addEmployeeBtn.setOnClickListener(clickListener)
        binding.showFullListBtn.setOnClickListener(clickListener)



    }

//Inflates custom menu.
    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }
//OnClicked Item handler
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
            R.id.mainMenuSettings -> {
                //add what to do on Settings click.
                Toast.makeText(applicationContext, "Klicked Settings", Toast.LENGTH_SHORT).show()
                true
            }
           R.id.mainMenuOptions -> {
               //Add what to do on burgerMenu click.
               Toast.makeText(applicationContext, "Klicked Settings", Toast.LENGTH_SHORT).show()
               true
           }

           else -> super.onOptionsItemSelected(item)
        }
    }



}