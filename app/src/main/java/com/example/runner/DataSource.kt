package com.example.runner

import java.io.Serializable
import java.util.ArrayList

object DataSource {
    val data = createList()
        fun createList():MutableList<Employee>{
            val mainList = mutableListOf<Employee>()
            mainList.add(
                Employee(201,"Tomas Tomasson",1,"2020-09-19",competence = Competence(driverTruck = true, driverLadder = true, searchAndRescueLeader = true, searchAndRescue = true)) //All but chief
            )
            mainList.add(
                Employee(21,"Erik Johansson",2,"2020-09-12",competence =  Competence(true,true,true,true,true))//Chief with full competence
            )
            mainList.add(
                Employee(331,"Sara Sarasson",3,competence =  Competence(chief = true))//Only Chief
            )
            mainList.add(
                Employee(12,"Martin Martinsson",4,"2020-09-12",competence =  Competence(driverLadder = true, driverTruck = true))//Only driver both trucks
            )
            mainList.add(
                Employee(7,"Edna Ednasson",1,competence =  Competence(searchAndRescue = true,driverTruck = true))//Search and Rescue + driver Truck
            )
            mainList.add(
                Employee(222,"Mats Matsson",2,competence =  Competence(searchAndRescue = true,driverTruck = true,driverLadder = true))//Search and Rescue + diver both trucks
            )
            mainList.add(
                Employee(633,"Sven Svensson",3,competence =  Competence(searchAndRescue = true,searchAndRescueLeader = true,driverTruck = true))//Search and Rescue Leader + Driver truck
            )
            mainList.add(
                Employee(170,"Ping Pingsson",4,competence =  Competence(searchAndRescue = true))//Only Search and Rescue
            )
            mainList.add(
                Employee(10,"Ibrahim Ibrahimsson",1,competence =  Competence(driverTruck = true, driverLadder = true, searchAndRescueLeader = true, searchAndRescue = true))// all but chief
            )
            mainList.add(
                Employee(90,"Nils Nilsson",2,competence =  Competence(driverTruck = true,driverLadder = true))// only driver
            )
            mainList.add(
                Employee(300,"Arne Arnesson",3,competence =  Competence(searchAndRescue = true))// only search and rescue
            )
            return mainList

    }
}