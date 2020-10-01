package com.example.runner

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.format.DateTimeFormatterBuilder
import java.util.*

const val POSITION_KEY = "POSITION_KEY"
const val DEFAULT_POSITION = -1

class AddEmployeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        val goToMainScreen = Intent(this, MainActivity::class.java)
        val employeePosition = intent.getIntExtra(POSITION_KEY, DEFAULT_POSITION)
        //Views in activity
        val nameInput = findViewById<EditText>(R.id.employeeNameInput)
        val idInput = findViewById<EditText>(R.id.employeeIdInput)

        val applyBtn = findViewById<Button>(R.id.applyBtn)

        val lastRunHeading = findViewById<TableRow>(R.id.lastRunHeading)
        val lastRunBody = findViewById<Button>(R.id.lastRunBody)

        val squadInput = findViewById<RadioGroup>(R.id.squadGroupInput)

        val checkBoxSquadLeader = findViewById<CheckBox>(R.id.squadLeaderCheckBox)
        val checkBoxDriver = findViewById<CheckBox>(R.id.driverCheckBox)
        val checkBoxDriverLadder = findViewById<CheckBox>(R.id.driverLadderCheckBox)
        val checkBoxSearchAndRescueLeader =
            findViewById<CheckBox>(R.id.searchAndRescueLeaderCheckBox)
        val checkBoxSearchAndRescue = findViewById<CheckBox>(R.id.searchAdnRescueCheckBox)

        val cal = GregorianCalendar.getInstance()
        var day = cal.get(Calendar.DAY_OF_MONTH)
        var month = cal.get(Calendar.MONTH)
        var year = cal.get(Calendar.YEAR)


        if (employeePosition == -1) {
            //click listener for apply Btn.
            applyBtn.setOnClickListener {
                val setSquad = when (squadInput.checkedRadioButtonId) {
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
                    { dialog, which ->
                        DataSource.data.add(
                            createEmployee(
                                setId,
                                setName,
                                setSquad,
                                Competence(sqdLeader, driver, driverLadder, sarLeader, sar)
                            )
                        )
                        startActivity(goToMainScreen)
                        Toast.makeText(this, "$setName har laggts till", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Nej") { dialog, which ->
                        dialog.cancel()
                    }
                    .show()
            }
        } else {
            val data = DataSource.data.sortedBy { it.lastRun }
            val selectedEmployee = data[employeePosition]

            var date: String = ""

            lastRunBody.visibility = View.VISIBLE
            lastRunHeading.visibility = View.VISIBLE
            lastRunBody.setOnClickListener {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                  val test =  DatePickerDialog(this,R.style.calenderPick)
                        test.setOnDateSetListener{dialog,year,month,day ->
                            var editedMonth = ""
                            if (month+1 < 10){
                                editedMonth = "0${month+1}"
                            }else{editedMonth = "${month+1}"}
                            date = "$year-$editedMonth-$day"
                            lastRunBody.text = date
                            Toast.makeText(this, "Senast löpning har uppdaterats", Toast.LENGTH_SHORT).show()
                        }
                        test.show()
                }
            }


                nameInput.setText(selectedEmployee.name)
                idInput.setText(selectedEmployee.id.toString())

                when (selectedEmployee.squad) {
                    1 -> {
                        findViewById<RadioButton>(R.id.squadRadioBtn1).isChecked = true
                    }
                    2 -> {
                        findViewById<RadioButton>(R.id.squadRadioBtn2).isChecked = true
                    }
                    3 -> {
                        findViewById<RadioButton>(R.id.squadRadioBtn3).isChecked = true
                    }
                    4 -> {
                        findViewById<RadioButton>(R.id.squadRadioBtn4).isChecked = true
                    }
                }

                checkBoxSquadLeader.isChecked = selectedEmployee.competence.chief
                checkBoxDriver.isChecked = selectedEmployee.competence.driverTruck
                checkBoxDriverLadder.isChecked = selectedEmployee.competence.driverLadder
                checkBoxSearchAndRescueLeader.isChecked =
                    selectedEmployee.competence.searchAndRescueLeader
                checkBoxSearchAndRescue.isChecked = selectedEmployee.competence.searchAndRescue

                lastRunBody.setText(selectedEmployee.lastRun)

                applyBtn.text = "Spara Ändringar"
                applyBtn.setOnClickListener {
                    val setSquad = when (squadInput.checkedRadioButtonId) {
                        R.id.squadRadioBtn1 -> 1
                        R.id.squadRadioBtn2 -> 2
                        R.id.squadRadioBtn3 -> 3
                        R.id.squadRadioBtn4 -> 4
                        else -> 0
                    }
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Är du säker")
                        .setPositiveButton("Ja") { dialog, which ->
                            selectedEmployee.name = nameInput.text.toString()
                            selectedEmployee.id = idInput.text.toString().toInt()
                            selectedEmployee.squad = setSquad
                            selectedEmployee.lastRun = lastRunBody.text.toString()
                            selectedEmployee.competence.chief = checkBoxSquadLeader.isChecked
                            selectedEmployee.competence.driverTruck = checkBoxDriver.isChecked
                            selectedEmployee.competence.driverLadder =
                                checkBoxDriverLadder.isChecked
                            selectedEmployee.competence.searchAndRescueLeader =
                                checkBoxSearchAndRescueLeader.isChecked
                            selectedEmployee.competence.searchAndRescue =
                                checkBoxSearchAndRescue.isChecked
                            startActivity(goToMainScreen)
                            Toast.makeText(
                                this,
                                "${selectedEmployee.id} har uppdaterats",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .setNegativeButton("Nej") { dialog, which ->
                            dialog.cancel()
                        }
                        .show()
                }


            }


        }


    fun createEmployee(id: Int, name: String, squad: Int, competence: Competence): Employee {
            return Employee(id = id, name = name, squad = squad, competence = competence)
        }
    }
