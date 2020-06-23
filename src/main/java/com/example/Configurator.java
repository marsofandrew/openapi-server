package com.example;

import com.example.routers.ChatRouter;
import com.example.routers.MessageRouter;
import io.reactivex.Completable;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.CookieHandler;

import java.util.Arrays;
import java.util.List;

public final class Configurator {

  public static List<Routable> routers = Arrays.asList(new ChatRouter(), new MessageRouter());

  public static Completable setupRouter(Router router, Vertx vertx) {
    return Completable.fromAction(() -> {
      //router.route().handler(CookieHandler.create());
      router.route().handler(BodyHandler.create());
      for (Routable routable : routers) {
        router.mountSubRouter("/", routable.loadRouter(vertx));
      }
    });
  }
}
