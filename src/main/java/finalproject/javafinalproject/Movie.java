package finalproject.javafinalproject;


import java.io.Serializable;

public class Movie implements Serializable {
    private String name;
    private int YearOfOrigin;
    private String[] Genre=new String[3];
    private int RunningTime;
    private String ProductionCompany;
    private int Budget;
    private int Revenue;
    private String imageLinks;

    Movie(String name, int Year, String[] genre, int time, String company, int budget, int revenue)
    {
        this.name=name;
        this.YearOfOrigin=Year;
        this.Budget=budget;
        this.Genre=genre;
        this.RunningTime=time;
        this.ProductionCompany=company;
        this.Revenue=revenue;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
        //setImage(imageLinks);
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public String getName() {
        return name;
    }

    public int getBudget() {
        return Budget;
    }

    public int getRevenue() {
        return Revenue;
    }

    public int getRunningTime() {
        return RunningTime;
    }

    public int getYearOfOrigin() {
        return YearOfOrigin;
    }

    public String getProductionCompany() {
        return ProductionCompany;
    }

    public String[] getGenre() {
        return Genre;
    }

    public void printInfo()
    {
        System.out.println("\tName of the movie: "+ name);
        System.out.println("\tReleased in: "+ YearOfOrigin);
        System.out.println("\tGenres: "+Genre[0]+","+Genre[1]+","+Genre[2]);
        System.out.println("\tRunning time: "+RunningTime);
        System.out.println("\tProduction company: "+ProductionCompany);
        System.out.println("\tTotal Budget: "+Budget);
        System.out.println("\tRevenue: "+Revenue+"\n\n");
    }

    public void setCompany(String company) {
        this.ProductionCompany=company;
    }
}
