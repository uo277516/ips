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
	public static String COMPID_ATL = "select a.dni, a.nombre, a.sexo, a.f_nac"
			+ " from atleta a, inscripcion i"
			+ " where a.dni = i.dni_a"
			+ " and i.id_c = ?";
	
	public List<AtletaDto> getAtletas() throws SQLException
	{
		return getAllAtletas();
	}
	
	
	
	
	private List<AtletaDto> getAllAtletas() throws SQLException
	{
		List<AtletaDto> listaAtletas = new ArrayList<AtletaDto>();

        // Conexi�n a la base de datos
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(sql1);
            rs = pst.executeQuery();

            // A�adimos los pedidos a la lista
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
	
	public List<AtletaDto> getAletasDeUnaCompeticion(int i) throws SQLException{
		List<AtletaDto> atletas = new ArrayList<AtletaDto>();
		
		Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            c = BaseDatos.getConnection();
            pst = c.prepareStatement(COMPID_ATL);
            rs = pst.executeQuery();

            atletas = DtoAssembler.toAtletaDtoList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
        return atletas;
	}
}
