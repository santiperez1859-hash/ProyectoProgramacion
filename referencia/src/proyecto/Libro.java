package proyecto;

public class Libro extends Documento {

	private static final long serialVersionUID = 1L;
	private String editorial;

	public Libro(String titulo, String autor, int anio, int valoracion, String editorial) {
		super(titulo, autor, anio, valoracion);
		this.editorial = editorial;
	}

	@Override
	public String getTipo() {
		return "Libro";
	}

	@Override
	public String getDatoExtra() {
		return editorial;
	}

	@Override
	public void setDatoExtra(String datoExtra) {
		this.editorial = datoExtra;
	}
}
