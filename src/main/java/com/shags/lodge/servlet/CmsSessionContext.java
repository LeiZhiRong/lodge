package com.shags.lodge.servlet;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class CmsSessionContext {

  private static final Map<String, HttpSession> ctx = new HashMap<>();

  private CmsSessionContext() {
  }

  public static void addSession(HttpSession session) {
    ctx.put(session.getId(), session);
  }

  public static HttpSession getSession(String sessionId) {
    return ctx.get(sessionId);
  }

  public static void removeSession(HttpSession session) {
    ctx.remove(session.getId());
  }

}

