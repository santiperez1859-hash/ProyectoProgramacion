# Lección 11 · Eventos: el botón Agregar

Hoy los botones cobran vida: la ventana aprende a escuchar al usuario, a leer el formulario y a crear documentos de verdad. Tiempo estimado: 2 horas.

## 1. La idea: escuchar

Un **evento** es algo que hace el usuario: apretar un botón, elegir una opción. Para reaccionar, la ventana tiene que **escuchar**. El profe lo enseña así en el material «4 Eventos», y así lo hacemos:

1. La ventana implementa la interfaz `ActionListener`.
2. Cada componente que importa se conecta con `addActionListener(this)`.
3. Todas las acciones caen en un único método, `actionPerformed`, que pregunta con `if` quién las generó.

## 2. implements ActionListener

Cambiá la línea de la clase:

```java
public class VentanaPrincipal extends JFrame implements ActionListener {
```

Aparecen dos errores, como muestra el material del profe:

1. `ActionListener` en rojo: falta el import. Apretá **Ctrl + Shift + O**.
2. `VentanaPrincipal` en rojo, porque todavía no tiene el método que la interfaz exige. Poné el cursor ahí, **Ctrl + 1** → **Add unimplemented methods**. Eclipse agrega:

```java
@Override
public void actionPerformed(ActionEvent e) {
}
```

Una **interfaz** es un contrato: si la ventana dice que es un `ActionListener`, se compromete a tener el método `actionPerformed`.

## 3. Conectar los componentes

En el constructor, sumá `addActionListener(this)` al desplegable del tipo y a los botones Agregar y Limpiar. El desplegable queda así:

```java
cmbTipo = new JComboBox<>();
cmbTipo.addItem("Libro");
cmbTipo.addItem("Artículo");
cmbTipo.addItem("Tesis");
cmbTipo.setBounds(110, 20, 180, 25);
cmbTipo.addActionListener(this);
contentPane.add(cmbTipo);
```

Y en los botones, antes de su `contentPane.add`:

```java
btnAgregar.addActionListener(this);
```

```java
btnLimpiar.addActionListener(this);
```

`this` es la propia ventana: «avisale a esta ventana cuando te toquen». Ojo con el desplegable: el `addActionListener` va **después** de los `addItem`. Si lo ponés antes, el primer `addItem` dispara un evento antes de tiempo y el programa se rompe. Modificar y Eliminar se conectan en la Lección 13.

## 4. La ventana recibe la biblioteca

La ventana necesita la `Biblioteca` para guardar lo que se agrega. Sumá el atributo debajo de los componentes:

```java
private Biblioteca biblioteca;
```

Y este método debajo del constructor:

```java
public void iniciar(Biblioteca biblioteca) {
    this.biblioteca = biblioteca;
}
```

En `principal`, creá la biblioteca y pasásela a la ventana:

```java
package proyecto;

public class principal {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.iniciar(biblioteca);
        ventana.setVisible(true);
    }
}
```

¿Por qué un método `iniciar` y no meter todo en el constructor? Porque WindowBuilder ejecuta el constructor para dibujar la vista Design: si adentro hubiera datos o archivos, la vista Design se rompe. El constructor arma la ventana; `iniciar` le da los datos.

## 5. actionPerformed: quién fue

```java
@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnAgregar) {
        agregarDocumento();
    } else if (e.getSource() == btnLimpiar) {
        limpiarFormulario();
    } else if (e.getSource() == cmbTipo) {
        actualizarEtiquetaExtra();
    }
}
```

`e` es el evento y `e.getSource()` devuelve el componente que lo generó. Acá sí se usa `==`: preguntamos si es **el mismo** botón, la misma caja, no si se parece. Cada rama llama a un método con nombre claro; los escribimos ahora, debajo de `actionPerformed`.

## 6. Validar el formulario

Es el `validar` de la Lección 8, pero leyendo los campos y avisando con un cartel:

```java
private boolean formularioValido() {
    if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El título y el autor son obligatorios.");
        return false;
    }
    try {
        int anio = Integer.parseInt(txtAnio.getText().trim());
        if (anio <= 0) {
            JOptionPane.showMessageDialog(this, "El año tiene que ser mayor que 0.");
            return false;
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "El año tiene que ser un número, por ejemplo 2015.");
        return false;
    }
    return true;
}
```

