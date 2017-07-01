package es.uca.gii.csi16.vengador.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.uca.gii.csi16.vengador.data.Data;
import es.uca.gii.csi16.vengador.data.TipoRefugio;

public class TipoRefugioTest {

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
			TipoRefugio refugio = new TipoRefugio(1);
			con = Data.Connection();
			
			rs = con.createStatement().executeQuery("SELECT id, nombre FROM tiporefugio "
				+ " WHERE id = 1");
			rs.next();
			
			Assert.assertEquals(refugio.getId(), rs.getInt("id"));
			Assert.assertEquals(refugio.getNombre(), rs.getString("nombre"));
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	public void testSelect() throws Exception
	{
		ArrayList<TipoRefugio> aTipoRefugio = TipoRefugio.Select();
		Iterator<TipoRefugio> it = aTipoRefugio.iterator();
		String[] asTiposRefugio = {"Disponible", "Ocupado", "Lleno", "Destruido"};
		int i = 0;
		TipoRefugio tipoRefugio;
		
		while(it.hasNext())
		{
			tipoRefugio = it.next();
			Assert.assertEquals(i + 1, tipoRefugio.getId());
			Assert.assertEquals(asTiposRefugio[i], tipoRefugio.getNombre());
			i++;
		}
	}

}
