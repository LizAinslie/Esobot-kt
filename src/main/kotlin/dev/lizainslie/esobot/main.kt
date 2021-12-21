package dev.lizainslie.esobot

import dev.lizainslie.esobot.commands.HelpCommand
import dev.lizainslie.esobot.commands.PingCommand
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

val logger: Logger = LoggerFactory.getLogger("Main-Context")

fun main(args: Array<String>) {

    var configLocation = Paths.get("").toAbsolutePath().resolve("config.json").toString()
    if (args.isNotEmpty()) {
        configLocation = Paths.get(args[0]).toAbsolutePath().toString()
    }

    logger.debug("Reading config: $configLocation")
    val configFile = File(configLocation)

    if (configFile.isDirectory) {
        logger.debug("Config must be a file, not a directory. Supplied: $configLocation")
        exitProcess(1)
    }

    if (!configFile.exists()) {
        logger.debug("Config file does not exist: $configLocation")
        exitProcess(1)
    }

    if (!configFile.canRead()) {
        logger.debug("Cannot access config file at $configLocation. Need read access")
        exitProcess(1)
    }

    val config: EsoBotConfig = Json.decodeFromString(configFile.readText())
    logger.debug("Config loaded from $configLocation")

    val bot = EsoBot(config)
    bot.login()

    logger.debug("Successfully logged in, registering commands.")

    bot.registerCommand(PingCommand())
    bot.registerCommand(HelpCommand())
}