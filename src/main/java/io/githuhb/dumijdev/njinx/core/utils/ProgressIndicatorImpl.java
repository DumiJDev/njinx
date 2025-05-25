package io.githuhb.dumijdev.njinx.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgressIndicatorImpl implements ProgressIndicator {
  private final Logger logger = LoggerFactory.getLogger(ProgressIndicatorImpl.class);

  @Override
  public void updateProgress(long downloadedBytes, long totalBytes) {
    int progress = (int) ((downloadedBytes * 100) / totalBytes);
    int progressBarLength = 50;
    int filledLength = (int) ((progress / 100.0) * progressBarLength);

    String progressBar = "=".repeat(filledLength) + " ".repeat(progressBarLength - filledLength);

    logger.info("Download progress: [{}] {}%", progressBar, progress);
  }
}
