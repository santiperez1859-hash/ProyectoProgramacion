package proyecto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame implements ActionListener, MouseListener {

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
	private Documento seleccionado;
	private Timer reloj;

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
		btnModificar.addActionListener(this);
		contentPane.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(20, 310, 130, 30);
		btnEliminar.addActionListener(this);
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
		tabla.addMouseListener(this);
		scrollPane.setViewportView(tabla);
	}

	public void iniciar(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
		actualizarTabla();
		reloj = new Timer(1000, this);
		if (biblioteca.pruebaTerminada()) {
			terminarPrueba();
		} else {
			mostrarTiempo();
			reloj.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAgregar) {
			agregarDocumento();
		} else if (e.getSource() == btnModificar) {
			modificarDocumento();
		} else if (e.getSource() == btnEliminar) {
			eliminarDocumento();
		} else if (e.getSource() == btnLimpiar) {
			limpiarFormulario();
		} else if (e.getSource() == btnBuscar || e.getSource() == cmbFiltro) {
			actualizarTabla();
		} else if (e.getSource() == cmbTipo) {
			actualizarEtiquetaExtra();
		} else if (e.getSource() == reloj) {
			pasarUnSegundo();
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
		guardarYActualizar();
		limpiarFormulario();
	}

	private void modificarDocumento() {
		if (seleccionado == null) {
			JOptionPane.showMessageDialog(this, "Primero hacé clic en un documento de la tabla.");
			return;
		}
		if (!formularioValido()) {
			return;
		}
		seleccionado.setTitulo(txtTitulo.getText().trim());
		seleccionado.setAutor(txtAutor.getText().trim());
		seleccionado.setAnio(Integer.parseInt(txtAnio.getText().trim()));
		seleccionado.setValoracion((int) spnValoracion.getValue());
		seleccionado.setDatoExtra(txtExtra.getText().trim());
		guardarYActualizar();
		limpiarFormulario();
	}

	private void eliminarDocumento() {
		if (seleccionado == null) {
			JOptionPane.showMessageDialog(this, "Primero hacé clic en un documento de la tabla.");
			return;
		}
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Borrar " + seleccionado.getTitulo() + "?", "Eliminar",
				JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			biblioteca.eliminar(seleccionado);
			guardarYActualizar();
			limpiarFormulario();
		}
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

	private void cargarSeleccionado() {
		int fila = tabla.getSelectedRow();
		if (fila == -1) {
			return;
		}
		seleccionado = mostrados.get(fila);
		cmbTipo.setSelectedItem(seleccionado.getTipo());
		cmbTipo.setEnabled(false);
		txtTitulo.setText(seleccionado.getTitulo());
		txtAutor.setText(seleccionado.getAutor());
		txtAnio.setText(String.valueOf(seleccionado.getAnio()));
		spnValoracion.setValue(seleccionado.getValoracion());
		txtExtra.setText(seleccionado.getDatoExtra());
	}

	private void limpiarFormulario() {
		seleccionado = null;
		txtTitulo.setText("");
		txtAutor.setText("");
		txtAnio.setText("");
		spnValoracion.setValue(3);
		txtExtra.setText("");
		tabla.clearSelection();
		if (!biblioteca.pruebaTerminada()) {
			cmbTipo.setEnabled(true);
		}
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

	private void guardarYActualizar() {
		Archivo.guardar(biblioteca);
		actualizarTabla();
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

	private void pasarUnSegundo() {
		biblioteca.sumarSegundo();
		Archivo.guardar(biblioteca);
		if (biblioteca.pruebaTerminada()) {
			terminarPrueba();
			JOptionPane.showMessageDialog(this, "Se terminó la versión de prueba. Desde ahora solo podés ver los documentos.");
		} else {
			mostrarTiempo();
		}
	}

	private void mostrarTiempo() {
		int restantes = biblioteca.getSegundosRestantes();
		lblTiempo.setText("Prueba: quedan " + restantes / 60 + " min " + restantes % 60 + " s");
	}

	private void terminarPrueba() {
		reloj.stop();
		cmbTipo.setEnabled(false);
		txtTitulo.setEnabled(false);
		txtAutor.setEnabled(false);
		txtAnio.setEnabled(false);
		spnValoracion.setEnabled(false);
		txtExtra.setEnabled(false);
		btnAgregar.setEnabled(false);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);
		lblTiempo.setText("Prueba terminada: solo lectura.");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == tabla) {
			cargarSeleccionado();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
