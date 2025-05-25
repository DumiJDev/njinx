package core.manager;

import io.githuhb.dumijdev.njinx.core.constants.NginxVersions;
import io.githuhb.dumijdev.njinx.core.exceptions.DownloadFailedException;
import io.githuhb.dumijdev.njinx.core.manager.NjinxManager;
import io.githuhb.dumijdev.njinx.core.utils.DirectoryCleanup;
import io.githuhb.dumijdev.njinx.core.utils.NginxDownloader;
import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;

public class NjinxManagerTests {
  @Test
  void shouldDownloadAndStartNginxWithNjinxManager() throws IOException, InterruptedException, DownloadFailedException, ArchiveException {
    var file = new File("/etc/apps");

    if (file.exists()) {
      DirectoryCleanup.cleanup(file.getAbsolutePath());
      if (file.exists()) {
        file.delete();
      }
    }

    NginxDownloader.download(NginxVersions.NGINX_1_28_0, file);

    var njinxManager = new NjinxManager(new File(new File(file, "nginx-" + NginxVersions.NGINX_1_28_0), "nginx"));

    njinxManager.startAsync();

    Thread.sleep(Duration.ofSeconds(5).toMillis());

    var localhost = getLocalhost();

    Assertions.assertTrue(localhost);

    njinxManager.stopAsync();

    Thread.sleep(Duration.ofSeconds(5).toMillis());

    localhost = getLocalhost();

    Assertions.assertFalse(localhost);
  }

  private boolean getLocalhost() throws IOException, InterruptedException {
    try {

      var response = newHttpClient().send(newBuilder().GET().uri(URI.create("http://localhost")).build(), ofString());

      return response.statusCode() == 200;
    } catch (IOException e) {
      return false;
    }
  }
}
