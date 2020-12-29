package DALTM;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class Server extends JFrame{
	public JPanel contentPane;
    public static int countEmptySeat = ServerImpl.availSeats.length;
    public boolean[] seats = ServerImpl.availSeats;
    JTextArea txtRec = new JTextArea();
    JTextArea txtStatus = new JTextArea();
	JTextArea txtSend = new JTextArea();
    
    private String seat = "0";
    private String hostName = "localhost";
    private String serviceName = "SendRequest";
    private String clientServiceName = "CarNumber" + seat;
    String details[] = {seat, hostName, clientServiceName};

    public ClientIF clientIF;
	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public Server(){
		initComponents();
        txtStatus.append("RMI server is running\n");
	}
	
	public void checkAvailSeats() {
		System.out.println("============================================");
        for(int i = 0; i < ServerImpl.availSeats.length; i++) {
        	System.out.println("seats[" + i + "] = " + ServerImpl.availSeats[i]);
        }
	}
	
	private void register(String[] details){
		try {
			Registry registry = LocateRegistry.getRegistry(7777);
			clientIF = (ClientIF)registry.lookup("rmi://" + hostName + "/" + serviceName);
			clientIF.registerWithServer(details);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}

	}
	public int takeNumOfBtn(JButton btn){
		String stringNumOfBtn = btn.getName().replace("btnSeat_", "");
		return Integer.parseInt(stringNumOfBtn);
	}
	
	public void check(JButton btn) {
		if(!ServerImpl.availSeats[takeNumOfBtn(btn)]) {
			btn.setIcon(null);
			btn.setText("Seat " + takeNumOfBtn(btn));
			btn.revalidate();
		}
	}
	public void initComponents(){
    	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 609, 593);
		setTitle("Người giữ xe - Server");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSend = new JLabel("Send");
		lblSend.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSend.setBounds(10, 11, 82, 22);
		contentPane.add(lblSend);
		
		JLabel lblReccieve = new JLabel("Recceive");
		lblReccieve.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblReccieve.setBounds(10, 238, 120, 36);
		contentPane.add(lblReccieve);
		
		txtSend.setEditable(false);
		txtSend.setBounds(10, 35, 302, 192);
		contentPane.add(txtSend);
		txtRec.setEditable(false);
		txtRec.setBounds(10, 274, 302, 192);
		contentPane.add(txtRec);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblStatus.setBounds(323, 238, 120, 36);
		contentPane.add(lblStatus);
		txtStatus.setEditable(false);
		txtStatus.setBounds(322, 274, 266, 192);
		contentPane.add(txtStatus);
		
		JLabel lblSeats = new JLabel("Seats");
		lblSeats.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSeats.setBounds(323, 11, 82, 22);
		contentPane.add(lblSeats);
		
		JButton btnSeat_1 = new JButton("Seat 1");
		btnSeat_1.setName("btnSeat_1");
		btnSeat_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "1";
				if(ServerImpl.availSeats[1]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_1);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_1.setBounds(322, 36, 82, 59);
		contentPane.add(btnSeat_1);
		
		JButton btnSeat_2 = new JButton("Seat 2");
		btnSeat_2.setName("btnSeat_2");
		btnSeat_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "2";
				if(ServerImpl.availSeats[2]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
						
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_2);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_2.setBounds(414, 36, 82, 59);
		contentPane.add(btnSeat_2);
		
		JButton btnSeat_3 = new JButton("Seat 3");
		btnSeat_3.setName("btnSeat_3");
		btnSeat_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "3";
				if(ServerImpl.availSeats[3]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
						
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_3);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_3.setBounds(506, 35, 82, 59);
		contentPane.add(btnSeat_3);
		
		JButton btnSeat_4 = new JButton("Seat 4");
		btnSeat_4.setName("btnSeat_4");
		btnSeat_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "4";
				if(ServerImpl.availSeats[4]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
						
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_4);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_4.setBounds(323, 106, 82, 59);
		contentPane.add(btnSeat_4);
		
		JButton btnSeat_5 = new JButton("Seat 5");
		btnSeat_5.setName("btnSeat_5");
		btnSeat_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "5";
				if(ServerImpl.availSeats[5]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
						
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_5);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_5.setBounds(414, 106, 82, 59);
		contentPane.add(btnSeat_5);
		
		JButton btnSeat_6 = new JButton("Seat 6");
		btnSeat_6.setName("btnSeat_6");
		btnSeat_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "6";
				if(ServerImpl.availSeats[6]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
						
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_6);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_6.setBounds(506, 105, 82, 59);
		contentPane.add(btnSeat_6);
		
		JButton btnSeat_7 = new JButton("Seat 7");
		btnSeat_7.setName("btnSeat_7");
		btnSeat_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "7";
				if(ServerImpl.availSeats[7]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
						
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_7);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_7.setBounds(322, 176, 82, 59);
		contentPane.add(btnSeat_7);
		
		JButton btnSeat_8 = new JButton("Seat 8");
		btnSeat_8.setName("btnSeat_8");
		btnSeat_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "8";
				if(ServerImpl.availSeats[8]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
						
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_8);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_8.setBounds(414, 176, 82, 59);
		contentPane.add(btnSeat_8);
		
		JButton btnSeat_9 = new JButton("Seat 9");
		btnSeat_9.setName("btnSeat_9");
		btnSeat_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = "9";
				if(ServerImpl.availSeats[9]) {
					txtStatus.append("Đã có người đổ ở vị trí số " + details[0] + "\n");
				}
				else {
					try {
						//Dang ky cho xe
						register(details);
						
				        countEmptySeat--;
				        txtStatus.append("Đã đỗ xe ở vị trí " + details[0] + "\n");
						new ServerImpl().changeIcon(btnSeat_9);;
						ServerImpl.carNum = details[0];
				        checkAvailSeats();
				        
					}catch(Exception e) {
						System.err.println("RMI exception: ");
			            e.printStackTrace();
					}
				}
			}
		});
		btnSeat_9.setBounds(506, 176, 82, 59);
		contentPane.add(btnSeat_9);
		
		JButton btnCheckReq = new JButton("Check");
		btnCheckReq.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnCheckReq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new ServerImpl().checkReq();
					check(btnSeat_1);
					check(btnSeat_2);
					check(btnSeat_3);
					check(btnSeat_4);
					check(btnSeat_5);
					check(btnSeat_6);
					check(btnSeat_7);
					check(btnSeat_8);
					check(btnSeat_9);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}	
		});
		btnCheckReq.setBounds(174, 477, 181, 59);
		contentPane.add(btnCheckReq);
	}
}
