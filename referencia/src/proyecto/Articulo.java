package proyecto;

public class Articulo extends Documento {

	private static final long serialVersionUID = 1L;
	private String revista;

	public Articulo(String titulo, String autor, int anio, int valoracion, String revista) {
		super(titulo, autor, anio, valoracion);
		this.revista = revista;
	}

	@Override
	public String getTipo() {
		return "Artículo";
	}

	@Override
	public String getDatoExtra() {
		return revista;
	}

	@Override
	public void setDatoExtra(String datoExtra) {
		this.revista = datoExtra;
	}
}
