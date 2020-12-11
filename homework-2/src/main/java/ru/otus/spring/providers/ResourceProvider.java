package ru.otus.spring.providers;

import org.springframework.core.io.Resource;

public class ResourceProvider {
    final private Resource resource;

    public ResourceProvider(final Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
