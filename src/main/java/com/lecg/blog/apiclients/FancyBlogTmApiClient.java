package com.lecg.blog.apiclients;

public class FancyBlogTmApiClient implements ClientWrapper {
    public Object getPosts(final String user) {
        return new Object(); // returns some proprietary DTO eg. LuxuryBlogFaceDTO
    }
}
