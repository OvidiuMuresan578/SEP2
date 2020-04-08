package utilities;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.sql.SQLException;
import java.util.ArrayList;

import program.Auditorium;
import program.Cinema;
import program.Movie;
import program.Projection;
import program.Reservation;
import program.Seat;

public interface RemoteInterface extends Remote{
	
	 
	public ArrayList<Movie> retrieveMovies() throws SQLException,RemoteException;/////////////////1
	public ArrayList<Movie> retrieveMovies(String title) throws SQLException,RemoteException;/////////////////1
	public ArrayList<Cinema> retrieveCinemas() throws SQLException,RemoteException;///////////////2
	public ArrayList<Auditorium> retrieveAuditoriums() throws SQLException,RemoteException;///////3
	public ArrayList<Auditorium> retrieveAuditoriums(int cinema_id) throws SQLException,RemoteException;///////3
	public ArrayList<Projection> retrieveProjections() throws SQLException,RemoteException;///////4
	public ArrayList<Projection> retrieveProjections(int cinema_id) throws SQLException,RemoteException;///////4
	public ArrayList<Seat> retrieveSeats() throws SQLException,RemoteException;///////////////////5
	public ArrayList<Reservation> retrieveReservations()throws SQLException,RemoteException;//////6
	public ArrayList<Reservation> retrieveReservations(String username)throws SQLException,RemoteException;//////6
	public ArrayList<Reservation> retrieveReservations(String username,Cinema cinema)throws SQLException,RemoteException;//////6
	public ArrayList<Projection> retrieveProjectionsForTheNext24Hours(int cinema_id)throws SQLException,RemoteException;

	public void addMovie(Movie movie) throws SQLException,RemoteException;
	public void addCinema(Cinema cinema)throws SQLException,RemoteException;
	public void addAuditorium(Auditorium auditorium) throws SQLException,RemoteException;
	public void addProjection(Projection projection) throws SQLException,RemoteException;
	public void addSeats(Seat seat) throws SQLException,RemoteException;
	public void addReservation(Reservation reservation) throws SQLException,RemoteException;
	
	public void deleteMovie(int id) throws SQLException,RemoteException;
	public void deleteCinema(int id) throws SQLException,RemoteException;
	public void deleteAuditorium(Auditorium auditorium) throws SQLException,RemoteException;
	public void deleteProjection(Projection projection) throws SQLException,RemoteException;
	
	public void deleteReservation(Reservation reservation) throws SQLException,RemoteException; //will need more of these:delete all reservations for a user, delete all reservations for a projection
	public void deleteReservation(String user) throws SQLException,RemoteException;
	public void deleteReservation(String user,int cinema_id) throws SQLException,RemoteException;
	public void deleteReservation(int auditorium_id,int cinema_id) throws SQLException,RemoteException;
	
	public void ModifyReservation(Reservation current,Reservation replacement) throws SQLException,RemoteException;
	
	public ArrayList<Projection> retrieveProjections(int cinema_id,int movie_id)throws SQLException,RemoteException;
	public ArrayList<Seat> retrieveSeat(Auditorium auditorium)throws SQLException,RemoteException;
	
	}