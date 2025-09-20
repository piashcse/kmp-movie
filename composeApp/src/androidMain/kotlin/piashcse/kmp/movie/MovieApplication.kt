package piashcse.kmp.movie

import android.app.Application
import di.initKoinAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid {
            androidLogger()
            androidContext(this@MovieApplication)
        }
    }
}