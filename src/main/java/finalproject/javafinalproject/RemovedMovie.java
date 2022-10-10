package finalproject.javafinalproject;

import java.io.Serializable;

public class RemovedMovie implements Serializable {
    String CompanyName;
    Movie movie;
    RemovedMovie(String name,Movie movie){
        this.movie=movie;
        this.CompanyName=name;
    }

    public Movie getMovie() {
        return movie;
    }
}
