package codechef.api.implementations;

import codechef.CodechefService;
import codechef.api.interfaces.ProblemApi;
import codechef.api.models.ApiResponse;
import codechef.api.models.ProblemApiResponse;
import codechef.api.models.ProblemResponse;
import codechef.job.TokenProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("problems")
@Singleton
public class ProblemApiImpl implements ProblemApi {

    @Inject
    OkHttpClient client;

    @Inject
    CodechefService service;

    @Inject
    TokenProvider tokenProvider;

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    @GET
    @Path("/{contest}/code/{problemCode}/get")
    @Produces(MediaType.TEXT_HTML)
    public String getProblemHtml(@PathParam("contest") String contest, @PathParam("problemCode") String id) {
        Request request = new Request.Builder()
                .url("https://api.codechef.com/contests/" + contest + "/problems/" + id)
                .addHeader("content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + tokenProvider.getAdminAccessToken())
                .build();
        Response apiResponse;
        ProblemResponse response = new ProblemResponse();
        try {
            apiResponse = client.newCall(request).execute();
            response = GSON.fromJson(apiResponse.body().charStream(),
                    ProblemResponse.class);
            System.out.println(apiResponse.body().string());
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
        }
        try {
            String body = response.getResult().getData().getContent().getBody();

            String html = "<!doctype html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <script\n" +
                    "  src=\"https://code.jquery.com/jquery-3.3.1.min.js\"\n" +
                    "  integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\"\n" +
                    "  crossorigin=\"anonymous\"></script>\n" +
                    "\n" +
                    "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/showdown/1.8.6/showdown.min.js\"></script>\n" +
                    "\n" +
                    "</head>\n" +
                    "<body class=\"problem-statement\" id=\"body\" >\n" +
                    body.replaceAll("(\\\\)\\\\", "$1") +
                    "</body>\n" +
                    "\n" +
                    "<script type=\"text/javascript\">\n" +
                    "\n" +
                    "    var converter = new showdown.Converter();\n" +
                    "    converter.setOption('simpleLineBreaks', true);\n" +
                    "    console.log(showdown.getDefaultOptions());\n" +
                    "    var c = function(e) {\n" +
                    "        var t = function(e) {\n" +
                    "            if (e)\n" +
                    "                return e.replace(/([-\\\\`*_{}[\\]()#+.!])/g, \"\\\\$1\")\n" +
                    "        }\n" +
                    "          , i = function(e) {\n" +
                    "            var t = /<b>(.*?)<\\/b>/.test(e) || /<p>(.*?)<\\/p>/.test(e) || /<ul>(.*?)<\\/ul>/.test(e) || /<li>(.*?)<\\/li>/.test(e) || /<br>(.*?)/.test(e) || /(.*?)<\\/br>/.test(e);\n" +
                    "            return !t\n" +
                    "        }\n" +
                    "          , n = function(e) {\n" +
                    "            if (!e || !i(e))\n" +
                    "                return e;\n" +
                    "            e = e.replace(/~/g, \"~T\");\n" +
                    "            e = e.replace(/\\$\\$([^]*?)\\$\\$/g, t);\n" +
                    "            e = e.replace(/\\$([^]*?)\\$/g, t);\n" +
                    "            var x = new showdown.Converter();\n" +
                    "            x.setOption('simpleLineBreaks', true);\n" +
                    "            return e = x.makeHtml(e),\n" +
                    "            e\n" +
                    "        };\n" +
                    "        return n(e)\n" +
                    "    }\n" +
                    "    \n" +
                    "    \n" +
                    "    // function mdown(callback) {\n" +
                    "\n" +
                    "        document.getElementById(\"body\").innerHTML = c(document.getElementById(\"body\").innerText);\n" +
                    "    // }\n" +
                    "        // window.setTimeout(function() {\n" +
                    "    //     // document.getElementById(\"body\").innerHTML = c(text);\n" +
                    "    //     m(1, \"body\")\n" +
                    "    // }, 2000)\n" +
                    "    \n" +
                    "    // document.getElementById(\"body\").innerHTML = converter.makeHtml(document.getElementById(\"body\").innerText);\n" +
                    "    \n" +
                    "</script>\n" +
                    "<script type=\"text/x-mathjax-config\">\n" +
                    "    MathJax.Hub.Config({\n" +
                    "      \"HTML-CSS\": {\n" +
                    "        preferredFont: \"TeX\",\n" +
                    "        availableFonts: [\"STIX\",\"TeX\"],\n" +
                    "        linebreaks: { automatic:true },\n" +
                    "        EqnChunk: (MathJax.Hub.Browser.isMobile ? 10 : 50)\n" +
                    "      },\n" +
                    "      ShowMathMenu: false,\n" +
                    "      TeX: {\n" +
                    "        extensions: [\"color.js\"],\n" +
                    "        noUndefined: {\n" +
                    "          attributes: {\n" +
                    "            mathcolor: \"red\",\n" +
                    "            mathbackground: \"#FFEEEE\",\n" +
                    "            mathsize: \"90%\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        Macros: { href: \"{}\" }\n" +
                    "      },\n" +
                    "      tex2jax: {\n" +
                    "        inlineMath: [['$','$'], ['\\\\(','\\\\)']],\n" +
                    "        displayMath: [ [\"$$\",\"$$\"], [\"\\\\[\", \"\\\\]\"] ],\n" +
                    "        multiline: true,\n" +
                    "        processEscapes: true,\n" +
                    "        ignoreClass: \"[a-zA-Z1-9]*\",\n" +
                    "        processClass: \"mathjax-support\"\n" +
                    "      },\n" +
                    "      menuSettings: {\n" +
                    "        context: \"Browser\"\n" +
                    "      },\n" +
                    "      messageStyle: \"none\"\n" +
                    "    });\n" +
                    "</script>\n" +
                    "\n" +
                    "<script type=\"text/javascript\">\n" +
                    "    var m = function(e, t) {\n" +
                    "                if (!(e > 33)) {\n" +
                    "                    var i = this\n" +
                    "                      , n = document.getElementById(t);\n" +
                    "                    if ($(n).html() && $(n).html().trim()) {\n" +
                    "                        n.className += \" mathjax-support\";\n" +
                    "                        var r = n.querySelectorAll(\"*\");\n" +
                    "                        r.forEach(function(e) {\n" +
                    "                            e.className += \" mathjax-support\"\n" +
                    "                        }),\n" +
                    "                        MathJax.Hub.Queue([\"Typeset\", MathJax.Hub])\n" +
                    "                    } else\n" +
                    "                        window.setTimeout(function() {\n" +
                    "                            m(e + 1, t)\n" +
                    "                        }, 300)\n" +
                    "                }\n" +
                    "            };\n" +
                    "</script>\n" +
                    "\n" +
                    "</script>\n" +
                    " -->\n" +
                    " <script type=\"text/javascript\">\n" +
                    "   jQuery.loadScript = function (callback) {\n" +
                    "      jQuery.ajax({\n" +
                    "          url: \"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML\",\n" +
                    "          dataType: 'script',\n" +
                    "          success: callback,\n" +
                    "          async: true\n" +
                    "      });\n" +
                    "  }\n" +
                    "  $.loadScript(function () {\n" +
                    "    window.setTimeout(function() {\n" +
                    "      m(1, \"body\"); \n" +
                    "    }, 3000)\n" +
                    "    \n" +
                    "  });\n" +
                    " </script>\n" +
                    "</html>";
            return html;
        } catch (Exception e) {
            tokenProvider.refreshAdminToken();
            return "Could not load problem statment. Try Again after some time";
        }
    }

    @GET
    @Path("search/{query}")
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse searchProblems(@PathParam("query") String query) {
        ApiResponse<ProblemApiResponse> response = new ApiResponse<>(200);
        response.setResult(service.searchProblems(query));
        return response;
    }
}
