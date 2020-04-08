package program;

import java.io.Serializable;

/**
 * A class representing an auditorium
 *
 * @author Stefan-Daniel Horvath
 * @version 0.2 26/06/2017
 */

public class Movie implements Serializable {
	
		private int id;
		private String title;
		private int length;
		
		private static final long serialVersionUID = 1L;
		//simple builder : Movie movie = new Movie().setTitle("title").setLength(123).setId(1);
		
		/**
		    * Three-argument constructor. The movie Id, title and length(in minutes).
		    * 
		    * Id and length are set to integer, and the title is set to string.
		    *
		    * @param id
		    * 		the id of the movie
		    * @param title
		    *  	 	the title of the movie
		    * @param length
		    *  	 	the length of the movie in minutes
		    */
		
		public Movie(int id, String title, int length)
		{
			setId(id).setTitle(title).setLength(length);

		}
		
		/**
		    * Sets the title of a movie.
		    * 
		    * @param that
		    *        the new title of the movie.
		    */
		
		public Movie setTitle(String that){
			this.title=that;
			return this;
		}
		
		/**
		* @return the title String of this movie
		*/
		
		public String getTitle()
		{
			return this.title;
		}
		
		/**
		    * Sets the length of a movie.
		    * 
		    * @param that
		    *        the new length of the movie.
		    */
		
		public Movie setLength(int that)
		{
			this.length=that;
			return this;
		}
		
		/**
		* @return the length int of this movie
		*/
		
		public int getLength()
		{
			return this.length;
		}
		
		/**
		    * Sets the id of a movie.
		    * 
		    * @param that
		    *        the new id of the movie.
		    */
		
		public Movie setId(int that)
		{
			this.id=that;
			return this;
		}
		
		/**
		* @return the id int of this movie
		*/
		
		public int getId()
		{
			return this.id;
		}

		/**
		* @return a String with all parameters of the Movie object
		*/
		
		public String toString(){
			return "Id:"+id+" |Title:"+title+" |Length(minutes):"+length;
		}
		
		/**
		* Comparing Movies to one another.
	    * 
	    * @param movie
	    * 		 a movie for comparison
	    * @return if the movies are equal to each other   
	    */
		
		public boolean equals(Movie movie){
			return id==movie.getId()&&title.equals(movie.getTitle())&&length == movie.getLength();
		}
	}