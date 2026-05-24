package utils.storage

import com.russhwolf.settings.Settings

class SimpleInMemorySettings : Settings {
    private val storage = mutableMapOf<String, String>()

    override fun getString(key: String, defaultValue: String): String {
        return storage[key] ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        storage[key] = value
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return storage[key]?.toIntOrNull() ?: defaultValue
    }

    override fun putInt(key: String, value: Int) {
        storage[key] = value.toString()
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return storage[key]?.toLongOrNull() ?: defaultValue
    }

    override fun putLong(key: String, value: Long) {
        storage[key] = value.toString()
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return storage[key]?.toFloatOrNull() ?: defaultValue
    }

    override fun putFloat(key: String, value: Float) {
        storage[key] = value.toString()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return storage[key]?.toBooleanStrictOrNull() ?: defaultValue
    }

    override fun putBoolean(key: String, value: Boolean) {
        storage[key] = value.toString()
    }

    override fun remove(key: String) {
        storage.remove(key)
    }

    override fun hasKey(key: String): Boolean {
        return storage.containsKey(key)
    }

    override fun clear() {
        storage.clear()
    }

    override val keys: Set<String>
        get() = storage.keys

    override val size: Int
        get() = storage.size

    override fun getStringOrNull(key: String): String? = storage[key]

    override fun getIntOrNull(key: String): Int? = storage[key]?.toIntOrNull()

    override fun getLongOrNull(key: String): Long? = storage[key]?.toLongOrNull()

    override fun getFloatOrNull(key: String): Float? = storage[key]?.toFloatOrNull()

    override fun getBooleanOrNull(key: String): Boolean? = storage[key]?.toBooleanStrictOrNull()

    override fun getDouble(key: String, defaultValue: Double): Double {
        return storage[key]?.toDoubleOrNull() ?: defaultValue
    }

    override fun getDoubleOrNull(key: String): Double? = storage[key]?.toDoubleOrNull()

    override fun putDouble(key: String, value: Double) {
        storage[key] = value.toString()
    }
}