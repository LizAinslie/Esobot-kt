package dev.lizainslie.botlin.commands

abstract class BaseCommand(
    val name: String,
    val description: String,
    val usage: String,
) {
    abstract fun runDiscord(ctx: DiscordCommandContext)
    abstract fun runRevolt() // todo: impl. revolt
}