package es.uca.gii.csi16.vengador.gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import es.uca.gii.csi16.vengador.data.Refugio;

public class RefugiosTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4313664440766945841L;
	private ArrayList<Refugio> _aData;
	
	public RefugiosTableModel(ArrayList<Refugio> aData) { _aData = aData; }

	public int getColumnCount() { return 7; }
	public int getRowCount() { return _aData.size(); }
	public Refugio getData(int iRow) { return _aData.get(iRow); }

	@Override
	public Object getValueAt(int iRow, int iCol) 
	{
		Refugio refugio = _aData.get(iRow);
		
		switch(iCol)
		{
			case 0: return refugio.getTipoRefugio().getNombre();
			case 1: return refugio.getCapacidadMax();
			case 2: return refugio.getOcupacionActual();
			case 3: return refugio.getLocalizacion(); 
			case 4: return refugio.getValoracion();
			case 5: return refugio.getDistanciaBase();
			case 6: return refugio.getTiempoEvacuacion();
			default: throw new IllegalStateException("Columna de la tabla incorrecta");
		}
	}

}
