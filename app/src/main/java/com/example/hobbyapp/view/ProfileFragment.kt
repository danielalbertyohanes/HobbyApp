package com.example.hobbyapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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


        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")


        binding.btnUpdateName.setOnClickListener {
            val firstName = binding.txtFirstName.text.toString()
            val lastName = binding.txtLastName.text.toString()

            if(firstName.isNotEmpty() && lastName.isNotEmpty()){
                viewModel.updateUser(username.toString(), firstName, lastName)
                binding.txtFirstName.setText("")
                binding.txtLastName.setText("")
            } else {
                Toast.makeText(requireContext(), "Some input is empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUpdatePass.setOnClickListener {
            val password = binding.txtPassword.text.toString()
            val confirmPassword = binding.txtConfPass.text.toString()
            if (password == confirmPassword) {
                viewModel.updatePass(username.toString(), password)
                binding.txtPassword.setText("")
                binding.txtConfPass.setText("")
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


        viewModel.userSuccessLD.observe(viewLifecycleOwner, Observer { userSuccess ->
            if (userSuccess) {
                Toast.makeText(requireContext(), "Updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Updated failed", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.passSuccessLD.observe(viewLifecycleOwner, Observer { passSuccess ->
            if (passSuccess) {
                Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Password update failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
