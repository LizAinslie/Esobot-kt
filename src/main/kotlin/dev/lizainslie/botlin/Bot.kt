package dev.lizainslie.botlin

import dev.lizainslie.botlin.commands.BaseCommand
import dev.lizainslie.botlin.commands.DiscordCommandContext
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import javax.security.auth.login.LoginException

open class Bot<Config : BaseConfig>(val config: Config): ListenerAdapter() {
    class DiscordCommandListener(private val commands: MutableList<BaseCommand>) : ListenerAdapter() {
        override fun onSlashCommand(event: SlashCommandEvent) {
            val commandLabel = event.name

            val command = commands.firstOrNull { it.name == commandLabel }

            if (command != null) {
                command.runDiscord(DiscordCommandContext(event))
            } else {
                println("Command $commandLabel not registered, sending warning to channel and ignoring server-side.")
                event
                    .reply("Command $commandLabel not registered, bot has logged this incident. Please inform the bot maintainer of this issue.")
                    .setEphemeral(true)
                    .queue()
            }
        }
    }

    var jdaBuilder: JDABuilder? = null
    private var discordCommandListener: DiscordCommandListener? = null
    var jda: JDA? = null
    var discordEnabled = false
    var discordLoggedIn = false
    val commands = mutableListOf<BaseCommand>()

    init {
        if (config.discordToken != null) {
            discordEnabled = true
            discordCommandListener = DiscordCommandListener(commands)
            jdaBuilder = JDABuilder.createDefault(config.discordToken).addEventListeners(discordCommandListener)
        }
    }

    /**
     * Add Discord event listeners from [listener].
     */
    inline fun <reified T : ListenerAdapter> addDiscordEventListener(listener: T) {
        if (discordEnabled) {
            if (discordLoggedIn)
                println("Warn: Already logged into Discord, not registering new event listener: ${T::class.simpleName}.")
            else jdaBuilder?.addEventListeners(listener)
        } else println("Info: Discord token not supplied, not registering new event listener: ${T::class.simpleName}.")
    }

    /**
     * Login to all platforms.
     *
     * Attempts to set [jda] by calling [jdaBuilder]'s `build` method. If
     * successful, [discordLoggedIn] is set to true. If not successful,
     * [discordEnabled] is set to false.
     */
    fun login() {
        if (discordEnabled) {
            try {
                jda = jdaBuilder?.build()
                println("Info: Successfully logged into Discord: ${jda!!.selfUser.id}")
                discordLoggedIn = true
            } catch (e: LoginException) {
                println("Fatal: Failed to log into Discord, disabling discord functionality and logging stack trace below.")
                discordEnabled = false
                e.printStackTrace()
            }
        } else println("Info: Discord token not supplied, not logging into Discord.")

        // todo: impl. revolt
    }

    /**
     * Attempts to register a command on all platforms.
     *
     * If Discord is enabled, and we are logged in, it registers a slash
     * command using [jda]'s `upsertCommand` method.
     */
    fun registerCommand(command: BaseCommand) {
        commands += command

        // Perhaps it'd be better to move the following code to something that
        // gets called upon logging in, but right now I'm leaving it be for the
        // sake of brevity. It's up for debate though because if we did that we
        // would also have to restrict loading commands to only before logging
        // in if we didn't want to diff the loaded ones from what has already
        // been registered. Personally I prefer it like this because I'd rather
        // not diff commands. We might have to anyway. (so the commands that no
        //  longer exist don't persist on Discord's end) If that's the case I
        //  will just accept my fate and implement diffs.
        if (discordEnabled) {
            if (discordLoggedIn) {
                if (config.debug) {
                    if (config.discordTestingServer != null) {
                        jda
                            ?.getGuildById(config.discordTestingServer!!)
                            ?.upsertCommand(command.name, command.description)
                            ?.queue()
                    }
                } else {
                    jda
                        ?.upsertCommand(command.name, command.description)
                        ?.queue()
                }
            } else println("Warn: Discord should be logged in before registering any commands. Call bot.login() first.")
        } else println("Info: Discord not enabled, skipping slash command registration for command: ${command.name}")

        // todo: impl. revolt
    }
}
