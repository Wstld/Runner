package com.example.runner

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.runner.databinding.FragmentDisplaySquadBinding
import com.example.runner.databinding.FragmentPickSquadBinding
import kotlinx.android.synthetic.main.fragment_pick_squad.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PickSquad.newInstance] factory method to
 * create an instance of this fragment.
 */
class PickSquad : Fragment() {
    private var _binding: FragmentPickSquadBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentPickSquadBinding.inflate(inflater,container,false)
        val view = binding.root
        val activity: SendRunnerActivity = activity as SendRunnerActivity

        val clickListener = View.OnClickListener { view ->
            // forwards view id to handler in send runner activity.
            when (view){
                binding.squadOneBtn -> activity.selectedSquadHandler(1)
                binding.squadTwoBtn -> activity.selectedSquadHandler(2)
                binding.squadThreeBtn -> activity.selectedSquadHandler(3)
                binding.squadFourBtn -> activity.selectedSquadHandler(4)
            }

        }

        binding.squadOneBtn.setOnClickListener(clickListener)
        binding.squadTwoBtn.setOnClickListener(clickListener)
        binding.squadThreeBtn.setOnClickListener(clickListener)
        binding.squadFourBtn.setOnClickListener(clickListener)
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
         * @return A new instance of fragment PickSquad.
         */

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