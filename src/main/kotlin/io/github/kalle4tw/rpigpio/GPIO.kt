package io.github.kalle4tw.rpigpio

/**
 * Raspberry Pi GPIO Library for Kotlin
 *
 * Simple GPIO control with automatic Pi version detection and native library loading.
 * Supports all Raspberry Pi models (Pi 1-5, Zero, Zero W).
 *
 * @author Kalle4tw
 * @since 1.0.0
 *
 * Usage:
 * ```kotlin
 * import io.github.kalle4tw.rpigpio.GPIO
 *
 * fun main() {
 *     GPIO.setHigh(4)   // Pin 4 auf HIGH
 *     GPIO.setLow(17)   // Pin 17 auf LOW
 *     val state = GPIO.read(27)  // Pin 27 lesen
 * }
 * ```
 */
object GPIO {

    private var initialized = false
    private val initLock = Any()

    init {
        loadNativeLibrary()
    }

    private fun loadNativeLibrary() {
        try {
            val osArch = System.getProperty("os.arch").lowercase()

            val archPath = when {
                osArch.contains("aarch64") || osArch.contains("arm64") -> {
                    val isPi5 = java.io.File("/dev/gpiochip4").exists()
                    if (isPi5) "linux-aarch64-pi5" else "linux-aarch64"
                }
                osArch.contains("armv7") || osArch.contains("armhf") -> "linux-armv7"
                osArch.contains("arm") -> {
                    val is32Bit = System.getProperty("sun.arch.data.model") == "32"
                    if (is32Bit) "linux-armv6-32" else "linux-armv6-64"
                }
                else -> throw GpioException(
                    "Unsupported architecture: $osArch. This library only supports ARM (Raspberry Pi)."
                )
            }

            val resourcePath = "/native/$archPath/libgpio_jni.so"
            val inputStream = GPIO::class.java.getResourceAsStream(resourcePath)
                ?: throw GpioException("Native library not found: $resourcePath")

            val tempFile = java.io.File.createTempFile("libgpio_jni", ".so").apply { deleteOnExit() }

            inputStream.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            System.load(tempFile.absolutePath)

        } catch (e: GpioException) {
            throw e
        } catch (e: Exception) {
            throw GpioException("Failed to load GPIO library: ${e.message}", e)
        }
    }

    fun init(model: String = "auto") {
        synchronized(initLock) {
            if (initialized) return

            val result = nativeInit(model)
            if (result != 0) {
                val error = GpioError.fromCode(result)
                throw GpioException("GPIO initialization failed: ${error.description} (code: ${error.code})")
            }

            initialized = true
            Runtime.getRuntime().addShutdownHook(Thread { cleanup() })
        }
    }

    private fun ensureInitialized() {
        if (!initialized) init()
    }

    fun setHigh(pin: Int) {
        ensureInitialized()
        nativeSetHigh(pin)
    }

    fun setLow(pin: Int) {
        ensureInitialized()
        nativeSetLow(pin)
    }

    fun set(pin: Int, state: Boolean) {
        if (state) setHigh(pin) else setLow(pin)
    }

    fun read(pin: Int): Boolean {
        ensureInitialized()
        return nativeRead(pin) != 0
    }

    fun setInput(pin: Int) {
        ensureInitialized()
        nativeSetInput(pin)
    }

    fun setOutput(pin: Int) {
        ensureInitialized()
        nativeSetOutput(pin)
    }

    fun cleanup() {
        synchronized(initLock) {
            if (initialized) {
                nativeCleanup()
                initialized = false
            }
        }
    }



    // Native methods
    private external fun nativeInit(model: String): Int
    private external fun nativeSetHigh(pin: Int)
    private external fun nativeSetLow(pin: Int)
    private external fun nativeRead(pin: Int): Int
    private external fun nativeSetInput(pin: Int)
    private external fun nativeSetOutput(pin: Int)
    private external fun nativeCleanup()
}