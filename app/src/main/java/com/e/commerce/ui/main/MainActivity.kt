package com.e.commerce.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.e.commerce.R
import com.e.commerce.databinding.ActivityMainBinding
import com.e.commerce.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

// Created by Hussein_Mohammad on 5/1/2021.

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPref: SharedPref
    private var isUser: Boolean = false
    private lateinit var userToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomView.visibility = View.VISIBLE
        init()
    }

    private fun init() {
        sharedPref = SharedPref(this)
        isUser = sharedPref.getBoolean(resources.getString(R.string.is_user))
        userToken = sharedPref.getString(resources.getString(R.string.user_token)).toString()

        binding.bottomView.menu.findItem(R.id.profile).setOnMenuItemClickListener {
            if (isUser.not()) {
                Timber.d("isUser::isUser")
                navController.navigate(R.id.signup)
            } else {
                Timber.d("isUser::isUser")
                navController.navigate(R.id.profile)
            }
            return@setOnMenuItemClickListener true
        }
    }

    override fun onStart() {
        super.onStart()
        setupBottomView()
    }

    private fun setupBottomView() {

//        val appBarConfiguration: AppBarConfiguration =
//            AppBarConfiguration.Builder(R.id.home, R.id.shop).build()

        navController = Navigation.findNavController(this, R.id.nav_host)
        NavigationUI.setupWithNavController(binding.bottomView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home -> showBottomNav()
                R.id.shop -> showBottomNav()
                R.id.profile -> showBottomNav()
                R.id.categoryDetailsFragment -> showBottomNav()
                R.id.favorites -> showBottomNav()
                R.id.bag -> showBottomNav()
                else -> hideBottomNav()
            }
        }

        // val handler =
        // Handler(mainLooper).postDelayed(Runnable { navController.navigate(R.id.shop) }, 5000)
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        // NavigationUI.setupWithNavController(binding.toolbar.tool, navController)

        // binding.bottomView.setupWithNavController(Navigation.findNavController(this, R.id.nav_host))
        // binding.bottomView.setOnNavigationItemSelectedListener {item ->
        // onNavDestinationSelected(item, Navigation.findNavController(this, R.id.nav_host))
        // return@setOnNavigationItemSelectedListener true
        // }

        binding.bottomView.setOnNavigationItemReselectedListener { }
    }

    private fun showBottomNav() {
        binding.bottomView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.bottomView.visibility = View.GONE
    }
}