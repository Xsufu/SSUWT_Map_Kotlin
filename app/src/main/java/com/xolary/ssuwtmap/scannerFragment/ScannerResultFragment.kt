package com.xolary.ssuwtmap.scannerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xolary.ssuwtmap.R
import com.xolary.ssuwtmap.databinding.FragmentScannerResultBinding

class ScannerResultFragment : Fragment() {

    private lateinit var binding: FragmentScannerResultBinding
    private val navArgs: ScannerResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScannerResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var floorNumber = "1"
        var iconSrc = R.drawable.outline_home_24

        binding.buildingName.text = navArgs.building

        when (navArgs.floor) {
            "1" -> floorNumber = "Первый этаж"
            "2" -> floorNumber = "Второй этаж"
            "3" -> floorNumber = "Третий этаж"
            "4" -> floorNumber = "Четвёртый этаж"
            "5" -> floorNumber = "Пятый этаж"
            "6" -> floorNumber = "Шестой этаж"
            "7" -> floorNumber = "Седьмой этаж"
            "8" -> floorNumber = "Восьмой этаж"
        }

        when (navArgs.building) {
            "Лабораторный" -> iconSrc = R.drawable.icon_lab
            "К2" -> iconSrc = R.drawable.icon_second
        }

        binding.floorNumberTv.text = floorNumber
        binding.buildingImage.setImageResource(iconSrc)
        binding.floorImage.setImageResource(navArgs.imageSrc)

        binding.backImage.setOnClickListener {
            findNavController().navigate(ScannerResultFragmentDirections.actionScannerResultFragmentToQrFragment())
        }
    }

}