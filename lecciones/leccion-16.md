# Lección 16 · Situaciones excepcionales, prolijidad y entrega

El programa ya hace todo lo que pide la consigna. Hoy lo intentás romper a propósito, lo dejás prolijo por dentro y por fuera, y lo preparás para entregar. Tiempo estimado: 2 horas.

## 1. Intentá romperlo

Recorré esta lista con el programa abierto y marcá cada punto cuando se comporte como dice. Si algo falla, anotalo y mandamelo.

- [ ] Agregar con el título o el autor vacíos, o con solo espacios: aviso de obligatorios.
- [ ] Año con letras («dos mil»): aviso de que tiene que ser un número.
- [ ] Año 0 o negativo: aviso de que tiene que ser mayor que 0.
- [ ] Modificar o Eliminar sin haber hecho clic en una fila: aviso.
- [ ] Eliminar y responder No: no se borra nada.
- [ ] Filtrar por un tipo y eliminar una fila: se borra ese documento y no otro.
- [ ] Buscar un título que no existe: tabla vacía, sin error. Borrar la búsqueda y tocar Buscar: vuelven todos.
- [ ] Doble clic en una celda: no se puede editar.
- [ ] Cerrar y abrir: siguen los datos y el tiempo restante.
- [ ] Borrar `biblioteca.bin` y abrir: arranca vacío, sin errores.
- [ ] Prueba vencida (con el límite en 20 segundos): se bloquea y avisa una vez; filtrar, buscar y ver siguen andando; al reabrir sigue bloqueado.

## 2. Prolijidad de la ventana

Vos sabés de diseño: aplicalo. Algunas ideas que se resuelven desde Design o desde Properties:

- Alineá todo a una grilla. En el código de referencia, los campos van cada 40 px (20, 60, 100…) y todos arrancan en x = 110.
- Agrupá el formulario y la tabla con bordes con título: en Properties, propiedad **border** → **TitledBorder**.
- Un título grande: un JLabel con `setFont(new Font("Tahoma", Font.BOLD, 16))` (con `import java.awt.Font;`). Si lo ponés arriba, corré el resto hacia abajo.
- Mismo tamaño y separación para los cuatro botones.
- Probá títulos largos: que nada quede cortado de forma fea.

## 3. Prolijidad del código

El profe evalúa la legibilidad y descuenta por exceso de comentarios. Antes de entregar, en cada clase:

1. **Ctrl + Shift + F**: ordena la sangría.
2. **Ctrl + Shift + O**: borra los `import` que no se usan.
3. Borrá los comentarios automáticos (`// TODO`, `/** Launch the application. */`, `/** Create the frame. */`).
4. Revisá que no queden `System.out.println` de prueba.
5. Revisá los nombres: cada variable y cada método tiene que decir qué es o qué hace.
6. Mirá los triángulos amarillos (*warnings*) y arreglá los que puedas.

## 4. Preparar la entrega

1. Revisá que el límite esté en `30 * 60`.
2. Cerrá el programa y borrá `biblioteca.bin`, así el profe arranca con los 30 minutos completos y sin tus datos de prueba.
3. Si querés, borrá el paquete `practica`, o preguntale al profe si le interesa verlo.
4. **File → Export → General → Archive File** → **Next** → tildá `ProyectoBiblioteca` → en **To archive file** elegí dónde guardar el .zip → **Finish**.
5. Probá el .zip en otra compu o en otro workspace: **File → Import → General → Existing Projects into Workspace** → **Select archive file** → el .zip. Si abre y funciona ahí, va a funcionar en la del profe.
6. Entregalo por donde indique el profe, antes del 15/11.

## 5. Ejercicios

- [ ] Completá la lista entera de la sección 1.
- [ ] Mejorá el diseño de la ventana con al menos dos ideas de la sección 2 y mandame una captura.
- [ ] Exportá el proyecto e importalo en otro workspace para comprobar que funciona.

## 6. Preguntas tipo defensa

1. Nombrá cinco situaciones excepcionales que controla tu programa y cómo lo hace.
2. ¿Por qué tu código casi no tiene comentarios?
3. ¿Qué hace **Ctrl + Shift + O**?
4. ¿Por qué se borra `biblioteca.bin` antes de entregar?
5. ¿Cómo comprobaste que el proyecto funciona en otra computadora?

Con esta lección termina la construcción. Lo que sigue es la semana de defensa: la sección «Preparación para la defensa» del plan.

---

[← Lección 15](leccion-15.md) · [Índice](README.md)
