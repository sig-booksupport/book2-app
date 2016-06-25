package app;

import static spark.Spark.get;
import static spark.Spark.port;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import app.snippet.SnippetDao;
// import app.snippet.SnippetFetcher;
import app.snippet.SnippetUrlDao;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;


public class Application {

    public static SnippetDao snippetDao;

    public static void main(String[] args) throws IOException {
        SnippetDao snippetDao = new SnippetDao();
        // SnippetFetcher fetcher = new SnippetFetcher();
        SnippetUrlDao snippetUrlDao = new SnippetUrlDao();

        Spark.staticFileLocation("/public");

        port(getHerokuAssignedPort());
        get("/hello", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("intro", "Hello Sylvan and Zeeger, did you like this snippet: ");
            model.put("code", snippetUrlDao.getUrls()); // snippetDao.getAllSnippets().iterator().next().getCode());

            return new ModelAndView(model, "/velocity/hello/test.vm");
        }, new VelocityTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
