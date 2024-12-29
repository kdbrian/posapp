package io.kdbrian.minipos.android

import android.app.Application
import timber.log.Timber

class MinPosApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}