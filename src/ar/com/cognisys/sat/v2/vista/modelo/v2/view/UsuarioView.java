package ar.com.cognisys.sat.v2.vista.modelo.v2.view;

import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IAlertaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.IUsuarioView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.cuenta.ICuentaAsociadaView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.interfaz.rs.IRegimenPadronesView;
import ar.com.cognisys.sat.v2.vista.modelo.v2.view.rs.RegimenPadronesView;

public class UsuarioView implements IUsuarioView {

	private static final long serialVersionUID = 3446704776301177905L;
	private String correo;
	private String cuit;
	private String telefono;
	private String telefono2;
	private boolean newsletter;
	private boolean muestroRS;
	private boolean migrado;
	private CuentaAsociadaView[] cuentasAsociadas;
	private RegimenPadronesView regimenPadrones;
	private String authToken;
	private AlertaView[] alertas;

	@Override
	public String getCorreo() {
		return correo;
	}

	@Override
	public String getCuit() {
		return cuit;
	}

	@Override
	public String getTelefono() {
		return telefono;
	}

	@Override
	public String getTelefono2() {
		return telefono2;
	}

	@Override
	public boolean isNewsletter() {
		return newsletter;
	}

	@Override
	public ICuentaAsociadaView[] getCuentasAsociadas() {
		return cuentasAsociadas;
	}

	@Override
	public IRegimenPadronesView getRegimenPadrones() {
		return regimenPadrones;
	}

	@Override
	public String getAuthToken() {
		return authToken;
	}

	@Override
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

	public void setCuentasAsociadas(CuentaAsociadaView[] cuentasAsociadas) {
		this.cuentasAsociadas = cuentasAsociadas;
	}

	public void setRegimenPadrones(RegimenPadronesView regimenPadrones) {
		this.regimenPadrones = regimenPadrones;
	}

	@Override
	public boolean isMuestroRS() {
		return muestroRS;
	}

	public void setMuestroRS(boolean muestroRS) {
		this.muestroRS = muestroRS;
	}

	@Override
	public IAlertaView[] getAlertas() {
		return alertas;
	}

	@Override
	public boolean isMigrado() {
		return migrado;
	}

	public void setMigrado(boolean migrado) {
		this.migrado = migrado;
	}
}