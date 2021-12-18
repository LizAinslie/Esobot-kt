package dev.lizainslie.esobot

import dev.lizainslie.botlin.BaseConfig
import kotlinx.serialization.Serializable

@Serializable
data class EsoBotConfig(
    override val revoltToken: String? = null,
    override val discordToken: String? = null,
    override val debug: Boolean = false,
    override val discordSupportServer: Long? = null,
    override val discordTestingServer: Long? = null,
) : BaseConfig()
