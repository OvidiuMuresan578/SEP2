package program;

import java.io.Serializable;

/**
 * A class representing an auditorium
 *
 * @author Stefan-Daniel Horvath
 * @version 0.2 26/06/2017
 */

public class Auditorium implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Cinema cinema;
	
	/**
	    * Two-argument constructor. The auditorium's Id and the Cinema it belongs to.
	    * 
	    * Id is set to integer, and Cinema is set to the Cinema object the auditorium 
	    * belongs to.
	    *
	    * @param id
	    * 		the id of the auditorium
	    * @param cinema
	    *  	 	the cinema object the auditorium belongs to
	    */
	
	public Auditorium(int id,Cinema cinema){
		setId(id);
		setCinema(cinema);
	}
	
	/**
	* @return the id of the auditorium
	*/
	
	public int getId(){
		return id;
	}
	
	/**
	* @return the Cinema object this auditorium belongs to
	*/
	
	public Cinema getCinema(){
		return cinema;
	}
	
	/**
	    * Sets the id of an auditorium.
	    * 
	    * @param id
	    *        the id of a auditorium as an int
	    */
	
	public void setId(int id){
		this.id=id;
	}
	
	/**
	    * Sets the cinema an auditorium belongs to.
	    * 
	    * @param cinema
	    *        the cinema object an auditorium is set to belong to
	    */
	
	public void setCinema(Cinema cinema){
		this.cinema=cinema;
	}
	
	/**
	* @return a String with all parameters of the auditorium object
	*/
	
	public String toString(){
		return "Id:"+id+"|Cinema_id:"+cinema.getId();
	}
	
	/**
	* Comparing auditoriums to one another.
    * 
    * @param auditorium
    * 		 an auditorium for comparison
    * @return if the auditoriums are equal to each other   
    */
	
	public boolean equals(Auditorium aud){
		return id==aud.getId() && cinema.equals(aud.getCinema());
	}
	
	/**
	* @return a different auditorium that has the same parameters
	*/
	
	public Auditorium copy(){
		return new Auditorium(id,cinema.copy());
	}
	
}