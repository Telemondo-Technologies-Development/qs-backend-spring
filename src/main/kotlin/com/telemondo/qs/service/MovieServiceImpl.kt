package com.telemondo.qs.service

import com.telemondo.qs.dto.MovieDTO
import com.telemondo.qs.repository.MovieRepository
import com.telemondo.qs.utils.mapper.MovieMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.swing.tree.ExpandVetoException


@Service
//inject a bean of the MovieRepository into the service layer
//we also need the mapper class for the movieDTO to be converted to Entity
class MovieServiceImpl(
    private val movieRepository: MovieRepository,
    private val movieMapper: MovieMapper
) : MovieService {
    override fun createMovie(movieDTO: MovieDTO): MovieDTO{

//        input validation for accidental inputting of ID so as to not override a primary key
        if(movieDTO.id != 0) {
            throw Exception("ID must be null or 0.")
        }

//        call repository
//        need to do this to save the ID to the movie variable because if not, the ID will not reflect the movieDTO object since the ID is generated in the save function itself
        val movie = movieMapper.toEntity(movieDTO)
        movieRepository.save(movie)
        return movieMapper.fromEntity(movie)
    }

    override fun getMovies(): List<MovieDTO> {
        val movies = movieRepository.findAll()

        if(movies.count() == 0){
            throw Exception("No movies yet.")
        }

//        mapping in iteration
        return movies.map {
            movieMapper.fromEntity(it)
        }
    }

    override fun getMovie(id: Int): MovieDTO {
//        since findById returns an optional object we need to get() it's value before we map it
        val optionalMovie = movieRepository.findById(id)
        val movie = optionalMovie.orElseThrow{Exception("Movie with id $id is not present")}
        return movieMapper.fromEntity(movie)
    }

    override fun updateMovie(movieDTO: MovieDTO): MovieDTO {
//        used getMovie's exception handling
//        getMovie(movieDTO.id)

//        alternate way of checking for entity existence using repository
        val exists = movieRepository.existsById(movieDTO.id)

        if(!exists){
            throw Exception("Movie with id ${movieDTO.id} is not present")
        }
        movieRepository.save(movieMapper.toEntity(movieDTO))
        return movieDTO
    }

//    override fun deleteMovie(id: Int) {
//        val exists = movieRepository.existsById(id)
//
//        if(!exists){
//            throw Exception("Movie with id $id is not present")
//        }
//
//        movieRepository.deleteById(id)
//    }

//test only to use ReqBody for delete
    override fun deleteMovie(movieDTO: MovieDTO) {
        val exists = movieRepository.existsById(movieDTO.id)

        if(!exists){
            throw Exception("Movie with id ${movieDTO.id} is not present")
        }

        movieRepository.deleteById(movieDTO.id)
    }
}