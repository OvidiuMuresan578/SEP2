package program;

import java.time.LocalDateTime;

/**
 * A class representing a projection(scheduled movie)
 *
 * @author Ovidiu Muresan
 * @version 0.2, 26/05/2017
 */

public class Projection {
	private Auditorium auditorium;
	private Movie movie;
	private LocalDateTime start;

	/**
	* Three-argument constructor. Auditorium is set to a given auditorium, movie is set to a given movie
	* and start is set to a given date.
	*
	* @param auditorium
	*    	 the cinema`s auditorium as an auditorium object	
	* @param movie
	*    	 the movie as a movie object
	* @param start
	*    	 the start of a movie as a local date and time
	* 
	*/
	
	public Projection(LocalDateTime start,Auditorium auditorium,Movie movie){;
		this.auditorium=auditorium;
		this.movie=movie;
		this.start=start;
	}

	/**
	* @return auditorium as an Auditorium
	*/
	
	public Auditorium getAuditorium(){
		return auditorium;
	}
	
	/**
	* @return movie as a Movie
	*/

	public Movie getMovie(){
		return movie;
	}
	
	/**
	* @return start as a LocalDateTime
	*/
	
	public LocalDateTime getStartTime(){
		return start;
	}
	
	/**
	 * Sets the start of a movie.
	 * 
	 * @param start
	 *        the start of a movie as a LocalDateTime
	 */
	
	public void setStart(LocalDateTime start){
		this.start=start;
	}
	
	/**
	 * Sets the auditorium of a Projection.
	 * 
	 * @param auditorium
	 *        the auditorium of a projection as an auditorium
	 */
	
	public void setAuditorium(Auditorium auditorium){
		this.auditorium=auditorium;
	}

	/**
	 * Sets the movie of a Projection.
	 * 
	 * @param movie
	 *        the movie of a projection as an auditorium
	 */
	
	public void setMovie(Movie movie){
		this.movie=movie;
	}
	
   /**
	* @return a String with all parameters of the auditorium object
	*/
	
	public String toString(){
		return auditorium.toString()+"|"+movie.toString()+"|"+start.toString();
	}
	
	/**
	* Comparing objects to one another.
    * 
    * @param proj
    * 		 an Object for comparison
    * @return if the objects are equal to each other   
    */
	
	public boolean equals(Projection proj){
		return auditorium.equals(proj.getAuditorium()) && movie.equals(proj.getMovie()) && start.isEqual(proj.getStartTime());
	}
}