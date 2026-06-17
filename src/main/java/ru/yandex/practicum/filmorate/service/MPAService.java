package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.MPARepository;
import ru.yandex.practicum.filmorate.dal.dto.MpaDTO;
import ru.yandex.practicum.filmorate.mapper.MPAMapper;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MPAService {

    private final MPARepository mpaRepository;

    public MpaDTO getMPAById(String id) {
        MPARating mpaRating = mpaRepository.getMpaById(Long.parseLong(id));
        return MPAMapper.mapToMpaDTO(mpaRating);
    }

    public Collection<MpaDTO> getAllMPA() {
        Collection<MPARating> mpaRatings = mpaRepository.getAllMPA();
        return MPAMapper.mapToMpaDTOCollection(mpaRatings);
    }
}
