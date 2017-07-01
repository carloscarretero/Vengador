package es.uca.gii.csi16.vengador.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.lang.Integer;
import java.sql.Types;

/**
 * @author Equipo Vengador
 */
public class Refugio extends Entidad
{
	/**
	 * Atributos
	 */
	private int _iCapacidadMax, _iOcupacionActual, _iValoracion,
		_iDistanciaBase, _iTiempoEvacuacion;
	private String _sLocalizacion;
	private TipoRefugio _tipoRefugio;
	
	/**
	 * Métodos GET
	 */
	public int getCapacidadMax() { return _iCapacidadMax; }
	public int getOcupacionActual() { return _iOcupacionActual; }
	public int getValoracion() { return _iValoracion; }
	public int getDistanciaBase() { return _iDistanciaBase; }
	public int getTiempoEvacuacion() { return _iTiempoEvacuacion; }
	public String getLocalizacion() { return _sLocalizacion; }
	public TipoRefugio getTipoRefugio() { return _tipoRefugio; }
	
	/**
	 * Metodos SET
	 */
	public void setCapacidadMax(int iCapacidadMax) throws Exception
	{
		if(iCapacidadMax < 0)
			throw new Exception("Capacidad maxima no puede ser negativa");
		_iCapacidadMax = iCapacidadMax; 
	}
	
	public void setOcupacionActual(int iOcupacionActual) throws Exception
	{ 
		if(iOcupacionActual < 0 || iOcupacionActual > _iCapacidadMax)
			throw new Exception("Ocupacion actual incorrecta: negativa o mayor que la capacidad maxima");
		_iOcupacionActual = iOcupacionActual; 
	}
	
	public void setValoracion(int iValoracion) throws Exception
	{ 
		if(iValoracion < 1 || iValoracion > 5)
			throw new Exception("Valoracion incorrecta: debe ser entre 1 y 5");
		_iValoracion = iValoracion; 
	}
	
	public void setDistanciaBase(int iDistanciaBase) throws Exception
	{ 
		if(iDistanciaBase < 0)
			throw new Exception("La distancia a la base no puede ser negativa");
		_iDistanciaBase = iDistanciaBase; 
	}
	
	public void setTiempoEvacuacion(int iTiempoEvacuacion) throws Exception
	{ 
		if(iTiempoEvacuacion < 0)
			throw new Exception("El tiempo de evacuacion negativo");
		_iTiempoEvacuacion = iTiempoEvacuacion; 
	}
	
	public void setLocalizacion(String sLocalizacion) throws Exception
	{ 
		if(sLocalizacion == null)
			throw new Exception("La localizacion no puede ser nula");
		_sLocalizacion = sLocalizacion; 
	}
	
	public void setTipoRefugio(TipoRefugio tipoRefugio) throws Exception 
	{ 
		if(tipoRefugio == null)
			throw new Exception("El tipo de refugio no puede ser nulo");
		_tipoRefugio = tipoRefugio;
	}
	
	/**
	 * Constructor de la clase Refugio
	 * @author Equipo Vengador
	 * @param iId
	 * @throws Exception
	 */
	public Refugio(int iId) throws Exception
	{
		super(iId, "Refugio");
		Connection con = null;
		try
		{
			con = Data.Connection();
			Initialize(iId, con);
		}
		catch(SQLException ee) { throw ee; }
		finally { if (con != null) con.close(); }
	}
	
	private Refugio(int iId, int iId_tipoRefugio, int iCapacidadMax, int iOcupacionActual, 
			String sLocalizacion, int iValoracion, int iDistanciaBase, int iTiempoEvacuacion) throws Exception
	{
		super(iId, "Refugio");
		_iCapacidadMax = iCapacidadMax; 
		_iOcupacionActual = iOcupacionActual;
		_iValoracion = iValoracion;
		_iDistanciaBase = iDistanciaBase;
		_iTiempoEvacuacion = iTiempoEvacuacion;
		_sLocalizacion = sLocalizacion;
		try
		{
			_tipoRefugio = new TipoRefugio(iId_tipoRefugio);
		}
		catch (SQLException ee) { throw ee; }
	}
	/**
	 * Metodo Create que crea un nuevo registro en la tabla Refugio y 
	 * devuelve un objeto de la clase Refugio con la misma informacion
	 * @param iIdTipoRefugio
	 * @param iCapacidadMax
	 * @param iOcupacionActual
	 * @param sLocalizacion
	 * @param iValoracion
	 * @param iDistanciaBase
	 * @param iTiempoEvacuacion
	 * @return Refugio
	 * @throws Exception
	 */
	public static Refugio Create(TipoRefugio tipoRefugio, int iCapacidadMax, int iOcupacionActual, String sLocalizacion, int iValoracion,
			int iDistanciaBase, int iTiempoEvacuacion) throws Exception
	{
		Connection con = null;
		try 
		{
			con = Data.Connection();
			StringBuilder sbQuery = new StringBuilder();
			
			sbQuery.append("INSERT INTO refugio (id_tipoRefugio, capacidadMax, ocupacionActual, "
				+ "localizacion, valoracion, distanciaBase, tiempoEvacuacion) VALUES (");
			sbQuery.append(tipoRefugio.getId()).append(", ");
			sbQuery.append(iCapacidadMax).append(", ");
			sbQuery.append(iOcupacionActual).append(", ");
			sbQuery.append(Data.String2Sql(sLocalizacion, true, false)).append(", ");
			sbQuery.append(iValoracion).append(", ");
			sbQuery.append(iDistanciaBase).append(", ");
			sbQuery.append(iTiempoEvacuacion).append(");");
			
			int iFilasInsertadas = con.createStatement().executeUpdate(sbQuery.toString());
			if(iFilasInsertadas != 1)
				throw new Exception("Ninguna fila insertada");
			
			return new Refugio(Data.LastId(con));
		}
		catch (SQLException ee) { throw ee; }
		finally { if (con != null) con.close(); }
	}
	
