package com.example.runner.data

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.runner.data.dto.Employee
import com.example.runner.ui.MainListActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmployeeRepository private constructor(private val employeeDao: EmployeeDao){
    lateinit var fireStoreDataBase:FirebaseFirestore

    fun getEmployeesFromFireStore(context: Context):LiveData<ArrayList<Employee>>{

        val allEmployees = MutableLiveData<ArrayList<Employee>>()
        fireStoreDataBase = FirebaseFirestore.getInstance()
        fireStoreDataBase.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        fillThefireStore()
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
                    if (e != null) Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    else Toast.makeText(context, "something went wrong!", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }


        }


        return allEmployees
    }
    fun deleteEmployeeFromFireStore(context: Context,id:Int){
        fireStoreDataBase.collection("employees").document(id.toString()).delete()
    }
    fun fillThefireStore(){

        val mainList = mutableListOf<Employee>()
        mainList.add(
            Employee(201,"Tomas Tomasson",1,"2020-09-19") //All but chief
        )
        mainList.add(
            Employee(21,"Erik Johansson",2,"2020-09-12")//Chief with full competence
        )
        mainList.add(
            Employee(331,"Sara Sarasson",3,"2020-08-12")//Only Chief
        )
        mainList.add(
            Employee(12,"Martin Martinsson",4,"2020-09-12")//Only driver both trucks
        )
        mainList.add(
            Employee(7,"Edna Ednasson",1,"2020-09-12")//Search and Rescue + driver Truck
        )
        mainList.add(
            Employee(222,"Mats Matsson",2,"2020-07-16")//Search and Rescue + diver both trucks
        )
        mainList.add(
            Employee(633,"Sven Svensson",3,"2020-05-11")//Search and Rescue Leader + Driver truck
        )
        mainList.add(
            Employee(170,"Ping Pingsson",4,"2020-01-02")//Only Search and Rescue
        )
        mainList.add(
            Employee(10,"Ibrahim Ibrahimsson",1,"2020-09-11")// all but chief
        )
        mainList.add(
            Employee(90,"Nils Nilsson",2,"2020-03-30")// only driver
        )
        mainList.add(
            Employee(300,"Arne Arnesson",3,"2020-05-13",)// only search and rescue
        )


        mainList.forEach {
            var city = hashMapOf(
                "id" to it.id,
                "name" to it.name,
                "squad" to it.squad,
                "lastRun" to it.lastRun
            )
            fireStoreDataBase.collection("employees").document(it.id.toString()).set(city
            )


        }


    }


    fun addEmployee(employee: Employee){
        val addedE = hashMapOf(
            "id" to employee.id,
            "name" to employee.name,
            "squad" to employee.squad,
            "lastRun" to employee.lastRun
        )
        fireStoreDataBase.collection("employees").document(employee.id.toString()).set(addedE
        )
    }

    fun filterList(string: String) = employeeDao.getFilteredEmployees(string)

    companion object{
        @Volatile private var instance:EmployeeRepository? = null
        fun getInstance(employeeDao: EmployeeDao) = instance?: synchronized(this){
            instance ?: EmployeeRepository(employeeDao).also { instance = it }
        }
    }
}