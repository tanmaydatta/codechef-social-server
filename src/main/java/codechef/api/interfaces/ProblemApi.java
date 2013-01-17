package codechef.api.interfaces;

import codechef.api.models.ApiResponse;
import codechef.api.models.ProblemApiResponse;

import javax.ws.rs.PathParam;


public interface ProblemApi {

    String getProblemHtml(@PathParam("contest") String contest,
                          @PathParam("problemCode") String problemCode);


    ApiResponse searchProblems(@PathParam("query") String query);
}
