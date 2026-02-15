package ssurent.ssurentbe.common.discord;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord")
public record DiscordWebhookProperties(String webhookUrl) {
}
