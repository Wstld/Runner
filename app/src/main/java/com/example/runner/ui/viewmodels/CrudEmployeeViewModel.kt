package com.example.runner.ui.viewmodels

import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.runner.data.EmployeeRepository
import com.example.runner.data.dto.Employee
import kotlinx.android.synthetic.main.activity_add_employee.*

class CrudEmployeeViewModel(private val employeeRepository: EmployeeRepository):ViewModel() {
    fun getDate():String{
        return ""
    }


    fun createEmployee(id:Int,name:String,squad:Int,context: Context) {
        val createdEmployee = Employee(id, name, squad,getDate())

        employeeRepository.addEmployee(createdEmployee)
    }

    fun getEmployee(id:Int,context: Context): Employee? {
        val emp = employeeRepository.getEmployeesFromFireStore(context).value!!.find { it.id == id }
        return emp
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