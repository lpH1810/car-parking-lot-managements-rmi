package DALTM;

import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;


public class Client extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	public ClientIF clientIF;
	JTextArea txtNoti = new JTextArea();

    private String seat = "0";
    private String hostName = "localhost";
    private String clientServiceName = "CarNumber" + seat;
    String details[] = {seat, hostName, clientServiceName};

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public Client(){
		JButton btnSendReq = new JButton("Send Request");
		JButton btnLeave = new JButton("Leave");
		try {
			this.getConnected("0");
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 173);
		setTitle("Khách hàng - Client");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNoti = new JLabel("Notifications");
		lblNoti.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNoti.setBounds(10, 11, 176, 22);
		contentPane.add(lblNoti);

		txtNoti.setEditable(false);
		txtNoti.setBounds(10, 35, 295, 91);
		contentPane.add(txtNoti);
		
		btnSendReq.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSendReq.setBounds(315, 49, 151, 33);
		btnSendReq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new ClientImpl(ClientImpl.clientGUI, "SendReq").sendReq();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnSendReq);
		
		btnLeave.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLeave.setBounds(315, 93, 151, 33);
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details[0] = ClientImpl.carNum;
				try {
					new ClientImpl(ClientImpl.clientGUI, "leave").LEAVE_PARK(details);
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ClientImpl.clientGUI.dispatchEvent(new WindowEvent(ClientImpl.clientGUI, WindowEvent.WINDOW_CLOSING));
				Server.countEmptySeat++;
			}
		});
		contentPane.add(btnLeave);
	}
    public void getConnected(String seat) throws RemoteException{
	try {
		clientIF = new ClientImpl(this, seat);
		clientIF.startClient();
	} catch (RemoteException e) {
		e.printStackTrace();
		}
	}
}
