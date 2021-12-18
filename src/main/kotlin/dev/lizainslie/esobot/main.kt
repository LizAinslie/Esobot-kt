package dev.lizainslie.esobot

import dev.lizainslie.esobot.commands.PingCommand
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    var configLocation = Paths.get("").toAbsolutePath().resolve("config.json").toString()
    if (args.isNotEmpty()) {
        configLocation = Paths.get(args[0]).toAbsolutePath().toString()
    }

    val configFile = File(configLocation)

    if (configFile.isDirectory) {
        println("Config must be a file, not a directory. Supplied: $configLocation")
        exitProcess(1)
    }

    if (!configFile.exists()) {
        println("Config file does not exist: $configLocation")
        exitProcess(1)
    }

    if (!configFile.canRead()) {
        println("Cannot access config file at $configLocation. Need read access")
        exitProcess(1)
    }

    val config: EsoBotConfig = Json.decodeFromString(configFile.readText())

    val bot = EsoBot(config)
    bot.login()

    bot.registerCommand(PingCommand())
}