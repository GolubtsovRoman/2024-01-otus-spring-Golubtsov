package ru.otus.hw.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("BookPageController должен быть доступен только аутентифицированным пользователям и")
@WebMvcTest(BookPageController.class)
class BookPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @DisplayName("в качесве стартовой страницы отображать страницу list без атрибутов")
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    void listPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("list"));
    }

    @DisplayName("в качесве страницы создания книги отображать страницу create без атрибутов")
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    void createPage() throws Exception {
        mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("create"));
    }

    @DisplayName("в качесве страницы изменения книги отображать страницу edit без атрибутов")
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    void editPage() throws Exception {
        long bookId = 1;
        BookDto bookDto = new BookDto(bookId, "title",
                new AuthorDto(1, "fullName"), Collections.emptyList());

        when(bookService.findById(bookId)).thenReturn(bookDto);

        mvc.perform(get("/edit/" + bookId))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("edit"));
    }

    @DisplayName("в качесве страницы удаления книги отображать страницу delete без атрибутов")
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    void deletePage() throws Exception {
        long bookId = 1;
        BookDto bookDto = new BookDto(bookId, "title",
                new AuthorDto(1, "fullName"), Collections.emptyList());

        when(bookService.findById(bookId)).thenReturn(bookDto);

        mvc.perform(get("/delete/" + bookId))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("delete"));
    }


    @DisplayName("при входе на стартовую страницу без аутентификации статус 401")
    @Test
    void listPageUnauthorized() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("при входе на страницу создания без аутентификации статус 401")
    @Test
    void createPageUnauthorized() throws Exception {
        mvc.perform(get("/create"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("при входе на страницу изменения без аутентификации статус 401")
    @Test
    void editPageUnauthorized() throws Exception {
        mvc.perform(get("/edit/1"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("при входе на страницу удаления без аутентификации статус 401")
    @Test
    void deletePageUnauthorized() throws Exception {
        mvc.perform(get("/delete/1"))
                .andExpect(status().isUnauthorized());
    }

}
