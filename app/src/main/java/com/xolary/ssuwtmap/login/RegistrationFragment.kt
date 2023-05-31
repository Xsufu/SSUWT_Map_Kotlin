package com.xolary.ssuwtmap.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.xolary.ssuwtmap.StudentApplication
import com.xolary.ssuwtmap.data.StudentViewModel
import com.xolary.ssuwtmap.databinding.FragmentRegistrationBinding
import com.xolary.ssuwtmap.encryption.sha256
import com.xolary.ssuwtmap.encryption.toHexString
import kotlin.properties.Delegates

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModel.StudentViewModelFactory(
            (activity?.application as StudentApplication).database.studentDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToLoginButton.setOnClickListener {
            val action =
                RegistrationFragmentDirections.actionRegistrationFragmentToEnterFragment("", "", 1)
            findNavController().navigate(action)
        }

        binding.createAccButton.setOnClickListener {
            addNewStudent()
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isRegEntryValid(
            binding.loginText.text.toString(),
            binding.passwordText.text.toString(),
            binding.nameText.text.toString(),
            binding.lastnameText.text.toString(),
            binding.groupText.toString()
        )
    }

    private fun addNewStudent() {
        if (isEntryValid()) {
            isStudentExist()
        } else {
            Toast.makeText(activity, "Проверьте заполненность полей", Toast.LENGTH_SHORT).show()
        }
    }

    private var isExist by Delegates.notNull<Int>()

    // Проверка на наличие уже созданного аккаунта
    // Если аккаунта нет, происходит регистрация
    private fun isStudentExist() {
        viewModel.isStudentExist(binding.loginText.text.toString()).observe(viewLifecycleOwner) {
            isExist = it
            if (isExist == 0) {
                viewModel.addNewStudent(
                    binding.loginText.text.toString(),
                    (sha256(binding.passwordText.text.toString())
                        .toHexString()).uppercase(),
                    binding.nameText.text.toString(),
                    binding.lastnameText.text.toString(),
                    binding.groupText.text.toString()
                )
                val action =
                    RegistrationFragmentDirections.actionRegistrationFragmentToEnterFragment(
                        binding.loginText.text.toString(),
                        binding.passwordText.text.toString(),
                        1
                    )
                findNavController().navigate(action)
            } else {
                Toast.makeText(
                    activity,
                    "Пользователь с указанным логином уже существует",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}