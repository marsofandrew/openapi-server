package com.example;

import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start(Future<Void> startFuture) throws Exception {
    super.start(startFuture);
    Router router = Router.router(vertx);
    Configurator.setupRouter(router, vertx)
        .andThen(vertx.createHttpServer()
            .requestHandler(router)
            .rxListen(8080))
        .subscribe(httpServer -> {}, startFuture::fail);
  }
}
