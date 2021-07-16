package com.shags.lodge.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CmsSessionListener implements HttpSessionListener {
  private static final Logger logger = LoggerFactory.getLogger(CmsSessionListener.class);

  @Override
  public void sessionCreated( HttpSessionEvent event) {
    logger.info("addSession["+event.getSession().getId()+"]");
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
    logger.info("removeSession["+event.getSession().getId()+"]");
    CmsSessionContext.removeSession(event.getSession());
  }

}

