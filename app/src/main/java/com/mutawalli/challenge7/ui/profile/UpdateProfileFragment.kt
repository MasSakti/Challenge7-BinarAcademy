package com.mutawalli.challenge7.ui.profile

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
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mutawalli.challenge7.R
import com.mutawalli.challenge7.data.local.UserEntity
import com.mutawalli.challenge7.databinding.FragmentUpdateProfileBinding
import com.mutawalli.challenge7.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {

    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UpdateProfileViewModel
    private lateinit var dateListener: DatePickerDialog.OnDateSetListener
    private val calendar = Calendar.getInstance()
    private var saveImageToInternalStorage: Uri? = null

    companion object {
        private const val GALLERY = 1
        private const val CAMERA = 2
        private const val IMAGE_DIRECTORY = "RegisterUserImage"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).updateProfileViewModel

        binding.ivBacktoProfile.setOnClickListener {
            findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
        }

        val userData = arguments?.getParcelable<UserEntity>("user")

        if (userData != null) {
            binding.apply {
                etEmailUpdate.setText(userData.email)
                etUsernameUpdate.setText(userData.username)
                etBirthUpdate.setText(userData.ttl)
                etAddressUpdate.setText(userData.address)
                etNameUpdate.setText(userData.fullname)

                saveImageToInternalStorage = Uri.parse(userData.image)
                binding.ivProfileHome.setImageURI(saveImageToInternalStorage)
                Glide.with(binding.root).load(userData.image)
                    .circleCrop()
                    .into(binding.ivProfileHome)
            }
        }

        dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        binding.etBirthUpdate.setOnClickListener {
            DatePickerDialog(
                view.context,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.ivProfileHome.setOnClickListener {
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


        binding.btnSaveUpdate.setOnClickListener {
            val email = binding.etEmailUpdate.text.toString()
            val username = binding.etUsernameUpdate.text.toString()
            val fullname = binding.etNameUpdate.text.toString()
            val ttl = binding.etBirthUpdate.text.toString()
            val address = binding.etAddressUpdate.text.toString()
            val password = userData?.password
            val id = userData?.id
            val image = saveImageToInternalStorage.toString()

            Log.d("updateImage", image)

            val user = UserEntity(
                id = id!!,
                email = email,
                username = username,
                fullname = fullname,
                ttl = ttl,
                address = address,
                password = password!!,
                image = image
            )
            viewModel.update(user)

        }

        viewModel.saved.observe(viewLifecycleOwner) {
            val check = it.getContentIfNotHandled() ?: return@observe
            if (check) {
                val dialog = AlertDialog.Builder(view.context)
                dialog.setTitle("Update User")
                dialog.setMessage("Apakah Anda Yakin Ingin Update Data User ?")
                dialog.setPositiveButton("Yakin") { _, _ ->
                    Toast.makeText(requireContext(), "User Berhasil Diupdate", Toast.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
                }

                dialog.setNegativeButton("Batal") { listener, _ ->
                    listener.dismiss()
                }

                dialog.show()

            } else {
                Toast.makeText(requireContext(), "User Gagal Diupdate", Toast.LENGTH_LONG).show()
            }
        }
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
                        Glide.with(binding.root)
                            .asBitmap()
                            .load(selectedImageBitmap)
                            .circleCrop()
                            .into(binding.ivProfileHome)
                        Log.e("Save Image: ", "path :: $saveImageToInternalStorage")
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
                Glide.with(binding.root).asBitmap()
                    .load(thumbnail)
                    .circleCrop()
                    .into(binding.ivProfileHome)

            }
        }

    }

    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.etBirthUpdate.setText(sdf.format(calendar.time).toString())
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
                    val fm: Fragment = this@UpdateProfileFragment
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
                    val fm: Fragment = this@UpdateProfileFragment
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}