package com.galvanize.tmo.paspringstarter.presentation.dto;

import lombok.Data;
import lombok.NonNull;


@Data
public class BookRequest {

    @NonNull
    private String author;

    @NonNull
    private String title;

    @NonNull
    private Integer yearPublished;
}
