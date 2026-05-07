package com.rain.blog.backend.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StartupUrlLogger {

    private static final Logger log = LoggerFactory.getLogger(StartupUrlLogger.class);

    private final Environment environment;

    public StartupUrlLogger(Environment environment) {
        this.environment = environment;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logUrls() {
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String normalizedContextPath = normalizeContextPath(contextPath);
        String localBaseUrl = "http://localhost:" + port + normalizedContextPath;
        String networkBaseUrl = "http://" + resolveHostAddress() + ":" + port + normalizedContextPath;

        log.info("");
        log.info("----------------------------------------------------------");
        log.info("Application started successfully");
        log.info("Local:    {}", localBaseUrl);
        log.info("Network:  {}", networkBaseUrl);
        log.info("Health:   {}/health", localBaseUrl);
        log.info("Knife4j:  {}/doc.html", localBaseUrl);
        log.info("OpenAPI:  {}/v3/api-docs", localBaseUrl);
        log.info("----------------------------------------------------------");
        log.info("");
    }

    private String normalizeContextPath(String contextPath) {
        if (!StringUtils.hasText(contextPath) || "/".equals(contextPath)) {
            return "";
        }
        return contextPath.startsWith("/") ? contextPath : "/" + contextPath;
    }

    private String resolveHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            return "localhost";
        }
    }
}
