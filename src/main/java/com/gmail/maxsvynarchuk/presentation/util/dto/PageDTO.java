package com.gmail.maxsvynarchuk.presentation.util.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<E> {
    private final List<E> elements;
    private final int currentPage;
    private final int numberOfPages;
}
