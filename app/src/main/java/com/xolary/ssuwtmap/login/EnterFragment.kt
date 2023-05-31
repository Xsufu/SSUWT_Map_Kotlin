package com.xolary.ssuwtmap.login

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xolary.ssuwtmap.StudentApplication
import com.xolary.ssuwtmap.data.StudentViewModel
import com.xolary.ssuwtmap.databinding.FragmentEnterBinding
import com.xolary.ssuwtmap.encryption.sha256
import com.xolary.ssuwtmap.encryption.toHexString
import com.xolary.ssuwtmap.sharedPreferences.SharedPreferencesManager
import kotlin.properties.Delegates

class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: EnterFragmentArgs by navArgs()

    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModel.StudentViewModelFactory(
            (activity?.application as StudentApplication).database.studentDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = SharedPreferencesManager(requireContext())

        if (navigationArgs.isBack == 1) {
            binding.loginText.setText(navigationArgs.login)
            binding.passwordText.setText(navigationArgs.password)
        }

        if (sharedPref.getAuthState()) {
            val action = EnterFragmentDirections.actionEnterFragmentToMainFragment()
            findNavController().navigate(action)
        } else {
            binding.loginButton.setOnClickListener {
                authStudent()
            }

            binding.signupButton.setOnClickListener {
                val action = EnterFragmentDirections.actionEnterFragmentToRegistrationFragment()
                findNavController().navigate(action)
            }
        }
    }

    // Проверка заполненности полей
    private fun isEntryValid(): Boolean {
        return viewModel.isLoginEntryValid(
            binding.loginText.text.toString(),
            binding.passwordText.text.toString()
        )
    }

    private var isExist by Delegates.notNull<Int>()

    // Проверка наличия пользователя в системе и правильности введённого пароля
    private fun isStudentExist() {
        viewModel.isStudentExist(binding.loginText.text.toString()).observe(viewLifecycleOwner) {
            isExist = it
            if (isExist == 1) {
                isPasswordCorrect()
            } else {
                Toast.makeText(
                    activity,
                    "Пользователь с указанным логином не зарегистрирован",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Проверка правильности введённого пароля
    private fun isPasswordCorrect() {
        viewModel.retrieveStudent(binding.loginText.text.toString())
            .observe(viewLifecycleOwner) { studentLD ->
                if ((sha256(binding.passwordText.text.toString())
                        .toHexString()).uppercase() == studentLD.password
                ) {
                    val sharedPref = SharedPreferencesManager(requireContext())
                    sharedPref.saveAuth()
                    val action = EnterFragmentDirections.actionEnterFragmentToMainFragment()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        activity,
                        "Неправильный логин или пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // Авторизация студента
    private fun authStudent() {
        if (isEntryValid()) {
            isStudentExist()
        } else {
            Toast.makeText(activity, "Проверьте заполненность полей", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}