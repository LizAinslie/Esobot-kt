package dev.lizainslie.esobot

import dev.lizainslie.botlin.BaseConfig
import kotlinx.serialization.Serializable

@Serializable
data class EsoBotConfig(
    override val revoltToken: String? = null,
    override val discordToken: String? = null,
) : BaseConfig()
