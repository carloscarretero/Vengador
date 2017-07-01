package es.uca.gii.csi16.vengador.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TipoRefugio extends Entidad
{
	private String _sNombre;
	
	public String getNombre() { return _sNombre; }
	
	public void setNombre(String sNombre) { _sNombre = sNombre; }
	
	/**
	 * @param iId
	 * @throws Exception
	 */
	public TipoRefugio(int iId) throws Exception
	{
		super(iId, "TipoRefugio");
		Connection con = null;
		try 
		{
			con = Data.Connection();
			Initialize(iId, con);
		}
		catch (SQLException ee) { throw ee; }
		finally { if (con != null) con.close(); }
	}
	
	public TipoRefugio(int iId, Connection con) throws Exception 
	{ 
		super(iId, "TipoRefugio");
		Initialize(iId, con); 
	}
	
	/**
	 * Metodo Select de la clase TipoRefugio 
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<TipoRefugio> Select() throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		ArrayList<TipoRefugio> alTipoRefugiosSeleccionados = 
			new ArrayList<TipoRefugio>();
		try
		{
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT id, nombre FROM tiporefugio;");
			
			while(rs.next())
				alTipoRefugiosSeleccionados.add(new TipoRefugio(rs.getInt("id")));
			
			return alTipoRefugiosSeleccionados;
		}
		catch(Exception ee){throw ee;}
		finally
		{
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
	
	private void Initialize(int iId, Connection con) throws Exception
	{
		ResultSet rs = null;
		try 
		{
			StringBuilder sbQuery = new StringBuilder();
			
			sbQuery.append("SELECT id, nombre FROM tiporefugio WHERE id = "); 
			sbQuery.append(iId).append(";");
			
			rs = con.createStatement().executeQuery(sbQuery.toString());
			rs.next();
			_sNombre = rs.getString("nombre");
		}
		catch (SQLException ee) { throw ee; }
		finally { if (rs != null) rs.close(); }
	}

	@Override
	public String toString() { return _sNombre; }
	
}
