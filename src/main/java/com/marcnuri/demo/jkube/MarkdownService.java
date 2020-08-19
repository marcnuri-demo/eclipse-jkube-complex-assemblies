package com.marcnuri.demo.jkube;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import io.smallrye.common.constraint.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Singleton
public class MarkdownService {

  private final Parser parser;
  private final HtmlRenderer renderer;

  @Inject
  public MarkdownService(Parser parser, HtmlRenderer renderer) {
    this.parser = parser;
    this.renderer = renderer;
  }

  public String render(@NotNull File file) throws IOException {
    if (file.exists() && file.isFile()) {
      try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
        return render(br);
      }
    }
    return String.format("<label class=\"error\">The requested file (%s) was not found.</label>",
      file.getAbsolutePath());
  }

  public String render(InputStream is) throws IOException {
    try (InputStreamReader isr = new InputStreamReader(is)) {
      return render(isr);
    }
  }

  private String render(Reader reader) throws IOException {
    return renderer.render(parser.parseReader(reader));
  }

}
