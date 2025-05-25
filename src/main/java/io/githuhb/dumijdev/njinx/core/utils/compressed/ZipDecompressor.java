package io.githuhb.dumijdev.njinx.core.utils.compressed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipDecompressor implements Decompressor {
  private final static Logger log = LoggerFactory.getLogger(ZipDecompressor.class);

  @Override
  public void decompress(File src, File dest) throws IOException {
    Path outputPath = dest.toPath();
    Files.createDirectories(outputPath);

    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(src))) {
      ZipEntry zipEntry;
      while ((zipEntry = zis.getNextEntry()) != null) {
        Path entryPath = outputPath.resolve(zipEntry.getName());
        if (zipEntry.isDirectory()) {
          Files.createDirectories(entryPath);
        } else {
          Files.createDirectories(entryPath.getParent());
          try (OutputStream os = Files.newOutputStream(entryPath)) {
            os.write(zis.readAllBytes());
          }
        }
      }
    }
    log.info("Extracted: {} to {}", src, dest);
  }
}
