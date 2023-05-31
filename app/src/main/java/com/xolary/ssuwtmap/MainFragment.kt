package com.xolary.ssuwtmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.xolary.ssuwtmap.databinding.FragmentMainBinding
import com.xolary.ssuwtmap.sharedPreferences.SharedPreferencesManager

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerViewMain) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.scannerFragment) {
                binding.exitFromAppButton.visibility = View.INVISIBLE
            } else {
                binding.exitFromAppButton.visibility = View.VISIBLE
            }
        }

        binding.exitFromAppButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setMessage("Вы уверены, что хотите выйти из аккаунта?")
            val dialog = builder.create()

            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена") { _, _ ->
                dialog.dismiss()
            }

            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ок") { _, _ ->
                val sharedPref = SharedPreferencesManager(requireContext())
                sharedPref.clearAuth()
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToEnterFragment())
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}