package proyecto;

public class Tesis extends Documento {

	private static final long serialVersionUID = 1L;
	private String universidad;

	public Tesis(String titulo, String autor, int anio, int valoracion, String universidad) {
		super(titulo, autor, anio, valoracion);
		this.universidad = universidad;
	}

	@Override
	public String getTipo() {
		return "Tesis";
	}

	@Override
	public String getDatoExtra() {
		return universidad;
	}

	@Override
	public void setDatoExtra(String datoExtra) {
		this.universidad = datoExtra;
	}
}
