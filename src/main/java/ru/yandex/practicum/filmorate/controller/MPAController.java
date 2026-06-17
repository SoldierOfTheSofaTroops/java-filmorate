package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dal.dto.MpaDTO;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mpa")
public class MPAController {

    private final MPAService mpaService;

    @GetMapping
    public Collection<MpaDTO> getAllMPA(){
        return mpaService.getAllMPA();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public MpaDTO getMpaById(@PathVariable String id){
        return mpaService.getMPAById(id);
    }
}
