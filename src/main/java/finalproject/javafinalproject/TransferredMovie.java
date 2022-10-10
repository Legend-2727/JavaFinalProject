package finalproject.javafinalproject;

import java.io.Serializable;

public class TransferredMovie implements Serializable {
    Movie movie;
    String CompanyName;
    TransferredMovie(Movie movie,String name){
        this.movie=movie;
        this.CompanyName=name;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getCompanyName() {
        return CompanyName;
    }
}
