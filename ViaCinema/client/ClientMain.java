package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Scanner;

import program.Cinema;

public class ClientMain {
	private static Client client;
	private static boolean condition;
	private static Cinema chosenCinema;
	private static Scanner in;
	private static boolean quit;

	public static void main(String[] args) throws SQLException, RemoteException, MalformedURLException, NotBoundException{
		in  = new Scanner(System.in);
		int IChoice=0;
		System.out.println("Hello,establishing connection . . .");
		client=new Client();

		condition=false;

		do{

			System.out.println("1.Choose cinema\n0.Quit");
			IChoice=in.nextInt();
			if(IChoice==1)
				try {
					chosenCinema = client.ChooseCinema();
					System.out.println("Cinema:"+chosenCinema.toString()+" chosen.\nIs this correct ?");
					condition=Choice();
				} catch (SQLException e) {}
			else if(IChoice==0)
				Quit();
		}while(!condition);
		///By this point, client has a Cinema chosen, now we will choose a 'login' , in this case, just a username

		if(chosenCinema != null){
			condition=false;
			do{

				System.out.print("\n1.Choose username\n0.Quit");
				IChoice=in.nextInt();
				if(IChoice==1){
					String auxUn;
					in.nextLine();
					System.out.print("Username:");
					auxUn=in.nextLine();
					System.out.println("Is '"+auxUn+"' correct?");
					condition=Choice();
					if(condition==true)
						client.setUsername(auxUn);
				}
				else if(IChoice==0){
					quit=true;
					Quit();
				}

			}while(!condition);

			if(!quit){
				condition=false;
				System.out.println("Greetings "+client.getUsername()+" !\n");

				do{
					System.out.println("1.View your reservations\n2.Create a new reservation for current cinema\n3.View todays projections\n4.Delete a reservation\n0.Quit");
					IChoice=in.nextInt();
					if(IChoice==1)
						System.out.println(client.getReservations(chosenCinema));
					//other side of client contains the username
					else if(IChoice==2)
						client.makeReservation(chosenCinema); //other side of client contains the process
					else if(IChoice==3)
						System.out.println(client.getTodayReservations(chosenCinema));
					else if(IChoice==4)
						client.Delete();
					else if(IChoice==0)
						condition=true;
				}while(!condition);

			}
		}
	}




	private static boolean Choice(){

		do{
			System.out.println("(Y/N)");
			String SChoice=in.next();
			if(SChoice.equalsIgnoreCase("y")){
				return true;
			}
			else if(SChoice.equalsIgnoreCase("n")){
				return false;
			}
		}while(true);
	}

	private static void Quit(){
		boolean repeat=true;
		String SChoice="";
		do{
			System.out.println("Are you sure you want to quit?(Y/N)");
			SChoice=in.next();
			if(SChoice.equalsIgnoreCase("y")){
				repeat=false;
				setCondition(true);
			}

			else if(SChoice.equalsIgnoreCase("n")){
				repeat=false;
			}
		}while(repeat);
	}

	private static void setCondition(boolean cond){
		condition=cond;
	}
}