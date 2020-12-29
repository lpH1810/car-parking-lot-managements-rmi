package DALTM;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientIF extends Remote,Serializable {
	
	public void startClient() throws RemoteException;
	public void registerWithServer(String[] details) throws RemoteException;
	public void sendMsg(String msg) throws RemoteException;
	public void sendReq() throws RemoteException;
	public void getMsg(String msg) throws RemoteException;
	public void SEND_MESSAGE(String msg) throws RemoteException;
	public void LEAVE_PARK(String[] details) throws RemoteException;
}
