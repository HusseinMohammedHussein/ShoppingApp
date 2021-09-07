package com.e.commerce.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.e.commerce.R
import com.e.commerce.databinding.ActivityMainBinding
import com.e.commerce.util.SharedPref
import timber.log.Timber

// Created by Hussein_Mohammad on 5/1/2021.

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null
    private var sharedPref: SharedPref? = null
    private var isUser: Boolean = false
    private lateinit var userToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomView.visibility = View.VISIBLE
        init()
    }

    private fun init() {
        sharedPref = SharedPref(this)
//        isUser = sharedPref?.getBoolean(resources.getString(R.string.is_user))!!
        Timber.d("BeforeClickIsUser::${sharedPref?.getBoolean(resources.getString(R.string.is_user))}")
        userToken = sharedPref?.getString(resources.getString(R.string.user_token)).toString()

        binding.bottomView.menu.findItem(R.id.profile).setOnMenuItemClickListener {
            if (!sharedPref?.getBoolean(resources.getString(R.string.is_user))!!) {
                Timber.d("AfterClickIsUser::$isUser")
                navController?.navigate(R.id.signup)
            } else {
                navController?.navigate(R.id.profile)
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
        navController?.let { NavigationUI.setupWithNavController(binding.bottomView, it) }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home -> showBottomNav()
                R.id.shop -> showBottomNav()
                R.id.profile -> showBottomNav()
                R.id.productsCategoryFragment -> showBottomNav()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}