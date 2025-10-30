package io.github.kalle4tw.rpigpio

/**
 * GPIO error codes returned by native operations
 */
enum class GpioError(val code: Int, val description: String) {
    ERROR_DEVMEM(-1, "Failed to open /dev/mem or /dev/gpiomem"),
    ERROR_MMAP(-2, "Memory mapping failed"),
    ERROR_OFFSET(-3, "Invalid GPIO offset or register access"),
    ERROR_GPIOD(-4, "GPIO character device access failed");

    companion object {
        fun fromCode(code: Int): GpioError =
            entries.find { it.code == code } ?: ERROR_DEVMEM
    }
}

/**
 * Exception thrown when GPIO operations fail
 *
 * @property message Error description
 * @property cause Original exception if any
 */
class GpioException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)