	/**
	 * @throws Exception 
	 */
	public void Delete() throws Exception { super.Delete(); }
	
	/**
	 * @throws Exception 
	 */
	public void Update() throws Exception
	{
		if(getIsDeleted()) throw new Exception("No se puede actualizar un refugio que ya se ha eliminado");
		
		super.Update("UPDATE Refugio SET id_tipoRefugio = " + _tipoRefugio.getId() + ", capacidadMax = "
			+ _iCapacidadMax + ", ocupacionActual = " + _iOcupacionActual + ", localizacion = " 
			+ Data.String2Sql(_sLocalizacion, true, false) + ", valoracion = " + _iValoracion + 
			", distanciaBase = " + _iDistanciaBase + ", tiempoEvacuacion = " + _iTiempoEvacuacion +
			" WHERE id = " + getId());
	}
	
	/**
	 * 
	 * @param iIdTipoRefugio
	 * @param iCapacidadMax
	 * @param iOcupacionActual
	 * @param sLocalizacion
	 * @param iValoracion
	 * @param iDistanciaBase
	 * @param iTiempoEvacuacion
	 * @return Refugio
	 * @throws Exception
	 */
	public static ArrayList<Refugio> Select(String sTipoRefugio, Integer iCapacidadMax, Integer iOcupacionActual, 
			String sLocalizacion, Integer iValoracion, Integer iDistanciaBase, Integer iTiempoEvacuacion) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Refugio> alRefugiosSeleccionados = new ArrayList<Refugio>();
		try
		{
			con = Data.Connection();
			String[] asCampos = { "tr.nombre", "r.capacidadMax", "r.ocupacionActual",
				"r.localizacion", "r.valoracion", "r.distanciaBase", "r.tiempoEvacuacion"};
			int[] atTipos = {Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, 
				Types.INTEGER, Types.INTEGER, Types.INTEGER};
			Object[] aoValores = {sTipoRefugio, iCapacidadMax, 
				iOcupacionActual, sLocalizacion, iValoracion, iDistanciaBase, iTiempoEvacuacion};
			rs = con.createStatement().executeQuery("SELECT r.id, r.id_TipoRefugio, r.capacidadMax, "
				+ "r.ocupacionActual, r.localizacion, r.valoracion, r.distanciaBase, r.tiempoEvacuacion "
				+ "FROM refugio r INNER JOIN tiporefugio tr ON r.id_TipoRefugio = tr.id " 
				+ Data.Where(asCampos, atTipos, aoValores) + ";");
			
			while(rs.next())
				alRefugiosSeleccionados.add(new Refugio(rs.getInt("id"), rs.getInt("id_TipoRefugio"),
					rs.getInt("capacidadMax"), rs.getInt("ocupacionActual"), rs.getString("localizacion"), 
					rs.getInt("valoracion"), rs.getInt("distanciaBase"), rs.getInt("tiempoEvacuacion")));
			
			/*System.out.println("SELECT r.id, r.id_TipoRefugio, r.capacidadMax, "
				+ "r.ocupacionActual, r.localizacion, r.valoracion, r.distanciaBase, r.tiempoEvacuacion "
				+ "FROM refugio r INNER JOIN tiporefugio tr ON r.id_TipoRefugio = tr.id " 
				+ Data.Where(asCampos, atTipos, aoValores) + ";");*/
			
			return alRefugiosSeleccionados;
		}
		catch(SQLException ee) { throw ee; }
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
			con = Data.Connection();
			StringBuilder sbQuery = new StringBuilder();
			
			sbQuery.append("SELECT id, id_tipoRefugio, capacidadMax, ocupacionActual, localizacion, "
				+ "valoracion, distanciaBase, tiempoEvacuacion FROM refugio WHERE id = "); 
			sbQuery.append(iId).append(";");
			
			rs = con.createStatement().executeQuery(sbQuery.toString());
			rs.next();
			_iCapacidadMax = rs.getInt("capacidadMax"); 
			_iOcupacionActual = rs.getInt("ocupacionActual");
			_iValoracion = rs.getInt("valoracion");
			_iDistanciaBase = rs.getInt("distanciaBase");
			_iTiempoEvacuacion = rs.getInt("tiempoEvacuacion");
			_sLocalizacion = rs.getString("localizacion");
			_tipoRefugio = new TipoRefugio(rs.getInt("id_tipoRefugio"), con);
		}
		catch (SQLException ee) { throw ee; }	
		finally { if (rs != null) rs.close(); }
	}

	@Override
	public String toString()
	{
		return super.toString() + ";" + getId() + ";" + _tipoRefugio.toString() + 
			";" + _iOcupacionActual + ";" + _iValoracion; 
	}
}
