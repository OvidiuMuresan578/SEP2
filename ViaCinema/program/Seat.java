package program;

/**
 * A class representing a seat
 *
 * @author Ovidiu Muresan
 * @version 0.2, 26/05/2017
 */

public class Seat {
	private int row;
	private int column;
	private boolean occupied;
	private Auditorium aud;
	
	/**
	* Three-argument constructor. Row is set to a given number, column is set to a given number,occupied is set to false
	* and aud is set to given auditorium.
	*
	* @param row
	*    	 the seat`s row as an integer
	* @param column
	*    	 the seat`s column as an integer
	* @param aud
	*    	 the seat`s auditorium as an auditorium
	* 
	*/
	
	public Seat(int row, int column, Auditorium aud)
	{
		this.row=row;
		this.column=column;
		this.occupied=false;
		this.aud=aud;
	}
	
	/**
	* @return row as a integer
	*/
	
	public int getRow()
	{
		return row;
	}
	
	/**
	* @return column as a integer
	*/
	
	public int getColumn()
	{
		return column;
	}
	
	/**
	* @return occupied as a boolean
	*/
	
	public boolean getStatus()
	{
		return occupied;
	}
	
	/**
	* @return aud as a auditorium
	*/
	
	public Auditorium getAuditorium()
	{
		return aud;
	}
	
	/**
	 * Sets the row of a seat.
	 * 
	 * @param row
	 *        the row of a seat as an integer
	 */
	
	public void setRow(int row)
	{
		this.row=row;
	}

	/**
	 * Sets the column of a seat.
	 * 
	 * @param col
	 *        the column of a seat as an integer
	 */
	
	public void setColumn(int col)
	{
		this.column=col;
	}
	
	/**
	 * Sets the Auditorium of a seat.
	 * 
	 * @param aud
	 *        the Auditorium of a seat as an auditorium
	 */
	
	public void setAuditorium(Auditorium aud)
	{
		this.aud=aud;
	}
	
   /**
	 * This is unused 
   	 */
	
	public void setFree()
	{
		this.occupied=false;
	}
	
  /**
    * This is unused 
    */
	
	public void setOccupied()
	{
		this.occupied=true;
	}
	
	/**
	* Comparing objects to one another.
    * 
    * @param seat
    * 		 an Object for comparison
    * @return if the objects are equal to each other   
    */
	
	public boolean equals(Seat seat){
		return row==seat.getRow() && column==seat.getColumn() 
				&& occupied == seat.getStatus() && aud.equals(seat.getAuditorium());
	}
	
	/**
	* @return a String with all parameters of the seat object
	*/
	
	
	public String toString(){
		return "Row:"+row+"|Column:"+column+"Auditorium:"+aud.getId();
	}
}