package io.helidon.examples.metrics;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

public class HealthCheckService implements Service {

    HealthCheckService() {
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/health", this::get);
    }

    public void get(ServerRequest req, ServerResponse res) {
        res.status(200).send();
    }
}