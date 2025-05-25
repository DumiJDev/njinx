package io.githuhb.dumijdev.njinx.core.utils.compressed;

import org.apache.commons.compress.archivers.ArchiveException;

import java.io.File;
import java.io.IOException;

public interface Decompressor {
  void decompress(File src, File dest) throws IOException, ArchiveException;
}
