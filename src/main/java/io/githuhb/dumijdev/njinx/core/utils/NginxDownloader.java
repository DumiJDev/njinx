package io.githuhb.dumijdev.njinx.core.utils;

import io.githuhb.dumijdev.njinx.core.exceptions.DownloadFailedException;
import io.githuhb.dumijdev.njinx.core.utils.compressed.GeneralDecompressor;
import org.apache.commons.compress.archivers.ArchiveException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class NginxDownloader {
  private static final String nginxDownloadUrl = "https://nginx.org/download/nginx-%s.%s";

  public static void download(String version, File targetPath) throws IOException, DownloadFailedException, ArchiveException {
    download(version, targetPath, new ProgressIndicatorImpl());
  }

  public static void download(String version, File targetPath, ProgressIndicator progressIndicator) throws IOException, DownloadFailedException, ArchiveException {
    var nginxDownloadUrl = createNginxDownloadUrl(version);
    var tempPath = Files.createTempDirectory(UUID.randomUUID().toString()).toFile();
    var file = FileDownloader.download(nginxDownloadUrl, tempPath, progressIndicator != null ? progressIndicator : (downloadedBytes, totalBytes) -> {
    });

    if (file != null) {
      new GeneralDecompressor().decompress(file, targetPath);
    }
  }

  private static boolean isWindows() {
    String osName = System.getProperty("os.name").toLowerCase();
    return osName.contains("win");
  }

  private static String createNginxDownloadUrl(String version) {
    return String.format(nginxDownloadUrl, version, isWindows() ? "zip" : "tar.gz");
  }
}
