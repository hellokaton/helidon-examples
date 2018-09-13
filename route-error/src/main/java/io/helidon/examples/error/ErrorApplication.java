package io.helidon.examples.error;

import io.helidon.webserver.*;
import io.helidon.webserver.json.JsonSupport;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Optional;

/**
 * Route Error
 *
 * @author biezhi
 * @date 2018/9/12
 */
public class ErrorApplication {

    public static void main(String[] args) {

        Routing routing = Routing.builder()
                .register(JsonSupport.get())
                .get("/", (req, res) -> res.send("Hello World"))
                .get("/exp", ErrorApplication::exceptionHandle)
                .error(MyException.class, (req, res, ex) -> myExceptionHandle(res, ex))
                .error(Throwable.class, ((req, res, ex) ->
                        res.send(ex.getMessage())))
                .build();

        WebServer webServer = WebServer.create(ServerConfiguration.builder()
                .port(2333)
                .build(), routing);
        webServer.start();
    }

    private static void myExceptionHandle(ServerResponse res, MyException ex) {
        JsonObject jsonObject = Json.createObjectBuilder().add("code", ex.getCode())
                .add("msg", ex.getMessage())
                .build();

        res.send(jsonObject);
    }

    private static void exceptionHandle(ServerRequest req, ServerResponse res) {
        Optional<String> nameOptional = req.queryParams().first("name");
        try {
            String name = nameOptional.orElseThrow(() -> new MyException(2018, "name not is empty."));
            res.send("name is: " + name);
        } catch (MyException e) {
            req.next(e);
        }
    }

}
