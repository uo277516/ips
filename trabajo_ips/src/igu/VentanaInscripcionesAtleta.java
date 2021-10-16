package igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;

import logica.InscripcionDto;
import logica.InscripcionModel;

import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JTextArea;

public class VentanaInscripcionesAtleta extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPreguntar;
	private JLabel lblPreguntar2;
	private JPanel panel;
	private JRadioButton radioDni;
	private JRadioButton radioEmail;
	private final ButtonGroup gr = new ButtonGroup();
	private JButton btnSig;
	private JLabel lblDni;
	private JTextField txtDNI;
	private JLabel lblEmail;
	private JButton btnMostrar;
	private InscripcionModel im;
	private List<InscripcionDto> insAtleta;
	private JTextArea txtInfo;
	private String infoInscripciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInscripcionesAtleta frame = new VentanaInscripcionesAtleta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaInscripcionesAtleta() {
		im= new InscripcionModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 498);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblPreguntar());
		contentPane.add(getLblPreguntar2());
		contentPane.add(getPanel());
		contentPane.add(getBtnSig());
		contentPane.add(getLblDni());
		contentPane.add(getTxtDNI());
		contentPane.add(getLblEmail());
		contentPane.add(getBtnMostrar());
		contentPane.add(getTxtInfo());
	}

	private JLabel getLblPreguntar() {
		if (lblPreguntar == null) {
			lblPreguntar = new JLabel("Para conocer el estado de sus inscripciones, elija el dato personal ");
			lblPreguntar.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblPreguntar.setBounds(21, 43, 399, 22);
		}
		return lblPreguntar;
	}
	private JLabel getLblPreguntar2() {
		if (lblPreguntar2 == null) {
			lblPreguntar2 = new JLabel("que prefiere seleccionar y rellene el campo:\r\n");
			lblPreguntar2.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblPreguntar2.setBounds(21, 64, 404, 13);
		}
		return lblPreguntar2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setFont(new Font("Tahoma", Font.PLAIN, 13));
			panel.setLayout(null);
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Elija la opción", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel.setBackground(Color.WHITE);
			panel.setBounds(21, 103, 307, 63);
			panel.add(getRadioDni());
			panel.add(getRadioEmail());
		}
		return panel;
	}
	private JRadioButton getRadioDni() {
		if (radioDni == null) {
			radioDni = new JRadioButton("DNI");
			radioDni.setFont(new Font("Tahoma", Font.PLAIN, 13));
			radioDni.setBounds(29, 23, 103, 21);
			gr.add(radioDni);
		}
		return radioDni;
	}
	private JRadioButton getRadioEmail() {
		if (radioEmail == null) {
			radioEmail = new JRadioButton("Email");
			radioEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
			radioEmail.setBounds(164, 23, 103, 21);
			gr.add(radioEmail);
		}
		return radioEmail;
	}
	private JButton getBtnSig() {
		if (btnSig == null) {
			btnSig = new JButton("Siguiente");
			btnSig.setBackground(SystemColor.inactiveCaption);
			btnSig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarParaIntroducir();
				}
			});
			btnSig.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnSig.setBounds(382, 123, 110, 21);
		}
		return btnSig;
	}
	
	protected void mostrarParaIntroducir() {
		if (!radioDni.isSelected() && !radioEmail.isSelected())
		{
			JOptionPane.showMessageDialog(this, "Tienes que seleccionar una opción");
		} else {
			radioDni.setEnabled(false);
			radioEmail.setEnabled(false);
			if (radioDni.isSelected()) {
				lblDni.setVisible(true);
			}
			else {
				lblEmail.setVisible(true);
			}
		txtDNI.setEnabled(true);
		btnMostrar.setEnabled(true);
		}
		
	}

	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("DNI:");
			lblDni.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblDni.setBounds(33, 188, 45, 13);
			lblDni.setVisible(false);
		}
		return lblDni;
	}
	private JTextField getTxtDNI() {
		if (txtDNI == null) {
			txtDNI = new JTextField();
			txtDNI.setBounds(88, 183, 160, 22);
			txtDNI.setColumns(10);
			txtDNI.setEnabled(false);
		}
		return txtDNI;
	}
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Email:");
			lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblEmail.setBounds(33, 188, 45, 13);
			lblEmail.setVisible(false);
		}
		return lblEmail;
	}
	private JButton getBtnMostrar() {
		if (btnMostrar == null) {
			btnMostrar = new JButton("Mostrar inscripciones");
			btnMostrar.setEnabled(false);
			btnMostrar.setBackground(new Color(124, 252, 0));
			btnMostrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarInscripciones();
				}
			});
			btnMostrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnMostrar.setBounds(316, 184, 160, 21);
		}
		return btnMostrar;
	}

	protected void mostrarInscripciones() {
		if (txtDNI.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Debes rellenar el campo");
		} else {
			buscarInscripciones();
		}
	}

	private void buscarInscripciones() {
		if (radioDni.isSelected()) {
			//buscardni
			System.out.println("dni");
			insAtleta=im.buscarInsByDni(txtDNI.getText());
		}
		else if (radioEmail.isSelected())
		{
			//buscaremail
			System.out.println("email");
			insAtleta=im.buscarInsByEmail(txtDNI.getText());
		}
		rellenarElTexto();
		mostrarInfo();
	}

	private void rellenarElTexto() {
		for(int i=0; i<insAtleta.size(); i++)
		{
			infoInscripciones+=insAtleta.get(i).get
		}
	}

	private void mostrarInfo() {
		txtInfo.setEnabled(true);
		txtInfo.setText(infoInscripciones);
	}
	
	private JTextArea getTxtInfo() {
		if (txtInfo == null) {
			txtInfo = new JTextArea();
			txtInfo.setEnabled(false);
			txtInfo.setBounds(29, 245, 425, 170);
		}
		return txtInfo;
	}
}
