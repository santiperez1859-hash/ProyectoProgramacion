package proyecto;

public class principal {

	public static void main(String[] args) {
		Biblioteca biblioteca = Archivo.cargar();
		VentanaPrincipal ventana = new VentanaPrincipal();
		ventana.iniciar(biblioteca);
		ventana.setVisible(true);
	}
}
