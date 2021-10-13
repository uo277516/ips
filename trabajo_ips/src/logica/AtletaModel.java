package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.BaseDatos;
import util.DtoAssembler;

public class AtletaModel 
{
	
	public static String sql1 = "select * from atleta";
	
	public List<AtletaDto> getAtletas() throws SQLException
	{
		return getAllAtletas();
	}
	
	
	
	
	private List<AtletaDto> getAllAtletas() throws SQLException
	{
		List<AtletaDto> listaAtletas = new ArrayList<AtletaDto>();

        // Conexión a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql1);
            rs = pst.executeQuery();

            // Añadimos los pedidos a la lista
            listaAtletas = DtoAssembler.toAtletaDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }

//        for (AtletaDto atletaDto : listaAtletas) {
//			System.out.println(atletaDto.getDni() + " " + atletaDto.getF_nac()
//			);
//		}
        return listaAtletas;
	}
	
	
	
}
