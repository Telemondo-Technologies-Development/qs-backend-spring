package com.telemondo.qs.utils.mapper

import com.telemondo.qs.dto.MovieDTO
import com.telemondo.qs.entity.Movie
import org.springframework.stereotype.Service


@Service
//this is for the conversion method
class MovieMapper: Mapper<MovieDTO, Movie> {

    override fun toEntity(domain: MovieDTO): Movie {
        return Movie(
            domain.id,
            domain.name,
            domain.rating
        )
    }

    override fun fromEntity(entity: Movie): MovieDTO {
        return MovieDTO(
            entity.id,
            entity.name,
            entity.rating
        )
    }
}