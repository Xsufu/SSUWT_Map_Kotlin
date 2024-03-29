package com.xolary.ssuwtmap.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xolary.ssuwtmap.R
import com.xolary.ssuwtmap.databinding.FragmentSecondBuildingBinding

class SecondBuilding : Fragment() {

    private lateinit var binding: FragmentSecondBuildingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBuildingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().runOnUiThread {
            binding.apply {
                button1.setOnClickListener {
                    floorImage.setImageResource(R.drawable.one_etag)
                }

                button2.setOnClickListener {
                    floorImage.setImageResource(R.drawable.two_etag)
                }

                button3.setOnClickListener {
                    floorImage.setImageResource(R.drawable.three_etag)
                }

                button4.setOnClickListener {
                    floorImage.setImageResource(R.drawable.four_etag)
                }

                button5.setOnClickListener {
                    floorImage.setImageResource(R.drawable.five_etag)
                }

                button6.setOnClickListener {
                    floorImage.setImageResource(R.drawable.six_etag)
                }

                button7.setOnClickListener {
                    floorImage.setImageResource(R.drawable.seven_etag)
                }

                button8.setOnClickListener {
                    floorImage.setImageResource(R.drawable.eight_etag)
                }
            }
        }
    }
}