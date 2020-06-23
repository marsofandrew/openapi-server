package com.example.routers;

import com.example.Routable;
import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import org.apache.commons.lang3.tuple.Pair;

public class MessageRouter implements Routable {

  public static String PATH = "/message/:id";

  @Override
  public Router loadRouter(Vertx vertx) {
    Router router = Router.router(vertx);
    router.route(HttpMethod.DELETE, PATH).handler(ctx -> {
      String id = ctx.request().getParam("id");
      Pair<Repository.SenderType, String> messageObj = Repository.messages.get(id);

      if (messageObj == null) {
        ctx.response().setStatusCode(404);
        ctx.response().end();
      }

      Repository.messages.remove(id);

      ctx.response().setStatusCode(204);
      ctx.response().end();
    });
    return router;
  }
}
