package es.uca.gii.csi16.vengador.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Iterator;

import es.uca.gii.csi16.vengador.data.Data;
import es.uca.gii.csi16.vengador.data.Refugio;
import es.uca.gii.csi16.vengador.data.TipoRefugio;

public class RefugioTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		Data.LoadDriver();
	}

	@Test
	public void testConstructor() throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		try
		{
			Refugio refugio = new Refugio(1);
			con = Data.Connection();
			
			rs = con.createStatement().executeQuery("SELECT id, id_tipoRefugio, capacidadMax, "
				+ "ocupacionActual, localizacion, valoracion, distanciaBase, tiempoEvacuacion "
				+ "FROM refugio WHERE id = 1");
			rs.next();
			
			Assert.assertEquals(refugio.getId(), rs.getInt("id"));
			Assert.assertEquals(refugio.getTipoRefugio().getId(), rs.getInt("id_tipoRefugio"));
			Assert.assertEquals(refugio.getCapacidadMax(), rs.getInt("capacidadMax"));
			Assert.assertEquals(refugio.getOcupacionActual(), rs.getInt("ocupacionActual"));
			Assert.assertEquals(refugio.getLocalizacion(), rs.getString("localizacion"));
			Assert.assertEquals(refugio.getValoracion(), rs.getInt("valoracion"));
			Assert.assertEquals(refugio.getDistanciaBase(), rs.getInt("distanciaBase"));
			Assert.assertEquals(refugio.getTiempoEvacuacion(), rs.getInt("tiempoEvacuacion"));
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	public void testCreate() throws Exception
	{
		
		Refugio refugio = Refugio.Create(new TipoRefugio(1), 5, 2, "28, -27", 2, 10, 5);
		
		Assert.assertEquals(1, refugio.getTipoRefugio().getId());
		Assert.assertEquals(5, refugio.getCapacidadMax());
		Assert.assertEquals(2, refugio.getOcupacionActual());
		Assert.assertEquals("28, -27", refugio.getLocalizacion());
		Assert.assertEquals(2, refugio.getValoracion());
		Assert.assertEquals(10, refugio.getDistanciaBase());
		Assert.assertEquals(5, refugio.getTiempoEvacuacion());
	}
	
	@Test
	public void testSelect() throws Exception
	{
		ArrayList<Refugio> aRefugio = Refugio.Select(null, null, null, null, new Integer(5), new Integer(100), null);
		
		Iterator<Refugio> it = aRefugio.iterator();
		// El resultado de la búsqueda devuelve dos resultados duplicados, 
		// intencionadamente, con estas condiciones de selección
		while(it.hasNext())
		{
			Refugio refugio = it.next();
			
			Assert.assertEquals(1, refugio.getTipoRefugio().getId());
			Assert.assertEquals(2, refugio.getCapacidadMax());
			Assert.assertEquals(0, refugio.getOcupacionActual());
			Assert.assertEquals("26,28.0", refugio.getLocalizacion());
			Assert.assertEquals(5, refugio.getValoracion());
			Assert.assertEquals(100, refugio.getDistanciaBase());
			Assert.assertEquals(10, refugio.getTiempoEvacuacion());
		}
	}
	
	@Test
	public void testUpdate() throws Exception
	{
		Refugio refugio = new Refugio(1);
		refugio.setTipoRefugio(new TipoRefugio(4));
		refugio.setCapacidadMax(200);
		refugio.setOcupacionActual(100);
		refugio.setLocalizacion("20,-0.5");
		refugio.setValoracion(0);
		refugio.setDistanciaBase(30);
		refugio.setTiempoEvacuacion(10);
		refugio.Update();
		
		refugio = new Refugio(1);
		
		Assert.assertEquals(4, refugio.getTipoRefugio().getId());
		Assert.assertEquals(200, refugio.getCapacidadMax());
		Assert.assertEquals(100, refugio.getOcupacionActual());
		Assert.assertEquals("20,-0.5", refugio.getLocalizacion());
		Assert.assertEquals(0, refugio.getValoracion());
		Assert.assertEquals(30, refugio.getDistanciaBase());
		Assert.assertEquals(10, refugio.getTiempoEvacuacion());
	}
	
	@Test
	public void testDelete() throws Exception
	{
		// Hay que cambiar el Id de refugio cada vez que se realiza una prueba, 
		// ya que se elimina el registro de la BD.
		int iIdRefugioEliminar = 25;
		Refugio refugio = new Refugio(iIdRefugioEliminar);
		Connection con = null;
		ResultSet rs = null;
		try
		{
			refugio.Delete();
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT id FROM refugio WHERE id = " + iIdRefugioEliminar + ";");
			
			Assert.assertEquals(false, rs.next());
			Assert.assertEquals(true, refugio.getIsDeleted());
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
}
