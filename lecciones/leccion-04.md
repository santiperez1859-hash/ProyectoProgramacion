# Lección 4 · Métodos y textos

Hoy creás tus propios métodos: bloques de código con nombre que escribís una vez y usás muchas veces. Todo el parcial está armado con métodos, como `agregarDocumento()`, `buscar(...)` o `mostrarTiempo()`. Tiempo estimado: 2 horas.

Antes de empezar: en `practica`, creá la clase `Leccion4` con el main tildado.

## 1. La idea: una receta con nombre

Un método es como una receta: la escribís una vez, le ponés nombre, y cada vez que la necesitás la llamás por su nombre. Así el código no se repite, y cada parte tiene un nombre que explica qué hace. Eso es justo lo que pide el profe en lugar de comentarios.

## 2. Tu primer método

Los métodos se escriben adentro de la clase pero afuera del main. Fijate dónde cierra cada llave:

```java
package practica;

public class Leccion4 {

    public static void main(String[] args) {
        saludar();
        saludar();
    }

    public static void saludar() {
        System.out.println("Bienvenido a Mi Biblioteca");
    }
}
```

- `saludar();` en el main es la **llamada**: Java salta al método, lo ejecuta y vuelve a la línea siguiente.
- `void` significa que el método hace algo pero no devuelve ningún resultado.
- Por ahora, todos los métodos de esta clase llevan `public static`, igual que el main. Qué significa `static` lo vemos en la Lección 5.

## 3. Parámetros: darle datos al método

Entre los paréntesis van los datos que el método necesita. Se escriben como variables, con tipo y nombre, separados por coma:

```java
public static void mostrarFicha(String titulo, String autor, int anio) {
    System.out.println(titulo + " - " + autor + " (" + anio + ")");
}
```

Y se llama pasándole los valores en el mismo orden:

```java
mostrarFicha("Rayuela", "Julio Cortázar", 1963);
mostrarFicha("La tregua", "Mario Benedetti", 1960);
```

Los valores tienen que coincidir en cantidad, orden y tipo: si ponés el año primero, Eclipse marca error.

## 4. return: que el método devuelva un resultado

En lugar de `void`, ponés el tipo del resultado. `return` devuelve el valor y termina el método:

```java
public static int antiguedad(int anio) {
    return 2026 - anio;
}

public static boolean anioValido(int anio) {
    return anio > 0;
}

public static String opinion(int valoracion) {
    if (valoracion >= 4) {
        return "Recomendable";
    } else {
        return "No tanto";
    }
}
```

El resultado se usa donde llamás al método, como si fuera un valor:

```java
int edad = antiguedad(1963);
System.out.println("Tiene " + edad + " años");

if (anioValido(-5)) {
    System.out.println("Año válido");
} else {
    System.out.println("Año inválido");
}

System.out.println(opinion(4));
```

Imprime «Tiene 63 años», «Año inválido» y «Recomendable».

|  | `void` | Con tipo (`int`, `String`, `boolean`…) |
| --- | --- | --- |
| ¿Devuelve algo? | No | Sí, con `return` |
| Ejemplo del parcial | `limpiarFormulario()` | `formularioValido()` devuelve `true` o `false` |
| Cómo se usa | Se llama sola: `saludar();` | Se guarda o se usa: `int edad = antiguedad(1963);` |

En un método `void` también podés escribir `return;` solo, sin valor: significa «salí del método acá». Lo vas a ver en el parcial: si el formulario tiene errores, `agregarDocumento()` hace `return;` y no agrega nada.

## 5. Métodos de los textos

Los `String` traen sus propios métodos, que se usan con un punto:

| Método | Qué hace | Con `"Cien años de soledad"` da |
| --- | --- | --- |
| `length()` | Cantidad de letras | `20` |
| `toUpperCase()` | Todo en mayúsculas | `"CIEN AÑOS DE SOLEDAD"` |
| `toLowerCase()` | Todo en minúsculas | `"cien años de soledad"` |
| `contains("años")` | ¿Contiene ese texto? | `true` |
| `contains("Soledad")` | Distingue mayúsculas | `false` |

Para buscar sin que importen las mayúsculas, pasá los dos textos a minúsculas antes de comparar:

```java
String titulo = "Cien años de soledad";
System.out.println(titulo.toLowerCase().contains("SOLEDAD".toLowerCase()));
```

Imprime `true`. Así funciona el buscador del parcial: escribís «ray» y encuentra «Rayuela».

## 6. Ahora sí: qué significa el main

`public static void main(String[] args)`, palabra por palabra:

- `public`: se puede usar desde afuera de la clase; Java lo llama al arrancar.
- `static`: pertenece a la clase y no a un objeto. La diferencia la ves en la Lección 5.
- `void`: no devuelve nada.
- `main`: el nombre que Java busca para arrancar el programa.
- `String[] args`: datos que se le pueden pasar al programa al iniciarlo. No lo usamos, pero tiene que estar.

## 7. Ejercicios

Hacé cada método en `Leccion4` y probalo desde el main.

- [ ] **tiempoRestante.** `public static String tiempoRestante(int segundosUsados)` devuelve «Prueba terminada» si llegó a 1800, y si no, «Quedan X min Y s». Con 0, 1799 y 1800 tiene que dar `Quedan 30 min 0 s`, `Quedan 0 min 1 s` y `Prueba terminada`.
- [ ] **coincide.** `public static boolean coincide(String titulo, String buscado)` devuelve `true` si el título contiene lo buscado, sin importar mayúsculas. `coincide("Rayuela", "RAY")` da `true` y `coincide("Rayuela", "tregua")` da `false`.
- [ ] **etiquetaExtra.** `public static String etiquetaExtra(String tipo)` devuelve «Editorial:», «Revista:» o «Universidad:» según el tipo sea `"Libro"`, `"Artículo"` o `"Tesis"`.
- [ ] **estrellas.** `public static String estrellas(int valoracion)` devuelve tantos asteriscos como la valoración, usando un `for`: con 3 devuelve `"***"`. Pista: arrancá con `String resultado = "";` y en cada vuelta sumale un `"*"`.

## 8. Preguntas tipo defensa

1. ¿Qué es un método y para qué sirve?
2. ¿Qué diferencia hay entre un método `void` y uno que devuelve un valor?
3. ¿Qué es un parámetro? Dame un ejemplo.
4. ¿Qué hace `return`? ¿Y `return;` solo, en un método `void`?
5. Explicá cada palabra de `public static void main(String[] args)`.
6. ¿Cómo buscarías un título sin que importen las mayúsculas?

**Cuando termines, mandame** el código de `Leccion4` y tus respuestas.

---

[← Lección 3](leccion-03.md) · [Índice](README.md) · [Lección 5 →](leccion-05.md)
