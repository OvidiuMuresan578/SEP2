package dbs;
import java.rmi.Remote;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import program.Auditorium;
import program.Cinema;
import program.Movie;
import program.Projection;
import program.Reservation;
import program.Seat;
public interface DBI extends Remote
{
	
	 
	public ArrayList<Movie> retrieveMovies() throws SQLException;/////////////////1
	public ArrayList<Movie> retrieveMovies(String title) throws SQLException;/////////////////1
	public ArrayList<Cinema> retrieveCinemas() throws SQLException;///////////////2
	public ArrayList<Auditorium> retrieveAuditoriums() throws SQLException;///////3
	public ArrayList<Auditorium> retrieveAuditoriums(int cinema_id) throws SQLException;///////3
	public ArrayList<Projection> retrieveProjections() throws SQLException;///////4
	public ArrayList<Projection> retrieveProjections(int cinema_id) throws SQLException;///////4
	public ArrayList<Seat> retrieveSeats() throws SQLException;///////////////////5
	public ArrayList<Reservation> retrieveReservations()throws SQLException;//////6
	public ArrayList<Reservation> retrieveReservations(String username)throws SQLException;//////6
	public ArrayList<Reservation> retrieveReservations(String username,Cinema cinema)throws SQLException;//////6
	public ArrayList<Projection> retrieveProjectionsForTheNext24Hours(int cinema_id)throws SQLException;
	
	public ArrayList<Movie> getMovies() throws SQLException;/////////////////1
	public ArrayList<Cinema> getCinemas() throws SQLException;///////////////2
	public ArrayList<Auditorium> getAuditoriums() throws SQLException;///////3
	public ArrayList<Projection> getProjections() throws SQLException;///////4
	public ArrayList<Seat> getSeats() throws SQLException;///////////////////5
	public ArrayList<Reservation> getReservations()throws SQLException;//////6

	public void addMovie(Movie movie) throws SQLException;
	public void addCinema(Cinema cinema)throws SQLException;
	public void addAuditorium(Auditorium auditorium) throws SQLException;
	public void addProjection(Projection projection) throws SQLException;
	public void addSeats(Seat seat) throws SQLException;
	public void addReservation(Reservation reservation) throws SQLException;
	
	public void deleteMovie(int id) throws SQLException;
	public void deleteCinema(int id) throws SQLException;
	public void deleteAuditorium(Auditorium auditorium) throws SQLException;
	public void deleteProjection(Projection projection) throws SQLException;
	
	public void deleteReservation(Reservation reservation) throws SQLException;
	public void deleteReservation(String user) throws SQLException;
	public void deleteReservation(String user,int cinema_id) throws SQLException;
	public void deleteReservation(int auditorium_id,int cinema_id) throws SQLException;
	
	public void ModifyReservation(Reservation current,Reservation replacement) throws SQLException;
	
	public ArrayList<Projection> retrieveProjections(int cinema_id,int movie_id)throws SQLException;
	public ArrayList<Seat> retrieveSeat(Auditorium auditorium)throws SQLException;
	
	public Cinema getCinema(int id) throws SQLException;
	public Auditorium getAuditorium(int id, int cinema_id)throws SQLException;
	public Movie getMovie(int id)throws SQLException;
	public Projection getProjection(LocalDateTime start, int auditorium_id,
			int cinema_id)throws SQLException;
	public Seat getSeat(int auditorium_id, int cinema_id, int row, int col) throws SQLException;
	public Reservation getReservation(LocalDateTime start, int auditorium_id,
			int cinema_id, int row, int col)throws SQLException;
	
	public void closeConnection();
	
}
