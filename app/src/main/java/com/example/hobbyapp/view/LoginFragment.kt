    package com.example.hobbyapp.view

    import android.content.Context
    import android.content.SharedPreferences
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
    import com.example.hobbyapp.databinding.FragmentLoginBinding
    import com.example.hobbyapp.viewmodel.LoginViewModel
    import com.example.hobbyapp.viewmodel.UpdateViewModel

    class LoginFragment : Fragment() {
        private lateinit var binding: FragmentLoginBinding
        private lateinit var  viewModel: LoginViewModel
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

            viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


            binding.btnSignin.setOnClickListener{
                var userName = binding.txtUsername.text.toString()
                var pass = binding.txtPassword.text.toString()

                if (userName.isNotEmpty() && pass.isNotEmpty()){
                    sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("username", userName).apply()

                    viewModel.loginUser(userName, pass)
                }else{
                    Toast.makeText(requireContext(), "Some input is empty", Toast.LENGTH_SHORT).show()
                }

            }
            viewModel.loginSuccessLD.observe(viewLifecycleOwner) { success ->
                if (success) {
                    Log.d("LoginFragment", "Nilai loginSuccessLD setelah permintaan: ${viewModel.loginSuccessLD.value}")
                    val action = LoginFragmentDirections.actionMainFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Login Feiled", Toast.LENGTH_SHORT).show()
                    Log.d("LoginFragment", "Nilai loginSuccessLD setelah permintaan gagal: ${viewModel.loginSuccessLD.value}")
                }
            }

            binding.btnRegis.setOnClickListener{
                val action = LoginFragmentDirections.actionRegisterFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }

        }

    }
