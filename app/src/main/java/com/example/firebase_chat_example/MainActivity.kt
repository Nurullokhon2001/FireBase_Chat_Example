package com.example.firebase_chat_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.firebase_chat_example.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initViews()
    }

    private fun initViews() {
        val navController = findNavController(R.id.fragment_container_view)
        val appBatConfiguration = AppBarConfiguration(
            setOf(
                R.id.chatListFragment,
                R.id.registrationFragment,
                R.id.authorizationFragment,
            ),
        )

        binding.toolbar.setupWithNavController(navController, appBatConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}