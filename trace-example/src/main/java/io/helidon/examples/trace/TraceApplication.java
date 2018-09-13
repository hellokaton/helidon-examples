package io.helidon.examples.trace;

import io.helidon.webserver.*;
import io.helidon.webserver.zipkin.ZipkinTracerBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Trace Example
 *
 * @author biezhi
 * @date 2018/9/12
 */
public class TraceApplication {

    public static void main(String[] args) {

        ServerConfiguration serverConfiguration = ServerConfiguration.builder()
                .port(2333)
                .tracer(ZipkinTracerBuilder.forService("my-application")
                        .zipkin("http://127.0.0.1:9411")
                        .build())
                .build();

        Routing routing = Routing.builder()
                .get("/", (req, res) -> res.send("Trace Hello."))
                .get("/timeout", TraceApplication::timeoutHandle)
                .build();

        WebServer.create(serverConfiguration, routing).start();
    }

    private static void timeoutHandle(ServerRequest req, ServerResponse res) {
        String seconds = req.queryParams().first("s").orElse("3");
        try {
            TimeUnit.SECONDS.sleep(Integer.valueOf(seconds));
            res.send("timeout is ok! sleep " + seconds + " seconds.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
