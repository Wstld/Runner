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
import com.example.runner.databinding.ActivityAddEmployeeBinding
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
    val cal:Calendar = GregorianCalendar.getInstance()
    var day = cal.get(Calendar.DAY_OF_MONTH)
    var month = cal.get(Calendar.MONTH)
    var year = cal.get(Calendar.YEAR)
    var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val goToMainScreen = Intent(this, MainActivity::class.java)
        //get employee if any.
        val employeeId = intent.getIntExtra(ID_KEY, DEFAULT_ID)



// if fragment is opened for adding new employee, (emlpoyeeId = defaultvalue from constant)
        if (employeeId == -1) {

            applyBtn.setOnClickListener {
                //radioBtn input.
                val setSquad = when (binding.squadGroupInput.checkedRadioButtonId) {
                    binding.squadRadioBtn1.id -> 1
                    binding.squadRadioBtn2.id -> 2
                    binding.squadRadioBtn3.id -> 3
                    binding.squadRadioBtn4.id -> 4
                    else -> 0
                }
                //name and id input.
                val setName = binding.employeeNameInput.text.toString()?:""
                val setId = if (binding.employeeIdInput.text.isNullOrBlank()){-1}else{binding.employeeIdInput.text.toString().toInt()}

                //checkbox values
                val sqdLeader = binding.squadLeaderCheckBox.isChecked
                val driver = binding.driverCheckBox.isChecked
                val driverLadder = binding.driverLadderCheckBox.isChecked
                val sarLeader = binding.searchAndRescueLeaderCheckBox.isChecked
                val sar = binding.searchAdnRescueCheckBox.isChecked
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
                binding.lastRunBody.visibility = View.VISIBLE
                binding.lastRunHeading.visibility = View.VISIBLE

                //Set displayed value for last run.
                binding.lastRunBody.text = selectedEmployee.lastRun

                //change last run value with datepicker when displayed last run is clicked.
                binding.lastRunBody.setOnClickListener {
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
                            binding.lastRunBody.text = date
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
                binding.employeeNameInput.setText(selectedEmployee.name)
                binding.employeeIdInput.setText(selectedEmployee.id.toString())

                //RadioBtn selection set to current employye squad number.
                when (selectedEmployee.squad) {
                    1 -> {
                        binding.squadRadioBtn1.isChecked = true
                    }
                    2 -> {
                        binding.squadRadioBtn2.isChecked = true
                    }
                    3 -> {
                        binding.squadRadioBtn3.isChecked = true
                    }
                    4 -> {
                        binding.squadRadioBtn4.isChecked = true
                    }
                }

                //checkboxes set to current employees competence status.
                binding.squadLeaderCheckBox.isChecked = selectedEmployee.competence.chief
                binding.driverCheckBox.isChecked = selectedEmployee.competence.driverTruck
                binding.driverLadderCheckBox.isChecked = selectedEmployee.competence.driverLadder
                binding.searchAndRescueLeaderCheckBox.isChecked = selectedEmployee.competence.searchAndRescueLeader
                binding.searchAdnRescueCheckBox.isChecked = selectedEmployee.competence.searchAndRescue



                //apply changes to employee with click on applyBtn.
                applyBtn.text = "Spara Ändringar"
                applyBtn.setOnClickListener {
                    //Checks current value for squad radioBtn.
                    val setSquad = when (binding.squadGroupInput.checkedRadioButtonId) {
                        binding.squadRadioBtn1.id -> 1
                        binding.squadRadioBtn2.id -> 2
                        binding.squadRadioBtn3.id -> 3
                        binding.squadRadioBtn4.id -> 4
                        else -> 0
                    }
                    //Checks current value for competence check boxes.
                    val listOfEmployeeCompetence: ArrayList<Boolean> = arrayListOf(
                        binding.squadLeaderCheckBox.isChecked,
                        binding.driverCheckBox.isChecked,
                        binding.driverLadderCheckBox.isChecked,
                        binding.searchAndRescueLeaderCheckBox.isChecked,
                        binding.searchAdnRescueCheckBox.isChecked
                    )
                    //safety check for inputs.
                    if(isName(binding.employeeNameInput.text.toString()) && !binding.employeeIdInput.text.isNullOrEmpty() && hasCompetence(listOfEmployeeCompetence)) {
                        MaterialAlertDialogBuilder(this)
                            .setTitle("Är du säker")
                            .setPositiveButton("Ja") { dialog, which ->
                                //sets new values to employee,gives status message and returns to main screen.

                                selectedEmployee.name = binding.employeeNameInput.text.toString()
                                selectedEmployee.id = binding.employeeIdInput.text.toString().toInt()
                                selectedEmployee.squad = setSquad
                                selectedEmployee.lastRun = binding.lastRunBody.text.toString()
                                selectedEmployee.competence.chief = binding.squadLeaderCheckBox.isChecked
                                selectedEmployee.competence.driverTruck = binding.driverCheckBox.isChecked
                                selectedEmployee.competence.driverLadder =
                                    binding.driverLadderCheckBox.isChecked
                                selectedEmployee.competence.searchAndRescueLeader =
                                    binding.searchAndRescueLeaderCheckBox.isChecked
                                selectedEmployee.competence.searchAndRescue =
                                    binding.searchAdnRescueCheckBox.isChecked
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
                        if (binding.employeeIdInput.text.isNullOrEmpty()){
                            inputErrorHandler(
                                isName(binding.employeeNameInput.text.toString()),
                                setSquad,
                                -1,
                                hasCompetence(listOfEmployeeCompetence)
                            )
                        }else {
                            inputErrorHandler(
                                isName(binding.employeeNameInput.text.toString()),
                                setSquad,
                                binding.employeeIdInput.text.toString().toInt(),
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
