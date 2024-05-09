package ru.otus.hw.controllers.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/create")
    public String createPage() {
        return "create";
    }

    @GetMapping("/edit/{id}")
    public String editPage() {
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String deletePage() {
        return "delete";
    }

}
