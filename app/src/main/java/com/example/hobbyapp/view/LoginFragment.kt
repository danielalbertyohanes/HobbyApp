package com.example.hobbyapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hobbyapp.databinding.FragmentLoginBinding
import com.example.hobbyapp.view.LoginFragmentDirections
import com.example.hobbyapp.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnSignin.setOnClickListener{
            var userName = binding.txtUsername.text.toString()
            var pass = binding.txtPassword.text.toString()

            sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("username", userName).apply()

            loginViewModel.loginSuccessLD.value = false
            loginViewModel.loginUser(userName, pass)
        }

        loginViewModel.loginSuccessLD.observe(viewLifecycleOwner) { success ->
            if (success) {
                val action = LoginFragmentDirections.actionMainFragment()
                Navigation.findNavController(requireView()).navigate(action)
                Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                // Setelah navigasi, reset nilai loginSuccessLD ke false
                loginViewModel.loginSuccessLD.value = false
            }
        }
        loginViewModel.loginErrorLD.observe(viewLifecycleOwner, { errorMessage ->
            Toast.makeText(requireContext(),errorMessage , Toast.LENGTH_SHORT).show()
        })


        binding.btnRegis.setOnClickListener{
            val action = LoginFragmentDirections.actionRegisterFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

    }

}
