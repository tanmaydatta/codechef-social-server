package codechef.api.implementations;

import codechef.api.interfaces.JobApi;
import codechef.api.models.ApiResponse;
import codechef.job.ContestJob;
import codechef.job.FriendActivityJob;
import codechef.job.FriendJob;
import codechef.job.ProblemJob;
import codechef.job.RankingJob;
import codechef.job.interfaces.PollingTask;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("jobs")
@Singleton
public class JobApiImpl implements JobApi {

    @Inject
    @ProblemJob
    PollingTask problemTask;

    @Inject
    @ContestJob
    PollingTask contestTask;

    @Inject
    @RankingJob
    PollingTask rankingTask;

    @Inject
    @FriendJob
    PollingTask friendTask;

    @Inject
    @FriendActivityJob
    PollingTask friendActivityTask;

    @Override
    @POST
    @Path("problems/start")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse startProblemJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            problemTask.poll();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("problems/stop")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse stopProblemJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            problemTask.stop();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("contests/start")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse startContestJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            contestTask.poll();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("contests/stop")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse stopContestJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            contestTask.stop();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("rankings/start")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse startRankingJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            rankingTask.poll();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("ranking/stop")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse stopRankingJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            rankingTask.stop();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("friends/start")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse startFriendJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            friendTask.poll();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("friends/stop")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse stopFriendJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            friendTask.stop();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("friendActivity/start")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse startFriendActivityJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            friendActivityTask.poll();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }

    @Override
    @POST
    @Path("friendActivity/stop")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse stopFriendActivityJob() {
        ApiResponse<Boolean> response = new ApiResponse<>(200);
        try {
            friendActivityTask.stop();
            response.setResult(true);
        } catch (Exception e) {
            //TODO: log
            response.setResult(false);
            e.printStackTrace();
        }
        return response;
    }
}
