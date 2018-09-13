package io.helidon.examples.metrics;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import io.prometheus.client.Counter;

public class MyService implements Service {

    static final Counter accessCtr = Counter.build()
        .name("requests_total").help("Total requests.").register();  

    @Override
    public void update(Routing.Rules rules) {
            rules.any(this::countAccess)
                 .get("/", this::myGet);
        }

    private void myGet(ServerRequest serverRequest, ServerResponse serverResponse) {
        serverResponse.send("get is ok!");
    }

    private void countAccess(ServerRequest request, ServerResponse response) {
            accessCtr.inc(); 
            request.next();
    }
}