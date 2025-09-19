package utils

enum class Platform {
    ANDROID, IOS, DESKTOP, WEB
}

expect fun getPlatform(): Platform