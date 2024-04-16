package com.example.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.FragmentRegisterBinding
import com.example.hobbyapp.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegis.setOnClickListener{
            var userName = binding.txtUsername.text.toString()
            var firstName=binding.txtFirstName.text.toString()
            var lastName=binding.txtLastName.text.toString()
            var email=binding.txtEmail.text.toString()
            var pass = binding.txtPassword.text.toString()
            var confPass= binding.txtConfPass.text.toString()

            if(pass==confPass){
                viewModel.registerUser(userName, firstName, lastName, email, pass)
            }else{
                Toast.makeText(requireContext(), "Password and confirmation password do not match", Toast.LENGTH_SHORT).show()
            }
        }
        // Observer untuk LiveData
        viewModel.registerSuccessLD.observe(viewLifecycleOwner) { success ->
            if (success) {
                val action= RegisterFragmentDirections.actionLoginFragment()
                Navigation.findNavController(requireView()).navigate(action)
                Snackbar.make(requireView(), "Registration successful", Snackbar.LENGTH_SHORT).show()
            } else{
                Snackbar.make(requireView(), "Registration failed", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
