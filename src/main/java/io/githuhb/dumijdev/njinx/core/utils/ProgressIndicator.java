package io.githuhb.dumijdev.njinx.core.utils;

@FunctionalInterface
public interface ProgressIndicator {
  void updateProgress(long downloadedBytes, long totalBytes);
}
