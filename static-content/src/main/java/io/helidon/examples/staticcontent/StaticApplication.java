package io.helidon.examples.staticcontent;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.StaticContentSupport;
import io.helidon.webserver.WebServer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Static Content
 * <p>
 * Access
 * <p>
 * http://127.0.0.1:2333/
 * <p>
 * AND
 * <p>
 * http://127.0.0.1:2333/static/sagiri.jpg
 * http://127.0.0.1:2333/static/microprofile-spec-2.0.pdf
 *
 * @author biezhi
 * @date 2018/9/12
 */
public class StaticApplication {

    public static void main(String[] args) throws Exception {

        Path staticPath = Paths.get(ClassLoader.getSystemResource("static").toURI());

        Routing routing = Routing.builder()
                .register("/static", StaticContentSupport.create(staticPath))
                .register("/", StaticContentSupport.builder("/html")
                        .welcomeFileName("index.html").build())
                .build();

        WebServer webServer = WebServer.create(ServerConfiguration.builder()
                .port(2333)
                .build(), routing);
        webServer.start();
    }

}
