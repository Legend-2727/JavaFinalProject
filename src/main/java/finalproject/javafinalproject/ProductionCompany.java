package finalproject.javafinalproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductionCompany implements Serializable {
    private String name;
    private List<Movie> prodCompMovieList=new ArrayList<>();

    ProductionCompany(String name) {
        this.name=name;
    }

    public void addMovies(Movie m)
    {
        prodCompMovieList.add(m);
    }

    public List<Movie> MaximumRevenueMovies() {
        List<Movie> tempList=new ArrayList<>();
        int max=-1;
        for(Movie temp:prodCompMovieList)
        {
            if(temp.getRevenue()>max) max=temp.getRevenue();
        }
        for(Movie temp:prodCompMovieList)
        {
            if(temp.getRevenue()==max) tempList.add(temp);
        }
        return tempList;
    }

    public long TotalProfit() {
        long tProfit=0;
        for(Movie temp:prodCompMovieList)
        {
            tProfit+=(temp.getRevenue()- temp.getBudget());
        }
        return tProfit;
    }


    public String getName()
    {
        return name;
    }
    public List<Movie> RecentMovie()
    {
        List<Movie> tempList=new ArrayList<>();
        int max=-1;
        for(Movie temp:prodCompMovieList)
        {
            if(temp.getYearOfOrigin()>max) max=temp.getYearOfOrigin();
        }
        for(Movie temp:prodCompMovieList)
        {
            if(temp.getYearOfOrigin()==max) tempList.add(temp);
        }
        return tempList;
    }
    public int getMovieCount()
    {
        return prodCompMovieList.size();
    }

    public List<Movie> getMovies() {
        return prodCompMovieList;
    }

    public void removeMovie(Movie movie) {
        int i=0;
        for(i=0;i<prodCompMovieList.size();i++)
        {
            if(prodCompMovieList.get(i).getName().equals(movie.getName()))
                break;
        }
        prodCompMovieList.remove(i);
    }
    public List<Movie> searchMovieByName(String title) {
        List<Movie> tempList=new ArrayList();
        for(Movie temp: prodCompMovieList)
        {
            if(temp.getName().equalsIgnoreCase(title))
            {
                tempList.add(temp);
            }
        }
        return tempList;
    }
    public List<Movie> searchMovieByYear(int year) {
        List<Movie> tempList=new ArrayList();
        for(Movie temp: prodCompMovieList)
        {
            if(temp.getYearOfOrigin()==year)
                tempList.add(temp);
        }
        return tempList;
    }
    public List<Movie> searchMovieByGenre(String genre) {
        List<Movie> tempList=new ArrayList();
        for(Movie temp: prodCompMovieList)
        {
            for(int i=0;i<3;i++)
            {
                if(temp.getGenre()[i].equalsIgnoreCase(genre) && genre!="")
                {
                    tempList.add(temp);
                    break;
                }
            }
        }
        return tempList;
    }
    public List<Movie> searchMovieByLength(int first_end, int second_end) {
        List<Movie> tempList=new ArrayList();
        for(Movie temp: prodCompMovieList)
        {
            if(temp.getRunningTime()>=first_end && temp.getRunningTime()<=second_end)
            {
                tempList.add(temp);
            }
        }
        return tempList;
    }

}
