package com.example.mybookstore.converter;

import org.apache.commons.collections4.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Converter<S, T> {

    T convert(S source);

    default Set<T> convertAll(Set<S> sourceSet) {
        if (CollectionUtils.isEmpty(sourceSet)) {
            return Collections.emptySet();
        }
        return sourceSet.stream().map(this::convert).collect(Collectors.toSet());
    }

    default List<T> convertAll(List<S> sourceList) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(this::convert).toList();
    }

    default Flux<T> convertAll(Flux<S> sourceList) {
        return sourceList.map(this::convert);
    }
}
