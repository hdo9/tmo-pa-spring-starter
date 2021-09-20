package com.galvanize.tmo.paspringstarter.presentation.dto;

import com.galvanize.tmo.paspringstarter.domain.entity.Book;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LibraryResponse {
    private List<Book> books;
}
