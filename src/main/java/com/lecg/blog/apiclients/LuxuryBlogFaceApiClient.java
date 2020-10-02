package com.lecg.blog.apiclients;

public class LuxuryBlogFaceApiClient implements ClientWrapper {
    public Object getExternalPosts(final String user) {
        return new Object(); // returns some proprietary DTO eg. LuxuryBlogFaceDTO
    }
}
