package com.xolary.ssuwtmap

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xolary.ssuwtmap.databinding.FragmentRoutesBinding


class RoutesFragment : Fragment() {

    private lateinit var binding: FragmentRoutesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoutesBinding.inflate(inflater, container, false)

        val routesList = listOf("...", "Главный", "Лабораторный", "К2")

        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, routesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        return binding.root
    }

    private var itemFrom: String = ""
    private var itemTo: String = ""

    private var latFrom: Float = 0.0F
    private var lonFrom: Float = 0.0F
    private var latTo: Float = 0.0F
    private var lonTo: Float = 0.0F

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                //Получаем выбранный объект
                itemFrom = parent.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                //Получаем выбранный объект
                itemTo = parent.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.accept.setOnClickListener {
            // Проверка заполненности всех полей
            if (itemFrom == itemTo) {
                if (itemFrom == "...") {
                    Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Пункты прибытия и отправления не должны совпадать", Toast.LENGTH_SHORT).show()
                }
            } else if (itemFrom == "...") {
                Toast.makeText(requireContext(), "Выберите пункт отправления", Toast.LENGTH_SHORT).show()
            } else if (itemTo == "...") {
                Toast.makeText(requireContext(), "Выберите пункт прибытия", Toast.LENGTH_SHORT).show()
            } else {
                when(itemFrom) {
                    "Главный" -> {
                        latFrom = 55.028423F
                        lonFrom = 82.91461F
                    }
                    "Лабораторный" -> {
                        latFrom = 55.046783F
                        lonFrom = 82.91261F
                    }

                    "К2" -> {
                        latFrom = 55.048637F
                        lonFrom = 82.92047F
                    }
                }

                when(itemTo) {
                    "Главный" -> {
                        latTo = 55.028423F
                        lonTo = 82.91461F
                    }
                    "Лабораторный" -> {
                        latTo = 55.046783F
                        lonTo = 82.91261F
                    }

                    "К2" -> {
                        latTo = 55.048637F
                        lonTo = 82.92047F
                    }
                }

                // Создаем интент для построения маршрута
                var intent = Intent("ru.yandex.yandexmaps.action.BUILD_ROUTE_ON_MAP")
                intent.setPackage("ru.yandex.yandexmaps")

                val infos: List<ResolveInfo> = requireContext().packageManager.queryIntentActivities(intent, 0)

                // Проверяем, установлены ли Яндекс.Карты
                if (infos == null || infos.isEmpty()) {
                    // Если нет - будем открывать страничку МЯК в Google Play
                    intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=ru.yandex.yandexmaps")
                } else {
                    intent.putExtra("lat_from", latFrom)
                    intent.putExtra("lon_from", lonFrom)
                    intent.putExtra("lat_to", latTo)
                    intent.putExtra("lon_to", lonTo)
                }

                // Запускаем нужную Activity
                startActivity(intent)
            }


        }
    }
}