package server;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class ServerMain {


	public static void main(String[] args) throws RemoteException, SQLException {
		System.out.println("Starting server");
		RMI server = new RMI();

		Thread serverT = new Thread(server);
		serverT.run();

		//add stuff to database
//		ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
//		for(int i=1;i<3;i++)
//			cinemas.add(new Cinema(i,"location"+i));
//
//		ArrayList<Auditorium> auditoriums = new ArrayList<Auditorium>();
//		for(int i=1;i<3;i++)
//			for(int j=1;j<4;j++)
//				auditoriums.add(new Auditorium(j,cinemas.get(i-1)));
//
//		ArrayList<Seat> seats = new ArrayList<Seat>();
//		for(int j=1;j<=auditoriums.size();j++)
//			for(int row=1;row<6;row++)
//				for(int col=1;col<4;col++)
//					seats.add(new Seat(col, row, auditoriums.get(j-1)));

	}

}