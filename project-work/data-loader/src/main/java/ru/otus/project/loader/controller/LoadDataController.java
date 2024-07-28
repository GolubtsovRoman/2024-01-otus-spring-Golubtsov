package ru.otus.project.loader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoadDataController {


    @PostMapping("/data/load/ready-set")
    public void loadData() {
        System.out.println("Loading data");
    }

    // todo load by CSV

}
