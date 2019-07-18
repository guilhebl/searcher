package com.searchprod.searcher.product.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {
    private final String imageServiceUrl;

    public ImageService(@Value("${image.service.url}") String imageServiceUrl) {
        this.imageServiceUrl = imageServiceUrl;
    }

    public String getImagePlaceholderUrl() {
        return getImageUrl("image-placeholder.png");
    }

    public String getImageUrl(String image) {
        return String.format("%s/%s", imageServiceUrl, image);
    }

    public String getImageUrlExternal(String url) {
        return Optional.ofNullable(url).orElse(getImagePlaceholderUrl());
    }
}
