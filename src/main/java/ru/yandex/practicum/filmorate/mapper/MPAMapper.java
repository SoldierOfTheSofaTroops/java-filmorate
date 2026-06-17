package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dal.dto.MpaDTO;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MPAMapper {

    public static MpaDTO mapToMpaDTO(MPARating mpaRating){
        MpaDTO mpaDTO = new MpaDTO();
        mpaDTO.setId(mpaRating.getId());
        mpaDTO.setName(mpaRating.getName());
        return mpaDTO;
    }

    public static Collection<MpaDTO> mapToMpaDTOCollection(Collection<MPARating> mpaRatings){
        Collection<MpaDTO> mpaDTOs = new ArrayList<>();
        for (MPARating mpaRating : mpaRatings) {
            mpaDTOs.add(mapToMpaDTO(mpaRating));
        }
        return mpaDTOs;
    }
}
