package proyecto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Archivo {

	private static final String RUTA = "biblioteca.bin";

	public static void guardar(Biblioteca biblioteca) {
		try {
			FileOutputStream flujoSalidaArchivo = new FileOutputStream(RUTA);
			ObjectOutputStream flujoSalidaObjeto = new ObjectOutputStream(flujoSalidaArchivo);
			flujoSalidaObjeto.writeObject(biblioteca);
			flujoSalidaObjeto.close();
			flujoSalidaArchivo.close();
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public static Biblioteca cargar() {
		try {
			FileInputStream flujoEntradaArchivo = new FileInputStream(RUTA);
			ObjectInputStream flujoEntradaObjeto = new ObjectInputStream(flujoEntradaArchivo);
			Biblioteca biblioteca = (Biblioteca) flujoEntradaObjeto.readObject();
			flujoEntradaObjeto.close();
			flujoEntradaArchivo.close();
			return biblioteca;
		} catch (IOException | ClassNotFoundException e) {
			return new Biblioteca();
		}
	}
}
