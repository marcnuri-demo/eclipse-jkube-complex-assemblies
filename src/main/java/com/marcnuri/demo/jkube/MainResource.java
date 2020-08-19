package com.marcnuri.demo.jkube;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;

@Path("/")
public class MainResource {

  private static final String OPT_STATIC = "/opt/static";
  private final Template main;
  private final MarkdownService md;

  @Inject
  public MainResource(Engine engine, MarkdownService md) {
    main = engine.getTemplate("main");
    this.md = md;
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance get() throws IOException {
    return main
      .data("title", "Hello Quarkus, you're so qute!")
      .data("markdownInResources", md.render(MainResource.class.getResourceAsStream("/main.md")))
      // Following files will only be available if application was deployed using Eclipse JKube
      .data("markdownInJKubeAssembly", md.render(new File(OPT_STATIC,"main.md")))
      .data("markdownFiltered", md.render(new File(OPT_STATIC, "filtered-fragment.md")));
  }

  @GET
  @Path("static/{fileName:.+}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public File getStaticFile(@PathParam("fileName") String fileName) {
    // File only available if application was deployed using Eclipse JKube
    return new File(OPT_STATIC, fileName);
  }
}
