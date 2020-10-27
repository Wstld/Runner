package com.example.runner.data

class DataBase private constructor(){
    val employeeDao = EmployeeDao()

    companion object{
        @Volatile private var instance:DataBase? = null
        fun getInstance() = instance?: synchronized(this){
            instance ?: DataBase().also { dataBase ->
                instance = dataBase

            }
        }
    }
}