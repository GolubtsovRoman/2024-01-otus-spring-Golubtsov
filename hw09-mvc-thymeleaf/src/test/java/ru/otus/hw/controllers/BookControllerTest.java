package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("BookController должен")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;


    @DisplayName("на стартовую страницу добавлять необходимые аттрибуты")
    @Test
    void listPage() throws Exception {
        List<BookDto> bookDtoList = new ArrayList<>();

        when(bookService.findAll()).thenReturn(bookDtoList);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("bookDtoList"))
                .andExpect(model().attribute("bookDtoList", bookDtoList))
                .andExpect(view().name("list"));

        verify(bookService).findAll();
    }

    @DisplayName("на страницу создания добавлять необходимые аттрибуты")
    @Test
    void createPage() throws Exception {
        List<AuthorDto> authorDtoList = new ArrayList<>();
        List<GenreDto> genreDtoList = new ArrayList<>();

        when(authorService.findAll()).thenReturn(authorDtoList);
        when(genreService.findAll()).thenReturn(genreDtoList);

        mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("authorDtoList"))
                .andExpect(model().attribute("authorDtoList", authorDtoList))
                .andExpect(model().attributeExists("genreDtoList"))
                .andExpect(model().attribute("genreDtoList", genreDtoList))
                .andExpect(view().name("create"));

        verify(authorService).findAll();
        verify(genreService).findAll();
    }

    @DisplayName("создавать книгу")
    @Test
    void createBook() throws Exception {
        String title = "new test title";
        String authorName = "Author_3"; // from test-data.sql
        String genresNames = "Genre_1,Genre_2,Genre_3"; // from test-data.sql

        mvc.perform(post("/create")
                .param("title", title)
                .param("authorName", authorName)
                .param("genresNames", genresNames))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/"));

        var setOfGenresNames = Arrays.stream(genresNames.split(",")).collect(Collectors.toSet());
        verify(bookService).insert(title, authorName, setOfGenresNames);
    }

    @DisplayName("на страницу изменения книги добавлять необходимые аттрибуты")
    @Test
    void editPage() throws Exception {
        long bookId = 1;
        BookDto bookDto = new BookDto(bookId, "title",
                new AuthorDto(1, "fullName"), Collections.emptyList());
        List<AuthorDto> authorDtoList = new ArrayList<>();
        List<GenreDto> genreDtoList = new ArrayList<>();

        when(bookService.findById(bookId)).thenReturn(bookDto);
        when(authorService.findAll()).thenReturn(authorDtoList);
        when(genreService.findAll()).thenReturn(genreDtoList);

        mvc.perform(get("/edit/" + bookId))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("bookDto"))
                .andExpect(model().attribute("bookDto", bookDto))
                .andExpect(model().attributeExists("authorDtoList"))
                .andExpect(model().attribute("authorDtoList", authorDtoList))
                .andExpect(model().attributeExists("genreDtoList"))
                .andExpect(model().attribute("genreDtoList", genreDtoList))
                .andExpect(view().name("edit"));

        verify(bookService).findById(bookId);
        verify(authorService).findAll();
        verify(genreService).findAll();
    }

    @DisplayName("изменять книгу")
    @Test
    void editBook() throws Exception {
        long bookId = 1;
        String title = "new test title";
        String authorName = "Author_3"; // from test-data.sql
        String genresNames = "Genre_1,Genre_2,Genre_3"; // from test-data.sql

        mvc.perform(post("/edit/" + bookId)
                        .param("title", title)
                        .param("authorName", authorName)
                        .param("genresNames", genresNames))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/"));

        var setOfGenresNames = Arrays.stream(genresNames.split(",")).collect(Collectors.toSet());
        verify(bookService).update(bookId, title, authorName, setOfGenresNames);
    }

    @DisplayName("на страницу удаления книги добавлять необходимые аттрибуты")
    @Test
    void deletePage() throws Exception {
        long bookId = 1;
        BookDto bookDto = new BookDto(bookId, "title",
                new AuthorDto(1, "fullName"), Collections.emptyList());

        when(bookService.findById(bookId)).thenReturn(bookDto);

        mvc.perform(get("/delete/" + bookId))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("bookDto"))
                .andExpect(model().attribute("bookDto", bookDto))
                .andExpect(view().name("delete"));

        verify(bookService).findById(bookId);
    }

    @DisplayName("удалять книгу")
    @Test
    void deleteBook() throws Exception {
        long bookId = 1;

        mvc.perform(post("/delete/" + bookId))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/"));

        verify(bookService).deleteById(bookId);
    }

}
