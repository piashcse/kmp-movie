package di

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    // iOS-specific dependencies
}

actual fun initKoinPlatform(): KoinApplication = initKoin()
