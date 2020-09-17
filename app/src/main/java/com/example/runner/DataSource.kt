package com.example.runner

class DataSource {
    companion object{
        fun createList():MutableList<Employee>{
            val mainList = mutableListOf<Employee>()
            mainList.add(
                Employee(201,"Tomas Tomasson", Competence(driverTruck = true, driverLadder = true, searchAndRescueLeader = true, searchAndRescue = true)) //All but chief
            )
            mainList.add(
                Employee(21,"Erik Johansson", Competence(true,true,true,true,true))//Chief with full competence
            )
            mainList.add(
                Employee(331,"Sara Sarasson", Competence(chief = true))//Only Chief
            )
            mainList.add(
                Employee(12,"Martin Martinsson", Competence(driverLadder = true, driverTruck = true))//Only driver both trucks
            )
            mainList.add(
                Employee(7,"Edna Ednasson", Competence(searchAndRescue = true,driverTruck = true))//Search and Rescue + driver Truck
            )
            mainList.add(
                Employee(222,"Mats Matsson", Competence(searchAndRescue = true,driverTruck = true,driverLadder = true))//Search and Rescue + diver both trucks
            )
            mainList.add(
                Employee(633,"Sven Svensson", Competence(searchAndRescue = true,searchAndRescueLeader = true,driverTruck = true))//Search and Rescue Leader + Driver truck
            )
            mainList.add(
                Employee(170,"Ping Pingsson", Competence(searchAndRescue = true))//Only Search and Rescue
            )
            mainList.add(
                Employee(10,"Ibrahim Ibrahimsson", Competence(driverTruck = true, driverLadder = true, searchAndRescueLeader = true, searchAndRescue = true))// all but chief
            )
            mainList.add(
                Employee(90,"Nils Nilsson", Competence(driverTruck = true,driverLadder = true))// only driver
            )
            mainList.add(
                Employee(300,"Arne Arnesson", Competence(searchAndRescue = true))// only search and rescue
            )
            return mainList
        }
    }
}