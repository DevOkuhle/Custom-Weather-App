package com.example.customweatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.customweatherapp.data.model.ComposableFunctionAttributes
import com.example.customweatherapp.ui.navigation.SetupCustomWeatherNavigationGraph
import com.example.customweatherapp.ui.theme.CustomWeatherAppTheme
import com.example.customweatherapp.viewModel.CustomWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var customWeatherViewModel: CustomWeatherViewModel
    private lateinit var navigationController: NavHostController
    private val customModifier: Modifier = Modifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomWeatherAppTheme {
                customWeatherViewModel = hiltViewModel<CustomWeatherViewModel>()
                navigationController = rememberNavController()
                val composableFunctionAttributes = ComposableFunctionAttributes(
                    modifier = customModifier,
                    navigationController = navigationController
                )
                SetupCustomWeatherNavigationGraph(composableFunctionAttributes, customWeatherViewModel)
            }
        }
    }
}
