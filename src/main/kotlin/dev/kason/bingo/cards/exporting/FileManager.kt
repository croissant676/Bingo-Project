package dev.kason.bingo.cards.exporting

import com.sun.security.auth.module.NTSystem
import java.io.File

fun initialize() {
    val ntSystem = NTSystem()
    file = File("C:\\Users\\${ntSystem.name}\\AppData\\Local\\BingoProject")
    if(!file.exists()) {
        file.createNewFile()
    }
    // "error.log"
    // "curgame.ser"

}

lateinit var file: File
    private set