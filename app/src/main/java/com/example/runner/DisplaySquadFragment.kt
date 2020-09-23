package com.example.runner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_display_squad.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplaySquadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplaySquadFragment : Fragment(){


    // TODO: Rename and change types of parameters
    private var btn: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            btn = it.getInt(ARG_PARAM1)

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity: SendRunnerActivity = activity as SendRunnerActivity
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_display_squad, container, false)
        val recyclerView = fragment.findViewById<RecyclerView>(R.id.squadList)

        when(btn) {
            R.id.squadOneBtn ->{
                val squad1 = DataSource.data.filter { it.squad==1 }.sortedBy { it.lastRun }
                activity.initRecycler(squad1,recyclerView)
            }

            R.id.squadTwoBtn -> {
                val squad2 = DataSource.data.filter { it.squad==2 }.sortedBy { it.lastRun }
                activity.initRecycler(squad2,recyclerView)
            }

            R.id.squadThreeBtn -> {
                val squad3 = DataSource.data.filter { it.squad==3 }.sortedBy { it.lastRun }
                activity.initRecycler(squad3,recyclerView)
            }

            R.id.squadFourBtn -> {
                val squad4 = DataSource.data.filter { it.squad==4 }.sortedBy { it.lastRun }
                activity.initRecycler(squad4,recyclerView)
            }

        }
        return fragment
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisplaySquadFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(btn: Int) =
            DisplaySquadFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, btn)
                }
            }
    }
/*    fun initRecyclerView(items:List<Employee>){
        squadList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = MainListRecyclerAdapter(this@DisplaySquadFragment)
        }
    }*/
}