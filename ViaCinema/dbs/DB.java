package dbs;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import org.postgresql.Driver;

import program.Auditorium;
import program.Cinema;
import program.Movie;
import program.Projection;
import program.Reservation;
import program.Seat;

/**
 * A class containing all the database calls
 *
 * @author Stefan-Daniel Horvath
 * @version 0.2 26/06/2017
 */

public class DB implements DBI
{

	private Connection connection;

	private ArrayList<Projection> projections;
	private ArrayList<Seat> seats;
	private ArrayList<Cinema> cinemas;
	private ArrayList<Reservation> reservations;
	private ArrayList<Auditorium> auditoriums;
	private ArrayList<Movie> movies;

	/**
	    * No argument constructor, a connection to the postgresql server is established.
	    */
	
	public DB(){
		connection = establishConnection();
	}

	public Connection establishConnection()
	{
		Driver driver = new Driver();
		try
		{
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres", "postgres",
					"1234");
		}
		catch (SQLException e)
		{
			System.out.println("Failed to connect the DataBases!!");
		}


		return connection;
	}

	/**
	    * Returns the array list of movies with all the movies in the cinema, and returns it.
	    * @return the array
	    */
	
	@Override
	public ArrayList<Movie> retrieveMovies() throws SQLException {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT * FROM sep2.movies");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{	
				int id=result.getInt("id");
				String title = result.getString("title");
				int length=result.getInt("length");
				movies.add(new Movie(id,title,length));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return movies;
	}

	/**
	    * Adds a movie to the database
	    * 
	    * @param movie
	    *        the movie object that will be added to the database
	    */
	
	@Override
	public void addMovie(Movie movie) throws SQLException {
		Connection connection = establishConnection();
		PreparedStatement insertStatement;
		try
		{
			insertStatement = connection.prepareStatement("INSERT INTO sep2.movies(title,length) "
					+ "VALUES (?,?,?)");
			insertStatement.setString(1, movie.getTitle());
			insertStatement.setInt(2, movie.getLength());

			insertStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    * Deletes a movie from the database 
	    * 
	    * @param id
	    *        the id of the movie that will be deleted from the database.
	    */
	
	@Override
	public void deleteMovie(int id) throws SQLException {
		try
		{	

			Connection connection = establishConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM sep2.movies WHERE id ="+id);
			deleteStatement.executeUpdate();
		}																			
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    * If exists, returns the cinema with the parameter id from the database.
	    * 
	    * @param id
	    *        the id of the cinema that is to be returned
	    * @return the requested cinema or null
	    */
	
	@Override
	public Cinema getCinema(int id) throws SQLException{
		cinemas=retrieveCinemas();
		for(int i=0;i<cinemas.size();i++)
			if(cinemas.get(i).getId()==id)
				return cinemas.get(i);
		return null;
	}
	
	/**
	    * If exists, returns the auditorium with the parameter id and cinema_id from the database.
	    * 
	    * @param id
	    *        the id of the auditorium that is to be returned
	    * @param cinema_id
	    *        the id of the cinema that the auditorium belongs to
	    * @return the requested auditorium or null
	    */
	
	@Override
	public Auditorium getAuditorium(int id,int cinema_id) throws SQLException{
		auditoriums=retrieveAuditoriums();
		for(int i=0;i<auditoriums.size();i++)
			if(auditoriums.get(i).getId()==id && auditoriums.get(i).getCinema().getId()==cinema_id )
				return auditoriums.get(i);
		return null;
	}
	
	/**
	    * If exists, returns the movie with the parameter id from the database.
	    * 
	    * @param id
	    *        the id of the movie that is to be returned
	    * @return the movie with the requested parameter or null
	    */
	
	@Override
	public Movie getMovie(int id) throws SQLException{
		movies=retrieveMovies();
		for(int i=0;i<movies.size();i++)
			if(movies.get(i).getId()==id)
				return movies.get(i);
		return null;
	}
	
	/**
	    * If exists, returns the projection with the parameter start, auditorium_id and cinema_id from the database.
	    * @param start
	    * 		 a LocalDateTime time that represents the start of a projection
	    * @param auditorium_id
	    *        the id of the auditorium in which the projection will start
	    * @param cinema_id
	    *        the id of the cinema in which the projection will start
	    * @return the projection with the requested parameters or null
	    */
	
	@Override
	public Projection getProjection(LocalDateTime start,int auditorium_id,int cinema_id) throws SQLException{
		projections=retrieveProjections();
		for(int i=0;i<projections.size();i++)
			if(projections.get(i).getStartTime().equals(start) && projections.get(i).getAuditorium().getId()==auditorium_id && 
			projections.get(i).getAuditorium().getCinema().getId()==cinema_id)
				return projections.get(i);
		return null;
	}

	/**
	    * If exists, returns the reservations with the parameter start, auditorium_id, cinema_id, row and col from the database.
	    * @param start
	    * 		 a LocalDateTime time that represents the start of a projection
	    * @param auditorium_id
	    *        the id of the auditorium in which the projection will start
	    * @param cinema_id
	    *        the id of the cinema in which the projection will start
	    @param row
	    *        the row of a reserved seat
	    @param col
	    *        the col of a reserved seat
	    * @return the reservation with the requested parameters or null
	    */
	
	@Override
	public Reservation getReservation(LocalDateTime start,int auditorium_id,int cinema_id,int row,int col) throws SQLException{
		reservations=retrieveReservations();
		for(int i=0;i<reservations.size();i++){
			Projection proj = reservations.get(i).getProjection();
			Seat seatAux = reservations.get(i).getSeat();
			if(		proj.getStartTime().equals(start) &&
					proj.getAuditorium().getId()==auditorium_id && 
					proj.getAuditorium().getCinema().getId()==cinema_id && 
					seatAux.getAuditorium()==proj.getAuditorium()&&
					seatAux.getColumn()==col && seatAux.getRow()==row){
				return reservations.get(i);
			}		
		}
		return null;
	}
	
	/**
	    * If exists, returns the seat with the parameter auditorium_id, cinema_id, row and col from the database.
	    * @param auditorium_id
	    *        the id of the auditorium in which the seat is located
	    * @param cinema_id
	    *        the id of the cinema in which the seat is located
	    @param row
	    *        the row of the seat
	    @param col
	    *        the col of a seat
	    * @return the seat with the requested parameters or null
	    */
	
	@Override
	public Seat getSeat(int auditorium_id,int cinema_id,int row,int col) throws SQLException{
		seats=retrieveSeats();
		for(int i=0;i<seats.size();i++){
			Seat aux = seats.get(i);
			if(aux.getAuditorium().getId()==auditorium_id && aux.getAuditorium().getCinema().getId()==cinema_id && 
					aux.getColumn()==col && aux.getRow() == row)
				return aux;
		}
		return null;
	}

	/**
	    *Returns the list of reservation 
	    * @return the list
	    */
	
	@Override
	public ArrayList<Reservation> retrieveReservations() throws SQLException {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT * FROM sep2.reservations");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{	

				Timestamp ts = result.getTimestamp("time"); 
				LocalDateTime start = null;
				if( ts != null ) 
					start =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);
				int auditorium_id=result.getInt("auditorium_id");
				int cinema_id=result.getInt("cinema_id");
				int row=result.getInt("row");
				int col=result.getInt("col");
				String client=result.getString("username");
				Projection proj=getProjection(start, auditorium_id, cinema_id);
				Seat seat = new Seat(row, col, getAuditorium(auditorium_id,cinema_id));
				reservations.add(new Reservation(proj,seat,client));


			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return reservations;
	}

	/**
	    *Adds a reservation to the database
	    *@param reservation
	    *		 The reservation object that will be inserted into the database.
	    */
	
	@Override
	public void addReservation(Reservation reservation)throws SQLException 
	{
		Connection connection = establishConnection();
		PreparedStatement insertStatement;
		try
		{
			//			Reservations has : time,auditorium_id,cinema_id,row,col,username
			insertStatement = connection.prepareStatement("INSERT INTO sep2.reservations(time,auditorium_id,cinema_id,row,col,username) "
					+ "VALUES (?,?,?,?,?,?)");

			//sets date
			LocalDateTime localDt = reservation.getProjection().getStartTime();
			Timestamp ts = null;
			if( localDt != null)    
				ts = new Timestamp(localDt.toInstant(ZoneOffset.UTC).toEpochMilli());
			insertStatement.setTimestamp(1,ts); 

			insertStatement.setInt(2, reservation.getProjection().getAuditorium().getId());

			insertStatement.setInt(3, reservation.getProjection().getAuditorium().getCinema().getId());
			insertStatement.setInt(4, reservation.getSeat().getRow());
			insertStatement.setInt(5, reservation.getSeat().getColumn());
			insertStatement.setString(6, reservation.getClient());

			insertStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Returns the list of cinemas 
	    * @return the list
	    */
	
	@Override
	public ArrayList<Cinema> retrieveCinemas() throws SQLException {
		ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT * FROM sep2.cinemas");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				int id=result.getInt("id");
				String location=result.getString("location");
				cinemas.add(new Cinema(id,location));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return cinemas;
	}

	/**
	    *Removes the data of all cinemas with the specified id from the database.
	    *@param id
	    *		 The id of the cinema that is to be removed from the database
	    */
	
	@Override
	public void deleteCinema(int id) throws SQLException {
		try
		{	

			Connection connection = establishConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM sep2.cinemas WHERE id ="+id);
			deleteStatement.executeUpdate();
		}																			
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}	

	/**
	    *Updates all the lists 
	    */
	
	@SuppressWarnings("unused")
	public void refresh(){
		try {
			ArrayList<Cinema> cinemas = retrieveCinemas();
			ArrayList<Movie> movies = retrieveMovies();
			ArrayList<Auditorium> auditoriums = retrieveAuditoriums();
			ArrayList<Seat> seats = retrieveSeats();
			ArrayList<Projection> projections = retrieveProjections();
			ArrayList<Reservation> reservations = retrieveReservations();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	    *Updates and returns the list of auditoriums 
	    * @return the list
	    */
	@Override
	public ArrayList<Auditorium> getAuditoriums() throws SQLException {
		auditoriums=retrieveAuditoriums();
		return auditoriums;
	}

	/**
	    *Updates and returns the list of projections
	    *@return the list 
	    */
	@Override
	public ArrayList<Projection> getProjections() throws SQLException {
		projections=retrieveProjections();
		return projections;
	}

	/**
	    *Updates and returns the list of seats
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Seat> getSeats() throws SQLException {
		seats=retrieveSeats();
		return seats;
	}

	/**
	    *Adds a cinema to the database
	    *@param cinema
	    *		 The cinema object that will be inserted into the database.
	    */
	
	@Override
	public void addCinema(Cinema cinema) throws SQLException {

		Connection connection = establishConnection();
		PreparedStatement insertStatement;
		try
		{
			insertStatement = connection.prepareStatement("INSERT INTO sep2.cinemas(id,location) "
					+ "VALUES (?,?)");
			insertStatement.setInt(1, cinema.getId());
			insertStatement.setString(2, cinema.getLocation());

			insertStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}


	}

	/**
	    *Adds an auditorium to the database
	    *@param auditorium
	    *		 The auditorium object that will be inserted into the database.
	    */
	
	@Override
	public void addAuditorium(Auditorium auditorium) throws SQLException {
		Connection connection = establishConnection();
		PreparedStatement insertStatement;
		try
		{
			insertStatement = connection.prepareStatement("INSERT INTO sep2.auditoriums(id,cinema_id) "
					+ "VALUES (?,?)");
			insertStatement.setInt(1, auditorium.getId());
			insertStatement.setInt(2, auditorium.getCinema().getId());

			insertStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Adds a projection to the database
	    *@param projection
	    *		 The projection object that will be inserted into the database.
	    */
	
	@Override
	public void addProjection(Projection projection) throws SQLException {
		Connection connection = establishConnection();
		PreparedStatement insertStatement;
		try
		{
			insertStatement = connection.prepareStatement("INSERT INTO sep2.projections(time,"
					+ "auditorium_id,cinema_id,movie_id) "
					+ "VALUES (?,?,?,?)");

			LocalDateTime localDt = projection.getStartTime();
			Timestamp ts = null;
			if( localDt != null)    
				ts = new Timestamp(localDt.toInstant(ZoneOffset.UTC).toEpochMilli());
			insertStatement.setTimestamp(1,ts); 

			insertStatement.setInt(2, projection.getAuditorium().getId());

			insertStatement.setInt(3, projection.getAuditorium().getCinema().getId());

			insertStatement.setInt(4, projection.getMovie().getId());

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Adds a seat to the database
	    *@param seat
	    *		 The seat object that will be inserted into the database.
	    */
	
	@Override
	public void addSeats(Seat seat) throws SQLException {
		Connection connection = establishConnection();
		PreparedStatement insertStatement;
		try
		{
			insertStatement = connection.prepareStatement("INSERT INTO sep2.seats(auditorium_id,cinema_id,row,col) VALUES (?,?,?,?)");

			insertStatement.setInt(1, seat.getAuditorium().getId());

			insertStatement.setInt(2, seat.getAuditorium().getCinema().getId());

			insertStatement.setInt(3, seat.getRow());

			insertStatement.setInt(4, seat.getColumn());

			insertStatement.execute();


		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	    *Removes the data of all auditoriums with the specified data from the database.
	    *@param auditorium
	    *		 The auditorium object that will be removed from the database
	    */
	
	@Override
	public void deleteAuditorium(Auditorium auditorium) throws SQLException {
		int id=auditorium.getId();
		int cinema_id=auditorium.getCinema().getId();
		try
		{	

			Connection connection = establishConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM sep2.auditoriums WHERE id ="+id
					+" AND cinema_id ="+cinema_id);
			deleteStatement.executeUpdate();
		}																			
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Removes the data of all projections with the specified data from the database.
	    *@param projection
	    *		 The projection object that will be removed from the database
	    */
	
	@Override
	public void deleteProjection(Projection projection) throws SQLException {
		try
		{	
			int auditorium_id=projection.getAuditorium().getId();
			int cinema_id=projection.getAuditorium().getCinema().getId();
			Connection connection = establishConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM sep2.projections "
					+ "WHERE time=? AND auditorium_id ="+auditorium_id+" AND cinema_id ="+cinema_id);

			Timestamp ts = null;
			if( projection.getStartTime() != null)    
				ts = new Timestamp(projection.getStartTime().toInstant(ZoneOffset.UTC).toEpochMilli());
			deleteStatement.setTimestamp(1,ts);

			deleteStatement.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Removes the data of all reservations with the specified data from the database.
	    *@param reservation
	    *		 The reservation object that will be removed from the database
	    */
	
	@Override
	public void deleteReservation(Reservation reservation) throws SQLException {
		try
		{	
			Projection projection = reservation.getProjection();
			int auditorium_id=projection.getAuditorium().getId();
			int cinema_id=projection.getAuditorium().getCinema().getId();
			int row = reservation.getSeat().getRow();
			int col = reservation.getSeat().getColumn();
			Connection connection = establishConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM sep2.reservations "
					+ "WHERE time=? AND auditorium_id ="+auditorium_id+" AND cinema_id ="+cinema_id
					+ "AND row ="+row+" AND col="+col);

			Timestamp ts = null;
			if( projection.getStartTime() != null)    
				ts = new Timestamp(projection.getStartTime().toInstant(ZoneOffset.UTC).toEpochMilli());
			deleteStatement.setTimestamp(1,ts); //work pls 

			deleteStatement.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Removes the data of all reservations of the specified user
	    *@param user
	    *		 The user whose reservations shall be deleted from the database
	    */
	
	@Override
	public void deleteReservation(String user) throws SQLException {
		try
		{	
			Connection connection = establishConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM sep2.reservations "
					+ "WHERE username="+user);

			deleteStatement.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Removes the data of all reservations of the specified user from a specified cinema
	    *@param user
	    *		 The user whose reservations shall be deleted from the database
	    *@param cinema_id
	    *		 The id of the cinema from where the users reservations will be deleted
	    */
	
	@Override
	public void deleteReservation(String user, int cinema_id)
			throws SQLException {
		try
		{	
			Connection connection = establishConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM sep2.reservations "
					+ "WHERE username="+user+" AND cinema_id="+cinema_id);

			deleteStatement.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Removes the data of all reservations of the specified auditorium from the specified cinema
	    *@param auditorium_id
	    *		 The id of the auditorium from which reservations will be deleted
	    *@param cinema_id
	    *		 The id of the cinema from which reservations will be deleted
	    */
	
	@Override
	public void deleteReservation(int auditorium_id, int cinema_id)
			throws SQLException {
		try
		{	
			Connection connection = establishConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM sep2.reservations "
					+ "WHERE auditorium_id="+auditorium_id+" AND cinema_id="+cinema_id);

			deleteStatement.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	    *Replaces the data of a current reservation with the data of another reservations
	    *@param current
	    *		 The reservation that already exists in the database
	    *@param replacement
	    *		 The reservation that will replace the current one
	    */
	
	@Override
	public void ModifyReservation(Reservation current,Reservation replacement)
			throws SQLException {
		try
		{	
			int auditorium_id=replacement.getProjection().getAuditorium().getId();
			int cinema_id=replacement.getProjection().getAuditorium().getCinema().getId();
			int row=replacement.getSeat().getRow();
			int col=replacement.getSeat().getColumn();

			int Cauditorium_id=current.getProjection().getAuditorium().getId();
			int Ccinema_id=current.getProjection().getAuditorium().getCinema().getId();
			int Crow=current.getSeat().getRow();
			int Ccol=current.getSeat().getColumn();

			Connection connection = establishConnection();
			PreparedStatement updateStatement = connection.prepareStatement("UPDATE sep2.reservations SET time=? , auditorium_id="
					+auditorium_id+", cinema_id="+cinema_id+",row="+row+", col="+col+" WHERE time=? , auditorium_id="
					+Cauditorium_id+", cinema_id="+Ccinema_id+",row="+Crow+", col="+Ccol+" ");

			Timestamp ts = null;
			if( replacement.getProjection().getStartTime() != null)    
				ts = new Timestamp(replacement.getProjection().getStartTime().toInstant(ZoneOffset.UTC).toEpochMilli());
			updateStatement.setTimestamp(1,ts); //work pls 
			ts = new Timestamp(current.getProjection().getStartTime().toInstant(ZoneOffset.UTC).toEpochMilli());
			updateStatement.setTimestamp(2,ts); //work pls 



			updateStatement.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	    *Returns the list of auditoriums 
	    * @return the list
	    */
	
	@Override
	public ArrayList<Auditorium> retrieveAuditoriums() throws SQLException {
		ArrayList<Auditorium> auditoriums = new ArrayList<Auditorium>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT * FROM sep2.auditoriums");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				int id=result.getInt("id");
				int cinema_id=result.getInt("cinema_id");
				auditoriums.add(new Auditorium(id,getCinema(cinema_id)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auditoriums;
	}

	/**
	    *Returns the list of projections 
	    * @return the list
	    */
	
	@Override
	public ArrayList<Projection> retrieveProjections() throws SQLException {
		ArrayList<Projection> projections = new ArrayList<Projection>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT * FROM sep2.projections");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				Timestamp ts = result.getTimestamp("time"); 
				LocalDateTime start = null;
				if( ts != null ) 
					start =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);

				int auditorium_id=result.getInt("auditorium_id");
				int cinema_id=result.getInt("cinema_id");
				int movie_id=result.getInt("movie_id");
				projections.add(new Projection(start,getAuditorium(auditorium_id,cinema_id),getMovie(movie_id)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return projections;
	}

	/**
	    *Returns the list of seats 
	    * @return the list
	    */
	
	@Override
	public ArrayList<Seat> retrieveSeats() throws SQLException {
		ArrayList<Seat> seats = new ArrayList<Seat>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT auditorium_id,cinema_id,row,col,occupied FROM sep2.seats");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				int auditorium_id=result.getInt("auditorium_id");
				int cinema_id=result.getInt("cinema_id");
				int row=result.getInt("row");
				int col=result.getInt("col");
				boolean occupied=result.getBoolean("occupied");
				Seat seat = new Seat(row, col, getAuditorium(auditorium_id,cinema_id));
				if(occupied)
					seat.setOccupied();
				seats.add(seat);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return seats;
	}

	/**
	    *Updates and returns the list of movies
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Movie> getMovies() throws SQLException {
		movies=retrieveMovies();
		return movies;
	}

	/**
	    *Updates and returns the list of cinemas
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Cinema> getCinemas() throws SQLException {
		cinemas=retrieveCinemas();
		return cinemas;
	}

	/**
	    *Updates and returns the list of reservations
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Reservation> getReservations() throws SQLException {
		reservations=retrieveReservations();
		return reservations;
	}

	/**
	    *Returns a list of movies with the specified title
	    *@param title
	    *		 Movie title
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Movie> retrieveMovies(String title) throws SQLException {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT * FROM sep2.movies WHERE title LIKE '"+title+"'");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				int id=result.getInt("id");
				int length=result.getInt("length");

				movies.add(new Movie(id,title,length));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return movies;
	}

	/**
	    *Returns a list of auditoriums from a specified cinema
	    *@param cinema_id
	    *		 The id of the cinema whose auditoriums are requested
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Auditorium> retrieveAuditoriums(int cinema_id)
			throws SQLException {
		ArrayList<Auditorium> auditoriums = new ArrayList<Auditorium>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT * FROM sep2.auditoriums WHERE cinema_id LIKE '"+cinema_id+"'");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				int id=result.getInt("id");
				auditoriums.add(new Auditorium(id,getCinema(cinema_id)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auditoriums;
	}

	/**
	    *Returns a list of projections from a specified cinema
	    *@param cinema_id
	    *		 The id of the cinema whose auditoriums are requested
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Projection> retrieveProjections(int cinema_id)
			throws SQLException {
		ArrayList<Projection> projections = new ArrayList<Projection>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT * FROM sep2.projections WHERE cinema_id LIKE '"+cinema_id+"'");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				Timestamp ts = result.getTimestamp("time"); 
				LocalDateTime start = null;
				if( ts != null ) 
					start =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);

				int auditorium_id=result.getInt("auditorium_id");
				int movie_id=result.getInt("movie_id");
				projections.add(new Projection(start,getAuditorium(auditorium_id,cinema_id),getMovie(movie_id)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return projections;
	}

	/**
	    *Returns a list of reservations from a specified user
	    *@param username
	    *		 The name of a user
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Reservation> retrieveReservations(String username)
			throws SQLException {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT time,auditorium_id,cinema_id,row,col FROM sep2.reservations WHERE username LIKE '"+username+"'");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{	

				Timestamp ts = result.getTimestamp("time"); 
				LocalDateTime start = null;
				if( ts != null ) 
					start =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);
				int auditorium_id=result.getInt("auditorium_id");
				int cinema_id=result.getInt("cinema_id");
				int row=result.getInt("row");
				int col=result.getInt("col");

				Projection proj=getProjection(start, auditorium_id, cinema_id);
				Seat seat = new Seat(row, col, getAuditorium(auditorium_id,cinema_id));

				reservations.add(new Reservation(proj,seat,username));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return reservations;
	}

	/**
	    *Returns a list of projections from a specified cinema of a specified movie
	    *@param cinema_id
	    *		 The id of the cinema 
	    *@param movie_id
	    *		 The id of the movie
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Projection> retrieveProjections(int cinema_id, int movie_id) {
		ArrayList<Projection> projections = new ArrayList<Projection>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT time,auditorium_id FROM sep2.projections WHERE cinema_id ="+cinema_id
					+ "AND movie_id ="+movie_id);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				Timestamp ts = result.getTimestamp("time"); 
				LocalDateTime start = null;
				if( ts != null ) 
					start =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);

				int auditorium_id=result.getInt("auditorium_id");
				projections.add(new Projection(start,getAuditorium(auditorium_id,cinema_id),getMovie(movie_id)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return projections;
	}

	/**
	    *Returns a list of seats from a specified auditorium object
	    *@param auditorium
	    *		 The auditorium object
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Seat> retrieveSeat(Auditorium auditorium) {
		ArrayList<Seat> seats = new ArrayList<Seat>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT row,col,occupied FROM sep2.seats WHERE auditorium_id="+auditorium.getId()
					+" AND cinema_id="+auditorium.getCinema().getId());
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				int row=result.getInt("row");
				int col=result.getInt("col");
				boolean occupied=result.getBoolean("occupied");
				Seat seat = new Seat(row, col, auditorium.copy());
				if(occupied)
					seat.setOccupied();
				seats.add(seat);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return seats;
	}

	/**
	    *Returns a list of reservations from a specified cinema and user
	    *@param username
	    *		 The username for which reservations will be fetched
	    *@param cinema
	    *		 The cinema whose reservations are requested
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Reservation> retrieveReservations(String username,Cinema cinema) throws SQLException {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT time,auditorium_id,row,col FROM sep2.reservations WHERE username LIKE '"+username+"' AND cinema_id ="+cinema.getId());
			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{	

				Timestamp ts = result.getTimestamp("time"); 
				LocalDateTime start = null;
				if( ts != null ) 
					start =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);
				int auditorium_id=result.getInt("auditorium_id");
				int row=result.getInt("row");
				int col=result.getInt("col");
				Projection proj=getProjection(start, auditorium_id, cinema.getId());
				Seat seat = new Seat(row, col, getAuditorium(auditorium_id,cinema.getId()));
				reservations.add(new Reservation(proj,seat,username));


			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return reservations;
	}

	/**
	    *Returns a list of projections from a specified cinema that start in the following 24 hours 
	    *@param cinema_id
	    *		 The id of the cinema whose auditoriums are requested
	    *@return the list 
	    */
	
	@Override
	public ArrayList<Projection> retrieveProjectionsForTheNext24Hours(int cinema_id) throws SQLException {

		ArrayList<Projection> projections = new ArrayList<Projection>();
		PreparedStatement preparedStatement;
		try
		{
			preparedStatement = connection.prepareStatement("SELECT time,auditorium_id,movie_id FROM sep2.projections "
					+ "WHERE DATE(time) >= CURRENT_DATE AND DATE(time) < CURRENT_DATE + INTERVAL '1 DAY'");

			ResultSet result = preparedStatement.executeQuery();
			while (result.next())
			{
				Timestamp ts = result.getTimestamp("time"); 
				LocalDateTime start = null;
				if( ts != null ) 
					start =  LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);

				int auditorium_id=result.getInt("auditorium_id");
				int movie_id=result.getInt("movie_id");
				projections.add(new Projection(start,getAuditorium(auditorium_id,cinema_id),getMovie(movie_id)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return projections;
	}

	@Override
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
