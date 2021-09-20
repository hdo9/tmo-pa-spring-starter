package com.galvanize.tmo.paspringstarter.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

    private Integer id;

    private String author;

    private String title;

    private Integer yearPublished;
}
