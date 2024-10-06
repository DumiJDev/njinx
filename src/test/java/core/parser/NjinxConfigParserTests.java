package core.parser;

import io.githuhb.dumijdev.njinx.core.models.blocks.Http;
import io.githuhb.dumijdev.njinx.core.models.blocks.Location;
import io.githuhb.dumijdev.njinx.core.models.blocks.NjinxConfig;
import io.githuhb.dumijdev.njinx.core.models.blocks.Server;
import io.githuhb.dumijdev.njinx.core.models.params.SimpleParam;
import io.githuhb.dumijdev.njinx.core.parser.NjinxConfigParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class NjinxConfigParserTests {

    private final NjinxConfigParser parser = new NjinxConfigParser();

    private final String nginxConf = "\n" +
            "user  nobody;\n" +
            "worker_processes  1;\n" +
            "\n" +
            "error_log  logs/error.log;\n" +
            "#error_log  logs/error.log  notice;\n" +
            "#error_log  logs/error.log  info;\n" +
            "\n" +
            "pid        logs/nginx.pid;\n" +
            "\n" +
            "\n" +
            "events {\n" +
            "    worker_connections  1024;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "http {\n" +
            "    include       mime.types;\n" +
            "    default_type  application/octet-stream;\n" +
            "\n" +
            "    log_format  main  '$remote_addr - $remote_user [$time_local] \"$request\" '\n" +
            "                      '$status $body_bytes_sent \"$http_referer\" '\n" +
            "                      '\"$http_user_agent\" \"$http_x_forwarded_for\"';\n" +
            "\n" +
            "    access_log  logs/access.log  main;\n" +
            "\n" +
            "    sendfile        on;\n" +
            "    #tcp_nopush     on;\n" +
            "\n" +
            "    #keepalive_timeout  0;\n" +
            "    keepalive_timeout  65;\n" +
            "\n" +
            "    gzip  on;\n" +
            "\n" +
            "    server {\n" +
            "        listen       80;\n" +
            "        server_name  localhost;\n" +
            "\n" +
            "        #charset koi8-r;\n" +
            "\n" +
            "        #access_log  logs/host.access.log  main;\n" +
            "\n" +
            "        location / {\n" +
            "            root   html;\n" +
            "            index  index.html index.htm;\n" +
            "        }\n" +
            "\n" +
            "        #error_page  404              /404.html;\n" +
            "\n" +
            "        # redirect server error pages to the static page /50x.html\n" +
            "        #\n" +
            "        error_page   500 502 503 504  /50x.html;\n" +
            "        location = /50x.html {\n" +
            "            root   html;\n" +
            "        }\n" +
            "\n" +
            "        # proxy the PHP scripts to Apache listening on 127.0.0.1:80\n" +
            "        #\n" +
            "        #location ~ \\.php$ {\n" +
            "        #    proxy_pass   http://127.0.0.1;\n" +
            "        #}\n" +
            "\n" +
            "        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000\n" +
            "        #\n" +
            "        #location ~ \\.php$ {\n" +
            "        #    root           html;\n" +
            "        #    fastcgi_pass   127.0.0.1:9000;\n" +
            "        #    fastcgi_index  index.php;\n" +
            "        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;\n" +
            "        #    include        fastcgi_params;\n" +
            "        #}\n" +
            "\n" +
            "        # deny access to .htaccess files, if Apache's document root\n" +
            "        # concurs with nginx's one\n" +
            "        #\n" +
            "        #location ~ /\\.ht {\n" +
            "        #    deny  all;\n" +
            "        #}\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    # another virtual host using mix of IP-, name-, and port-based configuration\n" +
            "    #\n" +
            "    #server {\n" +
            "    #    listen       8000;\n" +
            "    #    listen       somename:8080;\n" +
            "    #    server_name  somename  alias  another.alias;\n" +
            "\n" +
            "    #    location / {\n" +
            "    #        root   html;\n" +
            "    #        index  index.html index.htm;\n" +
            "    #    }\n" +
            "    #}\n" +
            "\n" +
            "\n" +
            "    # HTTPS server\n" +
            "    #\n" +
            "    #server {\n" +
            "    #    listen       443 ssl;\n" +
            "    #    server_name  localhost;\n" +
            "\n" +
            "    #    ssl_certificate      cert.pem;\n" +
            "    #    ssl_certificate_key  cert.key;\n" +
            "\n" +
            "    #    ssl_session_cache    shared:SSL:1m;\n" +
            "    #    ssl_session_timeout  5m;\n" +
            "\n" +
            "    #    ssl_ciphers  HIGH:!aNULL:!MD5;\n" +
            "    #    ssl_prefer_server_ciphers  on;\n" +
            "\n" +
            "    #    location / {\n" +
            "    #        root   html;\n" +
            "    #        index  index.html index.htm;\n" +
            "    #    }\n" +
            "    #}\n" +
            "\n" +
            "}\n";

    @Test
    void shouldParseNginxDotConfFromInputStreamToNjinxConfigObject() throws IOException {
        var nginxConfLocation = "/nginx/conf/nginx.conf";

        var njinxConfig = parser.read(getClass().getResourceAsStream(nginxConfLocation));

        System.out.println(njinxConfig);


        Assertions.assertNotNull(njinxConfig);
        Assertions.assertEquals(2, size(njinxConfig.blocks()));
        Assertions.assertEquals(4, size(njinxConfig.params()));
    }

    @Test
    void shouldParseNginxDotConfFromStringToNjinxConfigObject() throws IOException {
        var njinxConfig = parser.read(nginxConf);

        System.out.println(njinxConfig);


        Assertions.assertNotNull(njinxConfig);
        Assertions.assertEquals(2, size(njinxConfig.blocks()));
        Assertions.assertEquals(4, size(njinxConfig.params()));
    }

    @Test
    void shouldParseNginxDotConfFromFileToNjinxConfigObject() throws IOException {
        var nginxConfLocation = "/nginx/conf/nginx.conf";

        var njinxConfig = parser.read(new File(nginxConfLocation));

        System.out.println(njinxConfig);


        Assertions.assertNotNull(njinxConfig);
        Assertions.assertEquals(2, size(njinxConfig.blocks()));
        Assertions.assertEquals(3, size(njinxConfig.params()));
    }

    @Test
    void shouldWriteNginxDotConfToFileFromNjinxConfigObject() throws IOException {
        var nginxConfLocationOut = "/nginx/conf/nginx_generated.conf";

        var njinxConfig = new NjinxConfig();


        njinxConfig.add(new SimpleParam("test", "1234"));


        njinxConfig.add(new SimpleParam("test2", "1234"));
        njinxConfig.add(new SimpleParam("test3", "1234"));
        njinxConfig.add(new SimpleParam("test4", "1234"));

        var http = new Http();

        http.add(new Server()
                        .add(new SimpleParam("listen", "8080"))
                        .add(new Location("/hello").add(new SimpleParam("test", "test"))));

        njinxConfig.add(http);

        parser.write(njinxConfig, new File(nginxConfLocationOut));


        Assertions.assertTrue(Files.exists(Path.of(nginxConfLocationOut)));
    }

    private int size(Iterable<?> iterable) {
        int size = 0;

        for (Object o : iterable) {
            size++;
        }

        return size;
    }

}
