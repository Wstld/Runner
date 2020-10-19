package com.example.runner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.runner.data.DataSource
import com.example.runner.databinding.FragmentDisplaySquadBinding


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplaySquadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplaySquadFragment : Fragment(){
    private var _binding: FragmentDisplaySquadBinding? = null
    private val binding get() = _binding!!


    private var btn: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            //Gets int according to clicked btn in previous fragment. Used to filter employees according to squad.
            btn = it.getInt(ARG_PARAM1)

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //access parent activity for this fragment.
        val activity: SendRunnerActivity = activity as SendRunnerActivity
        // Inflate the layout for this fragment
        _binding = FragmentDisplaySquadBinding.inflate(inflater,container,false)
        val view = binding.root


        binding.addEmployeeToDisplayedListBtn.setOnClickListener { activity.displayMoreEmployees() }

        //filter logic for recyclerview according to previously clicked btn in PickSquad fragment.
        when(btn) {
            1 ->{
                val squad1 = DataSource.data.filter { it.squad==1 }.sortedBy { it.lastRun }
                activity.initRecycler(squad1,binding.squadList)
            }

            2 -> {
                val squad2 = DataSource.data.filter { it.squad==2 }.sortedBy { it.lastRun }
                activity.initRecycler(squad2,binding.squadList)
            }

            3 -> {
                val squad3 = DataSource.data.filter { it.squad==3 }.sortedBy { it.lastRun }
                activity.initRecycler(squad3,binding.squadList)
            }

            4 -> {
                val squad4 = DataSource.data.filter { it.squad==4 }.sortedBy { it.lastRun }
                activity.initRecycler(squad4,binding.squadList)
            }

        }
        //display fragment.
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        @JvmStatic
        fun newInstance(btn: Int) =
            DisplaySquadFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, btn)
                }
            }
    }

}