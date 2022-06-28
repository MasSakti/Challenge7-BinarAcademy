package com.mutawalli.challenge7.ui.register

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mutawalli.challenge7.R
import com.mutawalli.challenge7.databinding.FragmentRegisterBinding
import com.mutawalli.challenge7.ui.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel
    private lateinit var dateListener: DatePickerDialog.OnDateSetListener
    private val calendar = Calendar.getInstance()
    private var saveImageToInternalStorage: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).registerViewModel

        binding.tvAddImage.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(view.context)
            pictureDialog.setTitle("Pilih Aksi")
            val pictureDialogItems = arrayOf(
                "Pilih dari Galeri",
                "Ambil dari Kamera"
            )
            pictureDialog.setItems(pictureDialogItems) { _, which ->
                when (which) {
                    0 -> choosePhotoFromGalery()
                    1 -> takePhotoFromCamera()
                }

            }

            pictureDialog.show()
        }

        dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        binding.etBirthRegister.setOnClickListener {
            DatePickerDialog(
                view.context,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        updateDateInView()

        binding.btnRegister.setOnClickListener {
            if (binding.etUsernameRegister.text.isEmpty() || binding.etNameRegister.text.isEmpty() ||
                binding.etEmailRegister.text.isEmpty() || binding.etBirthRegister.text.isEmpty() ||
                binding.etAddressRegister.text.isEmpty() || binding.etPasswordRegister.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Tidak boleh ada isian yang kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!binding.etPasswordRegister.text!!.matches(Regex("(?=.*[a-z])(?=.*[A-Z]).+"))) {
                Toast.makeText(requireContext(), R.string.PasswordUpLow, Toast.LENGTH_SHORT).show()
            } else if (binding.etPasswordRegister.text!!.length < 8) {
                Toast.makeText(requireContext(), R.string.PasswordKurang, Toast.LENGTH_SHORT).show()
                // else if (binding.etPasswordRegister.text != binding.etPasswordRegisterVerif.text)
            } else if (binding.etPasswordRegister.text == binding.etPasswordRegisterVerif.text) {
                Toast.makeText(requireContext(), R.string.PasswordVerif, Toast.LENGTH_SHORT).show()
            } else if (!binding.etEmailRegister.text
                    .matches(Regex("^[a-zA-Z0-9_.]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$"))
            ) {
                Toast.makeText(requireContext(), R.string.EmailInvalid, Toast.LENGTH_SHORT).show()
            } else {
                val username = binding.etUsernameRegister.text.toString()
                val fullname = binding.etNameRegister.text.toString()
                val email = binding.etEmailRegister.text.toString()
                val ttl = binding.etBirthRegister.text.toString()
                val address = binding.etAddressRegister.text.toString()
                val password = binding.etPasswordRegister.text.toString()
                val image = saveImageToInternalStorage.toString()
                viewModel.save(email, username, fullname, ttl, address, password, image)
            }
        }

        viewModel.saved.observe(viewLifecycleOwner) {
            val check = it.getContentIfNotHandled() ?: return@observe
            if (check) {
                Toast.makeText(requireContext(), "Akun Berhasil Didaftarkan", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(requireContext(), "Akun Gagal Didaftarkan", Toast.LENGTH_LONG).show()
            }
        }

        binding.ivBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.etBirthRegister.setText(sdf.format(calendar.time).toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    val contentUri = data.data
                    try {
                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                contentUri
                            )

                        saveImageToInternalStorage = saveImageToInternalStorage(selectedImageBitmap)

                        Log.e("Save Image: ", "path :: $saveImageToInternalStorage")
                        binding.ivProfileRegister.setImageBitmap(selectedImageBitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            requireContext(),
                            "Failed to Load the image from",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else if (requestCode == CAMERA) {
                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                saveImageToInternalStorage = saveImageToInternalStorage(thumbnail)

                Log.e("Save Image: ", "path :: $saveImageToInternalStorage")
                binding.ivProfileRegister.setImageBitmap(thumbnail)

            }
        }

    }

    private fun takePhotoFromCamera() {
        Dexter.withActivity(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    val galleryIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val fm: Fragment = this@RegisterFragment
                    fm.startActivityForResult(galleryIntent, CAMERA)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRationalDialogForPermssions()
            }
        }).onSameThread().check()
    }

    private fun choosePhotoFromGalery() {
        Dexter.withActivity(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    val galleryIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI
                    )
                    val fm: Fragment = this@RegisterFragment
                    fm.startActivityForResult(galleryIntent, GALLERY)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRationalDialogForPermssions()
            }
        }).onSameThread().check()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(requireContext())
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }

    private fun showRationalDialogForPermssions() {
        AlertDialog.Builder(requireContext()).setMessage(
            "" +
                    "It Looks like you have turned off permission required" +
                    "for this feature. It can be enabled under the" +
                    "Applications Settings"
        )
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("CANCEL") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    companion object {
        private const val GALLERY = 1
        private const val CAMERA = 2
        private const val IMAGE_DIRECTORY = "RegisterUserImage"
    }

}