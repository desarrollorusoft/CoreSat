package ar.com.cognisys.sat.core.modelo.dao.ppc;

import ar.com.cognisys.sat.core.adds.PPCLogger;
import ar.com.cognisys.sat.core.modelo.abstracto.Dao;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.CuotaPPC;
import ar.com.cognisys.sat.core.modelo.comun.planDePago.PlanDePagoAPagar;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorEnBaseException;
import ar.com.cognisys.sat.core.modelo.excepcion.ErrorRecuperacionDatosException;
import ar.com.cognisys.sat.core.modelo.excepcion.ExcepcionControladaError;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryCuotaPPC;
import ar.com.cognisys.sat.core.modelo.factory.planDePago.FactoryPlanDePagoAPagar;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DaoPPC extends Dao {

    private static final String SP_BUSCAR_PLANES = "{call recaudaciones:spl_obtiene_planes( ?, ?)}";
    private static final String SP_RECUPERA_CUOTA_PLAN = "{call recaudaciones:spl_obtiene_cuotas_planes( ? )}";
    private static final String SP_GENERA_COMPROBANTE = "{call recaudaciones:spl_recibo_planes( ? , ? )}";
    private static final String SP_GENERA_COMPROBANTE_CANCELACION = "{call recaudaciones:spl_recibo_cancelacion_plan( ? )}";
    private static final String SP_GENERA_IMPORTE_CANCELACION = "{call recaudaciones:spl_cancelacion_plan( ? )}";
    private static final String SP_BUSCAR_PLANES_CUIT = "{call recaudaciones:spl_obtiene_planes_cuit( ? )}";
    private static final String SQL_BUSCA_PLANES_ASOCIADOS = "SELECT unique(s.n_plan), d_solicitante, c_estado, n_cant_cuotas, up.alias, s.c_cuenta, s.c_sistema \n" +
                                                            "FROM recaudaciones:solicitudes s, recaudaciones:ppc_cab p, web_recau:usuario_cogmvl u, web_recau:usuario_planes up\n" +
                                                            "WHERE s.c_plan = 1\n" +
                                                            "AND p.c_plan = 1\n" +
                                                            "AND p.c_estado IN (1,7)\n" +
                                                            "AND p.n_plan = s.n_plan\n" +
                                                            "AND p.n_plan NOT IN(select n_plan from recaudaciones:acta_plan)\n" +
                                                            "and u.cuit = ? \n" +
                                                            "and up.id_usuario = u.id_usuario\n" +
                                                            "and p.n_plan = up.numero_plan\n" +
                                                            "and up.flag_completo = 0";
    private static final String SQL_BUSCA_PLAN = "SELECT unique(s.n_plan), d_solicitante, c_estado, n_cant_cuotas, s.c_cuenta, s.c_sistema \n" +
                                                "FROM recaudaciones:solicitudes s, recaudaciones:ppc_cab p\n" +
                                                "WHERE s.c_plan = 1\n" +
                                                "AND p.c_plan = 1\n" +
                                                "AND p.c_estado IN (1,7)\n" +
                                                "AND p.n_plan = s.n_plan\n" +
                                                "AND p.n_plan NOT IN(select n_plan from recaudaciones:acta_plan)\n" +
                                                "and p.n_plan = ? ";
    private static final String INSERT_ASOCIAR_PLAN = "INSERT INTO web_recau:usuario_planes(numero_plan, id_usuario, alias) VALUES ( ? , ? , ? )";
    private static final String DELETE_DESASOCIAR_PLAN = "DELETE FROM web_recau:usuario_planes\n" +
                                                        "WHERE numero_plan = ? \n" +
                                                        "AND id_usuario = ? ";
    private static final String UPDATE_ACTUALIZAR_ALIAS = "UPDATE web_recau:usuario_planes\n" +
                                                        "SET alias = ? \n" +
                                                        "WHERE numero_plan = ? \n" +
                                                        "AND id_usuario = ? ";
    private static final String SQL_VALIDAR_EXISTENCIA = "SELECT 1 \n " +
                                                        "FROM web_recau:usuario_planes\n" +
                                                        "WHERE numero_plan = ? \n" +
                                                        "AND id_usuario = ? ";
    private static final String SQL_BUSCAR_CUENTA = "SELECT c_cuenta, c_sistema FROM solicitudes WHERE n_plan = ?";

    public DaoPPC(Connection connection) {
        super(connection);
    }

    public List<PlanDePagoAPagar> recuperarPlanes(Integer sistema, Integer cuenta) throws ErrorRecuperacionDatosException {

        PPCLogger.getLogger().info("Se procede a buscar los planes para la CUENTA ["+cuenta+"]");
        List<PlanDePagoAPagar> lista = new ArrayList<PlanDePagoAPagar>();
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            cs = this.getConnection().prepareCall( SP_BUSCAR_PLANES );
            cs.setInt(1, cuenta);
            cs.setInt(2, sistema);

            cs.execute();

            PPCLogger.getLogger().info("SP OK: call recaudaciones:spl_obtiene_planes("+cuenta+","+sistema+")");

            rs = cs.getResultSet();

            while (rs.next()) {
                PlanDePagoAPagar p = FactoryPlanDePagoAPagar.getInstance(rs.getInt("n_plan"),
                                                                        rs.getString("d_solicitante"),
                                                                        rs.getString("c_estado"),
                                                                        rs.getInt("n_cant_cuotas"),
                                                                        this.recuperarImporteCancelacion( rs.getInt("n_plan") ),
                                                                        cuenta,
                                                                        sistema);
                lista.add(p);
                PPCLogger.getLogger().info(p);
            }
        } catch (Exception e) {
            PPCLogger.getLogger().error("SP FALLO: call recaudaciones:spl_obtiene_planes("+cuenta+","+sistema+")", e);
            throw new ErrorRecuperacionDatosException(e);
        } finally {
            super.cerrarRecursos(cs, rs);
        }

        return lista;
    }

    public List<PlanDePagoAPagar> recuperarPlanesUsuario(String cuit) throws ErrorRecuperacionDatosException {
        List<PlanDePagoAPagar> lista = new ArrayList<PlanDePagoAPagar>();

        lista.addAll( this.recuperarPlanesContribuyente( cuit ) );

        for (PlanDePagoAPagar planAsociado: this.recuperarPlanesAsociados( cuit )) {
            boolean esta = false;
            for (PlanDePagoAPagar planContribuyente: lista) {
                if (planContribuyente.sos( planAsociado )) {
                    esta = true;
                    planContribuyente.setAlias( planAsociado.getAlias() );
                    break;
                }
            }

            if ( !esta )
                lista.add( planAsociado );
        }

        return lista;
    }

    private List<PlanDePagoAPagar> recuperarPlanesContribuyente(String cuit) throws ErrorRecuperacionDatosException {

        PPCLogger.getLogger().info("Se procede a buscar los planes del contribuyente, para la CUIT ["+cuit+"]");
        List<PlanDePagoAPagar> lista = new ArrayList<PlanDePagoAPagar>();
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            cs = this.getConnection().prepareCall( SP_BUSCAR_PLANES_CUIT );
            cs.setString(1, cuit);
            cs.execute();

            PPCLogger.getLogger().info("SP OK: call recaudaciones:spl_obtiene_planes_cuit('"+cuit+"')");

            rs = cs.getResultSet();

            while (rs.next()) {
                int numeroPlan = rs.getInt("n_plan");
                PlanDePagoAPagar p = FactoryPlanDePagoAPagar.getInstance(numeroPlan,
                                                                        rs.getString("d_solicitante"),
                                                                        rs.getString("c_estado"),
                                                                        rs.getInt("n_cant_cuotas"),
                                                                        this.recuperarImporteCancelacion( numeroPlan ));
                this.cargarCuentaPlan( p );
                lista.add(p);
                PPCLogger.getLogger().info(p);
            }
        } catch (Exception e) {
            PPCLogger.getLogger().error("SP FALLO: call recaudaciones:spl_obtiene_planes_cuit('"+cuit+"')", e);
            throw new ErrorRecuperacionDatosException(e);
        } finally {
            super.cerrarRecursos(cs, rs);
        }

        return lista;
    }

    private List<PlanDePagoAPagar> recuperarPlanesAsociados(String cuit) throws ErrorRecuperacionDatosException {

        PPCLogger.getLogger().info("Se procede a buscar los planes asociados, para la CUIT ["+cuit+"]");
        List<PlanDePagoAPagar> lista = new ArrayList<PlanDePagoAPagar>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement( SQL_BUSCA_PLANES_ASOCIADOS );
            ps.setString(1, cuit);

            rs = ps.executeQuery();

            PPCLogger.getLogger().info("QUERY OK");

            while (rs.next()) {
                PlanDePagoAPagar p = FactoryPlanDePagoAPagar.getInstance(rs.getInt("n_plan"),
                                                                        rs.getString("d_solicitante"),
                                                                        rs.getString("c_estado"),
                                                                        rs.getInt("n_cant_cuotas"),
                                                                        this.recuperarImporteCancelacion( rs.getInt("n_plan") ),
                                                                        rs.getInt("c_cuenta"),
                                                                        rs.getInt("c_sistema"),
                                                                        rs.getString("alias"),
                                                                        true);
                lista.add(p);
                PPCLogger.getLogger().info(p);
            }
        } catch (Exception e) {
            PPCLogger.getLogger().error("QUERY FALLO: ["+ SQL_BUSCA_PLANES_ASOCIADOS.replace("?", "'"+cuit+"'") +"]", e);
            throw new ErrorRecuperacionDatosException(e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }

        return lista;
    }

    public PlanDePagoAPagar recuperarPlan(Integer numeroPlan) throws ErrorRecuperacionDatosException {

        PPCLogger.getLogger().info("Se procede a buscar el planes con numero ["+numeroPlan.toString()+"]");
        PlanDePagoAPagar p = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement( SQL_BUSCA_PLAN );
            ps.setInt(1, numeroPlan);

            rs = ps.executeQuery();

            PPCLogger.getLogger().info("QUERY OK");

            if (rs.next()) {
                p = FactoryPlanDePagoAPagar.getInstance(rs.getInt("n_plan"),
                                                        rs.getString("d_solicitante"),
                                                        rs.getString("c_estado"),
                                                        rs.getInt("n_cant_cuotas"),
                                                        this.recuperarImporteCancelacion( rs.getInt("n_plan") ),
                                                        rs.getInt("c_cuenta"),
                                                        rs.getInt("c_sistema"));
                PPCLogger.getLogger().info(p);
            }
        } catch (Exception e) {
            PPCLogger.getLogger().error("QUERY FALLO: ["+ SQL_BUSCA_PLAN.replace("?", numeroPlan.toString()) +"]", e);
            throw new ErrorRecuperacionDatosException(e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }

        return p;
    }

    public List<CuotaPPC> recuperarCuotas(Integer nroPlan) throws ExcepcionControladaError {

        PPCLogger.getLogger().info("Se procede a buscar las cuotas para el PLAN ["+nroPlan+"]");
        List<CuotaPPC> cuotas = new ArrayList<CuotaPPC>();
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            cs = this.getConnection().prepareCall( SP_RECUPERA_CUOTA_PLAN );
            cs.setInt(1, nroPlan);
            cs.execute();

            PPCLogger.getLogger().info("SP OK: call recaudaciones:spl_obtiene_cuotas_planes("+nroPlan+")");

            rs = cs.getResultSet();

            while (rs.next()) {
                CuotaPPC c = FactoryCuotaPPC.generar(rs.getDate("f_vencimiento"),
                                                    rs.getFloat("i_capital"),
                                                    rs.getFloat("i_recargo"),
                                                    rs.getFloat("i_multa"),
                                                    rs.getFloat("i_interes"),
                                                    rs.getFloat("i_total"),
                                                    rs.getInt("n_cuota"));
                cuotas.add( c );
                PPCLogger.getLogger().info(c.toString());
            }
        } catch (Exception e) {
            PPCLogger.getLogger().info("SP FALLO: call recaudaciones:spl_obtiene_cuotas_planes("+nroPlan+")", e);
            throw new ErrorEnBaseException("Error recuperando las cuotas", e.getCause());
        } finally {
            super.cerrarRecursos(cs, rs);
        }

        return cuotas;
    }

    public String generarComprobante(Integer numeroPlan, Integer numeroCuota) throws ExcepcionControladaError {

        PPCLogger.getLogger().info("Se procede a generar el comprobante para el PLAN ["+numeroPlan+"] CUOTA ["+numeroCuota+"]");
        String comprobante = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            cs = this.getConnection().prepareCall( SP_GENERA_COMPROBANTE );
            cs.setInt(1, numeroPlan);
            cs.setInt(2, numeroCuota);
            cs.execute();

            PPCLogger.getLogger().info("SP OK: call recaudaciones:spl_recibo_planes("+numeroPlan+", "+numeroCuota+")");

            rs = cs.getResultSet();

            if (rs.next()) {
                comprobante = rs.getString("n_comprob");
                PPCLogger.getLogger().info("COMPROBANTE ["+ comprobante.trim() +"]");
            }
        } catch (Exception ex) {
            PPCLogger.getLogger().error("SP FALLO: call recaudaciones:spl_recibo_planes("+numeroPlan+", "+numeroCuota+")", ex);
            throw new ErrorRecuperacionDatosException(ex);
        } finally {
            super.cerrarRecursos(cs, rs);
        }

        return comprobante;
    }

    public String generarComprobanteCancelacion(Integer numeroPlan) throws ExcepcionControladaError {

        PPCLogger.getLogger().info("Se procede a generar el comprobante de Cancelacion, para el PLAN ["+numeroPlan+"]");
        String comprobante = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            cs = this.getConnection().prepareCall( SP_GENERA_COMPROBANTE_CANCELACION );
            cs.setInt(1, numeroPlan);
            cs.execute();

            PPCLogger.getLogger().info("SP OK: call recaudaciones:spl_recibo_cancelacion_plan("+numeroPlan+")");

            rs = cs.getResultSet();

            if (rs.next()) {
                comprobante = rs.getString("ln_comprob");
                PPCLogger.getLogger().info("COMPROBANTE ["+ comprobante.trim() +"]");
            }
        } catch (Exception ex) {
            PPCLogger.getLogger().error("SP FALLO: call recaudaciones:spl_recibo_cancelacion_plan("+numeroPlan+")", ex);
            throw new ErrorRecuperacionDatosException(ex);
        } finally {
            super.cerrarRecursos(cs, rs);
        }

        return comprobante;
    }

    public Float recuperarImporteCancelacion(Integer numeroPlan) throws ExcepcionControladaError {

        PPCLogger.getLogger().info("Se procede a recuperar el IMPORTE DE CANCELACION, para el PLAN ["+numeroPlan+"]");
        Float total = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            cs = this.getConnection().prepareCall( SP_GENERA_IMPORTE_CANCELACION );
            cs.setInt(1, numeroPlan);
            cs.execute();

            PPCLogger.getLogger().info("SP OK: call recaudaciones:spl_cancelacion_plan("+numeroPlan+")");

            rs = cs.getResultSet();

            if (rs.next()) {
                total = rs.getFloat( "li_total" );
                PPCLogger.getLogger().info("IMPORTE DE CANCELACION ["+ total +"]");
            }
        } catch (Exception ex) {
            PPCLogger.getLogger().error("SP FALLO: call recaudaciones:spl_cancelacion_plan("+numeroPlan+")", ex);
            throw new ErrorRecuperacionDatosException(ex);
        } finally {
            super.cerrarRecursos(cs, rs);
        }

        return total;
    }

    public void asociarPlan(Integer numeroPlan, Integer idUsuario, String alias) throws ErrorRecuperacionDatosException {
        PPCLogger.getLogger().info("Se procede a asociar el Plan al usuario, para el PLAN ["+numeroPlan+"] Usuario ["+idUsuario.toString()+"]");
        PreparedStatement ps = null;

        try {
            ps = this.getConnection().prepareStatement( INSERT_ASOCIAR_PLAN );
            ps.setInt(1, numeroPlan);
            ps.setInt(2, idUsuario);
            ps.setString(3, alias);
            ps.executeUpdate();

            PPCLogger.getLogger().info("Insert Asociacion OK");
        } catch (Exception ex) {
            PPCLogger.getLogger().error("FALLO la Asociacion: ["+ INSERT_ASOCIAR_PLAN +"]", ex);
            throw new ErrorRecuperacionDatosException(ex);
        } finally {
            super.cerrarRecursos(ps);
        }
    }

    public void deasociarPlan(Integer numeroPlan, Integer idUsuario) throws ErrorRecuperacionDatosException {
        PPCLogger.getLogger().info("Se procede a desasociar el Plan del usuario, para el PLAN ["+numeroPlan+"] Usuario ["+idUsuario.toString()+"]");
        PreparedStatement ps = null;

        try {
            ps = this.getConnection().prepareStatement( DELETE_DESASOCIAR_PLAN );
            ps.setInt(1, numeroPlan);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();

            PPCLogger.getLogger().info("Delete Desasociacion OK");
        } catch (Exception ex) {
            PPCLogger.getLogger().error("FALLO la desasociacion: ["+ DELETE_DESASOCIAR_PLAN +"]", ex);
            throw new ErrorRecuperacionDatosException(ex);
        } finally {
            super.cerrarRecursos(ps);
        }
    }

    public void actualizarAlias(Integer numeroPlan, Integer idUsuario, String alias) throws ErrorRecuperacionDatosException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement( UPDATE_ACTUALIZAR_ALIAS );
            ps.setString(1, alias);
            ps.setInt(2, numeroPlan);
            ps.setInt(3, idUsuario);
            ps.executeUpdate();

            PPCLogger.getLogger().info("Update actualizar alias OK");
        } catch (Exception ex) {
            PPCLogger.getLogger().error("FALLO la actualizacion de alias: ["+ UPDATE_ACTUALIZAR_ALIAS +"]", ex);
            throw new ErrorRecuperacionDatosException(ex);
        } finally {
            super.cerrarRecursos(ps, rs);
        }
    }

    public boolean estaAsociado(Integer numeroPlan, Integer idUsuario) throws ErrorRecuperacionDatosException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareStatement( SQL_VALIDAR_EXISTENCIA );
            ps.setInt(1, numeroPlan);
            ps.setInt(2, idUsuario);
            rs = ps.executeQuery();

            return (rs.next());
        } catch (Exception ex) {
            PPCLogger.getLogger().error("SP Consulta: ["+SQL_VALIDAR_EXISTENCIA+"]", ex);
            throw new ErrorRecuperacionDatosException(ex);
        } finally {
            super.cerrarRecursos(ps, rs);
        }
    }

    private void cargarCuentaPlan(PlanDePagoAPagar p) throws ErrorRecuperacionDatosException {
        PPCLogger.getLogger().info("Se procede a buscar la cuenta para el PLAN ["+p.getNroPlan()+"]");
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.getConnection().prepareCall( SQL_BUSCAR_CUENTA );
            ps.setInt(1, p.getNroPlan());
            rs = ps.executeQuery();

            if (rs.next()) {
                p.setCuenta( rs.getInt("c_cuenta") );
                p.setSistema( rs.getInt("c_sistema") );
            }
        } catch (Exception e) {
            PPCLogger.getLogger().error("FALLO la busqueda de la cuenta: PLAN ('"+p.getNroPlan()+"')", e);
            throw new ErrorRecuperacionDatosException(e);
        } finally {
            super.cerrarRecursos(ps, rs);
        }
    }
}