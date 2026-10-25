# Lección 7 · ArrayList y la clase Biblioteca

Hoy armás el estante del programa: una lista que guarda todos los documentos, y la clase `Biblioteca`, que sabe agregar, borrar, buscar y filtrar. Es el cerebro del parcial: la ventana solo le va a pedir cosas. Tiempo estimado: 2 horas.

## 1. La idea: una lista que crece

Hasta ahora cada documento estaba en su propia variable (`d1`, `d2`, `d3`). Con cien documentos eso es imposible. Un **ArrayList** es una lista que crece y se achica sola: le agregás, le sacás y la recorrés con un `for`.

## 2. ArrayList paso a paso

Creá `Leccion7` en `practica` y probá:

```java
package practica;

import java.util.ArrayList;

public class Leccion7 {

    public static void main(String[] args) {
        ArrayList<String> titulos = new ArrayList<>();
        titulos.add("Rayuela");
        titulos.add("La tregua");
        titulos.add("El túnel");

        System.out.println(titulos.size());
        System.out.println(titulos.get(0));
        System.out.println(titulos.contains("La tregua"));

        for (int i = 0; i < titulos.size(); i++) {
            System.out.println(i + ": " + titulos.get(i));
        }

        titulos.remove("La tregua");
        titulos.set(0, "Rayuela (edición nueva)");
        System.out.println(titulos);
        System.out.println(titulos.isEmpty());
    }
}
```

- `import java.util.ArrayList;` trae la clase `ArrayList`. Si te olvidás, Eclipse la subraya: **Ctrl + Shift + O** agrega el import.
- `ArrayList<String>`: entre `< >` va el tipo de lo que guarda la lista. Para números enteros se usa `Integer`, no `int`: `ArrayList<Integer>`.
- `new ArrayList<>()` crea la lista vacía.

| Método | Qué hace | En el ejemplo |
| --- | --- | --- |
| `add(x)` | Agrega al final | Suma los tres títulos |
| `size()` | Cantidad de elementos | `3` |
| `get(i)` | El elemento en la posición `i`, contando desde 0 | `get(0)` da `"Rayuela"` |
| `contains(x)` | ¿Está en la lista? | `true` |
| `remove(x)` | Saca ese elemento | Saca «La tregua» |
| `set(i, x)` | Reemplaza el de la posición `i` | Cambia el de la posición 0 |
| `isEmpty()` | ¿Está vacía? | `false` |

El `println(titulos)` del final imprime `[Rayuela (edición nueva), El túnel]`: así se ve una lista entera. Y fijate el `for`: es el patrón de la Lección 3, con `size()` y `get(i)`.

## 3. Una lista de documentos

Gracias al polimorfismo de la Lección 6, una `ArrayList<Documento>` puede guardar libros, artículos y tesis mezclados: todos son documentos. Esa lista es el corazón de la clase que sigue.

## 4. La clase Biblioteca

**New → Class** en `proyecto` → Name: `Biblioteca`:

```java
package proyecto;

import java.util.ArrayList;

public class Biblioteca {

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

}
```

- El atributo es la lista, y es `private`: nadie de afuera la toca directo; todo pasa por los métodos de `Biblioteca`.
- La lista se crea **en el constructor**. Si no, el atributo vale `null` (no hay lista) y el primer `add` rompe el programa con un `NullPointerException`. Es la misma idea que usó el profe en la clase `Usuario` del simulacro.
- `agregar` y `eliminar` reciben un documento y se lo pasan a la lista.

## 5. buscar: filtrar por tipo y por título

Este es el método más importante de la clase. Agregalo debajo de `eliminar`:

```java
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
```

Leelo en voz alta:

1. Crea una lista vacía para los resultados.
2. Recorre todos los documentos, uno por uno.
3. `tipoCoincide` es `true` si pidieron «Todos» **o** si el tipo del documento es el pedido.
4. `textoCoincide` es `true` si el título contiene el texto buscado, sin importar mayúsculas. Con texto vacío (`""`) siempre es `true`, porque todo título contiene el texto vacío.
5. Si coinciden las dos cosas, agrega el documento al resultado. Al final, lo devuelve.

