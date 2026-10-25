# Lección 14 · Guardar y cargar: serialización

Hoy los datos dejan de perderse al cerrar el programa: los guardás en un archivo con serialización, el método que da el profe en la página «Métodos de persistencia». Tiempo estimado: 2 horas.

## 1. La idea: de la memoria al disco

Mientras el programa está abierto, los documentos viven en la memoria, en la lista de la biblioteca. Al cerrar, la memoria se borra. **Serializar** es convertir un objeto en bytes para guardarlo en un archivo; **deserializar** es leerlo y reconstruir el objeto. Guardamos la `Biblioteca` entera de una vez: adentro van la lista y todos sus documentos.

## 2. Marcar las clases como Serializable

Java solo serializa las clases que lo permiten. En `Documento`, el principio queda así:

```java
package proyecto;

import java.io.Serializable;

public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    private String titulo;
```

Hacé lo mismo en `Biblioteca`. `Libro`, `Articulo` y `Tesis` ya son serializables porque heredan de `Documento`; igual sumales la línea del `serialVersionUID`, arriba de su atributo:

```java
public class Libro extends Documento {

    private static final long serialVersionUID = 1L;
    private String editorial;
```

¿Qué es `serialVersionUID`? Un número de versión de la clase. Si no lo ponés, Java calcula uno solo, y cualquier cambio en la clase (por ejemplo, agregar un atributo en la defensa) cambia ese número: el archivo viejo deja de poder leerse y parece que se perdieron los datos. Fijándolo en 1, el archivo se sigue leyendo después de cambios como agregar un atributo.

## 3. La clase Archivo

Creá la clase `Archivo` en `proyecto`. Es el código del profe, adaptado para guardar la biblioteca:

```java
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
```

- `RUTA = "biblioteca.bin"` es una **ruta relativa**: el archivo se crea en la carpeta del proyecto. El profe usa una ruta como `C:\Users\Ivanm\...`, que solo existe en su compu; con la relativa, el programa funciona en cualquier computadora, incluida la del profe cuando corrija.
- `static final` hace de `RUTA` una constante: un valor fijo que no cambia.
- `guardar` abre el archivo para escribir (`FileOutputStream`), le pone encima el convertidor de objetos (`ObjectOutputStream`), escribe la biblioteca con `writeObject` y cierra los dos.
- `cargar` hace lo mismo al revés, con `readObject`. Lo que lee es un `Object` genérico, por eso el cast `(Biblioteca)`.
- Los dos métodos son `static`: se usan sin crear un objeto `Archivo`, por ejemplo `Archivo.guardar(biblioteca)`.
- `catch (IOException | SecurityException e)` atrapa cualquiera de los dos errores con un solo bloque.
- El `catch` de `cargar` cubre la primera vez que abrís el programa: el archivo no existe, salta un `IOException` y devolvemos una biblioteca vacía. Así el programa arranca igual.

## 4. Cargar al abrir

En `principal`, cambiá `new Biblioteca()` por la carga del archivo:

```java
package proyecto;

public class principal {

    public static void main(String[] args) {
        Biblioteca biblioteca = Archivo.cargar();
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.iniciar(biblioteca);
        ventana.setVisible(true);
    }
}
```

## 5. Guardar después de cada cambio

En `VentanaPrincipal`, sumá este método justo antes de `actualizarTabla`:

```java
private void guardarYActualizar() {
    Archivo.guardar(biblioteca);
    actualizarTabla();
}
```

Y en `agregarDocumento`, `modificarDocumento` y `eliminarDocumento`, cambiá la llamada `actualizarTabla();` por `guardarYActualizar();`. Por ejemplo, el final de `agregarDocumento` queda:

```java
biblioteca.agregar(nuevo);
guardarYActualizar();
limpiarFormulario();
```

Guardamos en el momento de cada cambio y no solo al cerrar: si el programa se cierra de golpe, no se pierde nada. El resto de `VentanaPrincipal` queda igual que en la Lección 13.

## 6. Probar

1. Ejecutá, agregá dos documentos y cerrá la ventana.
2. Volvé a ejecutar: tienen que estar.
3. En el Package Explorer, click derecho sobre el proyecto → **Refresh** (F5): aparece `biblioteca.bin`.
4. Cerrá el programa, borrá `biblioteca.bin` y ejecutá: arranca vacío y sin errores.

## 7. Punto de control

Así tiene que quedar `Biblioteca.java` en esta etapa (`Archivo` y `principal` ya están completos arriba):

```java
package proyecto;

import java.io.Serializable;
import java.util.ArrayList;

public class Biblioteca implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Documento> documentos;

    public Biblioteca() {
        this.documentos = new ArrayList<>();
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
}
```

`Documento`, `Libro`, `Articulo` y `Tesis` ya quedan iguales a los de la carpeta [referencia](../referencia/).

## 8. Ejercicios

- [ ] Agregá tres documentos, cerrá, abrí y modificá uno. Cerrá y abrí de nuevo para comprobar que el cambio se guardó.
- [ ] Abrí `biblioteca.bin` con el Bloc de notas: vas a ver símbolos raros y algunos textos. Es el objeto convertido en bytes. No lo edites.
- [ ] Explicá en el cuaderno qué pasaría si guardaras solo al cerrar la ventana.

## 9. Preguntas tipo defensa

1. ¿Qué es serializar? ¿Qué se guarda en `biblioteca.bin`?
2. ¿Para qué sirve `implements Serializable`?
3. ¿Qué es `serialVersionUID` y por qué lo fijamos en 1?
4. ¿Por qué la ruta es `"biblioteca.bin"` y no `C:\Users\...`?
5. ¿Qué pasa la primera vez que se abre el programa, cuando el archivo no existe?
6. ¿Por qué se guarda después de cada cambio y no al cerrar?

**Cuando termines, mandame** `Archivo`, `principal` y tus respuestas.

---

[← Lección 13](leccion-13.md) · [Índice](README.md) · [Lección 15 →](leccion-15.md)
