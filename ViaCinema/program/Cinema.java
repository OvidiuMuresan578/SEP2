package program;

import java.io.Serializable;

/**
 * A class representing a Cinema
 *
 * @author Stefan-Daniel Horvath
 * @version 0.2 26/06/2017
 */

public class Cinema implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String location;
	
	/**
	    * Two-argument constructor. The cinemas's id and a location .
	    * 
	    * Id is set to integer, and location is set to String.
	    *
	    * @param id
	    * 		the id of the cinema
	    * @param location
	    *  	 	the address of the cinema
	    */
	
	public Cinema(int id,String location){
		this.id=id;
		this.location=location;
	}
	
	/**
	* @return the id of the cinema
	*/
	
	public int getId()
	{
		return id;
	}
	
	/**
	* @return the location of the auditorium
	*/
	
	public String getLocation()
	{
		return location;
	}
	
	/**
	    * Sets the id of a cinema.
	    * 
	    * @param id
	    *        the id of a cinema as an int
	    */
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	/**
	    * Sets the location of a cinema.
	    * 
	    * @param location
	    *        the location of a cinema as a string
	    */
	
	public void setLocation(String loc)
	{
		this.location=loc;
	}
	
	/**
	* @return a String with all parameters of the cinema object
	*/
	
	public String toString(){
		return "Id:"+id+"|location:"+location;
	}
	
	/**
	* Comparing cinemas to one another.
    * 
    * @param cinema
    * 		 a cinema for comparison
    * @return if the cinemas are equal to each other   
    */
	
	public boolean equals(Cinema cinema){
		return id==cinema.getId() && location.equals(cinema.getLocation());
	}
	
	/**
	* @return a different cinema that has the same parameters
	*/
	
	public Cinema copy(){
		return new Cinema(id,location);
	}
}
