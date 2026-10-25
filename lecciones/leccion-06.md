# Lección 6 · Herencia: Libro, Articulo y Tesis

Hoy creás los tres tipos de documento del parcial. Todos comparten título, autor, año y valoración, pero cada uno tiene un dato propio. En lugar de repetir código, usás **herencia**. Tiempo estimado: 2 horas.

## 1. La idea: «es un»

Un libro **es un** documento; un artículo **es un** documento. La herencia dice exactamente eso: `Libro` recibe todo lo que tiene `Documento` (atributos y métodos) y le suma lo suyo.

| Clase | Hereda de Documento | Le suma |
| --- | --- | --- |
| `Libro` | título, autor, año, valoración | `editorial` |
| `Articulo` | lo mismo | `revista`, donde se publicó |
| `Tesis` | lo mismo | `universidad` |

A `Documento` se la llama clase **padre** (o superclase) y a las otras, clases **hijas**. Es lo mismo que hizo el profe en el simulacro con `Contenido`, `Historia` y `Publicacion`.

## 2. Preparar a Documento: tres métodos para sobrescribir

Antes de crear las hijas, agregale a `Documento` estos tres métodos, debajo del constructor:

```java
public String getTipo() {
    return "Documento";
}

public String getDatoExtra() {
    return "";
}

public void setDatoExtra(String datoExtra) {
}
```

Parecen vacíos, y está bien: existen para que **todas** las hijas los tengan y cada una los **sobrescriba** con su propia versión. Así, más adelante, la ventana va a poder pedirle a cualquier documento su tipo y su dato extra sin saber si es un libro o una tesis.

## 3. Crear Libro

1. **New → Class** en `proyecto` → Name: `Libro`. En **Superclass** escribí `Documento` (o tocá **Browse…** y elegilo) → **Finish**.
2. Dejá la clase así (podés borrar lo que generó Eclipse y escribirla entera):

```java
package proyecto;

public class Libro extends Documento {

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
```

- `extends Documento`: `Libro` hereda todo lo de `Documento`.
- `super(titulo, autor, anio, valoracion);` llama al constructor del padre para que guarde los datos comunes. Tiene que ser la primera línea del constructor. Después, `this.editorial = editorial;` guarda lo propio.
- `@Override` avisa que el método **sobrescribe** uno del padre: cuando a un `Libro` le pidan `getTipo()`, va a responder «Libro» y no «Documento». Si escribís mal el nombre, gracias al `@Override` Eclipse te avisa.

## 4. Articulo y Tesis: el mismo patrón

Creá `Articulo` y `Tesis` igual que `Libro`, cambiando el atributo propio y lo que devuelve `getTipo()`:

| Clase | Atributo propio | `getTipo()` devuelve |
| --- | --- | --- |
| `Articulo` | `private String revista;` | `"Artículo"` |
| `Tesis` | `private String universidad;` | `"Tesis"` |

Ojo: la clase se llama `Articulo`, sin tilde, porque es un nombre de Java; pero el tipo que se muestra es `"Artículo"`, con tilde, porque es texto para el usuario. Si te perdés, compará con la carpeta [referencia](../referencia/), que tiene además las líneas `serialVersionUID` que agregamos en la Lección 14.

## 5. Polimorfismo: un tipo, muchas formas

Probalo en `principal`:

```java
package proyecto;

public class principal {

    public static void main(String[] args) {
        Documento d1 = new Libro("Rayuela", "Julio Cortázar", 1963, 5, "Sudamericana");
        Documento d2 = new Articulo("Attention Is All You Need", "Vaswani y otros", 2017, 5, "NeurIPS");
        Documento d3 = new Tesis("Ciudades inteligentes en Uruguay", "Ana Pérez", 2024, 4, "UdelaR");

        mostrar(d1);
        mostrar(d2);
        mostrar(d3);

        d1.setDatoExtra("Alfaguara");
        mostrar(d1);
    }

    public static void mostrar(Documento d) {
        System.out.println(d.getTipo() + ": " + d.getTitulo() + " (" + d.getAnio() + ") | " + d.getDatoExtra());
    }
}
```

Tiene que imprimir:

```
Libro: Rayuela (1963) | Sudamericana
Artículo: Attention Is All You Need (2017) | NeurIPS
Tesis: Ciudades inteligentes en Uruguay (2024) | UdelaR
Libro: Rayuela (1963) | Alfaguara
```

Lo importante: las tres variables son de tipo `Documento`, pero cada una guarda un objeto distinto, y `getTipo()` responde según lo que el objeto realmente es. Eso se llama **polimorfismo**, y es lo que te va a permitir guardar libros, artículos y tesis en una misma lista en la próxima lección.

`getTitulo()` no está escrito en `Libro`, pero funciona igual: lo heredó de `Documento`.

## 6. Punto de control

Así tiene que quedar `Documento.java` (`Libro` está completo en la sección 3):

```java
package proyecto;

public class Documento {

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
```

## 7. Ejercicios

- [ ] Creá un documento de cada tipo con datos tuyos y mostralos con `mostrar`.
- [ ] Cambiá la universidad de la tesis con `setDatoExtra` y volvé a mostrarla.
- [ ] Borrá a propósito el `@Override` de `getTipo()` en `Tesis`: ¿cambia algo al ejecutar? Después volvé a ponerlo y escribí mal el nombre (`getTip`): ¿qué dice Eclipse? Dejalo bien al terminar.
- [ ] En tu cuaderno, explicá con tus palabras qué hace `super(...)`.

## 8. Preguntas tipo defensa

1. ¿Qué es la herencia? ¿Qué gana el proyecto usándola?
2. ¿Qué hace `super(titulo, autor, anio, valoracion);`? ¿Por qué va primero?
3. ¿Qué significa `@Override`?
4. ¿Por qué `Documento` tiene un `setDatoExtra` vacío?
5. Si `Documento d = new Libro(...)`, ¿qué devuelve `d.getTipo()`? ¿Por qué?
6. ¿Por qué la clase se llama `Articulo` pero el tipo dice «Artículo»?

**Cuando termines, mandame** las cuatro clases y tus respuestas.

---

[← Lección 5](leccion-05.md) · [Índice](README.md) · [Lección 7 →](leccion-07.md)
