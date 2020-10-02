package com.lecg.blog.service;

import com.lecg.blog.apiclients.ApiClientHolder;
import com.lecg.blog.apiclients.ExternalBlogType;
import com.lecg.blog.apiclients.FancyBlogTmApiClient;
import com.lecg.blog.apiclients.LuxuryBlogFaceApiClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApiClientService {

    private List<ApiClientHolder> apiClientHolders;

    public ApiClientService() {
        apiClientHolders = new ArrayList<>();
        apiClientHolders.add(new ApiClientHolder(ExternalBlogType.LUXURY_BLOG_FACE, new LuxuryBlogFaceApiClient()));
        apiClientHolders.add(new ApiClientHolder(ExternalBlogType.FANCY_BLOG_TM, new FancyBlogTmApiClient()));
    }

    public Optional<ApiClientHolder> getApiClientHolder(final ExternalBlogType blogType) {
        return apiClientHolders.stream()
                               .filter(clientHolder -> clientHolder.handles(blogType))
                               .findFirst();
    }
}
