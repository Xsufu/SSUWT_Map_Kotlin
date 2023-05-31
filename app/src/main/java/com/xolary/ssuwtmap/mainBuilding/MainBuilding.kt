package com.xolary.ssuwtmap.mainBuilding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xolary.ssuwtmap.R
import com.xolary.ssuwtmap.databinding.FragmentMainBuildingBinding

class MainBuilding : Fragment() {

    private lateinit var binding: FragmentMainBuildingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBuildingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().runOnUiThread {
            binding.apply {
                button.setOnClickListener {
                    floorImage.setImageResource(R.drawable.first_floor)
                }

                button2.setOnClickListener {
                    floorImage.setImageResource(R.drawable.second_floor)
                }

                button3.setOnClickListener {
                    floorImage.setImageResource(R.drawable.third_floor)
                }

                button4.setOnClickListener {
                    floorImage.setImageResource(R.drawable.fourth_floor)
                }

                button5.setOnClickListener {
                    floorImage.setImageResource(R.drawable.fifth_floor)
                }

                button6.setOnClickListener {
                    floorImage.setImageResource(R.drawable.sixth_floor)
                }
            }
        }
    }
}