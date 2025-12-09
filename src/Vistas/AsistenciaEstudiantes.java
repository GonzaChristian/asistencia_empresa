package Vistas;

import DAO.AsistenciaDAO;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AsistenciaEstudiantes extends javax.swing.JFrame {

    private AsistenciaDAO asistenciaDAO;
    private DefaultTableModel modeloTabla;
    
    public AsistenciaEstudiantes() {
        initComponents();
        this.setLocationRelativeTo(null);
        asistenciaDAO = new AsistenciaDAO();
        inicializarTabla();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {

        cmbCarrera.setSelectedIndex(0);
        cmbCiclo.setSelectedIndex(0);
        cmbTurno.setSelectedIndex(0);
        
        // Configurar fecha actual en el spinner
        jSpinnerFecha.setValue(new java.util.Date());
    }
    
    private void inicializarTabla() {
        modeloTabla = new DefaultTableModel(
            new Object[]{"DNI", "Nombres", "Apellidos", "Carrera", "Ciclo", "Turno", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_estudiantes.setModel(modeloTabla);
    }
    
    private void cargarDatos() {
        limpiarTabla();
        
        // Obtener fecha seleccionada
        java.util.Date fechaUtil = (java.util.Date) jSpinnerFecha.getValue();
        Date fechaSQL = new Date(fechaUtil.getTime());
        
        // Obtener filtros seleccionados
        String carrera = cmbCarrera.getSelectedItem().toString();
        String ciclo = cmbCiclo.getSelectedItem().toString();
        String turno = cmbTurno.getSelectedItem().toString();
        
        // Obtener estudiantes con asistencia
        List<Map<String, Object>> lista = asistenciaDAO.obtenerEstudiantesConAsistencia(
            fechaSQL, carrera, ciclo, turno
        );
        
        for (Map<String, Object> fila : lista) {
            Object[] datos = {
                fila.get("dni"),
                fila.get("nombres"),
                fila.get("apellidos"),
                fila.get("carrera"),
                fila.get("ciclo"),
                fila.get("turno"),
                fila.get("estado")
            };
            modeloTabla.addRow(datos);
        }
    }
    
    private void limpiarTabla() {
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
    }
    
    private void marcarAsistencia(String estadoAsistencia) {
        int filaSeleccionada = table_estudiantes.getSelectedRow();
        
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un estudiante de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Obtener DNI del estudiante seleccionado
            String dni = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
            
            // Obtener ID del estudiante desde la base de datos
            int idEstudiante = obtenerIdEstudiantePorDNI(dni);
            
            if (idEstudiante == -1) {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo obtener el ID del estudiante", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener fecha seleccionada
            java.util.Date fechaUtil = (java.util.Date) jSpinnerFecha.getValue();
            Date fechaSQL = new Date(fechaUtil.getTime());
            
            // Registrar asistencia
            if (asistenciaDAO.registrarAsistenciaEstudiante(idEstudiante, fechaSQL, estadoAsistencia)) {
                JOptionPane.showMessageDialog(this, 
                    "Asistencia marcada como: " + estadoAsistencia, 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatos(); // Recargar la tabla
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al registrar asistencia", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int obtenerIdEstudiantePorDNI(String dni) {
        // Buscar el ID en la lista actual
        List<Map<String, Object>> lista = asistenciaDAO.obtenerEstudiantesConAsistencia(
            new Date(((java.util.Date) jSpinnerFecha.getValue()).getTime()),
            cmbCarrera.getSelectedItem().toString(),
            cmbCiclo.getSelectedItem().toString(),
            cmbTurno.getSelectedItem().toString()
        );
        
        for (Map<String, Object> fila : lista) {
            if (fila.get("dni").toString().equals(dni)) {
                return (int) fila.get("id_estudiante");
            }
        }
        return -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnMenuAsistencia = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_estudiantes = new javax.swing.JTable();
        cmbTurno = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnMarcarTardanza = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSpinnerFecha = new javax.swing.JSpinner();
        cmbCarrera = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbCiclo = new javax.swing.JComboBox<>();
        btnMarcarAsistencia = new javax.swing.JButton();
        btnMarcarFalta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(181, 181, 181));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setText("ASISTENCIA DE ESTUDIANTES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 690, 80));

        btnMenuAsistencia.setBackground(new java.awt.Color(181, 181, 181));
        btnMenuAsistencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/boton_menu_80.png"))); // NOI18N
        btnMenuAsistencia.setBorder(null);
        btnMenuAsistencia.setOpaque(true);
        btnMenuAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuAsistenciaActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenuAsistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 80, 80));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_iestpvillamaria_80.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 80));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 100));

        table_estudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "DNI", "Nombres", "Apellidos", "Carrera", "Ciclo", "Turno", "Estado"
            }
        ));
        jScrollPane1.setViewportView(table_estudiantes);
        if (table_estudiantes.getColumnModel().getColumnCount() > 0) {
            table_estudiantes.getColumnModel().getColumn(0).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(1).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(2).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(3).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(4).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(5).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(6).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 950, 400));

        cmbTurno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mañana", "Noche" }));
        cmbTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTurnoActionPerformed(evt);
            }
        });
        getContentPane().add(cmbTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 130, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Turno:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, -1, -1));

        btnMarcarTardanza.setBackground(new java.awt.Color(255, 204, 51));
        btnMarcarTardanza.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMarcarTardanza.setForeground(new java.awt.Color(255, 255, 255));
        btnMarcarTardanza.setText("Marcar Tardanza");
        btnMarcarTardanza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarTardanzaActionPerformed(evt);
            }
        });
        getContentPane().add(btnMarcarTardanza, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 160, 160, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Fecha:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, 70, 30));

        jSpinnerFecha.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_WEEK));
        jSpinnerFecha.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerFechaStateChanged(evt);
            }
        });
        getContentPane().add(jSpinnerFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 180, -1, 30));

        cmbCarrera.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbCarrera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Computación e Informática", "Enfermería Técnica", "Cosmética Dermatológica", "Industrias Alimentarias" }));
        cmbCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCarreraActionPerformed(evt);
            }
        });
        getContentPane().add(cmbCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 130, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Carrera:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Ciclo:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, -1, -1));

        cmbCiclo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbCiclo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I", "II", "III", "IV", "V", "VI" }));
        cmbCiclo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCicloActionPerformed(evt);
            }
        });
        getContentPane().add(cmbCiclo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 130, 30));

        btnMarcarAsistencia.setBackground(new java.awt.Color(51, 204, 0));
        btnMarcarAsistencia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMarcarAsistencia.setForeground(new java.awt.Color(255, 255, 255));
        btnMarcarAsistencia.setText("Marcar Asistencia");
        btnMarcarAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarAsistenciaActionPerformed(evt);
            }
        });
        getContentPane().add(btnMarcarAsistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 110, 160, 40));

        btnMarcarFalta.setBackground(new java.awt.Color(255, 51, 51));
        btnMarcarFalta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMarcarFalta.setForeground(new java.awt.Color(255, 255, 255));
        btnMarcarFalta.setText("Marcar Falta");
        btnMarcarFalta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarFaltaActionPerformed(evt);
            }
        });
        getContentPane().add(btnMarcarFalta, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 210, 160, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuAsistenciaActionPerformed
        VistaMenuPrincipal menu = new VistaMenuPrincipal();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
        this.dispose();

    }//GEN-LAST:event_btnMenuAsistenciaActionPerformed

    private void cmbTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTurnoActionPerformed
       cargarDatos();
    }//GEN-LAST:event_cmbTurnoActionPerformed

    private void btnMarcarTardanzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarTardanzaActionPerformed
        marcarAsistencia("Tardanza");
    }//GEN-LAST:event_btnMarcarTardanzaActionPerformed

    private void cmbCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCarreraActionPerformed
        cargarDatos();
    }//GEN-LAST:event_cmbCarreraActionPerformed

    private void cmbCicloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCicloActionPerformed
        cargarDatos();
    }//GEN-LAST:event_cmbCicloActionPerformed

    private void btnMarcarAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarAsistenciaActionPerformed
        marcarAsistencia("Presente");
    }//GEN-LAST:event_btnMarcarAsistenciaActionPerformed

    private void btnMarcarFaltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarFaltaActionPerformed
        marcarAsistencia("Ausente");
    }//GEN-LAST:event_btnMarcarFaltaActionPerformed

    private void jSpinnerFechaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerFechaStateChanged
        cargarDatos();
    }//GEN-LAST:event_jSpinnerFechaStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AsistenciaEstudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMarcarAsistencia;
    private javax.swing.JButton btnMarcarFalta;
    private javax.swing.JButton btnMarcarTardanza;
    private javax.swing.JButton btnMenuAsistencia;
    private javax.swing.JComboBox<String> cmbCarrera;
    private javax.swing.JComboBox<String> cmbCiclo;
    private javax.swing.JComboBox<String> cmbTurno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerFecha;
    private javax.swing.JTable table_estudiantes;
    // End of variables declaration//GEN-END:variables
}
