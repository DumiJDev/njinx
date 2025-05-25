package io.githuhb.dumijdev.njinx.core.utils.compressed;

import org.apache.commons.compress.archivers.ArchiveException;

import java.io.File;
import java.io.IOException;

/**
 * General decompressor implementation that handles ZIP and TAR.GZ files.
 */
public class GeneralDecompressor implements Decompressor {

  /**
   * Decompresses the source file to the destination path.
   *
   * @param src  Source compressed file
   * @param dest Destination path for decompressed content
   * @throws IOException      If an I/O error occurs
   * @throws ArchiveException If archive handling fails
   */
  @Override
  public void decompress(File src, File dest) throws IOException, ArchiveException {
    validateSourceFile(src);
    validateDestination(dest);

    var fileName = src.getName();
    validateFileFormat(fileName);

    ensureDestinationDirectory(dest);

    var uncompressor = createDecompressor(fileName);
    uncompressor.decompress(src, dest);
  }

  private void validateSourceFile(File src) {
    if (src == null)
      throw new IllegalArgumentException("Source file cannot be null");

    if (!src.exists())
      throw new IllegalArgumentException("Source file does not exist: " + src);

    if (!src.isFile())
      throw new IllegalArgumentException("Source must be a file: " + src);

    if (!src.canRead())
      throw new IllegalArgumentException("Cannot read source file: " + src);
  }

  private void validateDestination(File dest) {
    if (dest == null)
      throw new IllegalArgumentException("Destination file cannot be null");

    if (dest.exists() && !dest.canWrite())
      throw new IllegalArgumentException("Cannot write to destination: " + dest);
  }

  private void validateFileFormat(String fileName) {
    if (!isTarGzip(fileName) && !isZip(fileName))
      throw new IllegalArgumentException("Unsupported file format: " + fileName);
  }

  private void ensureDestinationDirectory(File dest) throws IOException {
    File parentDir = dest.getParentFile();
    if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs())
      throw new IOException("Failed to create destination directory: " + parentDir);
  }

  /**
   * Checks if the file is a ZIP archive.
   */
  private boolean isZip(String name) {
    return name.endsWith(".zip");
  }

  /**
   * Checks if the file is a TAR.GZ archive.
   */
  private boolean isTarGzip(String name) {
    return name.endsWith(".tar.gz");
  }

  /**
   * Creates appropriate decompressor based on file extension.
   */
  private Decompressor createDecompressor(String name) {
    if (isTarGzip(name)) {
      return new TarGzDecompressor();
    } else if (isZip(name)) {
      return new ZipDecompressor();
    }

    throw new IllegalArgumentException("File unsupported: " + name);
  }
}
