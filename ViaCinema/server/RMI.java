package server;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;



import program.Auditorium;
import program.Cinema;
import program.Movie;
import program.Projection;
import program.Reservation;
import program.Seat;
import utilities.RemoteInterface;
import dbs.DB;
import dbs.DBI;

public class RMI  extends UnicastRemoteObject implements RemoteInterface,Runnable
{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RMI() throws RemoteException {}
	private DBI db= new DB();

	//	public static void main(String[] args) 
	//	{ 
	//		try 
	//		{ Registry reg = LocateRegistry.createRegistry(1099);
	//		RMI obj = new RMI(); 
	//		Naming.rebind("sep2", obj);
	//		} 
	//		catch (Exception e) 
	//		{ 
	//			System.out.println("HelloImpl err: " + e.getMessage()); 
	//			e.printStackTrace(); 
	//		} 
	//	}



	@Override
	public ArrayList<Movie> retrieveMovies() throws SQLException,RemoteException {
		return db.retrieveMovies();
	}
	@Override
	public ArrayList<Movie> retrieveMovies(String title) throws SQLException,RemoteException {
		return db.retrieveMovies(title);
	}
	@Override
	public ArrayList<Cinema> retrieveCinemas() throws SQLException,RemoteException {
		return db.retrieveCinemas();
	}
	@Override
	public ArrayList<Auditorium> retrieveAuditoriums() throws SQLException,RemoteException {
		return db.retrieveAuditoriums();
	}
	@Override
	public ArrayList<Auditorium> retrieveAuditoriums(int cinema_id)
			throws SQLException,RemoteException {
		return db.retrieveAuditoriums(cinema_id);
	}
	@Override
	public ArrayList<Projection> retrieveProjections() throws SQLException,RemoteException {
		return db.retrieveProjections();
	}
	@Override
	public ArrayList<Projection> retrieveProjections(int cinema_id)
			throws SQLException,RemoteException {
		return db.retrieveProjections(cinema_id);
	}
	@Override
	public ArrayList<Seat> retrieveSeats() throws SQLException,RemoteException {
		return db.retrieveSeats();
	}
	@Override
	public ArrayList<Reservation> retrieveReservations() throws SQLException,RemoteException {
		return db.retrieveReservations();
	}
	@Override
	public ArrayList<Reservation> retrieveReservations(String username)
			throws SQLException,RemoteException {
		return db.retrieveReservations(username);
	}

	@Override
	public void addMovie(Movie movie) throws SQLException,RemoteException {
		db.addMovie(movie);

	}
	@Override
	public void addCinema(Cinema cinema) throws SQLException,RemoteException {
		db.addCinema(cinema);

	}
	@Override
	public void addAuditorium(Auditorium auditorium) throws SQLException,RemoteException {
		db.addAuditorium(auditorium);
	}
	@Override
	public void addProjection(Projection projection) throws SQLException,RemoteException {
		db.addProjection(projection);
	}
	@Override
	public void addSeats(Seat seat) throws SQLException,RemoteException {
		db.addSeats(seat);
	}
	@Override
	public void addReservation(Reservation reservation) throws SQLException,RemoteException {
		db.addReservation(reservation);
	}
	@Override
	public void deleteMovie(int id) throws SQLException,RemoteException {
		db.deleteMovie(id);
	}
	@Override
	public void deleteCinema(int id) throws SQLException,RemoteException {
		db.deleteCinema(id);
	}
	@Override
	public void deleteAuditorium(Auditorium auditorium) throws SQLException,RemoteException {
		db.deleteAuditorium(auditorium);
	}
	@Override
	public void deleteProjection(Projection projection) throws SQLException,RemoteException {
		db.deleteProjection(projection);
	}
	@Override
	public void deleteReservation(Reservation reservation) throws SQLException,RemoteException {
		db.deleteReservation(reservation);
	}
	@Override
	public void deleteReservation(String user) throws SQLException,RemoteException {
		db.deleteReservation(user);

	}
	@Override
	public void deleteReservation(String user, int cinema_id)
			throws SQLException,RemoteException {
		db.deleteReservation(user,cinema_id);
	}
	@Override
	public void deleteReservation(int auditorium_id, int cinema_id)
			throws SQLException,RemoteException {
		db.deleteReservation(auditorium_id,cinema_id);
	}
	@Override
	public void ModifyReservation(Reservation current, Reservation replacement)
			throws SQLException,RemoteException {
		db.ModifyReservation(current, replacement);
	}
	@Override
	public ArrayList<Reservation> retrieveReservations(String username,
			Cinema cinema) throws SQLException,RemoteException {
		return db.retrieveReservations(username, cinema);
	}
	@Override
	public ArrayList<Projection> retrieveProjectionsForTheNext24Hours(
			int cinema_id) throws SQLException,RemoteException {
		return db.retrieveProjectionsForTheNext24Hours(cinema_id);
	}
	@Override
	public ArrayList<Projection> retrieveProjections(int cinema_id, int movie_id)
			throws SQLException,RemoteException {
		return db.retrieveProjections(cinema_id,movie_id);
	}
	@Override
	public ArrayList<Seat> retrieveSeat(Auditorium auditorium)
			throws SQLException,RemoteException {
		return db.retrieveSeat(auditorium);
	}
	@Override
	public void run() { 
		try 
		{ 
			@SuppressWarnings("unused")
			Registry reg = LocateRegistry.createRegistry(1099);
			RMI obj = new RMI(); 
			Naming.rebind("sep2", obj); 
			System.out.println("Server online");
		} 
		catch (Exception e) 
		{ 
			System.out.println("HelloImpl err: " + e.getMessage()); 
			e.printStackTrace(); 
		} 
	}
}