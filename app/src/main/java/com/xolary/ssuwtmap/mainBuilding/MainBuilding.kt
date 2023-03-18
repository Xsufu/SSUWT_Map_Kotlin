package com.xolary.ssuwtmap.mainBuilding

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xolary.ssuwtmap.R
import com.xolary.ssuwtmap.databinding.FragmentMainBuildingBinding

class MainBuilding : Fragment() {

    private lateinit var binding: FragmentMainBuildingBinding

    companion object {
        fun newInstance() = MainBuilding()
    }

    private lateinit var viewModel: MainBuildingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBuildingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainBuildingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}