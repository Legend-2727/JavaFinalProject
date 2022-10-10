package finalproject.javafinalproject;


import java.io.IOException;


public class ReaderThreadServer implements Runnable {
    Thread thread;
    NetworkUtil networkUtil;


    public ReaderThreadServer(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object ob=networkUtil.read();
                if(ob!=null)
                {
                    if(ob instanceof InfoWithCommand){

                        if(((InfoWithCommand) ob).getCommand().equals("Username"))
                        {

                            for(int i=0;i<Server.getProductionCompanies().size();i++)
                            {
                                if(Server.getProductionCompanies().get(i).getName().equalsIgnoreCase(((InfoWithCommand) ob).getInformation()))
                                {
                                    networkUtil.write(Server.getProductionCompanies().get(i));
                                    Server.respectiveNet.put(Server.getProductionCompanies().get(i).getName(),networkUtil);
                                    Server.isActive.put(Server.getProductionCompanies().get(i).getName(),true);
                                    break;
                                }
                                if(i==Server.getProductionCompanies().size()-1)
                                {
                                    String temp="No Company";
                                    networkUtil.write(temp);
                                }
                            }
                        }
                    }
                    if(ob instanceof TransferredMovie){
                        String initialCompany=((TransferredMovie) ob).getMovie().getProductionCompany();
                        String finalCompany = new String();
                        for(int i=0;i<Server.getProductionCompanies().size();i++)
                        {
                            if(Server.getProductionCompanies().get(i).getName().equalsIgnoreCase(initialCompany))
                            {
                                System.out.println(Server.getProductionCompanies().get(i).getMovieCount());
                                Server.getProductionCompanies().get(i).removeMovie(((TransferredMovie) ob).getMovie());
                                System.out.println(Server.getProductionCompanies().get(i).getMovieCount());
                                break;
                            }
                        }

                        for(int i=0;i<Server.getProductionCompanies().size();i++)
                        {
                            if(Server.getProductionCompanies().get(i).getName().equalsIgnoreCase(((TransferredMovie) ob).getCompanyName()))
                            {
                                System.out.println(Server.getProductionCompanies().get(i).getMovieCount());
                                ((TransferredMovie) ob).getMovie().setCompany(Server.getProductionCompanies().get(i).getName());
                                Server.getProductionCompanies().get(i).addMovies(((TransferredMovie) ob).getMovie());
                                finalCompany=Server.getProductionCompanies().get(i).getName();
                                System.out.println(Server.getProductionCompanies().get(i).getMovieCount());
                                break;
                            }
                        }

                        if(Server.isActive.get(finalCompany)){
                            NewMovieTransferred newMovieTransferred=new NewMovieTransferred(((TransferredMovie) ob).getMovie(),initialCompany);
                            Server.respectiveNet.get(finalCompany).write(newMovieTransferred);
                            System.out.println("final khujse");
                        }
                        RemovedMovie removedMovie=new RemovedMovie(finalCompany,((TransferredMovie) ob).getMovie());
                        Server.respectiveNet.get(initialCompany).write(removedMovie);
                        System.out.println(finalCompany+initialCompany);
                        for(int i=0;i<Server.movieList.size();i++)
                        {
                            if(Server.movieList.get(i).getName().equals(((TransferredMovie) ob).getMovie().getName()))
                            {
                                Server.movieList.get(i).setCompany(finalCompany);
                                break;
                            }
                        }
                        Server.writeOnFile();
                    }
                    if(ob instanceof Movie)
                    {
                        for(int i=0;i<Server.getProductionCompanies().size();i++)
                        {
                            if(Server.getProductionCompanies().get(i).getName().equalsIgnoreCase(((Movie) ob).getProductionCompany()))
                            {
                                Server.getProductionCompanies().get(i).addMovies((Movie) ob);
                                break;
                            }
                        }
                        Server.movieList.add((Movie) ob);
                        Server.writeOnFile();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



