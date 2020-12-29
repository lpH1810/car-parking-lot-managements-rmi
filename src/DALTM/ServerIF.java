package DALTM;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JButton;

public interface ServerIF extends Remote, Serializable{
	public Vector<Car> getData() throws RemoteException;
	public void changeIcon(JButton btn) throws RemoteException;
	public void registerListener(String[] details) throws RemoteException;
	public void leavePark(String[] details) throws RemoteException;
	public void sendMsg(String msg) throws RemoteException;
	public void checkReq() throws RemoteException;
	public void getMsg(String msg) throws RemoteException;
	public void notify(String msg) throws RemoteException;
}
