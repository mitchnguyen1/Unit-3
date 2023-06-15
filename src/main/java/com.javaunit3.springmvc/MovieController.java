package com.javaunit3.springmvc;

import com.javaunit3.springmvc.Model.MovieEntity;
import com.javaunit3.springmvc.Model.VoteEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private BestMovieService bestMovieService;
    @Autowired
    private SessionFactory factory;

    @Autowired
    public MovieController(BestMovieService bestMovieService, SessionFactory factory){
        this.bestMovieService = bestMovieService;
        this.factory = factory;
    }



    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/bestMovie")
    public String getBestMovie(Model theModel){

        //get moviesentitys with votes
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<MovieEntity> query = session.createQuery("FROM MovieEntity").list();


        //find the most voted movie and send to front end
        String bestMovie = "";
        String bestMovieVoters = "";
        int top = 0;
        for(MovieEntity m : query){
           int votes = m.getVotes().size();
           if(votes > top){
               top = votes;
               bestMovie = m.getTitle();
               bestMovieVoters = "";
               for(VoteEntity v : m.getVotes()){
                   if(bestMovieVoters.equals("")){
                       bestMovieVoters += v.getName();
                   }else{
                       bestMovieVoters += ", "+v.getName();
                   }
               }
           }
        }

        theModel.addAttribute("bestMovie",bestMovie);
        theModel.addAttribute("bestMovieVoters",bestMovieVoters);
        session.getTransaction().commit();

        return "bestMovie";
    }

    @GetMapping("/voteForBestMovieForm")
    public String voteForBestMovieForm(Model theModel){
        //get all movies from db
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<MovieEntity> query = session.createQuery("FROM MovieEntity").list();
        session.getTransaction().commit();
        theModel.addAttribute("movies",query);
        //send to html as movies
        return "voteForBestMovie";
    }

    @RequestMapping("/voteForBestMovie")
    public String voted(HttpServletRequest request, Model theModel) {
        int movieId = Integer.parseInt(request.getParameter("movieId"));
        String name = request.getParameter("voterName");

        // Get existing movie
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        MovieEntity movie = (MovieEntity) session.get(MovieEntity.class, movieId);

        // Create a new vote and set the movie
        VoteEntity newVote = new VoteEntity();
        newVote.setName(name);
        newVote.setMovie(movie);

        // Add the new vote to the movie
        movie.addVote(newVote);

        // Save changes
        session.update(movie);
        session.getTransaction().commit();

        return "redirect:/voteForBestMovieForm";
    }


    @RequestMapping("/addMovieForm")
    public String addMovieForm(){
        return "addMovie";
    }

    @RequestMapping("/addMovie")
    //access the request body with ServletRequest
    public String addMovie(HttpServletRequest request){
        //get the value from the key(form's input name)
        String title = request.getParameter("title");
        String rating = request.getParameter("rating");
        String genre = request.getParameter("genre");

        System.out.println(title + rating + genre);
        //create a MovieEntity object with new movie
        MovieEntity newMovie = new MovieEntity();
        newMovie.setTitle(title);
        newMovie.setRating(rating);
        newMovie.setGenre(genre);

        //start a session with the factory we made in hibernate config
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(newMovie);
        session.getTransaction().commit();

        return "addMovie";
    }


}
