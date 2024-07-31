package ru.otus.project.account.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.account.controller.dto.AccountUpdateDto;
import ru.otus.project.account.dto.AccountDto;
import ru.otus.project.account.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountCrudController {

    private final AccountService accountService;


    @PostMapping("/account")
    public AccountDto create(@RequestBody AccountUpdateDto accountUpdateDto) {
        return accountService.create(accountUpdateDto.toAccountDto());
    }

    @GetMapping("/account/{id}")
    public AccountDto read(@PathVariable String id) {
        return accountService.findById(id);
    }

    @PutMapping("/account/{id}")
    public AccountDto update(@PathVariable String id, @RequestBody AccountUpdateDto accountUpdateDto) {
        return accountService.update(accountUpdateDto.toAccountDto(id));
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable String id) {
        accountService.delete(id);
    }

}

