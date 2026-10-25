# Lección 3 · Bucles: for y while

Hoy aprendés a repetir instrucciones sin copiarlas: los bucles. En el parcial los vas a usar para recorrer la lista de documentos, por ejemplo para llenar la tabla o buscar un título. Tiempo estimado: 2 horas.

Antes de empezar: en el paquete `practica`, creá la clase `Leccion3` con el main tildado. Todo lo de hoy va ahí.

## 1. La idea: repetir sin copiar

Para imprimir «Vuelta número 1» hasta el 5 podrías escribir cinco println, pero con mil sería imposible. Un **bucle** repite un bloque mientras se cumpla una condición.

## 2. while: repetir mientras se cumpla algo

```java
int contador = 1;
while (contador <= 5) {
    System.out.println("Vuelta número " + contador);
    contador++;
}
System.out.println("Terminó el while");
```

Cómo lo lee Java:

1. ¿`contador <= 5`? Sí, vale 1: ejecuta el bloque, imprime y le suma 1.
2. Vuelve arriba y pregunta otra vez. Así hasta que `contador` vale 6.
3. Con 6 la condición da `false`: sale del bucle y sigue con «Terminó el while».

`contador++` es un atajo de `contador = contador + 1`. También existen `contador--` (resta 1) y `suma += 5` (suma 5).

**Cuidado con el bucle infinito.** Si te olvidás del `contador++`, el contador nunca llega a 6 y el programa imprime para siempre. Para frenarlo, tocá el cuadrado rojo (**Terminate**) arriba de la consola.

## 3. for: lo mismo, más ordenado

```java
for (int i = 1; i <= 5; i++) {
    System.out.println("Vuelta número " + i);
}
```

El `for` junta en una línea las tres partes que el while tenía separadas:

| Parte | En el ejemplo | Qué hace |
| --- | --- | --- |
| Inicio | `int i = 1` | Se ejecuta una vez, al principio |
| Condición | `i <= 5` | Se pregunta antes de cada vuelta |
| Paso | `i++` | Se ejecuta al final de cada vuelta |

Usá `for` cuando sabés cuántas vueltas vas a dar, y `while` cuando depende de algo que no sabés de antemano.

El paso puede ser otro. Este recorre el tiempo de prueba de 5 en 5 minutos (300 segundos):

```java
for (int segundos = 0; segundos <= 1800; segundos = segundos + 300) {
    int quedan = 1800 - segundos;
    System.out.println("Quedan " + quedan / 60 + " minutos");
}
```

Imprime desde «Quedan 30 minutos» hasta «Quedan 0 minutos».

## 4. Acumular y contar

Dos patrones que vas a usar siempre:

- **Acumular:** una variable que arranca en 0 y en cada vuelta suma algo.
- **Contar:** una variable que arranca en 0 y suma 1 cuando se cumple una condición.

```java
int suma = 0;
for (int i = 1; i <= 5; i++) {
    suma = suma + i;
}
System.out.println("La suma de 1 a 5 es " + suma);

int cantidad = 0;
for (int i = 1; i <= 100; i++) {
    if (i % 7 == 0) {
        cantidad++;
    }
}
System.out.println("Entre 1 y 100 hay " + cantidad + " múltiplos de 7");
```

Imprime 15 y 14. Fijate el `if` adentro del `for`: en cada vuelta decide si cuenta o no. `i % 7 == 0` significa «dividido por 7 no deja resto».

Cada `for` crea su propia `i`, que desaparece cuando el bucle termina. Por eso podés volver a usar el nombre `i` en el bucle siguiente sin que Eclipse marque error.

## 5. Recorrer desde 0: el patrón del proyecto

En Java las posiciones se cuentan desde 0. Un texto de 7 letras tiene las posiciones 0 a 6:

```java
String titulo = "Rayuela";
for (int i = 0; i < titulo.length(); i++) {
    System.out.println("Posición " + i + ": " + titulo.charAt(i));
}
```

- `titulo.length()` da la cantidad de letras: 7.
- `titulo.charAt(i)` da la letra que está en la posición `i`.
- La condición es `i < titulo.length()`, con menor y no con menor o igual: la última posición es 6, no 7.

Este mismo patrón, `for (int i = 0; i < algo.size(); i++)`, es el que vas a usar en el parcial para recorrer la lista de documentos.

## 6. Ejercicios

- [ ] **Contar.** Del 1 al 10 con `for`, y después una cuenta regresiva del 10 al 1 con `while`.
- [ ] **Tabla del 7.** Imprimí desde `7 x 1 = 7` hasta `7 x 10 = 70`.
- [ ] **Suma grande.** Sumá los números del 1 al 100. Tiene que dar 5050.
- [ ] **Reloj de prueba.** Con un `for` que vaya de 30 a 0 restando 5, imprimí `Quedan 30 min`, `Quedan 25 min`… hasta `Quedan 0 min`.
- [ ] **Contar letras.** Contá cuántas `a` minúsculas tiene `"Cien años de soledad"`. Tiene que dar 2. Las letras sueltas van entre comillas simples: `titulo.charAt(i) == 'a'`.

## 7. Preguntas tipo defensa

1. ¿Qué diferencia hay entre `while` y `for`? ¿Cuándo usás cada uno?
2. ¿Qué pasa si en un `while` te olvidás de aumentar el contador?
3. ¿Qué significa `i++`?
4. En `for (int i = 0; i < 5; i++)`, ¿cuántas vueltas da y qué valores toma `i`?
5. ¿Por qué la condición es `i < titulo.length()` y no `i <= titulo.length()`?

**Cuando termines, mandame** el código de `Leccion3` y tus respuestas.

---

[← Lección 2](leccion-02.md) · [Índice](README.md) · [Lección 4 →](leccion-04.md)
