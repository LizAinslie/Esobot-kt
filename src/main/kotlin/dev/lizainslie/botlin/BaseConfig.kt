package dev.lizainslie.botlin

abstract class BaseConfig {
    abstract val revoltToken: String?
    abstract val discordToken: String?
    abstract val debug: Boolean
    abstract val discordSupportServer: Long?
    abstract val discordTestingServer: Long?
}