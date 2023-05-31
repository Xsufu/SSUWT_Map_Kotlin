package com.xolary.ssuwtmap.laborathory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xolary.ssuwtmap.R
import com.xolary.ssuwtmap.databinding.FragmentLaboratoryBuildingBinding

class LaboratoryBuilding : Fragment() {

    private lateinit var binding: FragmentLaboratoryBuildingBinding

    companion object {
        fun newInstance() = LaboratoryBuilding()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaboratoryBuildingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            button0.setOnClickListener {
                floorImage.setImageResource(R.drawable.zero_floor_lab)
            }

            button1.setOnClickListener {
                floorImage.setImageResource(R.drawable.first_floor_lab)
            }

            button2.setOnClickListener {
                floorImage.setImageResource(R.drawable.second_floor_lab)
            }

            button3.setOnClickListener {
                floorImage.setImageResource(R.drawable.third_floor_lab)
            }
        }
    }
}