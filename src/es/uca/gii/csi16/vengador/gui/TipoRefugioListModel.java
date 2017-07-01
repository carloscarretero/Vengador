package es.uca.gii.csi16.vengador.gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import es.uca.gii.csi16.vengador.data.TipoRefugio;

public class TipoRefugioListModel 
	extends AbstractListModel<TipoRefugio> 
	implements ComboBoxModel<TipoRefugio>
{
	private static final long serialVersionUID = 2358957361798704851L;
	private List<TipoRefugio> _aData;
	private Object _selection = null;
	
	public TipoRefugioListModel(List<TipoRefugio> aData) { _aData = aData; }
	
	public TipoRefugio getElementAt(int iIndex) { return _aData.get(iIndex); }
	public int getSize() { return _aData.size(); }
	public Object getSelectedItem() { return _selection; } 
	
	public void setSelectedItem(Object o) 
	{ 	
		if(o instanceof TipoRefugio)
			for(int i=0; i<_aData.size(); i++)
				if(_aData.get(i).getId() == ((TipoRefugio) o).getId())
					_selection = _aData.get(i);
		else
			_selection = o;
	
	}
}
