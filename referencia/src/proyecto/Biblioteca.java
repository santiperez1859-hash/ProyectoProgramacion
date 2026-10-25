package proyecto;

import java.io.Serializable;
import java.util.ArrayList;

public class Biblioteca implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int LIMITE_SEGUNDOS = 30 * 60;
	private ArrayList<Documento> documentos;
	private int segundosUsados;

	public Biblioteca() {
		this.documentos = new ArrayList<>();
		this.segundosUsados = 0;
	}

	public void agregar(Documento d) {
		documentos.add(d);
	}

	public void eliminar(Documento d) {
		documentos.remove(d);
	}

	public ArrayList<Documento> buscar(String tipo, String texto) {
		ArrayList<Documento> resultado = new ArrayList<>();
		for (int i = 0; i < documentos.size(); i++) {
			Documento d = documentos.get(i);
			boolean tipoCoincide = tipo.equals("Todos") || d.getTipo().equals(tipo);
			boolean textoCoincide = d.getTitulo().toLowerCase().contains(texto.toLowerCase());
			if (tipoCoincide && textoCoincide) {
				resultado.add(d);
			}
		}
		return resultado;
	}

	public void sumarSegundo() {
		segundosUsados++;
	}

	public int getSegundosRestantes() {
		return LIMITE_SEGUNDOS - segundosUsados;
	}

	public boolean pruebaTerminada() {
		return segundosUsados >= LIMITE_SEGUNDOS;
	}
}
