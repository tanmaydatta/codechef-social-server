package codechef.api;

import codechef.CodechefService;
import codechef.api.models.ApiResponse;
import codechef.models.User;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class RequestFilter implements ContainerRequestFilter {

    @Inject
    CodechefService service;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
//        Iterator<String> it = headers.keySet().iterator();
//
//
//        while(it.hasNext()){
//            String theKey = (String)it.next();
//            System.out.println(theKey + ": " + headers.getFirst(theKey));
//        }
        String accessToken = headers.getFirst("token");
        if (requestContext.getUriInfo().getPath().contains("problems")
                || requestContext.getUriInfo().getPath().contains("register")) {
            return;
        }
        if (accessToken == null) {
            //TODO: log
            ApiResponse<String> response = new ApiResponse<>(500);
            response.setResult("Token Header missing");
            requestContext.abortWith(Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(response)
                    .build());
        } else {
            User user = service.getUserByToken(accessToken);
            if (user == null) {
                ApiResponse<String> response = new ApiResponse<>(500);
                response.setResult("Invalid Token");
                requestContext.abortWith(Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(response)
                        .build());
            }
        }
    }
}
