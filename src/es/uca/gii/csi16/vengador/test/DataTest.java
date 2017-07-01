package es.uca.gii.csi16.vengador.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import es.uca.gii.csi16.vengador.data.Data;

public class DataTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}
	
	@Ignore @Test
	public void testTableAccess() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery( "SELECT COUNT(*) FROM Refugio;");
			rs.next();
			int iFilas = rs.getInt(1);
			rs.close();
			rs = con.createStatement().executeQuery( "SELECT * FROM Refugio;");
			int iContador = 0;
			while(rs.next())
			{
				System.out.println(rs.getInt("id") + "||" + rs.getInt("id_tipoRefugio") + "||"
						+ rs.getInt("capacidadMax") + "||" + rs.getInt("ocupacionActual") + "||"
						+ rs.getString("localizacion") + "||" + rs.getInt("valoracion") + "||"
						+ rs.getInt("distanciaBase") + "||" + rs.getInt("tiempoEvacuacion"));
				iContador++;
			}
			Assert.assertEquals(iFilas, iContador);
			Assert.assertEquals(8, rs.getMetaData().getColumnCount());
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	public void testString2Sql() throws Exception 
	{
		Assert.assertEquals("hola", Data.String2Sql("hola", false, false));
		Assert.assertEquals("'hola'", Data.String2Sql("hola", true, false));		
		Assert.assertEquals("%hola%", Data.String2Sql("hola", false, true));
		Assert.assertEquals("'%hola%'", Data.String2Sql("hola", true, true));
		Assert.assertEquals("O''Connell", Data.String2Sql("O'Connell", false, false));
		Assert.assertEquals("'O''Connell'", Data.String2Sql("O'Connell", true, false));
		Assert.assertEquals("%''Smith ''%", Data.String2Sql("'Smith '", false, true));
		Assert.assertEquals("'''Smith '''", Data.String2Sql("'Smith '", true, false));
		Assert.assertEquals("'%''Smith ''%'", Data.String2Sql("'Smith '", true, true));	
	}
	
	@Test
	public void testBoolean2Sql() throws Exception
	{
		Assert.assertEquals(0, Data.Boolean2Sql(false));
		Assert.assertEquals(1, Data.Boolean2Sql(true));
	}
}