package com.mutawalli.challenge7.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mutawalli.challenge7.R
import com.mutawalli.challenge7.ui.detail.DetailViewModel
import com.mutawalli.challenge7.ui.detail.DetailViewModelFactory
import com.mutawalli.challenge7.ui.favorite.FavoriteViewModel
import com.mutawalli.challenge7.ui.favorite.FavoriteViewModelFactory
import com.mutawalli.challenge7.ui.home.HomeViewModel
import com.mutawalli.challenge7.ui.home.HomeViewModelFactory
import com.mutawalli.challenge7.ui.login.LoginViewModel
import com.mutawalli.challenge7.ui.login.LoginViewModelFactory
import com.mutawalli.challenge7.ui.profile.ProfileViewModel
import com.mutawalli.challenge7.ui.profile.ProfileViewModelFactory
import com.mutawalli.challenge7.ui.profile.UpdateProfileViewModel
import com.mutawalli.challenge7.ui.profile.UpdateViewModelFactory
import com.mutawalli.challenge7.ui.register.RegisterViewModel
import com.mutawalli.challenge7.ui.register.RegisterViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    val loginViewModel: LoginViewModel by viewModels{
        loginViewModelFactory
    }

    @Inject
    lateinit var detailViewModelFactory: DetailViewModelFactory
    val detailViewModel: DetailViewModel by viewModels {
        detailViewModelFactory
    }

    @Inject
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    val favoriteViewModel: FavoriteViewModel by viewModels {
        favoriteViewModelFactory
    }

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    val homeViewModel: HomeViewModel by viewModels{
        homeViewModelFactory
    }

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    val profileViewModel: ProfileViewModel by viewModels{
        profileViewModelFactory
    }

    @Inject
    lateinit var updateViewModelFactory: UpdateViewModelFactory
    val updateProfileViewModel: UpdateProfileViewModel by viewModels{
        updateViewModelFactory
    }

    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory
    val registerViewModel: RegisterViewModel by viewModels {
        registerViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

}