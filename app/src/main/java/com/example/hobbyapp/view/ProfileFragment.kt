package com.example.hobbyapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.FragmentProfileBinding
import com.example.hobbyapp.databinding.FragmentRegisterBinding
import com.example.hobbyapp.viewmodel.RegisterViewModel
import com.example.hobbyapp.viewmodel.UpdateViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UpdateViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")

        // Gunakan binding untuk merujuk ke elemen UI dan mengatur listener
        binding.btnUpdateName.setOnClickListener {
            val firstName = binding.txtFirstName.text.toString()
            val lastName = binding.txtLastName.text.toString()

            if(firstName != "" && lastName != ""){
                viewModel.updateUser(username.toString(),firstName,lastName)
            }else{
                Toast.makeText(requireContext(), "Updated successfully", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUpdatePass.setOnClickListener {
            val password = binding.txtPassword.text.toString()
            val confirmPassword = binding.txtConfPass.text.toString()
            if (password == confirmPassword) {
                viewModel.updatePass(username.toString(),password)
            } else {
                Toast.makeText(requireContext(), "Password and confirmation password do not match", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { dialog, which ->
                    val action = ProfileFragmentDirections.actionLoginFragment2()
                    Navigation.findNavController(requireView()).navigate(action)
                }
                .setNegativeButton("No") { dialog, which ->
                }
                .show()
        }
    }

}