- `txtTitulo.getText()` devuelve lo que el usuario escribió.
- `JOptionPane.showMessageDialog(this, "...")` muestra un cartel con el mensaje y un botón Aceptar.
- El `catch` usa `ex` y no `e` porque `e` ya es el nombre del evento en `actionPerformed`; acá no chocan, pero así no se confunden.
- Los métodos internos de la ventana son `private`: solo la ventana los usa.

## 7. Agregar un documento

```java
private void agregarDocumento() {
    if (!formularioValido()) {
        return;
    }
    String titulo = txtTitulo.getText().trim();
    String autor = txtAutor.getText().trim();
    int anio = Integer.parseInt(txtAnio.getText().trim());
    int valoracion = (int) spnValoracion.getValue();
    String extra = txtExtra.getText().trim();
    String tipo = (String) cmbTipo.getSelectedItem();

    Documento nuevo;
    if (tipo.equals("Libro")) {
        nuevo = new Libro(titulo, autor, anio, valoracion, extra);
    } else if (tipo.equals("Artículo")) {
        nuevo = new Articulo(titulo, autor, anio, valoracion, extra);
    } else {
        nuevo = new Tesis(titulo, autor, anio, valoracion, extra);
    }
    biblioteca.agregar(nuevo);
    System.out.println("Documentos en la biblioteca: " + biblioteca.buscar("Todos", "").size());
    limpiarFormulario();
}
```

- Si el formulario no es válido, `return;` y no sigue (Lección 4).
- Lee cada campo con `getText().trim()`. El año pasa por `Integer.parseInt` sin riesgo: `formularioValido()` ya comprobó que es un número.
- `(int) spnValoracion.getValue()`: el spinner devuelve un objeto genérico; el *cast* lo convierte en `int`. Lo mismo `(String) cmbTipo.getSelectedItem()` con el tipo elegido.
- Según el tipo, crea un `Libro`, un `Articulo` o una `Tesis` y lo guarda en una variable `Documento`: polimorfismo, Lección 6.
- El `println` es provisorio, para ver en la consola que se guardó. En la Lección 12 lo cambiamos por la tabla.

## 8. Limpiar y cambiar la etiqueta

```java
private void limpiarFormulario() {
    txtTitulo.setText("");
    txtAutor.setText("");
    txtAnio.setText("");
    spnValoracion.setValue(3);
    txtExtra.setText("");
}

private void actualizarEtiquetaExtra() {
    String tipo = (String) cmbTipo.getSelectedItem();
    if (tipo.equals("Libro")) {
        lblExtra.setText("Editorial:");
    } else if (tipo.equals("Artículo")) {
        lblExtra.setText("Revista:");
    } else {
        lblExtra.setText("Universidad:");
    }
}
```

`actualizarEtiquetaExtra` es el `if` con `equals` de la Lección 2: cuando el usuario elige otro tipo, cambia «Editorial:» por «Revista:» o «Universidad:».

## 9. Probar

1. Ejecutá y elegí Artículo: la etiqueta tiene que decir «Revista:».
2. Escribí título y autor, y en el año «dos mil». Agregar: aparece el aviso del año.
3. Corregí el año y Agregar: el formulario se limpia y la consola dice «Documentos en la biblioteca: 1».
4. Dejá el título vacío y Agregar: aviso de obligatorios.

## 10. Punto de control

Así tiene que quedar `VentanaPrincipal.java`:

