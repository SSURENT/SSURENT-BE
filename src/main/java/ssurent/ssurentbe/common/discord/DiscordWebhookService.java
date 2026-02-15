package ssurent.ssurentbe.common.discord;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@Profile("prod")
@EnableConfigurationProperties(DiscordWebhookProperties.class)
public class DiscordWebhookService {

    private static final int MAX_STACKTRACE_LENGTH = 1000;
    private static final int EMBED_COLOR_RED = 0xFF0000;

    private final RestClient restClient;
    private final String webhookUrl;

    public DiscordWebhookService(DiscordWebhookProperties properties) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setReadTimeout(Duration.ofSeconds(5));
        this.restClient = RestClient.builder().requestFactory(factory).build();
        this.webhookUrl = properties.webhookUrl();
    }

    public void sendErrorNotification(Exception e) {
        try {
            String stackTrace = getStackTrace(e);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Map<String, Object> embed = Map.of(
                    "title", "500 Internal Server Error",
                    "color", EMBED_COLOR_RED,
                    "fields", List.of(
                            Map.of("name", "Exception", "value", e.getClass().getSimpleName(), "inline", true),
                            Map.of("name", "Message", "value", truncate(e.getMessage() != null ? e.getMessage() : "N/A", 200), "inline", true),
                            Map.of("name", "Timestamp", "value", timestamp, "inline", false),
                            Map.of("name", "StackTrace", "value", "```" + stackTrace + "```", "inline", false)
                    )
            );

            Map<String, Object> payload = Map.of("embeds", List.of(embed));

            restClient.post()
                    .uri(webhookUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ex) {
            log.warn("[*] Discord 웹훅 전송 실패 : {}", ex.getMessage());
        }
    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return truncate(sw.toString(), MAX_STACKTRACE_LENGTH);
    }

    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "...";
    }
}
