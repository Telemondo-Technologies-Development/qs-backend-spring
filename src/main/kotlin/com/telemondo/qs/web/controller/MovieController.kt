//package com.telemondo.qs.web.controller
//
//import com.telemondo.qs.service.MovieService
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.DeleteMapping
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.PutMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//class MovieController (
//    private val movieService: MovieService
//){
//    @GetMapping
//    fun getMovies(): ResponseEntity<List<MovieDTO>> {
//        return ResponseEntity(movieService.getMovies(), HttpStatus.OK)
//    }
//
//    @PostMapping
//    fun createMovie(@RequestBody movieDTO: MovieDTO): ResponseEntity<MovieDTO> {
//        return ResponseEntity(movieService.createMovie(movieDTO), HttpStatus.CREATED)
//    }
//
//    @GetMapping("/{id}")
//    fun getMovie(@PathVariable id: Int): ResponseEntity<MovieDTO> {
//        return ResponseEntity.ok(movieService.getMovie(id))
//    }
//
//    @PutMapping
//    fun updateMovie(@RequestBody movieDTO: MovieDTO): ResponseEntity<MovieDTO> {
//        return ResponseEntity.ok(movieService.updateMovie(movieDTO))
//    }
//
////    @DeleteMapping("/{id}")
////    fun deleteMovie(@PathVariable id: Int): ResponseEntity<Unit> {
////        return ResponseEntity(movieService.deleteMovie(id), HttpStatus.NO_CONTENT)
////    }
//
//    //test only to use ReqBody for delete
//    @DeleteMapping()
//    fun deleteMovie(@RequestBody movieDTO: MovieDTO): ResponseEntity<Unit> {
//        return ResponseEntity(movieService.deleteMovie(movieDTO), HttpStatus.NO_CONTENT)
//    }
//}