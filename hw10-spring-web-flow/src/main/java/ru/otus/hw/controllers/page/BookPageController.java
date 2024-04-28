package ru.otus.hw.controllers.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;


    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        List<AuthorDto> authorDtoList = authorService.findAll();
        List<GenreDto> genreDtoList = genreService.findAll();
        model.addAttribute("authorDtoList", authorDtoList);
        model.addAttribute("genreDtoList", genreDtoList);

        return "create";
    }

    @PostMapping("/create")
    public String insertBook(String title, String authorName, String genresNames) {
        var setOfGenresNames = Arrays.stream(genresNames.split(",")).collect(Collectors.toSet());
        bookService.insert(title, authorName, setOfGenresNames);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") String bookIdStr, Model model) {
        BookDto bookDto = bookService.findById(Long.parseLong(bookIdStr));
        List<AuthorDto> authorDtoList = authorService.findAll();
        List<GenreDto> genreDtoList = genreService.findAll();
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("authorDtoList", authorDtoList);
        model.addAttribute("genreDtoList", genreDtoList);

        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") String bookIdStr, String title, String authorName, String genresNames) {
        var setOfGenresNames = Arrays.stream(genresNames.split(",")).collect(Collectors.toSet());
        bookService.update(Long.parseLong(bookIdStr), title, authorName, setOfGenresNames);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable("id") String bookIdStr, Model model) {
        BookDto bookDto = bookService.findById(Long.parseLong(bookIdStr));
        model.addAttribute("bookDto", bookDto);

        return "delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") String bookIdStr) {
        bookService.deleteById(Long.parseLong(bookIdStr));

        return "redirect:/";
    }

}
