package com.xolary.ssuwtmap.scannerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.xolary.ssuwtmap.R
import com.xolary.ssuwtmap.databinding.FragmentQrBinding
import org.json.JSONObject

class QrFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var binding: FragmentQrBinding
    private lateinit var scanResult: JSONObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scannerView = binding.scannerView
        val activity = requireActivity()

        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                scanResult = JSONObject(it.text)
                showImageOnAnotherActivity(scanResult)
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        codeScanner.stopPreview()
        super.onPause()
    }

    private fun showImageOnAnotherActivity(scanResult: JSONObject) {
        if (scanResult.has("build")) {
            val buildNumber = scanResult.getString("build")
            val floor = scanResult.getString("floor")
            var imageSrc = 0
            var buildName = ""

            if (buildNumber == "1") {
                buildName = "Главный"
                when (floor) {
                    "1" -> {
                        imageSrc = R.drawable.main_1
                    }
                    else -> {
                        Toast.makeText(activity, "Ошибка чтения QR кода", Toast.LENGTH_LONG).show()
                    }
                }
            }

            if (buildNumber == "2") {
                buildName = "Лабораторный"
                when (floor) {
                    "1" -> {
                        imageSrc = R.drawable.laba_1
                    }
                    else -> {
                        Toast.makeText(activity, "Ошибка чтения QR кода", Toast.LENGTH_LONG).show()
                    }
                }
            }

            val action = QrFragmentDirections.actionQrFragmentToScannerResultFragment(
                buildName,
                floor,
                imageSrc
            )

            findNavController().navigate(action)

        } else {
            Toast.makeText(activity, "Ошибка чтения QR кода", Toast.LENGTH_LONG).show()
        }
    }
}