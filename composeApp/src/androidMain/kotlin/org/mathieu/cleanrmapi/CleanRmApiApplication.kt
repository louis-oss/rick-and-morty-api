package org.mathieu.cleanrmapi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.mathieu.cleanrmapi.data.androidModule

class CleanRmApiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CleanRmApiApplication)
            modules(
                androidModule
            )
        }
    }
}
