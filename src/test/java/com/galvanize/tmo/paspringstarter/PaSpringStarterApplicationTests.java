package com.galvanize.tmo.paspringstarter;

import com.galvanize.tmo.paspringstarter.domain.entity.Book;
import com.galvanize.tmo.paspringstarter.domain.service.LibraryService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaSpringStarterApplicationTests {

	private List<Book> books = LibraryService.getLibrary();

	@Autowired
	MockMvc mockMvc;

	@AfterEach
	void refresh() {
		LibraryService.removeAllBooks();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void isHealthy() throws Exception {
		mockMvc.perform(get("/health"))
				.andExpect(status().isOk());
	}

	@Test
	void isAdded() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" +
						"    \"author\": \"William Gibson\",\n" +
						"    \"title\": \"Neuromancer\",\n" +
						"    \"yearPublished\": 1984\n" +
						"}"))
				.andExpect(status().isCreated());

		// 400

		Assertions.assertEquals(1, books.size());

		mockMvc.perform(post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" +
								"    \"title\": \"Neuromancer\",\n" +
								"    \"yearPublished\": 1984\n" +
								"}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void isGetAllSortedBooks() throws Exception {
		books.addAll(Lists.newArrayList(
				createBook("1", "Douglas Adams", "The Hitchhiker's Guide to the Galaxy", 1979),
				createBook("2", "Philip K. Dick", "Philip K. Dick", 1968),
				createBook("3", "William Gibson", "Neuromancer", 1984)
		));
		mockMvc.perform(get("/api/books"))
				.andExpect(jsonPath("$.books").isNotEmpty())
				.andExpect(status().isOk());
	}

	@Test
	void isRemoveAllBooks() throws Exception {
		books.addAll(Lists.newArrayList(
				createBook("1", "Douglas Adams", "The Hitchhiker's Guide to the Galaxy", 1979),
				createBook("2", "Philip K. Dick", "Philip K. Dick", 1968),
				createBook("3", "William Gibson", "Neuromancer", 1984)
		));
		Assertions.assertEquals(3, books.size());
		mockMvc.perform(delete("/api/books"))
				.andExpect(status().isNoContent());
		Assertions.assertEquals(0, books.size());
	}

	private Book createBook(String id, String author, String title, Integer yearPublished) {
		return Book.builder()
				.id(id)
				.author(author)
				.title(title)
				.yearPublished(yearPublished)
				.build();
	}
}
