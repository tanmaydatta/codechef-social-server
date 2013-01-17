package codechef.api.implementations;

import codechef.api.interfaces.UserApi;
import codechef.api.models.ApiResponse;
import codechef.api.models.FcmTokenModel;
import codechef.api.models.FeedApiModelResponse;
import codechef.api.models.MessageApiModel;
import codechef.api.models.MessageApiModelResponse;
import codechef.api.models.MessageList;
import codechef.api.models.PostApiModelResponse;
import codechef.api.models.TokenResponse;
import codechef.CodechefService;
import codechef.api.models.UserApiModelResponse;
import codechef.models.Messages;
import codechef.models.Post;
import codechef.models.User;
import codechef.models.UserRating;
import codechef.utils.ModelToApiModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static codechef.utils.ModelToApiModel.toMessageApiModel;
import static codechef.utils.ModelToApiModel.toUserApiModel;

@Path("users")
@Singleton
public class UserApiImpl implements UserApi {

    @Inject
    CodechefService service;

    @Override
    @GET
    @Path("{userId}/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getUserById(@PathParam("userId") String id) {
        ApiResponse<UserApiModelResponse> response = new ApiResponse<>(200);
        User user = service.getUserById(id);
        if (user == null) {
            response.setStatus(500);
            return response;
        }
        response.setResult(toUserApiModel(user));
        return response;
    }

    @Override
    @GET
    @Path("getNewToken")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getNewToken(@NotNull @HeaderParam("token") String token) {
        ApiResponse<TokenResponse> response = new ApiResponse<>(200);
        TokenResponse tokenResponse = service.getNewToken(token, true);
        if (tokenResponse == null) {
            response.setStatus(500);
            return response;
        }
        response.setResult(tokenResponse);
        return response;
    }

//    @Override
//    @GET
//    @Path("{userId}/posts")
//    @Produces(MediaType.APPLICATION_JSON)
//    public ApiResponse getAllPosts(@PathParam("userId") String userId) {
//        Set<Post> posts = service.getAllPosts(userId);
//        ApiResponse<Set<PostApiModelResponse>> response = new ApiResponse<>(200);
//        if (posts == null) {
//            response.setStatus(500);
//            return response;
//        }
//        response.setResult(posts.stream().map(ModelToApiModel::toPostApiModel).collect(Collectors.toSet()));
//        return response;
//    }

    @Override
    @POST
    @Path("register/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse registerUser(@PathParam("userId") String userId, TokenResponse token) {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        response.setResult(service.registerUser(userId, token));
        return response;
    }

    @Override
    @GET
    @Path("feed/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getUserFeed(@NotNull @QueryParam("lastUpdatedAt") Long updatedAt,
                                   @NotNull @HeaderParam("token") String token) {
        ApiResponse<List<FeedApiModelResponse>> response = new ApiResponse<>(200);
        List<FeedApiModelResponse> responses = service.getUserFeed(token, updatedAt);
        responses.removeIf(r -> r.getUser().equals(r.getActionUser()));
        response.setResult(responses);
        return response;
    }

    @Override
    @GET
    @Path("notifications/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getUserNotifications(@NotNull @QueryParam("lastUpdatedAt") Long updatedAt,
                                            @NotNull @HeaderParam("token") String token) {
        ApiResponse<List<FeedApiModelResponse>> response = new ApiResponse<>(200);
        response.setResult(service.getUserNotifications(token, updatedAt));
        return response;
    }

    @Override
    @GET
    @Path("messages/{userId}/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getUserMessages(@PathParam("userId") String userId,
                                       @NotNull @QueryParam("lastUpdatedAt") Long updatedAt,
                                       @NotNull @HeaderParam("token") String token) {
        ApiResponse<List<MessageApiModelResponse>> response = new ApiResponse<>(200);
        response.setResult(service.getMessages(token, updatedAt, userId));
        return response;
    }

    @Override
    @POST
    @Path("messages/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse sendMessage(@PathParam("userId") String user, MessageApiModel msg,
                                   @NotNull @HeaderParam("token") String token) {
        ApiResponse<MessageApiModelResponse> response = new ApiResponse<>(200);
        response.setResult(toMessageApiModel(service.sendMessage(token, user, msg)));
        return response;
    }

    @Override
    @POST
    @Path("fcmToken/set")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse setFcmToken(FcmTokenModel fcmToken, @NotNull @HeaderParam("token") String token) {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        response.setResult(service.setFcmToken(fcmToken, token));
        return response;
    }

    @Override
    @GET
    @Path("friends/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getFriends(@NotNull @HeaderParam("token") String token) {
        ApiResponse<List<UserRating>> response = new ApiResponse<>(200);
        response.setResult(service.getRatingsOfFriends(service.getUserByToken(token)));
        return response;
    }

    @GET
    @Path("search/{query}")
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse searchUsers(@PathParam("query") @NotNull String query) {
        ApiResponse<List<String>> response = new ApiResponse<>(200);
        response.setResult(service.searchUsers(query));
        return response;
    }

    @GET
    @Path("messages/list")
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getMessages(@NotNull @HeaderParam("token") String token) {
        ApiResponse<MessageList> response = new ApiResponse<>(200);
        response.setResult(service.getMessageList(token));
        return response;
    }
}
