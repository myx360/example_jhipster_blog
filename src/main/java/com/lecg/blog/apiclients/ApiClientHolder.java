package com.lecg.blog.apiclients;

public class ApiClientHolder {
    private final ExternalBlogType blogType;

    private final ClientWrapper clientWrapper;

    public ApiClientHolder(final ExternalBlogType blogType, final ClientWrapper clientWrapper) {
        this.blogType = blogType;
        this.clientWrapper = clientWrapper;
    }

    public boolean handles(final ExternalBlogType blogType) {
        return this.blogType == blogType;
    }

    public ClientWrapper getClientWrapper() {
        return clientWrapper;
    }
}
