package ar.com.cognisys.sat.core.modelo.abstracto;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Dao {

	private Connection connection;
	
	public Dao(Connection connection) {
		this.setConnection(connection);
	}
	
	protected void cerrarRecursos(PreparedStatement ps, ResultSet rs) {
		this.cerrarRecursos(rs);
		this.cerrarRecursos(ps);
	}
	
	protected void cerrarRecursos(CallableStatement cs, ResultSet rs) {
		this.cerrarRecursos(rs);
		this.cerrarRecursos(cs);
	}
	
	protected void cerrarRecursos(CallableStatement cs) {
		if (cs != null)
			try {
				cs.close();
			} catch (SQLException e) {}
	}
	
	protected void cerrarRecursos(PreparedStatement ps) {
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {}
	}
	
	protected void cerrarRecursos(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {}
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}	
}