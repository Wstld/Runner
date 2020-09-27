package com.example.runner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddEmployeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        //Views in activity

        val nameInput = findViewById<EditText>(R.id.employeeNameInput)
        val idInput = findViewById<EditText>(R.id.employeeIdInput)

        val applyBtn = findViewById<Button>(R.id.applyBtn)

        val squadInput = findViewById<RadioGroup>(R.id.squadGroupInput)

        val checkBoxSquadLeader = findViewById<CheckBox>(R.id.squadLeaderCheckBox)
        val checkBoxDriver = findViewById<CheckBox>(R.id.driverCheckBox)
        val checkBoxDriverLadder = findViewById<CheckBox>(R.id.driverLadderCheckBox)
        val checkBoxSearchAndRescueLeader = findViewById<CheckBox>(R.id.searchAndRescueLeaderCheckBox)
        val checkBoxSearchAndRescue = findViewById<CheckBox>(R.id.searchAdnRescueCheckBox)

        //click listener for apply Btn.
        applyBtn.setOnClickListener{
            val setSquad = when(squadInput.checkedRadioButtonId){
                R.id.squadRadioBtn1 -> 1
                R.id.squadRadioBtn2 -> 2
                R.id.squadRadioBtn3 -> 3
                R.id.squadRadioBtn4 -> 4
                else -> 0
            }

            val setName = nameInput.text.toString()
            val setId = idInput.text.toString().toInt()

            //checkbox values
            val sqdLeader = checkBoxSquadLeader.isChecked
            val driver = checkBoxDriver.isChecked
            val driverLadder = checkBoxDriverLadder.isChecked
            val sarLeader = checkBoxSearchAndRescueLeader.isChecked
            val sar = checkBoxSearchAndRescue.isChecked

            MaterialAlertDialogBuilder(this)
                .setTitle("Vill du lägga till $setName?")
                .setPositiveButton("Ja")
                    {dialog,which ->
                        DataSource.data.add(createEmployee(setId,setName,setSquad,Competence(sqdLeader,driver,driverLadder,sarLeader,sar)))
                        val goToMainScreen = Intent(this,MainActivity::class.java)
                        startActivity(goToMainScreen)
                        Toast.makeText(this, "$setName har laggts till", Toast.LENGTH_SHORT).show()
                    }
                .setNegativeButton("Nej"){dialog,which ->
                    dialog.cancel()
                }
                .show()
        }
    }
    fun createEmployee(id:Int,name:String,squad:Int,competence: Competence):Employee{
        return Employee(id=id,name=name,squad=squad,competence = competence)
    }
}