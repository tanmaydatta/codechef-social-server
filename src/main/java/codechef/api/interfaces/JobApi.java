package codechef.api.interfaces;

import codechef.api.models.ApiResponse;

public interface JobApi {

    ApiResponse startProblemJob();

    ApiResponse stopProblemJob();

    ApiResponse startContestJob();

    ApiResponse stopContestJob();

    ApiResponse startRankingJob();

    ApiResponse stopRankingJob();

    ApiResponse startFriendJob();

    ApiResponse stopFriendJob();

    ApiResponse startFriendActivityJob();

    ApiResponse stopFriendActivityJob();
}
