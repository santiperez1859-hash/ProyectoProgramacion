# Lección 12 · JTable: mostrar, filtrar y buscar

Hoy armás la mitad derecha de la ventana: la tabla con todos los documentos, el filtro por tipo y el buscador por título. Con esto cumplís el «ver» de la consigna y dos de los puntos que suman. Tiempo estimado: 2 horas.

## 1. La idea: la tabla y su modelo

Una `JTable` tiene dos partes: la **tabla**, que es lo que se ve, y el **modelo** (`DefaultTableModel`), que guarda las columnas y las filas. Nunca escribimos en la tabla: cargamos el modelo y la tabla se dibuja sola. La tabla va adentro de un `JScrollPane`, que agrega la barra para desplazarse cuando hay muchas filas.

## 2. Los componentes de la derecha

| Componente | Variable | Texto | setBounds |
| --- | --- | --- | --- |
| JLabel | `lblFiltro` (local) | Mostrar: | 320, 20, 60, 25 |
| JComboBox | `cmbFiltro` |  | 380, 20, 120, 25 |
| JLabel | `lblBuscar` (local) | Buscar título: | 520, 20, 90, 25 |
| JTextField | `txtBuscar` |  | 610, 20, 160, 25 |
| JButton | `btnBuscar` | Buscar | 780, 20, 90, 25 |
| JScrollPane | `scrollPane` (local) |  | 320, 60, 550, 405 |
| JTable | `tabla` |  | adentro del scrollPane |

En Design: primero el JScrollPane (Palette, sección Containers) y después soltá el JTable **adentro** de él. Convertí a atributo `cmbFiltro`, `txtBuscar`, `btnBuscar` y `tabla`.

El desplegable del filtro se carga y se conecta igual que el del tipo:

```java
cmbFiltro = new JComboBox<>();
cmbFiltro.addItem("Todos");
cmbFiltro.addItem("Libro");
cmbFiltro.addItem("Artículo");
cmbFiltro.addItem("Tesis");
cmbFiltro.setBounds(380, 20, 120, 25);
cmbFiltro.addActionListener(this);
contentPane.add(cmbFiltro);
```

Las opciones tienen que ser **idénticas** a lo que devuelve `getTipo()`, con la tilde de «Artículo» incluida, porque `buscar` las compara con `equals`. Conectá también el botón: `btnBuscar.addActionListener(this);`.

## 3. Configurar la tabla

Sumá el atributo `private DefaultTableModel modelo;` y, en el constructor, dejá la tabla así:

```java
modelo = new DefaultTableModel();
modelo.addColumn("Tipo");
modelo.addColumn("Título");
modelo.addColumn("Autor");
modelo.addColumn("Año");
modelo.addColumn("Valoración");
modelo.addColumn("Dato extra");

tabla = new JTable();
tabla.setModel(modelo);
tabla.setDefaultEditor(Object.class, null);
tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
scrollPane.setViewportView(tabla);
```

- `addColumn` crea cada columna con su título.
- `setModel(modelo)` conecta la tabla con el modelo.
- `setDefaultEditor(Object.class, null)` impide editar las celdas con doble clic: los cambios se hacen solo desde el formulario.
- `setSelectionMode(ListSelectionModel.SINGLE_SELECTION)` deja seleccionar una sola fila a la vez.
- `scrollPane.setViewportView(tabla)` pone la tabla adentro del scroll. WindowBuilder lo escribe solo si la soltaste adentro.

## 4. actualizarTabla: de la lista a las filas

Sumá el atributo `private ArrayList<Documento> mostrados;` (con su `import java.util.ArrayList;`) y este método al final de la clase:

```java
private void actualizarTabla() {
    String tipo = (String) cmbFiltro.getSelectedItem();
    String texto = txtBuscar.getText().trim();
    mostrados = biblioteca.buscar(tipo, texto);
    modelo.setRowCount(0);
    for (int i = 0; i < mostrados.size(); i++) {
        Documento d = mostrados.get(i);
        modelo.addRow(new Object[] { d.getTipo(), d.getTitulo(), d.getAutor(), d.getAnio(), d.getValoracion(),
                d.getDatoExtra() });
    }
}
```

Leelo así:

1. Lee el filtro elegido y el texto buscado.
2. Le pide a la biblioteca la lista que coincide (el `buscar` de la Lección 7) y la guarda en `mostrados`: los documentos que se están viendo, en el mismo orden que las filas.
3. `modelo.setRowCount(0)` borra todas las filas viejas.
4. Recorre la lista y agrega una fila por documento. `new Object[] { ... }` es una fila: un grupo de valores, uno por columna, en el orden de las columnas.

`mostrados` va a ser clave en la Lección 13: la fila 0 de la tabla es `mostrados.get(0)`, aunque haya un filtro puesto.

