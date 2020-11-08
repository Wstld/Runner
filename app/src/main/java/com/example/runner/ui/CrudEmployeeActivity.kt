package com.example.runner.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.runner.data.dto.Competence
import com.example.runner.data.dto.Employee
import com.example.runner.R

import com.example.runner.databinding.ActivityCrudEmployeeBinding
import com.example.runner.ui.viewmodels.CrudEmployeeViewModel
import com.example.runner.util.InjectorUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import java.util.*
import kotlin.collections.ArrayList

//constants for intentExtras.
const val ID_KEY = "ID_KEY"
const val DEFAULT_ID = -1


class CrudEmployeeActivity : AppCompatActivity() {
    lateinit var viewModel: CrudEmployeeViewModel
    lateinit var binding: ActivityCrudEmployeeBinding


    private fun initUi(id: Int, context: Context) {
        if (id != -1) {
            val employee = viewModel.getEmployee(id, context)
            if (employee != null) {
                //name & date input set to name of selected employee.
                binding.employeeNameInput.setText(employee.name)
                binding.employeeIdInput.setText(employee.id.toString())

                //RadioBtn selection set to current employye squad number.
                when (employee.squad) {
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

                //Display date for last run value.
                binding.lastRunBody.visibility = View.VISIBLE
                binding.lastRunHeading.visibility = View.VISIBLE

                //Set displayed value for last run.
                binding.lastRunBody.text = employee.lastRun
                binding.applyBtn.setOnClickListener { viewModel.updateEmployee(
                    id = binding.employeeIdInput.text.toString().toInt(),
                    name = binding.employeeNameInput.text.toString(),
                    squad = binding.squadGroupInput.checkedRadioButtonId,
                    oldId = employee.id,
                    lastRun = "",
                    context = context
                ) }
            }
        }else{
            binding.applyBtn.setOnClickListener { createEmployee(this ) }
        }
    }

    private fun createEmployee(context: Context) {
        //Squad pick.
        val setSquad = when (binding.squadGroupInput.checkedRadioButtonId) {
            binding.squadRadioBtn1.id -> 1
            binding.squadRadioBtn2.id -> 2
            binding.squadRadioBtn3.id -> 3
            binding.squadRadioBtn4.id -> 4
            else -> 0
        }
        //name and id input.
        val setName = binding.employeeNameInput.text.toString() ?: ""
        val setId = if (binding.employeeIdInput.text.isNullOrBlank()) {
            -1
        } else {
            binding.employeeIdInput.text.toString().toInt()
        }

        viewModel.createEmployee(setId, setName, setSquad, context)
    }

/*
    //set date.
    val cal: Calendar = GregorianCalendar.getInstance()
    var day = cal.get(Calendar.DAY_OF_MONTH)
    var month = cal.get(Calendar.MONTH)
    var year = cal.get(Calendar.YEAR)
    var date = ""
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = InjectorUtil.provideCrudEmployeeViewModel()
        viewModel = ViewModelProvider(this, factory).get(
            CrudEmployeeViewModel::class.java
        )

        val employeeId = intent.getIntExtra(ID_KEY, DEFAULT_ID)

        initUi(employeeId, this)


/*


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



    }*/
    }


}