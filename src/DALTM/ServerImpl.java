package DALTM;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ServerImpl extends UnicastRemoteObject implements ServerIF{
	private static final long serialVersionUID = 1L;
	public static boolean[] availSeats = new boolean[10];
	public Vector<Car> data;
	public static String start = "RMI server is running";
	public static Server serverGUI = new Server();
	public static String carNum = "0";

    public ServerImpl() throws RemoteException {
		super();
		data = new Vector<Car>(10,1);
	}
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				String hostName = "localhost";
				String serviceName = "CarPark";
			    System.setProperty("java.rmi.server.hostname",hostName);
			    
				try {
					Registry reg = LocateRegistry.createRegistry(7777);
					serverGUI.setVisible(true);
					ServerIF run = new ServerImpl();
					reg.rebind("rmi://" + hostName + "/" + serviceName, run);

				} catch (Exception e) {
					System.err.println("RMI exception: ");
		            e.printStackTrace();
				}
			}
		});
	}
	
	//Lấy dữ liệu của bãi đổ xe
	public Vector<Car> getData() throws RemoteException {
		return data;
	}
	
	public void registerListener(String[] details) throws RemoteException{
		registerCar(details);
	}
	
	//Đăng kí chỗ đỗ cho xe
	public void registerCar(String[] details){
	    try {
	    	Registry reg = LocateRegistry.getRegistry(7777);
			ClientIF car = (ClientIF)reg.lookup("rmi://" + details[1] + "/" + details[2]);
			car.getMsg("Xe đã đổ ở vị trí thứ " + details[0] + "\n");

			Car c = new Car(car, details[0]);
	        c.setSeat(details[0]);
			data.addElement(c);
			
			int seat = Integer.parseInt(details[0]);
	        availSeats[seat] = true;
	        carNum = details[0];
	        
	        //In ra danh sach bai dau
	        for (Car xe : data) {
	            System.out.print(xe.seat + " ");
	        }
	        System.out.println();

	    }catch(Exception e) {
			System.err.println("RMI exception: ");
            e.printStackTrace();
	    }
	}
	
	
	public void changeIcon(JButton btn) throws RemoteException{
		try {
        	Image image = ImageIO.read(new File("E:\\car.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    		btn.setIcon(new ImageIcon(image));
          } catch (Exception ex) {
            System.out.println(ex);
          }
	}
	
	public void leavePark(String[] details) throws RemoteException{
		leave(details);
	}
	public void leave(String[] details){
		try {
			this.notify("Xe thứ " + details[0] + " đã rời đi\n");
			
			for(int i = 0; i < data.size(); i++) {
				if(data.get(i).getSeat().equals(details[0])) {
					data.remove(data.get(i));
					int seat = Integer.parseInt(details[0]);
					availSeats[seat] = false;
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	
	public void notify(String msg) throws RemoteException{
		serverGUI.txtStatus.append(msg);
	}
	public void sendMsg(String msg) throws RemoteException{
		serverGUI.txtSend.append(msg);
	}
	public void getMsg(String msg) throws RemoteException{
		serverGUI.txtRec.append(msg);
	}
	
	public void checkReq() throws RemoteException {
		String serviceName = "SendRequest", hostName = "localhost";
		try {
			Registry reg = LocateRegistry.getRegistry(7777);
			reg.lookup("rmi://" + hostName + "/" + serviceName);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		this.sendMsg("Những vị trí còn trống là: ");
		if(Server.countEmptySeat <= 1) {
			this.sendMsg("\nKhông còn chỗ trống!");
		}
		else {
	        for (int i = 0; i < ServerImpl.availSeats.length; i++) {
	        	if(ServerImpl.availSeats[i] == false) {
	        		this.sendMsg("  " + i);
	        	}
	        }
		}
        this.sendMsg("\n");
	}
}
