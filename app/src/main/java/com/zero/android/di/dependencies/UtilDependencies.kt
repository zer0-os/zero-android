package com.zero.android.di.dependencies

import com.zero.android.common.system.Logger
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface UtilDependencies {

	fun logger(): Logger
}
