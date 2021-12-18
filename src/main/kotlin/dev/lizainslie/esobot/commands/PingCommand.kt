package dev.lizainslie.esobot.commands

import dev.lizainslie.botlin.commands.BaseCommand
import dev.lizainslie.botlin.commands.DiscordCommandContext

class PingCommand : BaseCommand("ping", "Check the bot ping", "") {
    override fun runDiscord(ctx: DiscordCommandContext) {
        val time = System.currentTimeMillis()
        ctx.event.reply("Pong!").flatMap {
            ctx.event.hook.editOriginal("Pong! ${System.currentTimeMillis() - time}ms")
        }.queue()
    }

    override fun runRevolt() {
        // todo: impl. revolt
    }
}