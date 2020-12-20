package ru.otus.spring.providers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.springframework.core.io.Resource;

@RequiredArgsConstructor
@ToString
public class ResourceProvider {

    @Getter
    final private Resource resource;
}
