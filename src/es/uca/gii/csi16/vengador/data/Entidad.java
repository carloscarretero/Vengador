package es.uca.gii.csi16.vengador.data;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author Carlos Carretero Aguilar
 * @author Manuel Jesús López Jiménez
 */

public class Entidad 
{
	private int _iId;
	private boolean _bIsDeleted;
	private String _sTabla;
	
	public int getId() { return _iId; }
	public boolean getIsDeleted() { return _bIsDeleted; }
	
	/**
	 * Constructor de la clase Entidad
	 * @author Equipo Vengador
	 * @param iId
	 * @param sTabla
	 * @throws Exception
	 */
	public Entidad(int iId, String sTabla)
	 {
	 	_bIsDeleted = false;
	 	_iId = iId;
	 	_sTabla = sTabla;
	 }
	
	/**
	 * 
	 * @param sQuery
	 * @throws Exception
	 */
	protected void Update(String sQuery) throws Exception
	{
		Connection con = null;
		try
		{
			con = Data.Connection();
			con.createStatement().execute(sQuery);
		}
		catch(SQLException ee) { throw ee; }
		finally
		{
			if (con != null) con.close();
		}
	}
	
	/**
	 * Método Delete para eliminar la entidad con id _iId de la tabla sTabla
	 * @param sTabla
	 * @throws Exception
	 */
	public void Delete() throws Exception	
	{
		if(!_bIsDeleted)
		{
			Connection con = null;
			try
			{
				con = Data.Connection();
				con.createStatement().execute("DELETE FROM " + _sTabla + " WHERE id = " + _iId + ";");
				_bIsDeleted = true;
			}
			catch(SQLException ee) { throw ee; }
			finally 
			{
				if (con != null) con.close();
			}
		}
		else throw new Exception("El registro de la tabla " + _sTabla + " ya se ha eliminado");
	}
}
