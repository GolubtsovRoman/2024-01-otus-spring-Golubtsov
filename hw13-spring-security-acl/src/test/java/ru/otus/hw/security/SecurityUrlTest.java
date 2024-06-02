package ru.otus.hw.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.configurations.security.SecurityConfiguration;
import ru.otus.hw.controllers.rest.AuthorRestController;
import ru.otus.hw.controllers.rest.BookRestController;
import ru.otus.hw.controllers.rest.GenreRestController;
import ru.otus.hw.controllers.rest.dto.BookViewDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Выполнят проверку доступов ролей по URL")
@WebMvcTest({BookRestController.class, AuthorRestController.class, GenreRestController.class})
@ContextConfiguration(classes = {BookRestController.class,
        AuthorRestController.class,
        GenreRestController.class,
        SecurityConfiguration.class})
class SecurityUrlTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @SuppressWarnings("unused")
    @MockBean
    private AuthorService authorService;

    @SuppressWarnings("unused")
    @MockBean
    private GenreService genreService;


    @DisplayName("пользователь с ролью ADMIN может создавать книги")
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createBookAllow() throws Exception {
        BookDto bookDto = new BookDto(0, "title",
                new AuthorDto(1, "fullName"), Collections.emptyList());
        BookViewDto bookViewDto = BookViewDto.from(bookDto);
        String bookViewDtoJson = new ObjectMapper().writeValueAsString(bookViewDto);

        when(bookService.insert("title", "fullName", Collections.emptySet())).thenReturn(bookDto);

        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookViewDtoJson))
                .andExpect(status().isOk());
    }

    @DisplayName("пользователь с ролью EDITOR не может создавать книги")
    @Test
    @WithMockUser(roles = {"EDITOR"})
    void createBookDeny() throws Exception {
        BookDto bookDto = new BookDto(0, "title",
                new AuthorDto(1, "fullName"), Collections.emptyList());
        BookViewDto bookViewDto = BookViewDto.from(bookDto);
        String bookViewDtoJson = new ObjectMapper().writeValueAsString(bookViewDto);

        when(bookService.insert("title", "fullName", Collections.emptySet())).thenReturn(bookDto);

        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookViewDtoJson))
                .andExpect(status().isForbidden());
    }

    @DisplayName("get-запросы доступны для всех аутентифицированных пользователей")
    @Test
    @WithMockUser
    void readBookAllow() throws Exception {
        mvc.perform(get("/api/book"))
                .andExpect(status().isOk());

        mvc.perform(get("/api/author"))
                .andExpect(status().isOk());

        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk());
    }

    @DisplayName("get-запросы не доступны для не авторизованных пользователей (редирект)")
    @Test
    void readBookDeny() throws Exception {
        mvc.perform(get("/api/book"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/api/author"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/api/genre"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("пользователь с ролью EDITOR может изменять книги")
    @Test
    @WithMockUser(roles = {"EDITOR"})
    void editBookAllow() throws Exception {
        long bookId = 1;
        BookDto bookDto = new BookDto(bookId, "title",
                new AuthorDto(1, "fullName"), Collections.emptyList());
        BookViewDto bookViewDto = BookViewDto.from(bookDto);
        String bookViewDtoJson = new ObjectMapper().writeValueAsString(bookViewDto);

        when(bookService.update(bookId, "title", "fullName", Collections.emptySet())).thenReturn(bookDto);

        mvc.perform(put("/api/book/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookViewDtoJson))
                .andExpect(status().isOk());
    }

    @DisplayName("пользователь без роли EDITOR или ADMIN не может менять книги")
    @Test
    @WithMockUser
    void editBookDeny() throws Exception {
        long bookId = 1;
        BookDto bookDto = new BookDto(bookId, "title",
                new AuthorDto(1, "fullName"), Collections.emptyList());
        BookViewDto bookViewDto = BookViewDto.from(bookDto);
        String bookViewDtoJson = new ObjectMapper().writeValueAsString(bookViewDto);

        when(bookService.update(bookId, "title", "fullName", Collections.emptySet())).thenReturn(bookDto);

        mvc.perform(put("/api/book/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookViewDtoJson))
                .andExpect(status().isForbidden());
    }

    @DisplayName("пользователь с ролью ADMIN может удалять книги")
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteBookAllow() throws Exception {
        mvc.perform(delete("/api/book/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("пользователь с ролью EDITOR не может удалять книги")
    @Test
    @WithMockUser(roles = {"EDITOR"})
    void deleteBookDeny() throws Exception {
        mvc.perform(delete("/api/book/1"))
                .andExpect(status().isForbidden());
    }

}
