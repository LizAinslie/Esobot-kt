package dev.lizainslie.esobot

import dev.lizainslie.botlin.BaseConfig

data class EsoBotConfig(
    override val revoltToken: String?,
    override val discordToken: String?
) : BaseConfig()
