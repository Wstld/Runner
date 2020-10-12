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
import kotlinx.android.synthetic.main.activity_add_employee.*
import java.time.format.DateTimeFormatterBuilder
import java.util.*
import kotlin.collections.ArrayList

//constants for intentExtras.
const val ID_KEY = "ID_KEY"
const val DEFAULT_ID = -1

class AddEmployeeActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener {

    lateinit var selectedEmployee:Employee

    //set date.
    val cal = GregorianCalendar.getInstance()
    var day = cal.get(Calendar.DAY_OF_MONTH)
    var month = cal.get(Calendar.MONTH)
    var year = cal.get(Calendar.YEAR)
    var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        val goToMainScreen = Intent(this, MainActivity::class.java)
        //get employee if any.
        val employeeId = intent.getIntExtra(ID_KEY, DEFAULT_ID)

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
        val checkBoxSearchAndRescueLeader = findViewById<CheckBox>(R.id.searchAndRescueLeaderCheckBox)
        val checkBoxSearchAndRescue = findViewById<CheckBox>(R.id.searchAdnRescueCheckBox)



// if fragment is opened for adding new employee, (emlpoyeeId = defaultvalue from constant)
        if (employeeId == -1) {

            applyBtn.setOnClickListener {
                //radioBtn input.
                val setSquad = when (squadInput.checkedRadioButtonId) {
                    R.id.squadRadioBtn1 -> 1
                    R.id.squadRadioBtn2 -> 2
                    R.id.squadRadioBtn3 -> 3
                    R.id.squadRadioBtn4 -> 4
                    else -> 0
                }
                //name and id input.
                val setName = nameInput.text.toString()?:""
                val setId = if (idInput.text.isNullOrBlank()){-1}else{idInput.text.toString().toInt()}

                //checkbox values
                val sqdLeader = checkBoxSquadLeader.isChecked
                val driver = checkBoxDriver.isChecked
                val driverLadder = checkBoxDriverLadder.isChecked
                val sarLeader = checkBoxSearchAndRescueLeader.isChecked
                val sar = checkBoxSearchAndRescue.isChecked
                val competenceValues = arrayListOf<Boolean>(sqdLeader,driver,driverLadder,sarLeader,sar)

                //input check.
                if(isName(setName) && setId >= 0 && setSquad > 0 && hasCompetence(competenceValues)){
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
                            Toast.makeText(this, "$setName har laggts till", Toast.LENGTH_SHORT)
                                .show()

                    }
                    .setNegativeButton("Nej") { dialog, which ->
                        dialog.cancel()
                    }
                    .show()
            } else {
                    // When error in input occurs.
                    inputErrorHandler(isName(setName),setSquad,setId,hasCompetence(competenceValues))
                }
            }
        }
        //if fragment is opened to edit existing employee
        else {
            //find employee in datasource based on Id.
            val selectedEmployee = DataSource.data.find { it.id == employeeId }

            //null safety.
            if (selectedEmployee != null) {

                //Display date for last run value.
                lastRunBody.visibility = View.VISIBLE
                lastRunHeading.visibility = View.VISIBLE

                //Set displayed value for last run.
                lastRunBody.text = selectedEmployee.lastRun

                //change last run value with datepicker when displayed last run is clicked.
                lastRunBody.setOnClickListener {
                    // for API < 24
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        val test = DatePickerDialog(this, R.style.calenderPick)
                        test.setOnDateSetListener { dialog, year, month, day ->
                            var editedMonth = ""
                            editedMonth = if (month + 1 < 10) {
                                "0${month + 1}"
                            } else {
                                "${month + 1}"
                            }
                            date = "$year-$editedMonth-$day"
                            lastRunBody.text = date
                            Toast.makeText(
                                this,
                                "Senast löpning har uppdaterats",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        test.show()
                    } else {
                        //for api > 24
                        val datePicker = DatePickerDialog(this, this, year, month, day)
                        datePicker.show()
                    }
                }

                //name & date input set to name of selected employee.
                nameInput.setText(selectedEmployee.name)
                idInput.setText(selectedEmployee.id.toString())

                //RadioBtn selection set to current employye squad number.
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

                //checkboxes set to current employees competence status.
                checkBoxSquadLeader.isChecked = selectedEmployee.competence.chief
                checkBoxDriver.isChecked = selectedEmployee.competence.driverTruck
                checkBoxDriverLadder.isChecked = selectedEmployee.competence.driverLadder
                checkBoxSearchAndRescueLeader.isChecked = selectedEmployee.competence.searchAndRescueLeader
                checkBoxSearchAndRescue.isChecked = selectedEmployee.competence.searchAndRescue



                //apply changes to employee with click on applyBtn.
                applyBtn.text = "Spara Ändringar"
                applyBtn.setOnClickListener {
                    //Checks current value for squad radioBtn.
                    val setSquad = when (squadInput.checkedRadioButtonId) {
                        R.id.squadRadioBtn1 -> 1
                        R.id.squadRadioBtn2 -> 2
                        R.id.squadRadioBtn3 -> 3
                        R.id.squadRadioBtn4 -> 4
                        else -> 0
                    }
                    //Checks current value for competence check boxes.
                    val listOfEmployeeCompetence: ArrayList<Boolean> = arrayListOf(
                        checkBoxSquadLeader.isChecked,
                        checkBoxDriver.isChecked,
                        checkBoxDriverLadder.isChecked,
                        checkBoxSearchAndRescueLeader.isChecked,
                        checkBoxSearchAndRescue.isChecked
                    )
                    //safety check for inputs.
                    if(isName(nameInput.text.toString()) && !idInput.text.isNullOrEmpty() && hasCompetence(listOfEmployeeCompetence)) {
                        MaterialAlertDialogBuilder(this)
                            .setTitle("Är du säker")
                            .setPositiveButton("Ja") { dialog, which ->
                                //sets new values to employee,gives status message and returns to main screen.
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
                    }else {
                        //if error occurs checks for what error with inputErrorHandler.
                        if (idInput.text.isNullOrEmpty()){
                            inputErrorHandler(
                                isName(nameInput.text.toString()),
                                setSquad,
                                -1,
                                hasCompetence(listOfEmployeeCompetence)
                            )
                        }else {
                            inputErrorHandler(
                                isName(nameInput.text.toString()),
                                setSquad,
                                idInput.text.toString().toInt(),
                                hasCompetence(listOfEmployeeCompetence)
                            )
                        }
                    }

                }
            }
        }
    }

    //Date picker logic for Api > 24. Logic for Api < 24 is found at lastRunBody.setOnClickListener.
    override fun onDateSet(datePicekr: DatePicker?, year: Int, month: Int, day: Int) {
        var editedMonth = ""
        editedMonth = if (month + 1 < 10) {
            "0${month + 1}"
        } else {
            "${month + 1}"
        }
        date = "$year-$editedMonth-$day"
        lastRunBody.text = date
        Toast.makeText(this, "Senast löpning har uppdaterats", Toast.LENGTH_SHORT)
            .show()
    }

    //Checks if input name is only A-Z and a-z inculding space.
    fun isName(string: String):Boolean{
        return !string.contains("""[^\w\s*]+|[\d]""".toRegex())&&string.isNotEmpty()
    }

    //Checks if min one check box is checked.
    fun hasCompetence(arrayList: ArrayList<Boolean>):Boolean{
        return arrayList.contains(element = true)
    }

    //Checks what is wrong and outputs toast notifying user.
    fun inputErrorHandler(name:Boolean,squad: Int,id: Int,competence: Boolean){
        if(!name){
            Toast.makeText(this, "Fyll i namn!", Toast.LENGTH_SHORT).show()
        }else if (id < 0){
            Toast.makeText(this, "Fyll i nummer!", Toast.LENGTH_SHORT).show()
        }else if (squad<=0){
            Toast.makeText(this, "Fyll i grupp!", Toast.LENGTH_SHORT).show()
        }else if (!competence){
            Toast.makeText(this, "Fyll i kompetenser!", Toast.LENGTH_SHORT).show()
        }
    }

    //Creates a Employee Object.
    private fun createEmployee(id: Int, name: String, squad: Int, competence: Competence): Employee {
            return Employee(id = id, name = name, squad = squad, competence = competence)
        }
    }
