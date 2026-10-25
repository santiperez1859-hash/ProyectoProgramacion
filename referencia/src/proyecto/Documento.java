package proyecto;

import java.io.Serializable;

public class Documento implements Serializable {

	private static final long serialVersionUID = 1L;
	private String titulo;
	private String autor;
	private int anio;
	private int valoracion;

	public Documento(String titulo, String autor, int anio, int valoracion) {
		this.titulo = titulo;
		this.autor = autor;
		this.anio = anio;
		this.valoracion = valoracion;
	}

	public String getTipo() {
		return "Documento";
	}

	public String getDatoExtra() {
		return "";
	}

	public void setDatoExtra(String datoExtra) {
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
}
