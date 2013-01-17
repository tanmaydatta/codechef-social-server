package codechef.api.interfaces;

import codechef.api.models.ApiResponse;
import codechef.api.models.FcmTokenModel;
import codechef.api.models.MessageApiModel;
import codechef.api.models.TokenResponse;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public interface UserApi {

    ApiResponse getNewToken(@NotNull @HeaderParam("token") String token);

    ApiResponse getUserById(@PathParam("userId") String id);

//    ApiResponse getAllPosts(@PathParam("userId") String userId);

    ApiResponse registerUser(@PathParam("userId") String userId, TokenResponse token);

    ApiResponse getUserFeed(@QueryParam("lastUpdatedAt") Long updatedAt, @NotNull @HeaderParam("token") String token);

    ApiResponse getUserNotifications(@QueryParam("lastUpdatedAt") Long updatedAt, @NotNull @HeaderParam("token") String token);

    ApiResponse getUserMessages(@QueryParam("user") String user,
                                @QueryParam("lastUpdatedAt") Long updatedAt,
                                @NotNull @HeaderParam("token") String token);

    ApiResponse sendMessage(@QueryParam("user") String user,
                            MessageApiModel msg, @NotNull @HeaderParam("token") String token);

    ApiResponse setFcmToken(FcmTokenModel fcmToken, @NotNull @HeaderParam("token") String token);

    ApiResponse getFriends(@NotNull @HeaderParam("token") String token);

    ApiResponse searchUsers(@NotNull String query);

    ApiResponse getMessages(@NotNull @HeaderParam("token") String token);

}
