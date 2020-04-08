package client;

import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;





import program.Cinema;
import program.Movie;
import program.Projection;
import program.Reservation;
import program.Seat;
import utilities.RemoteInterface;

public class Client{
	private  String username;
	private RemoteInterface rmiServer;
	@SuppressWarnings("unused")
	private Registry registry;
	private static Scanner keyboard;

	@SuppressWarnings("unused")
	public Client() throws MalformedURLException, RemoteException, NotBoundException{

		String ip="192.168.1.136";
		String home="localhost";
		int port =1099;

		registry = LocateRegistry.getRegistry(ip, port);
		rmiServer = (RemoteInterface) Naming.lookup("sep2");
		keyboard = new Scanner(System.in);
	}


	public void setUsername(String thus){
		username=thus;
	}
	public String getUsername(){
		return username;
	}

	public void makeReservation(Cinema cinema) throws SQLException, RemoteException{

		ChooseMovie(cinema);	
	}

	public Cinema ChooseCinema() throws SQLException, RemoteException
	{

		ArrayList<Cinema> cinemas=rmiServer.retrieveCinemas();

		String output="Index|Data\n";
		for(int i=0;i<cinemas.size();i++){
			output += i+1 +"|" + cinemas.get(i).toString()+"\n";

		}
		System.out.println(output);

		int choice=0;
		do{
			System.out.println("Choose a cinema by index");
			choice=keyboard.nextInt();
		}while(choice<=0 || choice>cinemas.size()+1);

		return cinemas.get(choice-1);
	}


	private void ChooseMovie(Cinema cin) throws SQLException, RemoteException
	{
		ArrayList<Movie> mov=rmiServer.retrieveMovies();
		String output="Index|Data\n";
		for(int i=0;i<mov.size();i++){
			output += i+1 +"|" + mov.get(i).toString()+"\n";
		}
		System.out.println(output);
		int choice=0;
		do{
			System.out.println("Choose a movie by index");
			choice=keyboard.nextInt();
		}while(choice<=0 || choice>mov.size()+1);

		Movie chosen=mov.get(choice-1);
		ChooseProjection(chosen,cin);
	}


	private void ChooseProjection(Movie mov,Cinema cinema) throws SQLException, RemoteException
	{

		ArrayList<Projection> proj=rmiServer.retrieveProjections(cinema.getId(),mov.getId());

		String output="Index|Data\n";

		for(int i=0;i<proj.size();i++){
			output = i+1 +"|" + proj.get(i).toString();
		}
		System.out.println(output);
		int choice=0;
		do{
			System.out.println("Choose a projection by index");
			choice=keyboard.nextInt();
		}while(choice<=0 || choice>proj.size()+1);
		Projection chosen = proj.get(choice-1);
		ChooseSeat(chosen);
	}

	private void ChooseSeat(Projection proj) throws SQLException, RemoteException
	{
		ArrayList<Seat> seat=rmiServer.retrieveSeat(proj.getAuditorium());

		/**** DISPLAYING SEATS /////////////////////////////*/ //Just realized seat boolean is pointless. 
		//It will show occupied even if you want to reserve for the next movie (if it is booked for a movie in the future, but before the movie you want to see)

		ArrayList<Reservation> oRes = rmiServer.retrieveReservations();
		
		ArrayList<Reservation> otherRes = new ArrayList<Reservation>();
		
		for(int i=0;i<oRes.size();i++){
			if(oRes.get(i).getClient()!=getUsername())
				otherRes.add(oRes.get(i));
		}
		oRes=null;
		
		
		ArrayList<Seat> oSeats = new ArrayList<Seat>();
		
		for(int i=0;i<otherRes.size();i++)
			oSeats.add(otherRes.get(i).getSeat());

		String output="Seats( [X] - occupied | [ ] - free ) \n";
		int rowaux=0;
		for(int i=0;i<seat.size();i++){
			if(rowaux<=seat.get(i).getRow()){
				rowaux=seat.get(i).getRow();
				output += "("+(i+1)+")";
				if(contains(oSeats,seat.get(i)))
					output+="[X]|";
				else
					output+="[ ]|";
			}
			else{
				rowaux=seat.get(i).getRow();
				output += "\n("+(i+1)+")";
				if(contains(oSeats,seat.get(i)))
					output+="[X]|";
				else 
					output+="[ ]|";
			}
		}
		System.out.println(output);
		/**-------------------/////////////////////////////*/
		int choice=0;
		do{
			System.out.println("Choose a seat by index");
			choice=keyboard.nextInt();
		}while(choice<=0 || choice>seat.size()+1);

		Seat chosen = seat.get(choice-1);

		createReservation(proj,chosen);

	}

	private void createReservation( Projection p, Seat s) throws SQLException, RemoteException{
		Reservation aux = new Reservation(p,s,username);

		System.out.println(aux.toString());
		boolean condition=true;
		do{
			System.out.println("Is this reservation ok ?(Y/N)");
			String choice = keyboard.next();
			if(choice.equalsIgnoreCase("y")){
				condition=false;
				rmiServer.addReservation(aux);
			}
			else if(choice.equalsIgnoreCase("n"))
				condition=false;
		}
		while(condition);
	}

	public String getReservations(Cinema cinema) throws SQLException, RemoteException{
		ArrayList<Reservation> reservations=rmiServer.retrieveReservations(username,cinema);
		String output="Reservations:\n";
		for(int i=0;i<reservations.size();i++){
			output+=reservations.get(i).toString()+"\n";
		}
		return output;
	}

	public void Delete() throws SQLException, RemoteException{
		ArrayList<Reservation> reservations=rmiServer.retrieveReservations(username);
		String output="Index|Data\n";
		for(int i=0;i<reservations.size();i++){
			output+=reservations.get(i).toString()+"\n";
		}
		System.out.println(output);
		int choice=0;
		do{
			System.out.println("Choose a projection by index");
			choice=keyboard.nextInt();
		}while(choice<=0 || choice>reservations.size()+1);

		System.out.println(reservations.get(choice));

		boolean condition=true;
		do{
			System.out.println("This reservation will be deleted ?(Y/N)");
			String Choice = keyboard.next();
			if(Choice.equalsIgnoreCase("y")){
				condition=false;
				rmiServer.deleteReservation(reservations.get(choice));
			}
			else if(Choice.equalsIgnoreCase("n"))
				condition=false;
		}
		while(condition);
	}

	public String getTodayReservations(Cinema cinema) throws RemoteException {
		ArrayList<Projection> proj=null;
		proj = todaysProj(cinema.getId());

		String output="";
		for(int i=0;i<proj.size();i++)
			output+=proj.get(i).toString()+"\n";
		return output;
	}
	private ArrayList<Projection>todaysProj(int cinema_id) throws RemoteException{
		try {
			return rmiServer.retrieveProjectionsForTheNext24Hours(cinema_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	private boolean contains(ArrayList<Seat> seats,Seat s){
		for(int i=0;i<seats.size();i++)
		{
			if(seats.get(i).equals(s))
				return true;
		}
		return false;
	}
}