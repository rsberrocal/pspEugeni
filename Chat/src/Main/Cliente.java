package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel{
	
	public LaminaMarcoCliente(){
	
		JLabel texto=new JLabel("CLIENTE");
		
		add(texto);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");

		SendText st = new SendText();
		miboton.addActionListener(st);

		add(miboton);	
		
	}
	
	private class SendText implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			try {
				Socket s = new Socket("10.0.207.15",9998);

				DataOutputStream dataStream = new DataOutputStream(s.getOutputStream());

				dataStream.writeUTF(campo1.getText());
				System.out.println(campo1.getText());
				dataStream.close();

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		}
	}
	
		
		
		
	private JTextField campo1;
	
	private JButton miboton;
	
}
