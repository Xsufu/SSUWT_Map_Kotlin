package com.xolary.ssuwtmap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xolary.ssuwtmap.databinding.ActivityMainBinding
import com.xolary.ssuwtmap.laborathory.LaboratoryBuilding
import com.xolary.ssuwtmap.mainBuilding.MainBuilding
import com.xolary.ssuwtmap.second.SecondBuilding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)

        val requestCamera = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                startActivity(Intent(this, ScannerActivity::class.java))
            } else {
                Toast.makeText(
                    applicationContext,
                    "Отказано в доступе к камере",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        binding.qrScanner.setOnClickListener{
            requestCamera.launch(android.Manifest.permission.CAMERA)
        }

        val buildNumber = intent.getStringExtra("buildNumber")?.toInt()
        val floor = intent.getStringExtra("floor")?.toInt()

        Log.d("buildNumberLog", buildNumber.toString())

        when(buildNumber) {
            1 -> {
                supportFragmentManager.commit {
                    add(R.id.fragmentContainerView, MainBuilding.newInstance())
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }

            2 -> {
                supportFragmentManager.commit {
                    add(R.id.fragmentContainerView, LaboratoryBuilding.newInstance())
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }

            }

            3 -> {
                supportFragmentManager.commit {
                    add(R.id.fragmentContainerView, SecondBuilding.newInstance())
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            else -> {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }
}