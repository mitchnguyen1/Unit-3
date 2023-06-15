package com.javaunit3.springmvc.Model;

import javax.persistence.*;

@Entity
public class VoteEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "vote_id")
    private int id;

    @Column(name = "voter_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    public VoteEntity(){}

    public VoteEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
}
