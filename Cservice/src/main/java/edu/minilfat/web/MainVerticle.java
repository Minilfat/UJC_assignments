package edu.minilfat.web;


import edu.minilfat.web.models.ClientProfile;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

    private static Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {


        Router httpRouter = Router.router(vertx);
        httpRouter.route().handler(BodyHandler.create());

        // Get requests
        httpRouter.get("/captcha/new").handler(this::newCaptcha);
        httpRouter.get("/captcha/image").handler(this::getImage);
        httpRouter.get("/captcha/verify").handler(this::verify);

        // Post requests
        httpRouter.post("/register/new/").handler(this::newUser);
        httpRouter.post("/captcha/solve").handler(this::checkAnswer);


        vertx.createHttpServer()
                .requestHandler(httpRouter::accept)
                .listen(8080, i -> {
                    if (i.succeeded())
                        LOG.info("Server started on port: " + 8080);
                    else
                        LOG.error("Couldn't start a server: ", i.cause());
                });
    }



    private void newCaptcha(RoutingContext context) {

    }

    private void getImage(RoutingContext context) {
    }

    private void verify(RoutingContext routingContext) {
    }




    private void newUser(RoutingContext context) {
        ClientProfile c = new ClientProfile();
        JsonObject json = new JsonObject().put("secret", c.getPrivateKey().toString())
                                          .put("public", c.getPublicKey().toString());

        context.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                     .end(json.encode());

        LOG.info("New client connected: " + c.getPrivateKey() + ", " +  c.getPublicKey());

    }



    private void checkAnswer(RoutingContext routingContext) {
    }




}
