package DALTM;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Car implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String seat;
	ClientIF parkingClient;
	public Car(ClientIF parkingClient, String seat)
		{
				this.seat = seat;
				this.parkingClient = parkingClient;
		}
	public String getSeat() throws RemoteException {
			return seat;
	}
	
	public ClientIF getClient() throws RemoteException{
		return parkingClient;
	}
	
	public void setSeat(String seat) throws RemoteException {
			this.seat = seat;
	}
}
