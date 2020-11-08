package com.example.runner.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.runner.data.EmployeeRepository
import com.example.runner.data.dto.Employee
import com.example.runner.ui.MainActivity


class CrudEmployeeViewModel(private val employeeRepository: EmployeeRepository):ViewModel() {
    fun getDate():String{
        return ""
    }
    fun updateEmployee(oldId:Int,id: Int,name: String,squad: Int,lastRun:String,context: Context){
        employeeRepository.updateEmployee(id,name,squad,lastRun,oldId)
        Toast.makeText(context, "$id has been updated", Toast.LENGTH_SHORT).show()

        val mainActivity = Intent(context, MainActivity::class.java)
        goTo(context,mainActivity)
    }

    fun createEmployee(id:Int,name:String,squad:Int,context: Context) {
        val createdEmployee = Employee(id, name, squad,getDate())
        employeeRepository.addEmployee(createdEmployee)
        Toast.makeText(context, "$name was added!", Toast.LENGTH_SHORT).show()
        val mainActivity = Intent(context, MainActivity::class.java)
        goTo(context,mainActivity)
    }

    fun getEmployee(id:Int,context: Context): Employee? {
       val emp = employeeRepository.getSpecificEmployee(id = id)
        return if (emp != null) emp
        else {Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show()
            null
        }
    }

    fun goTo(context: Context,intent: Intent){
        startActivity(context,intent,intent.extras)
    }


    /* override fun onDateSet(datePicekr: DatePicker?, year: Int, month: Int, day: Int) {
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
     }*/

}