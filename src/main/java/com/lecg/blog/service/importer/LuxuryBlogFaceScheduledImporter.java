package com.lecg.blog.service.importer;

import com.lecg.blog.apiclients.ApiClientHolder;
import com.lecg.blog.apiclients.ExternalBlogType;
import com.lecg.blog.exceptions.ScheduledImportException;
import com.lecg.blog.service.ApiClientService;
import com.lecg.blog.service.PostService;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;

public class LuxuryBlogFaceScheduledImporter extends ScheduledImporter {

    private final static String POLLING_PERIOD = "6000000";

    private Optional<ApiClientHolder> luxuryBlogApiClient;
    private PostService postService;

    public LuxuryBlogFaceScheduledImporter(final ApiClientService apiClientService, final PostService postService) {
        this.luxuryBlogApiClient = apiClientService.getApiClientHolder(ExternalBlogType.LUXURY_BLOG_FACE);
        this.postService = postService;
    }

    @Override
    @Scheduled(fixedDelayString = POLLING_PERIOD)
    public void scheduleUpdate() {
        if (luxuryBlogApiClient.isPresent()) {
            sync();
        } else {
            // log error
        }
    }

    @Override
    public boolean containsNewData() {
        // compare db to external data check if there is new data
        return true;
    }

    @Override
    protected void updateDatabase() throws ScheduledImportException {
        // would get the Posts using luxuryBlogApiClient and convert using PostMapper (with a new method of course)

    }
}
