package ua.nure.cbrnprotector.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import ua.nure.cbrnprotector.R
import ua.nure.cbrnprotector.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        setNavigation()
    }

    private fun setNavigation() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_rif,
                R.id.nav_hostiles_indicator,
                R.id.nav_v0,
                R.id.nav_En,
                R.id.nav_Rrhz,
                R.id.nav_RrhzCrit,
                R.id.nav_EhbryaNec,
                R.id.nav_Ehbrya0,
                R.id.nav_Whbrya,
                R.id.nav_Wvo,
                R.id.nav_Wpg,
                R.id.nav_Wln,
                R.id.nav_Prhz,
                R.id.nav_Kn,
                R.id.nav_Vrhz,
                R.id.nav_Cn,
                R.id.nav_Ehbrya,
                R.id.nav_V0Mod,
                R.id.nav_Rv,
                R.id.nav_Vhbrya_Rhz_Op,
                R.id.nav_Pv,
                R.id.nav_Vv,
                R.id.nav_R0_mod,
                R.id.nav_Kiob,
                R.id.nav_E0am
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}