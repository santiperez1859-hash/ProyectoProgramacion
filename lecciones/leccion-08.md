# Lección 8 · Excepciones y parseo

Todo lo que el usuario escribe en una ventana llega como texto. Hoy aprendés a convertirlo en número y a que el programa no se rompa cuando alguien escribe cualquier cosa: es lo que la consigna llama «funcionar en situaciones excepcionales». Tiempo estimado: 2 horas.

Antes de empezar: en `practica`, creá `Leccion8` con el main tildado.

## 1. Parsear: de texto a número

Un campo de texto devuelve `"1963"` (texto), no `1963` (número). Para hacer cuentas hay que convertirlo:

```java
String textoAnio = "1963";
int anio = Integer.parseInt(textoAnio);
System.out.println(anio + 1);

String deNuevo = String.valueOf(anio);
System.out.println(deNuevo + 1);
```

Imprime `1964`, porque suma, y `19631`, porque pega texto.

| Conversión | Qué hace | Ejemplo | Da |
| --- | --- | --- | --- |
| `Integer.parseInt(texto)` | Texto a número entero | `Integer.parseInt("1963")` | `1963` |
| `String.valueOf(numero)` | Número a texto | `String.valueOf(1963)` | `"1963"` |
| `(int) valor` | *Cast*: de `double` a `int` | `(int) 450.75` | `450`, corta los decimales |

Las dos primeras las vas a usar en el parcial: `parseInt` para leer el año del formulario y `String.valueOf` para mostrarlo en el campo cuando selecciones un documento.

## 2. Cuando falla: la excepción

¿Y si el usuario escribe «dos mil»? Escribí esto en el main, ejecutá y mirá la consola:

```java
int malo = Integer.parseInt("dos mil");
```

La consola se pone roja y el programa se corta:

```
Exception in thread "main" java.lang.NumberFormatException: For input string: "dos mil"
```

Cómo leerlo: el tipo de error es `NumberFormatException` («formato de número») y la causa es el texto «dos mil». Más abajo aparece la línea de tu código donde pasó, como un link azul: clic y te lleva. Después de mirarlo, borrá esa línea.

Una **excepción** es un error que aparece mientras el programa corre. Si nadie la atrapa, el programa termina.

## 3. try y catch: atrapar el error

```java
try {
    int malo = Integer.parseInt("dos mil");
    System.out.println("Esto no se imprime: " + malo);
} catch (NumberFormatException e) {
    System.out.println("Eso no es un número");
} finally {
    System.out.println("Esto se imprime siempre");
}
```

- `try`: «intentá esto». Si algo falla adentro, Java salta directo al `catch` y no ejecuta el resto del bloque.
- `catch (NumberFormatException e)`: «si pasó este error, hacé esto otro». `e` es el error atrapado, por si lo querés usar.
- `finally`, que es opcional: se ejecuta siempre, haya error o no.

Imprime «Eso no es un número» y «Esto se imprime siempre», y el programa sigue vivo.

## 4. Validar un formulario

Así se combina todo. Este método es casi igual al que va a validar el formulario del parcial; escribilo en `Leccion8`, afuera del main:

```java
public static String validar(String titulo, String autor, String textoAnio) {
    if (titulo.trim().isEmpty() || autor.trim().isEmpty()) {
        return "El título y el autor son obligatorios.";
    }
    try {
        int anio = Integer.parseInt(textoAnio.trim());
        if (anio <= 0) {
            return "El año tiene que ser mayor que 0.";
        }
    } catch (NumberFormatException e) {
        return "El año tiene que ser un número, por ejemplo 2015.";
    }
    return "Datos correctos";
}
```

Probalo desde el main:

```java
System.out.println(validar("Rayuela", "Cortázar", "1963"));
System.out.println(validar("", "Cortázar", "1963"));
System.out.println(validar("Rayuela", "Cortázar", "mil"));
System.out.println(validar("Rayuela", "Cortázar", "-4"));
```

Tiene que imprimir:

```
Datos correctos
El título y el autor son obligatorios.
El año tiene que ser un número, por ejemplo 2015.
El año tiene que ser mayor que 0.
```

Hay tres controles distintos: campos vacíos con `if`, año con letras con `try`/`catch` y año negativo con `if`. No todo se resuelve con excepciones: lo que podés preguntar con un `if`, preguntalo con un `if`.

## 5. Otras excepciones que conviene conocer

| Excepción | Cuándo aparece | Cómo la evitamos en el parcial |
| --- | --- | --- |
| `NumberFormatException` | Convertir a número un texto que no lo es | `try`/`catch` al leer el año |
| `NullPointerException` | Usar una variable que vale `null`, o sea, que no apunta a ningún objeto | Preguntar `if (seleccionado == null)` antes de modificar o eliminar |
| `IndexOutOfBoundsException` | Pedir una posición que no existe en una lista | Preguntar si la fila elegida es `-1` antes de usarla |
| `IOException` | Errores al leer o guardar archivos | `try`/`catch` en la clase `Archivo` (Lección 14) |

Probá la idea de la segunda y la tercera fila (necesita `import java.util.ArrayList;`):

```java
ArrayList<String> lista = new ArrayList<>();
lista.add("Rayuela");
int posicion = 5;
if (posicion < lista.size()) {
    System.out.println(lista.get(posicion));
} else {
    System.out.println("No hay nada en la posición " + posicion);
}

String texto = null;
if (texto == null) {
    System.out.println("La variable está vacía (null)");
} else {
    System.out.println(texto.length());
}
```

Sin esos `if`, `lista.get(5)` y `texto.length()` romperían el programa.

## 6. Ejercicios

- [ ] **Edad del libro.** Guardá el año como texto (`String textoAnio = "1990";`), convertilo y mostrá cuántos años tiene. Después probá con `"mil novecientos"` y atrapá el error con un mensaje claro.
- [ ] **Valoración segura.** Hacé `public static String validarValoracion(String texto)` que devuelva «OK» si es un número del 1 al 5, «Fuera de rango» si es otro número y «No es un número» si tiene letras.
- [ ] **Romper a propósito.** Hacé que aparezca un `IndexOutOfBoundsException` con una lista de 2 elementos y `get(2)`. Leé el mensaje rojo y anotá qué dice. Después arreglalo con un `if`.

## 7. Preguntas tipo defensa

1. ¿Qué es una excepción?
2. ¿Qué hace `Integer.parseInt`? ¿Qué pasa si recibe letras?
3. Explicá qué hacen `try`, `catch` y `finally`.
4. ¿Por qué no todo se valida con `try`/`catch`?
5. ¿Qué es `null` y qué error produce si lo usás?
6. ¿Qué situaciones excepcionales va a controlar tu programa?

**Cuando termines, mandame** el código de `Leccion8` y tus respuestas.

---

[← Lección 7](leccion-07.md) · [Índice](README.md) · [Lección 9 →](leccion-09.md)
