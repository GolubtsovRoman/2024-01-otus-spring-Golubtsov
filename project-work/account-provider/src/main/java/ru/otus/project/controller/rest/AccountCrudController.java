package ru.otus.project.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.controller.dto.AccountUpdateDto;
import ru.otus.project.dto.AccountDto;
import ru.otus.project.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountCrudController {

    private final AccountService accountService;


    @PostMapping("/account")
    public AccountDto create(@RequestBody AccountUpdateDto accountUpdateDto) {
        return accountService.create(
                accountUpdateDto.login(),
                accountUpdateDto.password(),
                accountUpdateDto.expirePassword(),
                accountUpdateDto.email(),
                accountUpdateDto.active()
        );
    }

    @GetMapping("/account/{id}")
    public AccountDto read(@PathVariable String id) {
        return accountService.findById(id);
    }

    @PutMapping("/account/{id}")
    public AccountDto update(@PathVariable String id, @RequestBody AccountUpdateDto accountUpdateDto) {
        return accountService.update(
                id,
                accountUpdateDto.login(),
                accountUpdateDto.password(),
                accountUpdateDto.expirePassword(),
                accountUpdateDto.email(),
                accountUpdateDto.active()
        );
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable String id) {
        accountService.delete(id);
    }

}

