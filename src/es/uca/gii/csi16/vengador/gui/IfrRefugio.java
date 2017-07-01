package es.uca.gii.csi16.vengador.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import es.uca.gii.csi16.vengador.data.Refugio;
import es.uca.gii.csi16.vengador.data.TipoRefugio;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IfrRefugio extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox<TipoRefugio> cmbTipoRefugio;
	private JTextField txtCapacidadMaxima;
	private JTextField txtOcupacionActual;
	private JTextField txtLocalizacion;
	private JTextField txtValoracion;
	private JTextField txtDistanciaBase;
	private JTextField txtTiempoEvacuacion;
	private Refugio _refugio = null;

	/**
	 * Create the frame.
	 */
	public IfrRefugio(Refugio refugio)
	{
		if(refugio != null)
			_refugio = refugio;
		
		setClosable(true);
		setResizable(true);
		setTitle("Refugio");
		setBounds(100, 100, 330, 300);
		getContentPane().setLayout(null);
				
		JLabel lblTipoRefugio = new JLabel("Tipo de refugio");
		lblTipoRefugio.setBounds(10, 11, 82, 14);
		getContentPane().add(lblTipoRefugio);
		
		cmbTipoRefugio = new JComboBox<TipoRefugio>();
		try {
			cmbTipoRefugio.setModel(new TipoRefugioListModel(TipoRefugio.Select()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cmbTipoRefugio.setBounds(136, 8, 86, 20);
		getContentPane().add(cmbTipoRefugio);
		if(_refugio != null)
			cmbTipoRefugio.setSelectedIndex(_refugio.getTipoRefugio().getId() - 1);
		
		JLabel lblCapacidadMaxima = new JLabel("Capacidad máxima");
		lblCapacidadMaxima.setBounds(10, 36, 96, 14);
		getContentPane().add(lblCapacidadMaxima);
		
		txtCapacidadMaxima = new JTextField();
		if(_refugio != null)
			txtCapacidadMaxima.setText(String.valueOf(_refugio.getCapacidadMax()));
		txtCapacidadMaxima.setBounds(136, 33, 86, 20);
		getContentPane().add(txtCapacidadMaxima);
		txtCapacidadMaxima.setColumns(10);
		
		JLabel lblOcupacionActual = new JLabel("Ocupación Actual");
		lblOcupacionActual.setBounds(10, 64, 96, 14);
		getContentPane().add(lblOcupacionActual);
		
		txtOcupacionActual = new JTextField();
		if(_refugio != null)
			txtOcupacionActual.setText(String.valueOf(_refugio.getOcupacionActual()));
		txtOcupacionActual.setBounds(136, 61, 86, 20);
		getContentPane().add(txtOcupacionActual);
		txtOcupacionActual.setColumns(10);
		
		JLabel lblLocalizacion = new JLabel("Localización");
		lblLocalizacion.setBounds(10, 89, 96, 14);
		getContentPane().add(lblLocalizacion);
		
		txtLocalizacion = new JTextField();
		if(_refugio != null)
			txtLocalizacion.setText(_refugio.getLocalizacion());
		txtLocalizacion.setBounds(136, 86, 86, 20);
		getContentPane().add(txtLocalizacion);
		txtLocalizacion.setColumns(10);
		
		JLabel lblValoracion = new JLabel("Valoración");
		lblValoracion.setBounds(10, 114, 82, 14);
		getContentPane().add(lblValoracion);
		
		txtValoracion = new JTextField();
		if(_refugio != null)
			txtValoracion.setText(String.valueOf(_refugio.getValoracion()));
		txtValoracion.setBounds(136, 111, 86, 20);
		getContentPane().add(txtValoracion);
		txtValoracion.setColumns(10);
		
		JLabel lblDistanciaBase = new JLabel("Distancia a la base");
		lblDistanciaBase.setBounds(10, 139, 96, 14);
		getContentPane().add(lblDistanciaBase);
		
		txtDistanciaBase = new JTextField();
		if(_refugio != null)
			txtDistanciaBase.setText(String.valueOf(_refugio.getDistanciaBase()));
		txtDistanciaBase.setBounds(136, 136, 86, 20);
		getContentPane().add(txtDistanciaBase);
		txtDistanciaBase.setColumns(10);
		
		JLabel lblTiempoEvacuacion = new JLabel("Tiempo de evacuación");
		lblTiempoEvacuacion.setBounds(10, 164, 116, 14);
		getContentPane().add(lblTiempoEvacuacion);
		
		txtTiempoEvacuacion = new JTextField();
		if(_refugio != null)
			txtTiempoEvacuacion.setText(String.valueOf(_refugio.getTiempoEvacuacion()));
		txtTiempoEvacuacion.setBounds(136, 161, 86, 20);
		getContentPane().add(txtTiempoEvacuacion);
		txtTiempoEvacuacion.setColumns(10);
		
		JButton butSave = new JButton("Guardar");
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					if(cmbTipoRefugio.getModel().getSelectedItem() == null)
						throw new Exception("Selecciona un tipo de refugio");
					if(_refugio == null)
						_refugio = Refugio.Create((TipoRefugio)cmbTipoRefugio.getModel().getSelectedItem(), 
							Integer.parseInt(txtCapacidadMaxima.getText()), 
							Integer.parseInt(txtOcupacionActual.getText()), txtLocalizacion.getText(), 
							Integer.parseInt(txtValoracion.getText()), Integer.parseInt(txtDistanciaBase.getText()), 
							Integer.parseInt(txtTiempoEvacuacion.getText()));
					else
					{
						_refugio.setTipoRefugio( (TipoRefugio) cmbTipoRefugio.getModel().getSelectedItem());
						_refugio.setCapacidadMax(Integer.parseInt(txtCapacidadMaxima.getText()));
						_refugio.setOcupacionActual(Integer.parseInt(txtOcupacionActual.getText()));
						_refugio.setLocalizacion(txtLocalizacion.getText());
						_refugio.setValoracion(Integer.parseInt(txtValoracion.getText()));
						_refugio.setDistanciaBase(Integer.parseInt(txtDistanciaBase.getText()));
						_refugio.setTiempoEvacuacion(Integer.parseInt(txtTiempoEvacuacion.getText()));
						_refugio.Update();
					}
				}
				catch(Exception ee){
					JOptionPane.showMessageDialog(null, ee.getMessage());
				}	
			}
		});
		butSave.setBounds(133, 192, 89, 23);
		getContentPane().add(butSave);
	}
}
