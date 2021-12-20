package dev.lizainslie.esobot.commands

import dev.lizainslie.botlin.commands.BaseCommand
import dev.lizainslie.botlin.commands.DiscordCommandContext
import dev.lizainslie.esobot.EsoBot
import net.dv8tion.jda.api.EmbedBuilder

class HelpCommand : BaseCommand("help", "This help message", "") {
    override fun runDiscord(ctx: DiscordCommandContext) {
        val eb = EmbedBuilder()
        eb.setTitle("Help")

        for (command in EsoBot.instance.commands)
            eb.addField(command.name, "${command.description} | Usage: /${command.name} `${command.usage}`", false)

        ctx.event.replyEmbeds(eb.build()).queue()
    }

    override fun runRevolt() {}
}