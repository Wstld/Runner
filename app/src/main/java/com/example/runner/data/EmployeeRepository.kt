package com.example.runner.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.runner.data.dto.Employee

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class EmployeeRepository private constructor(private val employeeDao: EmployeeDao){
        private val allEmployees = MutableLiveData<ArrayList<Employee>>()
        private val fireStoreDataBase = FirebaseFirestore.getInstance()
    init {
        fireStoreDataBase.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        fireStoreDataBase.collection("employees").addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                val employees = ArrayList<Employee>()
                val documents = snapshot.documents
                documents.forEach {
                    val employee = it.toObject(Employee::class.java)
                    if (employee != null) employees.add(employee)
                }
                allEmployees.value =  employees

            } else {
                if (e != null) Log.d("Error", "$e")
                else Log.d("Error", "Something Went Wrong")
                return@addSnapshotListener
            }


        }
    }


    fun getSpecificEmployee(id: Int):Employee? = allEmployees.value?.find { it.id == id }

    fun getEmployeesFromFireStore(context: Context):LiveData<ArrayList<Employee>>{
        return allEmployees
    }
    fun deleteEmployeeFromFireStore(context: Context,id:Int){
        fireStoreDataBase.collection("employees").document(id.toString()).delete()
    }

    fun addEmployee(employee: Employee){
        val addedE = hashMapOf(
            "id" to employee.id,
            "name" to employee.name,
            "squad" to employee.squad,
            "lastRun" to employee.lastRun
        )
        fireStoreDataBase.collection("employees").document().set(addedE
        )
    }

    fun filterList(string: String) = employeeDao.getFilteredEmployees(string)
    fun updateEmployee(id: Int, name: String, squad: Int, lastRun: String, oldId:Int) {
        var documentId = ""
        Log.d("FEL", "oldId:$oldId")
        val collection = fireStoreDataBase.collection("employees").whereEqualTo("id",oldId).get()
            .addOnSuccessListener {
               it.documents[0].reference.update(
                   "id",id,"name",name,"squad",squad,"lastRun",lastRun
               )
            }

        }




    companion object{
        @Volatile private var instance:EmployeeRepository? = null
        fun getInstance(employeeDao: EmployeeDao) = instance?: synchronized(this){
            instance ?: EmployeeRepository(employeeDao).also { instance = it }
        }
    }
}