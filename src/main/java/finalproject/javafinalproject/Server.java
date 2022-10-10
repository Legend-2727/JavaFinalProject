package finalproject.javafinalproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    public static List<ProductionCompany> productionCompanies=new ArrayList<>();
    public static List<Movie> movieList=new ArrayList<>();
    private static final String INPUT_FILE_NAME = "movies.txt";
    private static final String OUTPUT_FILE_NAME = "movies.txt";
    public static HashMap<String,NetworkUtil> respectiveNet=new HashMap<>();
    public static HashMap<String,Boolean> isActive=new HashMap<>();
    Server() throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(INPUT_FILE_NAME));

        while(true)
        {
            String line = br.readLine();
            //String links=brLinks.readLine();
            if (line == null) break;
            String[] temp=line.split(",");
            String[] genre={temp[2],temp[3],temp[4]};
            int year=Integer.parseInt(temp[1]);
            int time=Integer.parseInt(temp[5]);
            int budget=Integer.parseInt(temp[7]);
            int revenue=Integer.parseInt(temp[8]);
            //String[] temp2=links.split(",");
            Movie movie = new Movie(temp[0], year, genre, time, temp[6], budget, revenue);
            String imageLocation="MovieThumbnails/"+movie.getName().replaceAll("[^a-zA-Z0-9]", "")+"poster.jpg";
            movie.setImageLinks(imageLocation);
            //movie.setTrailerLinks(temp2[1]);
            movieList.add(movie);
            int i;
            for(i=0;i<productionCompanies.size();i++){
                if(productionCompanies.get(i).getName().equalsIgnoreCase(temp[6]))
                {
                    productionCompanies.get(i).addMovies(movie);
                    break;
                }
            }
            if(i==productionCompanies.size())
            {
                ProductionCompany temporary=new ProductionCompany(temp[6]);
                temporary.addMovies(movie);
                productionCompanies.add(temporary);
            }
        }
        br.close();
        //.close();
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                System.out.println("server started");
                Socket clientSocket = serverSocket.accept();
                System.out.println("connected");
                serve(clientSocket);
                System.out.println("serving");
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public static void writeOnFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for(Movie m:movieList)
        {
            String temp=m.getName()+","+m.getYearOfOrigin()+",";
            int i=0;
            for(i=0;i<m.getGenre().length;i++)
                temp+=m.getGenre()[i]+",";
            for(;i<3;i++)
                temp+=",";
            temp+=m.getRunningTime()+","+m.getProductionCompany()+","+m.getBudget()+","+m.getRevenue();
            bw.write(temp);
            bw.write(System.lineSeparator());
        }
        bw.close();
    }

    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReaderThreadServer(networkUtil);
    }
    public static List<ProductionCompany> getProductionCompanies(){
        return productionCompanies;
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
