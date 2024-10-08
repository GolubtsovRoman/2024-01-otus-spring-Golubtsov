package ru.otus.project.employee.controller.rest.crudl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.employee.controller.dto.OfficeUpdateDto;
import ru.otus.project.employee.dto.OfficeDto;
import ru.otus.project.employee.service.OfficeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfficeCrudlController {

    private final OfficeService officeService;


    @PostMapping("/office")
    public OfficeDto create(@RequestBody OfficeUpdateDto officeUpdateDto) {
        return officeService.create(
                officeUpdateDto.address(),
                officeUpdateDto.capacity(),
                officeUpdateDto.description()
        );
    }

    @GetMapping("/office/{id}")
    public OfficeDto read(@PathVariable long id) {
        return officeService.findById(id);
    }

    @PutMapping("/office/{id}")
    public OfficeDto update(@PathVariable long id, @RequestBody OfficeUpdateDto officeUpdateDto) {
        return officeService.update(
                id,
                officeUpdateDto.address(),
                officeUpdateDto.capacity(),
                officeUpdateDto.description()
        );
    }

    @DeleteMapping("/office/{id}")
    public void delete(@PathVariable long id) {
        officeService.delete(id);
    }

    @GetMapping("/office")
    public List<OfficeDto> list() {
        return officeService.findAll();
    }

}
