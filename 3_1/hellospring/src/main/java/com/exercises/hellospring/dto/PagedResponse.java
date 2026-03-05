package com.exercises.hellospring.dto;

import java.util.ArrayList;
import java.util.List;

public class PagedResponse<T> {

    /*
     * Make the fields private with getters so that Spring's Jackson can serialize this class.
     * content should be immutable so callers can’t mutate the internal list. 
     * This prevents subtle bugs where someone modifies the returned list and unintentionally affects this stored state
     */

    private final List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages) {
        this.content = List.copyOf(content);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
    
}
