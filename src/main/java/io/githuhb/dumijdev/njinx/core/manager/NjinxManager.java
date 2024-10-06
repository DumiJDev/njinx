package io.githuhb.dumijdev.njinx.core.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.runAsync;

public class NjinxManager {
    private final String nginxExecutable;
    private final String nginxDirectory;
    private NjinxStatus status = NjinxStatus.STOPPED;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public NjinxManager() {
        this("nginx");
    }

    public NjinxManager(String nginxExecutable) {
        this.nginxExecutable = nginxExecutable;
        this.nginxDirectory = new File(nginxExecutable).getParent();
        logger.debug("Nginx executable set to: {}", nginxExecutable);
        logger.debug("Nginx directory set to: {}", nginxDirectory);
    }

    public void validate() throws IOException {
        logger.info("Validating nginx configuration...");
        executeCommand(nginxExecutable + " -t");
        logger.info("Nginx configuration is valid.");
    }

    public void startAsync() throws IOException {
        runAsync(() -> unchecked(this::start));
    }

    public void start() throws IOException {
        if (isRunning()) {
            logger.warn("Nginx is already running.");
            return;
        }
        logger.info("Starting nginx...");
        executeCommand(nginxExecutable);
        status = NjinxStatus.STARTED;
        logger.info("Nginx started successfully.");
    }

    public void stop() throws IOException {
        if (!isRunning()) {
            logger.warn("Nginx is already stopped.");
            return;
        }
        logger.info("Stopping nginx...");
        executeCommand(nginxExecutable + " -s stop");
        status = NjinxStatus.STOPPED;
        logger.info("Nginx stopped successfully.");
    }

    public void restartAsync() {
        runAsync(() -> {
            try {
                restart();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void restart() throws IOException {
        logger.info("Restarting nginx...");
        stop();
        start();
    }

    private void executeCommand(String command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command.split(" "));

        if (nginxDirectory != null) {
            processBuilder.directory(new File(nginxDirectory));
            logger.debug("Setting working directory to: {}", nginxDirectory);
        }

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        }

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Error executing command: " + command);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("The process was interrupted", e);
        }

        logger.debug("Command executed successfully: {}", command);
    }

    public boolean isRunning() {
        return status == NjinxStatus.STARTED;
    }

    @FunctionalInterface
    interface CheckedRunnable {
        void run() throws Exception;
    }

    private void unchecked(CheckedRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            logger.error("Error occurred", e);
            throw new RuntimeException(e);
        }
    }
}
