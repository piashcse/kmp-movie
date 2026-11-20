package di

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

actual val platformModule: Module = module {
    // Android-specific dependencies
}

fun initKoinAndroid(appDeclaration: KoinAppDeclaration = {}): KoinApplication =
    initKoin(appDeclaration)

actual fun initKoinPlatform(): KoinApplication = initKoinAndroid()