## 5. Llamar a actualizarTabla

En tres lugares:

1. En `iniciar`, para mostrar lo que haya al abrir:

```java
public void iniciar(Biblioteca biblioteca) {
    this.biblioteca = biblioteca;
    actualizarTabla();
}
```

2. En `agregarDocumento`, en lugar del `println` provisorio: `actualizarTabla();`.
3. En `actionPerformed`, cuando cambian el filtro o tocan Buscar, con una rama nueva antes de la de `cmbTipo`:

```java
} else if (e.getSource() == btnBuscar || e.getSource() == cmbFiltro) {
    actualizarTabla();
```

El filtro funciona apenas elegís una opción, porque el desplegable también avisa a `actionPerformed`.

## 6. Probar

1. Agregá tres documentos de tipos distintos: aparecen en la tabla.
2. En Mostrar elegí «Libro»: quedan solo los libros.
3. Volvé a «Todos», escribí parte de un título y tocá Buscar.
4. Borrá el texto de búsqueda y tocá Buscar: vuelven todos.
5. Doble clic en una celda: no se tiene que poder editar.

## 7. Punto de control

Así tiene que quedar `VentanaPrincipal.java`:

```java
package proyecto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
    private JComboBox<String> cmbFiltro;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tabla;
    private DefaultTableModel modelo;

    private Biblioteca biblioteca;
    private ArrayList<Documento> mostrados;

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

        JLabel lblFiltro = new JLabel("Mostrar:");
        lblFiltro.setBounds(320, 20, 60, 25);
        contentPane.add(lblFiltro);

        cmbFiltro = new JComboBox<>();
        cmbFiltro.addItem("Todos");
        cmbFiltro.addItem("Libro");
        cmbFiltro.addItem("Artículo");
        cmbFiltro.addItem("Tesis");
        cmbFiltro.setBounds(380, 20, 120, 25);
        cmbFiltro.addActionListener(this);
        contentPane.add(cmbFiltro);

        JLabel lblBuscar = new JLabel("Buscar título:");
        lblBuscar.setBounds(520, 20, 90, 25);
        contentPane.add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(610, 20, 160, 25);
        contentPane.add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(780, 20, 90, 25);
        btnBuscar.addActionListener(this);
        contentPane.add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(320, 60, 550, 405);
        contentPane.add(scrollPane);

        modelo = new DefaultTableModel();
        modelo.addColumn("Tipo");
        modelo.addColumn("Título");
        modelo.addColumn("Autor");
        modelo.addColumn("Año");
        modelo.addColumn("Valoración");
        modelo.addColumn("Dato extra");

        tabla = new JTable();
        tabla.setModel(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(tabla);
    }

    public void iniciar(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        actualizarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            agregarDocumento();
        } else if (e.getSource() == btnLimpiar) {
            limpiarFormulario();
        } else if (e.getSource() == btnBuscar || e.getSource() == cmbFiltro) {
            actualizarTabla();
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
        actualizarTabla();
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

    private void actualizarTabla() {
        String tipo = (String) cmbFiltro.getSelectedItem();
        String texto = txtBuscar.getText().trim();
        mostrados = biblioteca.buscar(tipo, texto);
        modelo.setRowCount(0);
        for (int i = 0; i < mostrados.size(); i++) {
            Documento d = mostrados.get(i);
            modelo.addRow(new Object[] { d.getTipo(), d.getTitulo(), d.getAutor(), d.getAnio(), d.getValoracion(),
                    d.getDatoExtra() });
        }
    }
}
```

## 8. Ejercicios

- [ ] Hacé que la búsqueda también se dispare con Enter: `txtBuscar.addActionListener(this);` y sumá `e.getSource() == txtBuscar` a la condición de buscar.
- [ ] Agregá una columna «Antigüedad» (2026 menos el año): una columna más en el modelo y un valor más en cada fila. Después sacala si no te convence.
- [ ] Escribí «Articulo» sin tilde en el `addItem` del filtro y probá filtrar: ¿por qué no aparece nada? Volvé a dejarlo bien.

## 9. Preguntas tipo defensa

1. ¿Qué diferencia hay entre la tabla y el modelo?
2. ¿Qué hace `modelo.setRowCount(0)` y por qué va antes del `for`?
3. ¿Qué es `new Object[] { ... }`?
4. ¿Para qué sirve la lista `mostrados`?
5. ¿Cómo funciona el filtro por tipo? ¿Y la búsqueda por título?
6. ¿Por qué no se pueden editar las celdas?

**Cuando termines, mandame** una captura con la tabla filtrada y tus respuestas.

---

[← Lección 11](leccion-11.md) · [Índice](README.md) · [Lección 13 →](leccion-13.md)
