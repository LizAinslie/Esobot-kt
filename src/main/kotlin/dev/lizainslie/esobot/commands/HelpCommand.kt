package dev.lizainslie.esobot.commands

import dev.lizainslie.botlin.commands.BaseCommand
import dev.lizainslie.botlin.commands.DiscordCommandContext
import dev.lizainslie.esobot.EsoBot
import net.dv8tion.jda.api.EmbedBuilder

class HelpCommand : BaseCommand("commands", "List Commands", "") {
    override fun runDiscord(ctx: DiscordCommandContext) {
        val eb = EmbedBuilder()
        eb.setTitle("Command List")

        for (command in EsoBot.instance.commands)
            eb.addField(
                command.name,
                command.buildHelpText(),
                false
            )

        ctx.event.replyEmbeds(eb.build()).queue()
    }

    override fun runRevolt() {}
}