Con un solo método resolvés las tres cosas que pide la consigna: ver todo con `buscar("Todos", "")`, filtrar por tipo con `buscar("Libro", "")` y buscar por nombre con `buscar("Todos", "ray")`.

## 6. Probarlo en la consola

En `principal`:

```java
package proyecto;

import java.util.ArrayList;

public class principal {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.agregar(new Libro("Rayuela", "Julio Cortázar", 1963, 5, "Sudamericana"));
        biblioteca.agregar(new Libro("La tregua", "Mario Benedetti", 1960, 4, "Alfa"));
        biblioteca.agregar(new Articulo("Attention Is All You Need", "Vaswani y otros", 2017, 5, "NeurIPS"));
        biblioteca.agregar(new Tesis("Ciudades inteligentes en Uruguay", "Ana Pérez", 2024, 4, "UdelaR"));

        System.out.println("== Todos ==");
        mostrarLista(biblioteca.buscar("Todos", ""));

        System.out.println("== Solo libros ==");
        mostrarLista(biblioteca.buscar("Libro", ""));

        System.out.println("== Títulos con 'ray' ==");
        mostrarLista(biblioteca.buscar("Todos", "ray"));

        ArrayList<Documento> encontrados = biblioteca.buscar("Todos", "rayuela");
        biblioteca.eliminar(encontrados.get(0));
        System.out.println("== Después de borrar Rayuela ==");
        mostrarLista(biblioteca.buscar("Todos", ""));
    }

    public static void mostrarLista(ArrayList<Documento> lista) {
        for (int i = 0; i < lista.size(); i++) {
            Documento d = lista.get(i);
            System.out.println(d.getTipo() + ": " + d.getTitulo());
        }
        System.out.println("Total: " + lista.size());
    }
}
```

Tiene que imprimir:

```
== Todos ==
Libro: Rayuela
Libro: La tregua
Artículo: Attention Is All You Need
Tesis: Ciudades inteligentes en Uruguay
Total: 4
== Solo libros ==
Libro: Rayuela
Libro: La tregua
Total: 2
== Títulos con 'ray' ==
Libro: Rayuela
Total: 1
== Después de borrar Rayuela ==
Libro: La tregua
Artículo: Attention Is All You Need
Tesis: Ciudades inteligentes en Uruguay
Total: 3
```

## 7. Punto de control

Así tiene que quedar `Biblioteca.java`. La parte del tiempo de prueba (`segundosUsados` y sus métodos) se suma en la Lección 15.

```java
package proyecto;

import java.util.ArrayList;

public class Biblioteca {

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

## 8. Ejercicios

- [ ] Agregá dos documentos más y probá `buscar("Tesis", "")` y `buscar("Todos", "a")`.
- [ ] Agregá a `Biblioteca` un método `public int cantidad()` que devuelva cuántos documentos hay. Pista: `size()`.
- [ ] Agregá a `principal` un método `public static double promedio(ArrayList<Documento> lista)` que calcule el promedio de las valoraciones. Pista: acumulá con un `for` y dividí con `(double) suma / lista.size()`.
- [ ] Borrá un momento la línea `this.documentos = new ArrayList<>();` del constructor y ejecutá. Anotá el error que aparece y volvé a ponerla.

## 9. Preguntas tipo defensa

1. ¿Qué es un ArrayList y qué ventaja tiene frente a variables sueltas?
2. ¿Por qué la lista se crea en el constructor de `Biblioteca`?
3. ¿Por qué `get(0)` da el primer elemento?
4. ¿Cómo puede una misma lista guardar libros y tesis?
5. Explicá línea por línea qué hace `buscar`.
6. ¿Qué devuelve `buscar("Todos", "")`? ¿Por qué?

**Cuando termines, mandame** `Biblioteca`, `principal` y tus respuestas.

---

[← Lección 6](leccion-06.md) · [Índice](README.md) · [Lección 8 →](leccion-08.md)
