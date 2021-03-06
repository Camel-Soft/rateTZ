package com.camelsoft.ratetz._presentation.activity_main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._presentation.utils.dialogs.showError
import com.camelsoft.ratetz._presentation.utils.dialogs.showPermShouldGive
import com.camelsoft.ratetz._presentation.utils.reqPermissions
import com.camelsoft.ratetz.common.state.StateSync
import com.camelsoft.ratetz.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var weakContext: WeakReference<Context>
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weakContext = WeakReference<Context>(this)

        navController = findNavController(R.id.mainNavHostFragment)
        setSupportActionBar(binding.mainToolbar)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.fragGraphRate))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.mainToolbar.setNavigationOnClickListener { onBackPressed() }

        //getPermissions()
    }

    // Top menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }
            else -> { super.onOptionsItemSelected(item) }
        }
    }

    // Permissions
    private fun getPermissions() {
        reqPermissions(weakContext.get()!!) { result ->
            when (result) {
                is StateSync.Success -> {
                    result.data.let {
                        if (!result.data)
                            showPermShouldGive(weakContext.get()!!) { finish() }
                    }
                }
                is StateSync.Error -> {
                    val backupMessage = resources.getString(R.string.error_in)+
                            " ActivityMain.getPermissions: "+
                            resources.getString(R.string.error_text_unknown)
                    showError(weakContext.get()!!, result.message?:backupMessage) {
                        finish()
                    }
                }
            }
        }
    }
}