package ar.com.cognisys.sat.core.modelo.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.cognisys.sat.core.modelo.abstracto.CuentaAutomotor;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.TasaAbl;
import ar.com.cognisys.sat.core.modelo.comun.TasaProteccionCiudadana;
import ar.com.cognisys.sat.core.modelo.comun.cuenta.CuentaABL;
import ar.com.cognisys.sat.core.modelo.comun.recibos.Recibo;
import ar.com.cognisys.sat.core.modelo.comun.recibos.abl.InformacionPrincipalReciboAbl;
import ar.com.cognisys.sat.core.modelo.enums.QueriesInformix;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.excepcion.ReciboInexistenteException;
import ar.com.cognisys.sat.core.modelo.factory.FactoryTasaAbl;
import ar.com.cognisys.sat.core.modelo.factory.FactoryTasaProteccionCiudadana;
import ar.com.cognisys.sat.core.modelo.factory.recibo.FactoryInfomacionPrincipalReciboAbl;
import ar.com.cognisys.sat.core.modelo.factory.recibo.FactoryRecibo;

public class DaoRecibos extends Dao {

	public DaoRecibos(Connection connection) {
		super(connection);
	}

	public List<Recibo> buscarListaRecibosVehiculos(CuentaAutomotor cuenta) throws ExcepcionControladaError {

		CallableStatement cs = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Recibo> listaRecibos = new ArrayList<Recibo>();
		
		String query = "Select V_ano, V_cuota, V_tasa, V_seg_vto \n"
				     + "From tmp_recibos_cogmvl \n"
				     + "Order By f_seg_vto";
				
		try {
			cs = this.getConnection().prepareCall(QueriesInformix.BUSQUEDA_RECIBOS_1.getQuery());
			cs.setInt(1, cuenta.getNumero());
			cs.execute();	
			
			pstmt = this.getConnection().prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				listaRecibos.add( FactoryRecibo.generarIntanciaCompleta(rs.getInt("V_ano"), 
																		rs.getInt("V_cuota"), 
																		rs.getInt("V_tasa"), 
																		rs.getDate("V_seg_vto")) );
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return listaRecibos;
	}
	
	public InformacionPrincipalReciboAbl recuperarInformacionPrincipal(CuentaABL cuenta, Recibo recibo) throws ExcepcionControladaError {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InformacionPrincipalReciboAbl info = new InformacionPrincipalReciboAbl();
		
		String query = "Select * \n "
				     + "From externos \n "
				     + "Where c_cuenta = ? \n ";
				     		
		try {
			pstmt = this.getConnection().prepareStatement(query);
			pstmt.setInt(1, cuenta.getNumero());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				info = FactoryInfomacionPrincipalReciboAbl.generarInstanciaCompleta(rs.getString("c_cuenta"), 
																					rs.getString("c_eximido"), 
																					rs.getString("c_postal"), 
																					rs.getString("c_seccion"), 
																					rs.getString("d_apellido"), 
																					rs.getString("d_calle_pro"), 
																					rs.getString("d_calle_tit"), 
																					rs.getString("d_lmanz"), 
																					rs.getString("d_loc_tit"), 
																					rs.getString("d_lparc"), 
																					rs.getString("d_lufun"), 
																					rs.getString("d_nombre"),
																					null,
	//																				rs.getString("f_proceso"), 
																					rs.getString("n_cate"), 
																					rs.getString("n_circ"), 
																					rs.getString("n_fraccion"), 
																					rs.getString("n_frente"), 
																				    rs.getString("n_manz"), 
																				    rs.getString("n_nro_pro"), 
																				    rs.getString("n_nro_tit"), 
																				    rs.getString("n_parc"), 
																				    rs.getString("n_partida"), 
																				    rs.getString("n_poli"), 
																				    rs.getString("n_ufun"), 
																				    rs.getString("n_valua"), 
																				    rs.getString("n_verificador"), 
																				    rs.getString("s_deuda"),
																				    rs.getString("n_reparto"), 
																				    rs.getString("n_orden"));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return info;
	}
	
	public Recibo recuperarRecibo(Integer anio, Integer cuota) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Recibo recibo = null;
		Integer tasa = 100;
		
		String query = "SELECT f_prim_vto \n"
					 + "FROM tmp_recibos_cogmvl ";
		
		try {
			cs = this.getConnection().prepareCall(QueriesInformix.BUSQUEDA_RECIBOS_2.getQuery());
			cs.setInt(1, anio);
			cs.setInt(2, cuota);
			cs.execute();
			
			pstmt = this.getConnection().prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				recibo = FactoryRecibo.generarIntanciaCompleta(anio, 
															   cuota, 
															   tasa, 
															   rs.getDate("f_prim_vto"));
			} else {
				throw new ReciboInexistenteException();
			}
		} catch(ReciboInexistenteException e) {
			throw e;
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return recibo;
	}
	
	public TasaAbl recuperarDatosTasaAbl(CuentaABL cuenta, Recibo recibo, String numeroComprobante) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TasaAbl tasa = new TasaAbl();
		
		String query = "Select V_pago, V_prim_vto, V_seg_vto, V_deuda, V_rec_prim_vto, V_rec_seg_vto \n "
				     + "From tmp_recibos_cogmvl ";
		
		try {
			cs = this.getConnection().prepareCall(QueriesInformix.BUSQUEDA_RECIBOS_3.getQuery());
			cs.setInt(1, cuenta.getNumero());
			cs.setInt(2, recibo.getAnio());
			cs.setInt(3, recibo.getCuota());
			cs.setBigDecimal(4, new BigDecimal(numeroComprobante));
			cs.execute();
			
			pstmt = this.getConnection().prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				tasa = FactoryTasaAbl.generarInstanciaCompleta(rs.getString("V_pago"), 
															   rs.getString("V_prim_vto"), 
															   rs.getString("V_seg_vto"), 
															   rs.getString("V_deuda"), 
															   rs.getString("V_deuda"), 
															   rs.getString("V_rec_prim_vto"), 
															   rs.getString("V_rec_seg_vto"), 
															   recibo.getAnio().toString(), 
															   numeroComprobante, 
															   recibo.getCuota().toString()
															   );
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return tasa;
	}
	
	public TasaProteccionCiudadana recuperarDatosTasaProteccionCiudadana(CuentaABL cuenta, Recibo recibo) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TasaProteccionCiudadana tasa = new TasaProteccionCiudadana();
		
		String query = "Select V_deuda, V_rec_prim_vto, V_rec_seg_vto \n "
				     + "From tmp_recibos_cogmvl ";
		
		try {
			cs = this.getConnection().prepareCall(QueriesInformix.BUSQUEDA_RECIBOS_4.getQuery());
			cs.setInt(1, cuenta.getNumero());
			cs.setInt(2, recibo.getAnio());
			cs.setInt(3, recibo.getCuota());
			cs.execute();
			
			pstmt = this.getConnection().prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				tasa = FactoryTasaProteccionCiudadana.generarInstanciaCompleta(rs.getString("V_deuda"), 
																			   rs.getString("V_deuda"), 
																			   rs.getString("V_rec_prim_vto"), 
																			   rs.getString("V_rec_seg_vto"));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return tasa;
	}
	
	public InformacionPrincipalReciboAbl recuperarInformacionPrincipalVencidas(Integer numeroCuenta) throws ExcepcionControladaError {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InformacionPrincipalReciboAbl info = new InformacionPrincipalReciboAbl();
		
		String query = "Select * \n "
				     + "From externos \n "
				     + "Where c_cuenta = ? ";
		
		try {
			pstmt = this.getConnection().prepareStatement(query);
			pstmt.setInt(1, numeroCuenta);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				String fechaProceso = "0000-00-00";
				
				try {
					fechaProceso = rs.getString("f_proceso");
				} catch (Exception e) {
				}
				
				info = FactoryInfomacionPrincipalReciboAbl.generarInstanciaCompleta(rs.getString("c_cuenta"), 
																					rs.getString("c_eximido"), 
																					rs.getString("c_postal"), 
																					rs.getString("c_seccion"), 
																					rs.getString("d_apellido"), 
																					rs.getString("d_calle_pro"), 
																					rs.getString("d_calle_tit"), 
																					rs.getString("d_lmanz"), 
																					rs.getString("d_loc_tit"), 
																					rs.getString("d_lparc"), 
																					rs.getString("d_lufun"), 
																					rs.getString("d_nombre"), 
																					fechaProceso, 
																					rs.getString("n_cate"), 
																					rs.getString("n_circ"), 
																					rs.getString("n_fraccion"), 
																					rs.getString("n_frente"), 
																				    rs.getString("n_manz"), 
																				    rs.getString("n_nro_pro"), 
																				    rs.getString("n_nro_tit"), 
																				    rs.getString("n_parc"), 
																				    rs.getString("n_partida"), 
																				    rs.getString("n_poli"), 
																				    rs.getString("n_ufun"), 
																				    rs.getString("n_valua"), 
																				    rs.getString("n_verificador"), 
																				    rs.getString("s_deuda"),
																				    rs.getString("n_reparto"), 
																				    rs.getString("n_orden"));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return info;
	}
	
	public TasaAbl recuperarDatosTasaAblVencidas(Integer numeroCuenta) throws ExcepcionControladaError {
		
		CallableStatement cs = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TasaAbl tasa = new TasaAbl();
		
		String query = "Select V_pago, V_prim_vto, V_seg_vto, V_deuda, V_rec_prim_vto, V_rec_seg_vto, V_ano, V_cuota, V_comprob \n "
				     + "From tmp_recibos_cogmvl ";
		
		try {
			cs = this.getConnection().prepareCall(QueriesInformix.BUSQUEDA_RECIBOS_5.getQuery());
			cs.setInt(1, numeroCuenta);
			cs.execute();
			
			pstmt = this.getConnection().prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				tasa = FactoryTasaAbl.generarInstanciaCompleta(rs.getString("V_pago"), 
															   rs.getString("V_prim_vto"), 
															   rs.getString("V_seg_vto"), 
															   rs.getString("V_deuda"), 
															   rs.getString("V_deuda"), 
															   rs.getString("V_rec_prim_vto"), 
															   rs.getString("V_rec_seg_vto"), 
															   rs.getString("V_ano"), 
															   rs.getString("V_comprob"), 
															   rs.getString("V_cuota"));
			}
		} catch (Exception ex) {
			throw new ErrorRecuperacionDatosException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return tasa;
	}
}