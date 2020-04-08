package program;

/**
 * A class representing a reservation
 *
 * @author Ovidiu Muresan
 * @version 0.2, 25/05/2017
 */

public class Reservation {
	private Projection proj;
	private Seat seat;
	private String client;
	
	/**
	* Three-argument constructor. Proj is set to a given proj, seat is set to a given seat
	* and client is set to a given client.
	*
	* @param proj
	*    	 the cinema`s projection as an projection object	
	* @param movie
	*    	 the seat as a seat object
	* @param start
	*    	 the client of a reservation as a client object
	* 
	*/
	
	public Reservation(Projection proj,Seat seat, String client)
	{
		this.proj=proj;
		this.seat=seat;
		this.client=client;
	}
	
	/**
	* @return proj as a Projection
	*/
	
	public Projection getProjection()
	{
		return this.proj;
	}
	
	/**
	* @return client`s name as a String
	*/
	
	public String getClient()
	{
		return client;
	}
	
	/**
	* @return seat as a Seat
	*/
	
	public Seat getSeat()
	{
		return seat;
	}
	
	/**
	* @return client as a Client
	*/
	
	public void setClient(String client)
	{
		this.client=client;
	}
	
	/**
	 * Sets the projection of a reservation.
	 * 
	 * @param pr
	 *        the projection of a reservation as an projection
	 */
	
	public void setProjection(Projection pr)
	{
		this.proj=pr;
	}
	
	/**
	 * Sets the seat of a reservation.
	 * 
	 * @param seat
	 *        the seat of a reservation as an seat
	 */
	
	public void setSeat(Seat seat)
	{
		this.seat=seat;
	}

   /**
	* @return a String with all parameters of the reservation object
	*/
	
	public String toString(){
		return "Movie:"+proj.getMovie().getTitle()+"|Seat:"+seat.getColumn()+":"+seat.getRow()+" Client:"+client+"\n";
	}
	
	/**
	* Comparing objects to one another.
    * 
    * @param res
    * 		 an Object for comparison
    * @return if the objects are equal to each other   
    */
	
	public boolean equals(Reservation res){
		return proj.equals(res.getProjection()) && seat.equals(res.getSeat()) && client.equals(res.getClient());
	}



}
