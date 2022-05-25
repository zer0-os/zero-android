package com.zero.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import com.zero.android.di.components.DaggerMainComponent
import com.zero.android.di.dependencies.UtilDependencies
import com.zero.android.ui.AppLayout
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)
		setContent { AppLayout(calculateWindowSizeClass(this)) }

		DaggerMainComponent.builder()
			.context(this)
			.appDependencies(
				EntryPointAccessors.fromApplication(applicationContext, UtilDependencies::class.java)
			)
			.build()
			.inject(this)
	}
}
