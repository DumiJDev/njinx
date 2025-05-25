package io.githuhb.dumijdev.njinx.core.utils.compressed;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TarGzDecompressor implements Decompressor {
  private final static Logger log = LoggerFactory.getLogger(TarGzDecompressor.class);

  @Override
  public void decompress(File src, File dest) throws IOException, ArchiveException {
    Path outputPath = dest.toPath();
    Files.createDirectories(outputPath);

    try (TarArchiveInputStream tais = new TarArchiveInputStream(new GzipCompressorInputStream(new FileInputStream(src)))) {

      TarArchiveEntry entry;
      while ((entry = tais.getNextEntry()) != null) {
        Path entryPath = outputPath.resolve(entry.getName());
        if (entry.isDirectory()) {
          Files.createDirectories(entryPath);
        } else {
          Files.createDirectories(entryPath.getParent());
          try (OutputStream os = Files.newOutputStream(entryPath)) {
            os.write(tais.readAllBytes());
          }
        }
      }
    }
    log.info("Extracted: {} to {}", src, dest);
  }
}

