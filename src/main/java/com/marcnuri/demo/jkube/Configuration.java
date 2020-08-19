package com.marcnuri.demo.jkube;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import javax.inject.Singleton;
import javax.ws.rs.Produces;

@Singleton
public class Configuration {

  @Produces
  @Singleton
  public DataHolder options() {
    return new MutableDataSet();
  }

  @Produces
  @Singleton
  public Parser parser(DataHolder options) {
    return Parser.builder(options).build();
  }

  @Produces
  @Singleton
  public HtmlRenderer htmlRenderer(DataHolder options) {
    return HtmlRenderer.builder(options).build();
  }
}
