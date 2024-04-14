package com.telemondo.qs.service

import com.telemondo.qs.dto.MovieDTO
import org.springframework.stereotype.Service

@Service
interface MovieService {
    fun createMovie(movieDTO: MovieDTO): MovieDTO

    fun getMovies(): List<MovieDTO>

    fun getMovie(id: Int): MovieDTO

    fun updateMovie(movieDTO: MovieDTO): MovieDTO

    fun deleteMovie(movieDTO: MovieDTO)

}