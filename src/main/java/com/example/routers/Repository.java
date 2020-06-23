package com.example.routers;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class Repository {

  public static final Map<String, Pair<SenderType, String>> messages = new HashMap<>();

  enum SenderType {
    SERVER("server"),
    CLIENT("client");
    public final String text;

    SenderType(String str) {
      text = str;
    }

    @Override
    public String toString() {
      return text;
    }
  }
}
