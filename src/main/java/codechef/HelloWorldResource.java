package codechef;

import com.google.inject.Inject;
import okhttp3.OkHttpClient;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class HelloWorldResource {

    @Inject
    OkHttpClient client;

    @POST
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public TestModel sayhello(TestModel model) {
        return model;
    }

}
