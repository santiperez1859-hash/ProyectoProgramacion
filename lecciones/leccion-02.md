# Lección 2 · Decisiones con if y else

Hoy le enseñás al programa a decidir: hacer una cosa u otra según los datos. Con esto, más adelante, el formulario va a avisar si falta el título y la ventana se va a bloquear a los 30 minutos. Tiempo estimado: 2 horas.

Antes de empezar:

1. Para no mezclar la práctica con el proyecto, creá un paquete `practica` (click derecho en `src` → **New → Package**) y adentro una clase `Leccion2` con el main tildado. Todos los ejemplos y ejercicios de hoy van en ese main, uno debajo del otro. Ese paquete es tu práctica personal: no se sube a GitHub.

## 1. La idea: si pasa esto, hacé aquello

Hasta ahora tu programa ejecutaba todas las líneas, una tras otra. Con `if` (en inglés, «si») ejecuta un bloque solo cuando se cumple una condición. Escribí esto en el main de `Leccion2`:

```java
int valoracion = 5;

if (valoracion == 5) {
    System.out.println("¡Excelente!");
}
System.out.println("Fin");
```

- `if` hace una pregunta.
- Entre paréntesis va la **condición**: una pregunta que solo puede dar `true` (verdadero) o `false` (falso). Acá: ¿la valoración es igual a 5?
- Entre llaves va el **bloque**: lo que se ejecuta solo si la respuesta es `true`.
- Lo que está afuera de las llaves (el «Fin») se ejecuta siempre.

Ejecutalo: salen las dos líneas. Cambiá la valoración a 3 y volvé a ejecutar: ahora solo sale «Fin».

Trampa: no pongas `;` después del paréntesis. `if (valoracion == 5);` termina el if ahí mismo, y el bloque de abajo se ejecuta siempre.

## 2. Comparar números

Las condiciones se arman con estos operadores. Agregá `int anio = 1963;` a tu main y probalos:

| Operador | Pregunta | Ejemplo | Da |
| --- | --- | --- | --- |
| `==` | ¿es igual? | `anio == 1963` | `true` |
| `!=` | ¿es distinto? | `anio != 2000` | `true` |
| `>` | ¿es mayor? | `anio > 2000` | `false` |
| `<` | ¿es menor? | `anio < 2000` | `true` |
| `>=` | ¿es mayor o igual? | `anio >= 1963` | `true` |
| `<=` | ¿es menor o igual? | `anio <= 0` | `false` |

Podés imprimir una condición para ver qué da: `System.out.println(anio > 2000);` imprime `false`.

La confusión más común: `=` guarda un valor (Lección 1) y `==` compara. Si escribís `if (anio = 1963)`, Eclipse marca `Type mismatch: cannot convert from int to boolean`: le diste un número donde esperaba una pregunta.

## 3. if, else y else if

Con `else` («si no») le decís qué hacer cuando la condición da `false`. Siempre se ejecuta uno de los dos bloques, nunca los dos. Usando el `anio` de la sección anterior:

```java
if (anio <= 0) {
    System.out.println("Año inválido");
} else {
    System.out.println("Año correcto");
}
```

Cuando hay más de dos caminos, encadenás con `else if`:

```java
valoracion = 4;

if (valoracion == 5) {
    System.out.println("Imperdible");
} else if (valoracion >= 3) {
    System.out.println("Recomendable");
} else {
    System.out.println("No lo recomiendo");
}
```

Fijate que no repito `int`: `valoracion` ya existe desde la sección 1, así que solo le cambio el valor. Si la volvés a crear, Eclipse marca `Duplicate local variable`.

Java revisa las condiciones de arriba hacia abajo y entra **solo en la primera que da `true`**; el `else` del final atrapa todo lo demás. Con 4 imprime «Recomendable». Por eso importa el orden: si `valoracion >= 3` fuera primero, un 5 también diría «Recomendable» y nunca llegaría a «Imperdible».

## 4. Comparar textos: equals, isEmpty y trim

Los textos (`String`) **no** se comparan con `==`, sino con `.equals(...)`. El `==` pregunta si son la misma caja, no si tienen las mismas letras: a veces acierta de casualidad y a veces no. Con textos, siempre `equals`.

```java
String tipo = "Libro";

if (tipo.equals("Libro")) {
    System.out.println("Dato extra: Editorial");
} else if (tipo.equals("Artículo")) {
    System.out.println("Dato extra: Revista");
} else {
    System.out.println("Dato extra: Universidad");
}
```

Este mismo `if` lo vas a usar en la ventana del parcial, para cambiar la etiqueta del dato extra según el tipo elegido.

Otros tres que vas a usar mucho:

