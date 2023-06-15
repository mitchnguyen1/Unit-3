package com.javaunit3.springmvc.Model;

import com.javaunit3.springmvc.Movie;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "movies")
public class MovieEntity implements Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private int id;

    @Column(name = "title")
    private String title;
    @Column(name = "maturity_rating")
    private String rating;
    @Column(name = "genre")
    private String genre;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<VoteEntity> votes;

    public MovieEntity(){

    }

    public MovieEntity(int id, String title, String rating, String genre, List<VoteEntity> votes) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.genre = genre;
        this.votes = votes;
    }

    public List<VoteEntity> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteEntity> votes) {
        this.votes = votes;
    }

    public void addVote(VoteEntity vote){
        votes.add(vote);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
