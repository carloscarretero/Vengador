package es.uca.gii.csi16.vengador.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import es.uca.gii.csi16.vengador.util.Config;

/**
 * @author Carlos Carretero Aguilar
 * @author Manuel Jesús López Jiménez
 */

public class Data {
	
	/**
	 * Método String2Sql para transformar un string a un formato que pueda soportar SQL.
	 * @param s
	 * @param bAddQuotes
	 * @param bAddWildcards
	 * @return String
	 */
	public static String String2Sql(String s, boolean bAddQuotes, boolean bAddWildcards)
	{
		s = s.replaceAll("'", "''");
		if(bAddWildcards)
			s = "%" + s + "%";
		if(bAddQuotes)
			s = "'" + s + "'";
		return s;
	}
	
	/**
	 * Método Boolean2Sql que devuelve 0 si b es falso y 1 si b es verdadero.
	 * @param b
	 * @return int
	 */
	public static int Boolean2Sql(boolean b)
	{
		return b ? 1 : 0;
	}
	
	/**
	 * Funcion que devuelve el ultimo ID insertado en la BD en la conexion actual
	 * @param con
	 * @return LastId
	 * @throws Exception
	 */
	public static int LastId(Connection con) throws Exception
	{
		ResultSet rs = null;
		try{
			Properties properties = Config.Properties(getPropertiesUrl());
			rs = con.createStatement().executeQuery(properties.getProperty("jdbc.lastIdSentence"));
			rs.next();
			return rs.getInt(1);
		}
		catch(SQLException ee) {throw ee;}
		finally {
			if (rs != null) rs.close();
		}
	}
	
	/**
	 * Método Where que recibe los tipos y valores de los campos con los que se
	 * hará la cláusula Where de la consulta Select o Update
	 * @param asCampos
	 * @param atTipos
	 * @param aoValores
	 * @return
	 */
	protected static String Where(String[] asCampos, int[] atTipos, Object[] aoValores)
	{
		String sWhere = "";
		
		for(int i = 0; i < asCampos.length; i++)
		{
			if(aoValores[i] != null)
			{
				sWhere += " " + asCampos[i] + " ";
				switch(atTipos[i])
				{
					case Types.INTEGER: sWhere += " = " + aoValores[i];
										break;
					case Types.VARCHAR: sWhere += (aoValores[i].toString().contains("%") ||
											aoValores.toString().contains("?")) ?
											" = " + Data.String2Sql(aoValores[i].toString(), true, true)
											: " LIKE " + Data.String2Sql(aoValores[i].toString(), true, true);
										break;
				}
				sWhere += " AND";
			}
		}
		
		if(!sWhere.isEmpty())
			sWhere = "WHERE" + sWhere.substring(0, sWhere.length()-4);
		
		return sWhere;
	}
	
    public static String getPropertiesUrl() { return "./db.properties"; }
    public static Connection Connection() throws Exception {
        try {
            Properties properties = Config.Properties(getPropertiesUrl());
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
	}
    
    public static void LoadDriver() 
        throws InstantiationException, IllegalAccessException, 
        ClassNotFoundException, IOException {
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
}