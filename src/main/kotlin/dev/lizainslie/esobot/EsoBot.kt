package dev.lizainslie.esobot

import dev.lizainslie.botlin.Bot

class EsoBot(config: EsoBotConfig) : Bot<EsoBotConfig>(config) {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: EsoBot
    }
}