| Método | Qué hace | Ejemplo | Da |
| --- | --- | --- | --- |
| `equalsIgnoreCase` | Compara sin importar mayúsculas | `"libro".equalsIgnoreCase("Libro")` | `true` |
| `isEmpty()` | Pregunta si está vacío | `"".isEmpty()` | `true` |
| `trim()` | Saca los espacios de los costados | `"  Rayuela ".trim()` | `"Rayuela"` |

`trim` e `isEmpty` juntos detectan un campo vacío aunque tenga espacios: `"   ".isEmpty()` da `false`, pero `"   ".trim().isEmpty()` da `true`. Así va a validar el formulario que el título no esté en blanco.

## 5. Combinar condiciones: &&, || y !

| Símbolo | Se lee | Da `true` cuando |
| --- | --- | --- |
| `&&` | y | las dos condiciones son `true` |
| `\|\|` | o | al menos una es `true` |
| `!` | no | da vuelta el resultado: `true` pasa a `false` y al revés |

Con que falte un campo obligatorio ya hay que avisar, por eso va `||`:

```java
String titulo = "Rayuela";
String autor = "";

if (titulo.trim().isEmpty() || autor.trim().isEmpty()) {
    System.out.println("El título y el autor son obligatorios.");
}
```

Para que una valoración sea válida tienen que cumplirse las dos cosas a la vez, por eso va `&&`:

```java
valoracion = 7;

if (valoracion >= 1 && valoracion <= 5) {
    System.out.println("Valoración válida");
} else {
    System.out.println("La valoración va de 1 a 5");
}
```

Imprime «La valoración va de 1 a 5». Un error típico es escribir `1 <= valoracion <= 5` como en matemática: en Java no funciona, hay que separarlo en dos condiciones unidas con `&&`.

## 6. Guardar una condición en un boolean

Una condición es un valor `true` o `false`, así que se puede guardar en una variable `boolean` y usarla después:

```java
int segundosUsados = 1800;
boolean pruebaTerminada = segundosUsados >= 1800;

if (pruebaTerminada) {
    System.out.println("Solo lectura");
} else {
    System.out.println("Podés agregar documentos");
}
```

- `if (pruebaTerminada)` es lo mismo que `if (pruebaTerminada == true)`, pero más corto y más claro.
- `!pruebaTerminada` se lee «la prueba NO terminó».
- Con 1800 imprime «Solo lectura». Probá con 900.

Esta es la idea de la versión de prueba del parcial: el programa pregunta «¿se terminó la prueba?» y, según la respuesta, deja o no deja agregar documentos.

## 7. Ejercicios

Hacelos en el main de `Leccion2`, debajo de los ejemplos. Reusá las variables que ya tenés cambiándoles el valor, sin repetir el tipo.

- [ ] **Opinión.** Con `valoracion`, imprimí «Muy malo», «Malo», «Regular», «Bueno» o «Excelente» según vaya de 1 a 5. Si no está entre 1 y 5, «Valoración inválida». Probá con 0, 3 y 5.
- [ ] **Clásico o moderno.** Con `anio`: si es mayor que 2026, «Todavía no salió»; si tiene más de 50 años, «Clásico»; si no, «Moderno». Probá con 1963, 2020 y 2030.
- [ ] **Validador.** Con `titulo`, `autor` y `anio`, imprimí «Datos correctos» solo si el título y el autor no están en blanco y el año es mayor que 0. Si algo está mal, decí qué. Probalo con tres combinaciones distintas.
- [ ] **Tipo sin mayúsculas.** Hacé que el `if` de la sección 4 funcione también si `tipo` vale `"libro"` o `"TESIS"`.
- [ ] **Reloj de prueba.** Con `segundosUsados`: si llegó a 1800, «Prueba terminada»; si no, cuántos minutos y segundos quedan (con `/` y `%` de la Lección 1). Con 0 tiene que salir `Quedan 30 min 0 s`; con 1799, `Quedan 0 min 1 s`; con 1800, `Prueba terminada`.

## 8. Preguntas tipo defensa

Respondelas con tus palabras y sin mirar:

1. ¿Qué diferencia hay entre `=` y `==`?
2. ¿Por qué los textos se comparan con `equals` y no con `==`?
3. En un `if / else if / else`, ¿pueden ejecutarse dos bloques? ¿Por qué?
4. ¿Qué diferencia hay entre `&&` y `||`? Dame un ejemplo del proyecto.
5. ¿Qué hace `trim()` y por qué conviene usarlo antes de `isEmpty()`?
6. Si `boolean terminada = false;`, ¿qué vale `!terminada`?

**Cuando termines, mandame por el chat** el código de `Leccion2` (o una captura con la consola) y tus respuestas. Con eso te corrijo y seguimos con la Lección 3: bucles.

---

[← Lección 1](leccion-01.md) · [Índice](README.md) · [Lección 3 →](leccion-03.md)
