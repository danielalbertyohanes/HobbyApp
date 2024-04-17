package com.example.hobbyapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hobbyapp.databinding.FragmentRegisterBinding
import com.example.hobbyapp.viewmodel.LoginViewModel
import com.example.hobbyapp.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegis.setOnClickListener {
            val userName = binding.txtUsername.text.toString()
            val firstName = binding.txtFirstName.text.toString()
            val lastName = binding.txtLastName.text.toString()
            val email = binding.txtEmail.text.toString()
            val pass = binding.txtPassword.text.toString()
            val confPass = binding.txtConfPass.text.toString()

            if (userName.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty()) {
                if (pass == confPass) {
                    viewModel.registerUser(userName, firstName, lastName, email, pass)
                } else {
                    Toast.makeText(requireContext(), "Password and confirmation password do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Some input is empty", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.registerSuccessLD.observe(viewLifecycleOwner) { success ->
            if (success) {
                Log.d("RegisterFragment", "Registration success parameter nya ${viewModel.registerSuccessLD.value}")
                val action = RegisterFragmentDirections.actionLoginFragment()
                Navigation.findNavController(requireView()).navigate(action)
                Toast.makeText(requireContext(), "Registration success", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("RegisterFragment", "Registration failed parameter nya ${viewModel.registerSuccessLD.value}")
            }
        }
    }
}
