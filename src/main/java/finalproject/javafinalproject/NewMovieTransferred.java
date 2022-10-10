package finalproject.javafinalproject;

import java.io.Serializable;

public class NewMovieTransferred implements Serializable {
    Movie movie;
    String previousCompany;
    NewMovieTransferred(Movie m,String name){
        this.movie=m;
        this.previousCompany=name;
    }

    public Movie getMovie() {
        return movie;
    }
}
