package io.helidon.examples.metrics;

import io.helidon.config.Config;
import io.helidon.config.internal.ClasspathConfigSource;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.prometheus.PrometheusSupport;

/**
 * Metrics Example
 *
 * @author biezhi
 * @date 2018/9/12
 */
public class MetricsApplication {

    public static void main(String[] args) {
        Routing routing = Routing.builder()
                .register(PrometheusSupport.create())
                .register("/myapp", new MyService())
                .build();

        // By default this picks up application.yaml from the classpath
        Config config = Config.create();

        // Get WebServer config from the "server" section of application.yaml
        ServerConfiguration serverConfig = ServerConfiguration.
                fromConfig(config.get("server"));

        WebServer server = WebServer.builder(routing)
                .configuration(serverConfig)
                .addNamedRouting("health",
                        Routing.builder()
                                .register(new HealthCheckService())
                                .build())
                .build();

        server.start();
    }

}
