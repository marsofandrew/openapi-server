package com.example.routers;

import com.example.Routable;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.Router;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChatRouter implements Routable {

  private static String PATH = "/chat";

  @Override
  public Router loadRouter(Vertx vertx) {
    Router router = Router.router(vertx);

    router.route(HttpMethod.GET, PATH).handler(ctx -> {
      JsonArray array = new JsonArray();

      Repository.messages.forEach((k, v) -> {
        JsonObject object = new JsonObject()
            .put("id", k)
            .put("from", v.getLeft().text)
            .put("message", v.getRight());
        array.add(object);
      });

      ctx.response().setStatusCode(200);
      ctx.response().end(String.valueOf(array.toBuffer()));
    });

    router.route(HttpMethod.POST, PATH).handler(ctx -> {
      JsonObject body = ctx.getBodyAsJson();
      String message = body.getString("message");
      String hash = getHash(message);
      Repository.messages.put(hash, new ImmutablePair<>(Repository.SenderType.CLIENT, message));

      String response = String.format("Client message is: '%s', length is: %d", message, message.length());

      String serverHash = getHash(response);
      Repository.messages.put(serverHash, new ImmutablePair<>(Repository.SenderType.SERVER, response));

      JsonObject object = new JsonObject()
          .put("id", serverHash)
          .put("message", response);

      ctx.response().setStatusCode(200);
      ctx.response().end(Buffer.newInstance(object.toBuffer()));
    });

    return router;
  }

  private String getHash(String message) {
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    byte[] encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
    return new String(encodedhash);
  }
}
