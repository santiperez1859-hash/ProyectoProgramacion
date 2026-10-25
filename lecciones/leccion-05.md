# Lección 5 · Clases y objetos: la clase Documento

Hoy escribís la primera clase real del parcial: `Documento`, el molde de todo lo que va a guardar el programa. Es el corazón de la Programación Orientada a Objetos. Tiempo estimado: 2 horas.

## 1. La idea: el molde y las fichas

Pensá en una biblioteca con fichas de papel. La **clase** es el formulario en blanco: dice qué datos tiene toda ficha. Cada **objeto** es una ficha llena: «Rayuela, Julio Cortázar, 1963, 5». Con un solo molde hacés todas las fichas que quieras, y cada una guarda sus propios datos.

| Concepto | En la biblioteca | En Java |
| --- | --- | --- |
| Clase | El formulario en blanco | `public class Documento { ... }` |
| Atributo | Cada casillero del formulario | `private String titulo;` |
| Objeto | Una ficha llena | `new Documento("Rayuela", ...)` |
| Método | Algo que la ficha sabe hacer | `getTitulo()` |

## 2. Crear la clase y sus atributos

1. Click derecho sobre el paquete `proyecto` → **New → Class** → Name: `Documento`, con mayúscula, y sin tildar el main → **Finish**.
2. Adentro de las llaves de la clase, escribí los atributos:

```java
package proyecto;

public class Documento {

    private String titulo;
    private String autor;
    private int anio;
    private int valoracion;

}
```

Los atributos son variables que pertenecen al objeto: cada documento tiene las suyas. Van arriba de todo, adentro de la clase y afuera de cualquier método.

`private` significa que solo se pueden tocar desde adentro de esta clase; desde afuera se usan los getters y setters (sección 4). Eso se llama **encapsulamiento**: el objeto cuida sus propios datos.

## 3. El constructor: cómo nace un objeto

El constructor es un método especial que se ejecuta al crear el objeto con `new`. Tiene el mismo nombre que la clase y no lleva tipo de retorno. Agregalo debajo de los atributos:

```java
public Documento(String titulo, String autor, int anio, int valoracion) {
    this.titulo = titulo;
    this.autor = autor;
    this.anio = anio;
    this.valoracion = valoracion;
}
```

`this.titulo` es el atributo del objeto; `titulo` a secas es el parámetro que llega. Se lee: «el título de este objeto es el título que me pasaron». Sin el `this`, Java confundiría los dos.

## 4. Getters y setters

Como los atributos son `private`, hacen falta métodos para leerlos (**get**) y cambiarlos (**set**). Para el título:

```java
public String getTitulo() {
    return titulo;
}

public void setTitulo(String titulo) {
    this.titulo = titulo;
}
```

Para el resto, Eclipse los escribe por vos: click derecho adentro de la clase → **Source → Generate Getters and Setters…** → tildá `autor`, `anio` y `valoracion` → **Generate**. Mirá lo que generó: tiene que seguir el mismo patrón que el del título.

## 5. Crear objetos desde principal

En `principal` borrá lo de la Lección 1 (si querés conservarlo, copialo antes a una clase `Leccion1` en `practica`) y escribí:

```java
package proyecto;

public class principal {

    public static void main(String[] args) {
        Documento d1 = new Documento("Rayuela", "Julio Cortázar", 1963, 5);
        Documento d2 = new Documento("La tregua", "Mario Benedetti", 1960, 4);

        mostrar(d1);
        mostrar(d2);

        d2.setValoracion(5);
        System.out.println("Nueva valoración de " + d2.getTitulo() + ": " + d2.getValoracion());
    }

    public static void mostrar(Documento d) {
        System.out.println(d.getTitulo() + " - " + d.getAutor() + " (" + d.getAnio() + ") " + d.getValoracion() + "/5");
    }
}
```

Tiene que imprimir:

```
Rayuela - Julio Cortázar (1963) 5/5
La tregua - Mario Benedetti (1960) 4/5
Nueva valoración de La tregua: 5
```

- `Documento d1` declara una variable de tipo `Documento`: la clase que creaste funciona como un tipo nuevo, igual que `int` o `String`.
- `new Documento(...)` crea el objeto y ejecuta el constructor.
- `d2.setValoracion(5)` cambia solo la ficha `d2`; `d1` queda igual.
- Si probás `d1.titulo = "Otro";`, Eclipse marca `The field Documento.titulo is not visible`: es el `private` cuidando el dato.

## 6. static, por fin

Un método `static` pertenece a la clase y se usa sin crear objetos, como `mostrar(d1)` o el main. Uno sin `static`, como `getTitulo()`, pertenece a cada objeto y se usa con el punto: `d1.getTitulo()` devuelve el título de `d1`, y `d2.getTitulo()` el de `d2`.

Y la razón por la que `String` va con mayúscula: es una clase, como `Documento`. Por eso los textos tienen métodos con punto, como `titulo.length()`.

## 7. Punto de control

Así tiene que quedar `Documento.java`:

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

## 8. Ejercicios

- [ ] Creá un tercer documento con tu libro favorito y mostralo con `mostrar`.
- [ ] Cambiá el autor de `d1` con su setter e imprimilo de nuevo.
- [ ] Agregá a `principal` un método `public static boolean esClasico(Documento d)` que devuelva `true` si el documento tiene más de 50 años. Probalo con `d1` y con tu libro.
- [ ] Explicá en tu cuaderno, con tus palabras, la diferencia entre clase y objeto.

## 9. Preguntas tipo defensa

1. ¿Qué diferencia hay entre una clase y un objeto?
2. ¿Qué es un atributo? ¿Por qué los atributos son `private`?
3. ¿Qué hace el constructor? ¿Cuándo se ejecuta?
4. ¿Qué significa `this.titulo = titulo;`?
5. ¿Para qué sirven los getters y setters?
6. ¿Qué diferencia hay entre un método `static` y uno que no lo es?

**Cuando termines, mandame** `Documento`, `principal` y tus respuestas.

---

[← Lección 4](leccion-04.md) · [Índice](README.md) · [Lección 6 →](leccion-06.md)
