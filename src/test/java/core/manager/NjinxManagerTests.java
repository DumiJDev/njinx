package core.manager;

import io.githuhb.dumijdev.njinx.core.manager.NjinxManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

public class NjinxManagerTests {
    @Test
    void shouldStartNginxWithNjinxManager() throws IOException, InterruptedException {
        var njinxManager = new NjinxManager("/nginx/nginx");

        njinxManager.startAsync();

        Thread.sleep(Duration.ofSeconds(30).toMillis());

        njinxManager.stop();
    }
}
