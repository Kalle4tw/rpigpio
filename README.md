# Raspberry Pi GPIO for Kotlin

A lightweight, easy-to-use Kotlin library for GPIO control on Raspberry Pi with native performance.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg)](https://kotlinlang.org)

## ✨ Features

- 🚀 **Native Performance** - Direct hardware access via JNI
- 🎯 **Simple API** - Control GPIOs with just one line of code
- 🔄 **Auto-Detection** - Automatically detects your Raspberry Pi model
- 📦 **Zero Configuration** - Just add to Gradle and start coding
- 🎮 **All Models Supported** - Pi 1, 2, 3, 4, 5, Zero, Zero W

## 📦 Installation

Add to your `build.gradle.kts`:
```kotlin
dependencies {
    implementation("io.github.kalle4tw:rpigpio:1.0.0")
}
```

Or `build.gradle`:
```groovy
dependencies {
    implementation 'io.github.kalle4tw:rpigpio:1.0.0'
}
```

## 🚀 Quick Start
```kotlin
import io.github.kalle4tw.rpigpio.GPIO

fun main() {
    // Set pin 4 HIGH
    GPIO.setHigh(4)
    
    // Set pin 17 LOW
    GPIO.setLow(17)
    
    // Read pin 27
    val state = GPIO.read(27)
    println("Pin 27 is: ${if (state) "HIGH" else "LOW"}")
    
    // Generic set method
    GPIO.set(18, true)  // HIGH
    GPIO.set(18, false) // LOW
}
```

## 📚 API Reference

| Method | Description |
|--------|-------------|
| `GPIO.setHigh(pin: Int)` | Set GPIO pin to HIGH |
| `GPIO.setLow(pin: Int)` | Set GPIO pin to LOW |
| `GPIO.set(pin: Int, state: Boolean)` | Set GPIO pin to specified state |
| `GPIO.read(pin: Int): Boolean` | Read GPIO pin state (true = HIGH) |
| `GPIO.setOutput(pin: Int)` | Configure pin as output |
| `GPIO.setInput(pin: Int)` | Configure pin as input |
| `GPIO.cleanup()` | Clean up resources (called automatically on shutdown) |

## ⚙️ Supported Hardware

**Tested and verified on:**
- ✅ Raspberry Pi 5
- ✅ Raspberry Pi 4 Model B
- ✅ Raspberry Pi 3 Model B v1.2
- ✅ Raspberry Pi 2 Model B v1.1
- ✅ Raspberry Pi Zero W v1.1

**Should work on (untested):**
- Raspberry Pi 4 (other variants)
- Raspberry Pi 3 (Model B+, A+)
- Raspberry Pi 1 (Model B, B+, A+)
- Raspberry Pi Zero / Zero 2 W

**Architectures:** ARMv6, ARMv7, ARM64 (aarch64)

> 💡 If you successfully test this library on an untested model, please open an issue to let me know!

## 🎯 Pin Numbering

This library uses **BCM (Broadcom) pin numbering**, not physical pin numbers.

Example: GPIO 4 = Physical Pin 7

[See pinout.xyz for reference](https://pinout.xyz/)

## 🛠️ How It Works

The library uses native C code via JNI for direct hardware access.
Native libraries are automatically extracted and loaded based on your Pi model and architecture.

## 📄 License

MIT License - see [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## ⚠️ Disclaimer

Use at your own risk. Incorrect GPIO usage can damage your Raspberry Pi. Always check your wiring before running code.


---

**Made with ❤️ for the Raspberry Pi community**