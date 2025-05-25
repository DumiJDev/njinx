package io.githuhb.dumijdev.njinx.core.utils;

import io.githuhb.dumijdev.njinx.core.constants.NginxVersions;
import io.githuhb.dumijdev.njinx.core.exceptions.DownloadFailedException;
import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class NginxDownloaderTest {

  @Test
  void testDownloadNginx() throws DownloadFailedException, IOException, ArchiveException {
    NginxDownloader.download(NginxVersions.NGINX_1_28_0, new File("/etc/apps"));

    Assertions.assertTrue(new File("/etc/apps/nginx-1.28.0").exists());
    Assertions.assertTrue(new File("/etc/apps/nginx-1.28.0/conf").exists());
    Assertions.assertTrue(new File("/etc/apps/nginx-1.28.0/html").exists());
    Assertions.assertTrue(new File("/etc/apps/nginx-1.28.0/logs").exists());

    Assertions.assertTrue(new File("/etc/apps/nginx-1.28.0").isDirectory());
    Assertions.assertTrue(new File("/etc/apps/nginx-1.28.0/conf").isDirectory());
    Assertions.assertTrue(new File("/etc/apps/nginx-1.28.0/html").isDirectory());
    Assertions.assertTrue(new File("/etc/apps/nginx-1.28.0/logs").isDirectory());
  }
}