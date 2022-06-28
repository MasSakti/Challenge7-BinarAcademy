package com.mutawalli.challenge7.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mutawalli.challenge7.R
import com.mutawalli.challenge7.databinding.FragmentLoginBinding
import com.mutawalli.challenge7.resource.Status
import com.mutawalli.challenge7.ui.MainActivity
import com.mutawalli.challenge7.utils.SharedPreference

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private var sharedPref: SharedPreference? = null
    private var status = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).loginViewModel

        binding.btnLogin.setOnClickListener {

            viewModel.login(
                binding.etEmailLogin.text.toString(),
                binding.etPasswordLogin.text.toString()
            )

            viewModel.loginStatus.observe(viewLifecycleOwner) { user ->
                when (user.status) {
                    Status.SUCCESS -> {
                        if (user.data != null) {
                            viewModel.saveUserDataStore(true, user.data.id)
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            Toast.makeText(
                                requireContext(),
                                "Login Berhasil",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (binding.etEmailLogin.text.isEmpty() && binding.etPasswordLogin.text.isNullOrEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                R.string.EmailPasswordKosong,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else if (binding.etEmailLogin.text.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                R.string.EmailKosong,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!binding.etEmailLogin.text
                                .matches(Regex("^[a-zA-Z0-9_.]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$"))
                        ) {
                            Toast.makeText(
                                requireContext(),
                                R.string.EmailInvalid,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (binding.etPasswordLogin.text.isNullOrEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                R.string.PasswordKosong,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (binding.etPasswordLogin.text!!.length < 8) {
                            Toast.makeText(
                                requireContext(),
                                R.string.PasswordKurang,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!binding.etPasswordLogin.text!!.matches(Regex("(?=.*[a-z])(?=.*[A-Z]).+"))) {
                            Toast.makeText(
                                requireContext(),
                                R.string.PasswordUpLow,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "User Tidak Ditemukan",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

        binding.tvCreate.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}