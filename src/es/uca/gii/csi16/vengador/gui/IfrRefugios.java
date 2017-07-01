package es.uca.gii.csi16.vengador.gui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi16.vengador.data.Refugio;
import es.uca.gii.csi16.vengador.data.TipoRefugio;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IfrRefugios extends JInternalFrame {

	private static final long serialVersionUID = -8852195579405140491L;
	private JComboBox<TipoRefugio> cmbTipoRefugio;
	private JTextField txtCapacidadMaxima;
	private JTextField txtOcupacionActual;
	private JTextField txtLocalizacion;
	private JTextField txtValoracion;
	private JTextField txtDistanciaBase;
	private JTextField txtTiempoEvacuacion;
	private JTable tabResult;
	private Container pnlParent;

	/**
	 * Create the frame.
	 */
	public IfrRefugios(Container frame) 
	{
		pnlParent = frame;
		setResizable(true);
		setClosable(true);
		setTitle("Refugios");
		setBounds(100, 100, 781, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblTipoRefugio = new JLabel("Tipo de refugio");
		panel.add(lblTipoRefugio);
		
		cmbTipoRefugio = new JComboBox<TipoRefugio>();
		cmbTipoRefugio.setEditable(true);
		panel.add(cmbTipoRefugio);
		try {
			cmbTipoRefugio.setModel(new TipoRefugioListModel(TipoRefugio.Select()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JLabel lblCapacidadMaxima = new JLabel("Capacidad Máxima");
		panel.add(lblCapacidadMaxima);
		
		txtCapacidadMaxima = new JTextField();
		panel.add(txtCapacidadMaxima);
		txtCapacidadMaxima.setColumns(10);
		
		JLabel lblOcupacionActual = new JLabel("Ocupación Actual");
		panel.add(lblOcupacionActual);
		
		txtOcupacionActual = new JTextField();
		panel.add(txtOcupacionActual);
		txtOcupacionActual.setColumns(10);
		
		JLabel lblLocalizacion = new JLabel("Localización");
		panel.add(lblLocalizacion);
		
		txtLocalizacion = new JTextField();
		panel.add(txtLocalizacion);
		txtLocalizacion.setColumns(10);
		
		JLabel lblValoracion = new JLabel("Valoración");
		panel.add(lblValoracion);
		
		txtValoracion = new JTextField();
		panel.add(txtValoracion);
		txtValoracion.setColumns(10);
		
		JLabel lblDistanciaBase = new JLabel("Distancia a la base");
		panel.add(lblDistanciaBase);
		
		txtDistanciaBase = new JTextField();
		panel.add(txtDistanciaBase);
		txtDistanciaBase.setColumns(10);
		
		JLabel lblTiempoEvacuacion = new JLabel("Tiempo de evacuación");
		panel.add(lblTiempoEvacuacion);
		
		txtTiempoEvacuacion = new JTextField();
		panel.add(txtTiempoEvacuacion);
		txtTiempoEvacuacion.setColumns(10);
		
		JButton butSearch = new JButton("Buscar");
		butSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				String sTipoRefugio = cmbTipoRefugio.getEditor().getItem().toString();
				Integer iCapacidadMax = (txtCapacidadMaxima.getText().isEmpty()) ?
					null : new Integer(Integer.parseInt(txtCapacidadMaxima.getText()));
				Integer iOcupacionActual = (txtOcupacionActual.getText().isEmpty()) ?
					null : new Integer(Integer.parseInt(txtOcupacionActual.getText()));
				String sLocalizacion = (txtLocalizacion.getText().isEmpty()) ?
					null : txtLocalizacion.getText();
				Integer iValoracion = (txtValoracion.getText().isEmpty()) ?
					null : new Integer(Integer.parseInt(txtValoracion.getText()));
				Integer iDistanciaBase = (txtDistanciaBase.getText().isEmpty()) ?
					null : new Integer(Integer.parseInt(txtDistanciaBase.getText()));
				Integer iTiempoEvacuacion = (txtTiempoEvacuacion.getText().isEmpty()) ?
					null : new Integer(Integer.parseInt(txtTiempoEvacuacion.getText()));
				try
				{
					tabResult.setModel(
						new RefugiosTableModel(Refugio.Select(sTipoRefugio, iCapacidadMax, iOcupacionActual,
							sLocalizacion, iValoracion, iDistanciaBase, iTiempoEvacuacion)));
				}
				catch(Exception ee) { JOptionPane.showMessageDialog(null, ee.getMessage()); }
			}
		});
		panel.add(butSearch);
		
		tabResult = new JTable();
		tabResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					int iRow = ((JTable) e.getSource()).getSelectedRow();
					
					Refugio refugio = ((RefugiosTableModel) tabResult.getModel()).getData(iRow);
					
					if(refugio != null)
					{
						IfrRefugio ifrRefugio = new IfrRefugio(refugio);
						ifrRefugio.setBounds(10, 27, 450, 450);
						pnlParent.add(ifrRefugio, 0);
						ifrRefugio.setVisible(true);
					}
				}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER);
	}
}
