package DALTM;

import java.awt.EventQueue;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientIF, Remote {
	private static final long serialVersionUID = 1L;
	public String seat;
	private String hostName = "localhost";
	private String serviceName = "CarPark";
	private String clientServiceName;
	public static String carNum = "0";
	public ServerIF serverIF;
	public ClientIF clientIF;

	public static Client clientGUI = new Client();
	
	@SuppressWarnings("static-access")
	public ClientImpl(Client c, String s) throws RemoteException {
		super();
		this.clientGUI = c;
		this.seat = s; //Also is a name
		this.clientServiceName = "CarNumber" + seat;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 clientGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void startClient() throws RemoteException{
		String details[] = {seat, hostName, clientServiceName};
		System.setProperty("java.rmi.server.hostname",details[1]);
		try {
			Registry reg = LocateRegistry.getRegistry(7777);
			reg.rebind("rmi://" + details[1] + "/" + details[2], this);

            this.sendMsg("Xe đến bãi đổ xe\n");
	
		} catch (Exception e) {
			System.err.println("RMI exception: ");
            e.printStackTrace();
		}
	}
	
	public void registerWithServer(String[] details) throws RemoteException{
		try{
			Registry reg = LocateRegistry.getRegistry(7777);
			serverIF = (ServerIF)reg.lookup("rmi://" + hostName + "/" + serviceName);
			serverIF.registerListener(details);
			carNum = details[0];
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void LEAVE_PARK(String[] details) throws RemoteException{
		try {
			Registry reg = LocateRegistry.getRegistry(7777);
			serverIF = (ServerIF)reg.lookup("rmi://" + hostName + "/" + serviceName);
			serverIF.leavePark(details);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMsg(String msg) throws RemoteException{
		clientGUI.txtNoti.append(msg);
	}
	
	public void getMsg(String msg) throws RemoteException{
		clientGUI.txtNoti.append(msg);
	}
	
	public void SEND_MESSAGE(String msg) throws RemoteException{
		try {
			Registry reg = LocateRegistry.getRegistry(7777);
			serverIF = (ServerIF)reg.lookup("rmi://" + hostName + "/" + serviceName);
			serverIF.getMsg(msg);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void sendReq() throws RemoteException{
		try {
			Registry reg = LocateRegistry.getRegistry(7777);
			reg.rebind("rmi://" + hostName + "/" + "SendRequest", this);
			
			this.sendMsg("Gửi yêu cầu đỗ xe! \n");
			this.SEND_MESSAGE("Khách hàng gửi yêu cầu!\n");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	

}
