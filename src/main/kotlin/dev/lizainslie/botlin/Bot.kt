package dev.lizainslie.botlin

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import javax.security.auth.login.LoginException

open class Bot<Config : BaseConfig>(val config: Config) {
    var jda: JDA? = null
    var discordEnabled = false

    fun login() {
        if (config.discordToken != null) {
            try {
                jda = JDABuilder.createDefault(config.discordToken).build()
                discordEnabled = true
                println("Successfully logged into Discord: ${jda!!.selfUser.id}")
            } catch (e: LoginException) {
                println("Failed to log into Discord, stack trace below.")
                e.printStackTrace()
            }
        } else println("Discord token not supplied, ignoring Discord target.")


    }
}