package di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

actual val platformModule: Module = module {
    // Android-specific dependencies
}

fun initKoinAndroid(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(
            appModule,
            platformModule
        )
    }
}

actual fun initKoinPlatform(): KoinApplication = initKoinAndroid()