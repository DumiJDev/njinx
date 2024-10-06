package io.githuhb.dumijdev.njinx.core.constants;

public class NginxDirectives {

    public static class LocationDirectives {
        public static final String PROXY_PASS = "proxy_pass";
        public static final String REWRITE = "rewrite";
        public static final String HOST = "host";
        public static final String TRY_FILES = "try_files";
        public static final String INDEX = "index";
        public static final String RETURN = "return";
        public static final String ROOT = "root";
        public static final String ALIAS = "alias";
        public static final String INTERNAL = "internal";
        public static final String ERROR_PAGE = "error_page";
        public static final String AUTH_BASIC = "auth_basic";
        public static final String AUTH_BASIC_USER_FILE = "auth_basic_user_file";
        public static final String LIMIT_RATE = "limit_rate";
    }

    public static class HttpDirectives {
        public static final String INCLUDE = "include";
        public static final String SERVER = "server";
        public static final String CLIENT_MAX_BODY_SIZE = "client_max_body_size";
        public static final String KEEPALIVE_TIMEOUT = "keepalive_timeout";
        public static final String SEND_TIMEOUT = "send_timeout";
        public static final String GZIP = "gzip";
        public static final String GZIP_MIN_LENGTH = "gzip_min_length";
        public static final String GZIP_TYPES = "gzip_types";
        public static final String GZIP_PROXIED = "gzip_proxied";
        public static final String GZIP_DISABLE = "gzip_disable";
        public static final String LOG_FORMAT = "log_format";
        public static final String ACCESS_LOG = "access_log";
        public static final String ERROR_LOG = "error_log";
        public static final String CHARSET = "charset";
        public static final String TCP_NODELAY = "tcp_nodelay";
        public static final String TYPES_HASH_MAX_SIZE = "types_hash_max_size";
    }

    public static class ServerDirectives {
        public static final String LISTEN = "listen";
        public static final String SERVER_NAME = "server_name";
        public static final String ROOT = "root";
        public static final String INDEX = "index";
        public static final String ERROR_PAGE = "error_page";
        public static final String REWRITE = "rewrite";
        public static final String RETURN = "return";
        public static final String SSL_CERTIFICATE = "ssl_certificate";
        public static final String SSL_CERTIFICATE_KEY = "ssl_certificate_key";
        public static final String SSL_PROTOCOLS = "ssl_protocols";
        public static final String SSL_CIPHERS = "ssl_ciphers";
        public static final String CLIENT_MAX_BODY_SIZE = "client_max_body_size";
        public static final String KEEPALIVE_TIMEOUT = "keepalive_timeout";
        public static final String ACCESS_LOG = "access_log";
        public static final String ERROR_LOG = "error_log";
    }

    public static class EventsDirectives {
        public static final String WORKER_CONNECTIONS = "worker_connections";
        public static final String USE = "use";
        public static final String ACCEPT_MUTEX = "accept_mutex";
        public static final String MULTI_ACCEPT = "multi_accept";
    }

    public static class UpstreamDirectives {
        public static final String SERVER = "server";
        public static final String KEEPALIVE = "keepalive";
        public static final String IP_HASH = "ip_hash";
        public static final String LEAST_CONN = "least_conn";
        public static final String LEAST_TIME = "least_time";
        public static final String HASH = "hash";
        public static final String FAIR = "fair";
        public static final String ZONE = "zone";
    }

    public static class GlobalDirectives {
        public static final String USER = "user";
        public static final String WORKER_PROCESSES = "worker_processes";
        public static final String PID = "pid";
        public static final String WORKER_RLIMIT_NOFILE = "worker_rlimit_nofile";
        public static final String INCLUDE = "include";
        public static final String EVENTS = "events";
        public static final String HTTP = "http";
        public static final String STREAM = "stream";
        public static final String ERROR_LOG = "error_log";
    }
}