```java
package proyecto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cmbTipo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAnio;
    private JSpinner spnValoracion;
    private JLabel lblExtra;
    private JTextField txtExtra;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JLabel lblTiempo;

    private Biblioteca biblioteca;

    public VentanaPrincipal() {
        setTitle("Mi Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 520);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(20, 20, 90, 25);
        contentPane.add(lblTipo);

        cmbTipo = new JComboBox<>();
        cmbTipo.addItem("Libro");
        cmbTipo.addItem("Artículo");
        cmbTipo.addItem("Tesis");
        cmbTipo.setBounds(110, 20, 180, 25);
        cmbTipo.addActionListener(this);
        contentPane.add(cmbTipo);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(20, 60, 90, 25);
        contentPane.add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(110, 60, 180, 25);
        contentPane.add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(20, 100, 90, 25);
        contentPane.add(lblAutor);

        txtAutor = new JTextField();
        txtAutor.setBounds(110, 100, 180, 25);
        contentPane.add(txtAutor);

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setBounds(20, 140, 90, 25);
        contentPane.add(lblAnio);

        txtAnio = new JTextField();
        txtAnio.setBounds(110, 140, 180, 25);
        contentPane.add(txtAnio);

        JLabel lblValoracion = new JLabel("Valoración:");
        lblValoracion.setBounds(20, 180, 90, 25);
        contentPane.add(lblValoracion);

        spnValoracion = new JSpinner();
        spnValoracion.setModel(new SpinnerNumberModel(3, 1, 5, 1));
        spnValoracion.setBounds(110, 180, 60, 25);
        contentPane.add(spnValoracion);

        lblExtra = new JLabel("Editorial:");
        lblExtra.setBounds(20, 220, 90, 25);
        contentPane.add(lblExtra);

        txtExtra = new JTextField();
        txtExtra.setBounds(110, 220, 180, 25);
        contentPane.add(txtExtra);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 270, 130, 30);
        btnAgregar.addActionListener(this);
        contentPane.add(btnAgregar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(160, 270, 130, 30);
        contentPane.add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(20, 310, 130, 30);
        contentPane.add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(160, 310, 130, 30);
        btnLimpiar.addActionListener(this);
        contentPane.add(btnLimpiar);

        lblTiempo = new JLabel("");
        lblTiempo.setBounds(20, 440, 280, 25);
        contentPane.add(lblTiempo);
    }

    public void iniciar(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            agregarDocumento();
        } else if (e.getSource() == btnLimpiar) {
            limpiarFormulario();
        } else if (e.getSource() == cmbTipo) {
            actualizarEtiquetaExtra();
        }
    }

    private void agregarDocumento() {
        if (!formularioValido()) {
            return;
        }
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        int anio = Integer.parseInt(txtAnio.getText().trim());
        int valoracion = (int) spnValoracion.getValue();
        String extra = txtExtra.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        Documento nuevo;
        if (tipo.equals("Libro")) {
            nuevo = new Libro(titulo, autor, anio, valoracion, extra);
        } else if (tipo.equals("Artículo")) {
            nuevo = new Articulo(titulo, autor, anio, valoracion, extra);
        } else {
            nuevo = new Tesis(titulo, autor, anio, valoracion, extra);
        }
        biblioteca.agregar(nuevo);
        System.out.println("Documentos en la biblioteca: " + biblioteca.buscar("Todos", "").size());
        limpiarFormulario();
    }

    private boolean formularioValido() {
        if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título y el autor son obligatorios.");
            return false;
        }
        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            if (anio <= 0) {
                JOptionPane.showMessageDialog(this, "El año tiene que ser mayor que 0.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año tiene que ser un número, por ejemplo 2015.");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnio.setText("");
        spnValoracion.setValue(3);
        txtExtra.setText("");
    }

    private void actualizarEtiquetaExtra() {
        String tipo = (String) cmbTipo.getSelectedItem();
        if (tipo.equals("Libro")) {
            lblExtra.setText("Editorial:");
        } else if (tipo.equals("Artículo")) {
            lblExtra.setText("Revista:");
        } else {
            lblExtra.setText("Universidad:");
        }
    }
}
```

## 11. Ejercicios

- [ ] Agregá un aviso cuando el documento se agrega bien, con `JOptionPane.showMessageDialog(this, "Documento agregado");`. Probalo y decidí si lo dejás.
- [ ] Poné `System.out.println(e.getSource());` como primera línea de `actionPerformed` y mirá en la consola qué aparece al tocar cada cosa. Después borralo.
- [ ] Explicá en el cuaderno, con tus palabras, el recorrido completo desde que tocás Agregar hasta que el documento queda en la biblioteca.

## 12. Preguntas tipo defensa

1. ¿Qué es un evento? ¿Qué es una interfaz?
2. ¿Qué hace `btnAgregar.addActionListener(this)`? ¿Qué es `this` ahí?
3. ¿Cómo sabe `actionPerformed` qué botón se apretó?
4. ¿Por qué `getSource()` se compara con `==` y los textos con `equals`?
5. ¿Para qué sirve el cast `(int) spnValoracion.getValue()`?
6. ¿Por qué la biblioteca llega con `iniciar` y no por el constructor?

**Cuando termines, mandame** `VentanaPrincipal`, `principal` y tus respuestas.

---

[← Lección 10](leccion-10.md) · [Índice](README.md) · [Lección 12 →](leccion-12.md)
