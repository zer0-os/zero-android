package com.zero.android

import android.app.Application
import com.zero.android.common.system.Logger
import com.zero.android.network.NetworkInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ZeroApp : Application() {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var networkInitializer: NetworkInitializer

    override fun onCreate() {
        super.onCreate()

        logger.setup(BuildConfig.DEBUG)
        networkInitializer.initialize(context = this)
    }
}
