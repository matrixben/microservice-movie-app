package jason.luo.controller;

import jason.luo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieWebController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/{id}")
    public String findMovieById(@PathVariable int id, Model model){
        model.addAttribute("movie", movieService.getMovie(id));
        return "moviedetail";
    }

    @GetMapping("/allmovies")
    public String findAllMoives(Model model){
        model.addAttribute("movieList",movieService.getAllMovies());
        return "movielist";
    }
}
