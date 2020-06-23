package com.example;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;

public interface Routable {
  Router loadRouter(Vertx vertx);
}
