package codechef;

import javax.ws.rs.PathParam;

public interface HelloWorldInterface {

    TestModel sayhello(@PathParam("userId") String id);
}
