package ru.autodoc.tz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import ru.autodoc.tz.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()

        KeyboardVisibilityEvent.registerEventListener(activity = this) {
            binding.navView.isVisible = !it
        }
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
        binding.navView.setupWithNavController(navController = navController)
        navController.let {
            NavigationUI.setupWithNavController(
                binding.navView,
                it
            )
            binding.navView.setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, it)
                true
            }
            binding.navView.setOnItemReselectedListener { item ->
                it.popBackStack(destinationId = item.itemId, inclusive = false)
            }
        }
    }
}