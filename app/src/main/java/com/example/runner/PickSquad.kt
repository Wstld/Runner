package com.example.runner

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_pick_squad.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PickSquad.newInstance] factory method to
 * create an instance of this fragment.
 */
class PickSquad : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_pick_squad, container, false)

        val clickListener = View.OnClickListener { view ->
            when (view.id){
                R.id.squadOneBtn -> {
                Toast.makeText(fragment.context, "Fungerar", Toast.LENGTH_SHORT).show()
                    DataSource.data.removeLast()
            }
                R.id.squadTwoBtn -> {}
                R.id.squadThreeBtn -> {}
                R.id.squadFourBtn -> {}

            }
        }

        if (fragment != null) {
            //Get buttons
            val btn1 = fragment.findViewById<Button>(R.id.squadOneBtn)
            val btn2 = fragment.findViewById<Button>(R.id.squadTwoBtn)
            val btn3 = fragment.findViewById<Button>(R.id.squadThreeBtn)
            val btn4 = fragment.findViewById<Button>(R.id.squadFourBtn)
            //Set listeners
            btn1.setOnClickListener(clickListener)
            btn2.setOnClickListener(clickListener)
            btn3.setOnClickListener(clickListener)
            btn4.setOnClickListener(clickListener)
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
         * @return A new instance of fragment PickSquad.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PickSquad().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}