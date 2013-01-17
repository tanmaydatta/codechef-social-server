package codechef.api;

import codechef.api.models.CodechefContestsApiModel;
import codechef.api.models.CodechefProblemsResponse;
import codechef.api.models.CodechefRankApiModel;
import codechef.api.models.CodechefSetApiModel;
import codechef.api.models.CodechefSubmissionsApiModel;
import codechef.api.models.CodechefTokenResponse;
import codechef.api.models.CodechefUserApiModel;
import codechef.api.models.SetMembers;
import codechef.models.Friend;
import codechef.modules.MainConfig;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CodechefApiClient {

    @Inject
    OkHttpClient client;

    @Inject
    MainConfig config;

    private static final Gson GSON = new GsonBuilder().create();
    private static final String TOKEN_BODY_JSON = "{\"grant_type\": \"refresh_token\", \"refresh_token\": \"%s\", " +
            "\"client_id\": \"%s\", \"client_secret\": \"%s\"}";
    private static final String TOKEN_URL = "https://api.codechef.com/oauth/token";
    private static final String PROBLEMS_URL = "https://api.codechef.com/problems/%s";
    private static final String USER_URL = "https://api.codechef.com/users/%s";
    private static final String RANKINGS_URL = "https://api.codechef.com/rankings/%s?offset=%s&limit=25";
    private static final String CONTESTS_URL = "https://api.codechef.com/contests?status=%s";
    private static final String SET_URL = "https://api.codechef.com/sets";
    private static final String SET_MEMBERS_URL = "https://api.codechef.com/sets/members/get?setName=%s";
    private static final String SUBMISSIONS_URL = "https://api.codechef.com/submissions?username=%s&limit=20";

    public CodechefTokenResponse getToken(String refreshToken) {
        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .addHeader("content-Type", "application/json")
                .post(RequestBody.create(null, String.format(TOKEN_BODY_JSON, refreshToken,
                        config.getCLIENT_ID(), config.getCLIENT_SECRET())))
                .build();
        Response apiResponse;
        String response = "no response";
        try {
            apiResponse = client.newCall(request).execute();
            response = CharStreams.toString(apiResponse.body().charStream());
            System.out.println(response);
            CodechefTokenResponse codechefTokenResponse = GSON.fromJson(response, CodechefTokenResponse.class);
            return codechefTokenResponse;
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            return new CodechefTokenResponse();
        }
    }

    public CodechefProblemsResponse getProblemsByCategoryName(String categoryName, int offSet, String accessToken) {
        Request request = new Request.Builder()
                .url(String.format(PROBLEMS_URL, categoryName) + "?offset="
                        + Integer.toString(offSet) + "&limit=100&sortBy=problemCode")
                .addHeader("content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response apiResponse;
        try {
            apiResponse = client.newCall(request).execute();
            CodechefProblemsResponse problemsResponse = GSON.fromJson(apiResponse.body().charStream(),
                    CodechefProblemsResponse.class);
            System.out.println(apiResponse.body().string());
            return problemsResponse;
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            return new CodechefProblemsResponse();
        }
    }

    public String getFullName(String userId, String accessToken) {
        Request request = new Request.Builder()
                .url(String.format(USER_URL, userId) + "?fields=username, fullname")
                .addHeader("content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response apiResponse;
        try {
            apiResponse = client.newCall(request).execute();
            CodechefUserApiModel userApiModel = GSON.fromJson(apiResponse.body().charStream(),
                    CodechefUserApiModel.class);
            System.out.println(apiResponse.body().string());
            return userApiModel.getResult().getData().getContent().getFullname();
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            return null;
        }
    }

    public CodechefContestsApiModel getContestsByStatus(String status, String accessToken) throws Exception {
        Request request = new Request.Builder()
                .url(String.format(CONTESTS_URL, status))
                .addHeader("content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response apiResponse;
        try {
            apiResponse = client.newCall(request).execute();
            CodechefContestsApiModel contestsResponse = GSON.fromJson(apiResponse.body().charStream(),
                    CodechefContestsApiModel.class);
            System.out.println(apiResponse.body().string());
            return contestsResponse;
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public CodechefRankApiModel getRankingsOfContest(String contestCode, int offSet, String accessToken) {
        Request request = new Request.Builder()
                .url(String.format(RANKINGS_URL, contestCode, Integer.toString(offSet)))
                .addHeader("content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response apiResponse;
        try {
            apiResponse = client.newCall(request).execute();
            CodechefRankApiModel rankResponse = GSON.fromJson(apiResponse.body().charStream(),
                    CodechefRankApiModel.class);
            System.out.println(apiResponse.body().string());
            return rankResponse;
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            return new CodechefRankApiModel();
        }
    }

    public List<SetMembers.Member> getFriendsOfUser(String accessToken) throws Exception {
        List<String> setNames = getSetNames(accessToken);
        List<SetMembers.Member> res = new ArrayList<>();
        for (String s : setNames) {
            res.addAll(getSetMembers(accessToken, s));
        }
        return res;
    }

    public CodechefSubmissionsApiModel getSubmissionsOfUser(String userId, String token) throws Exception {
        Request request = new Request.Builder()
                .url(String.format(SUBMISSIONS_URL, userId))
                .addHeader("content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Response apiResponse;
        String response = "no response";
        try {
            apiResponse = client.newCall(request).execute();
            response = CharStreams.toString(apiResponse.body().charStream());
            System.out.println(response);
            CodechefSubmissionsApiModel rankResponse = GSON.fromJson(response, CodechefSubmissionsApiModel.class);
            return rankResponse;
        } catch (Exception e) {
            //TODO: log
            System.out.println("submissions exception");
            System.out.println(response);
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private List<SetMembers.Member> getSetMembers(String accessToken, String setName) throws Exception {
        Request request = new Request.Builder()
                .url(String.format(SET_MEMBERS_URL, setName))
                .addHeader("content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response apiResponse;
        String response = "no response";
        try {
            apiResponse = client.newCall(request).execute();
            response = CharStreams.toString(apiResponse.body().charStream());
            System.out.println(response);
            SetMembers sets = GSON.fromJson(response, SetMembers.class);
            System.out.println(apiResponse.body().string());
            return sets.getResult().getData().getContent();
        } catch (Exception e) {
            //TODO: log
            System.out.println("set members exception");
            System.out.println(response);
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private List<String> getSetNames(String accessToken) throws Exception {
        Request request = new Request.Builder()
                .url(String.format(SET_URL))
                .addHeader("content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response apiResponse;
        String response = "no response";
        try {
            apiResponse = client.newCall(request).execute();
            response = CharStreams.toString(apiResponse.body().charStream());
            System.out.println(response);
            CodechefSetApiModel sets = GSON.fromJson(response, CodechefSetApiModel.class);
            return sets.getResult().getData().getContent().stream()
                    .map(CodechefSetApiModel.SetDesc::getSetName).collect(Collectors.toList());
        } catch (Exception e) {
            //TODO: log
            System.out.println("setnames exception");
            System.out.println(response);
            System.out.println(e.getMessage());
            throw e;
        }
    }

}
