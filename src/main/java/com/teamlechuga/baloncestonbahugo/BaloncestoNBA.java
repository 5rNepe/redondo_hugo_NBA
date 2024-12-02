/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.teamlechuga.baloncestonbahugo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;

/**
 *
 * @author GS2
 */
public class BaloncestoNBA extends javax.swing.JFrame {

    /**
     * Creates new form BaloncestoNBA
     */
    public BaloncestoNBA() {
        initComponents();
        setTitle("Estadísticas de Baloncesto");
        setSize(600, 400);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarBotones();
    }
    
    private void iniciarBotones() {
        Tiros2Realizados.addChangeListener(e -> {
            TirosTotales.setValue((int) Tiros2Realizados.getValue() + (int) Tiros3Realizados.getValue() + (int) TirosLibresRealizados.getValue());
        });
        Tiros3Realizados.addChangeListener(e -> {
            TirosTotales.setValue((int) Tiros2Realizados.getValue() + (int) Tiros3Realizados.getValue() + (int) TirosLibresRealizados.getValue());
        });
        TirosLibresRealizados.addChangeListener(e -> {
            TirosTotales.setValue((int) Tiros2Realizados.getValue() + (int) Tiros3Realizados.getValue() + (int) TirosLibresRealizados.getValue());
        });
        
        int Tiross2 = (int) Tiros2.getValue();

        Calcular.addActionListener(e -> {
            calcularYExportar(NombreJugador, Tiros2, Tiros2Realizados, Tiros3, Tiros3Realizados, TirosLibres, TirosLibresRealizados, TirosTotales);
        });
    }
    
    public static void escribirEnExcel(String nombreJugador, int tiros2, int tiros2Realizados, int tiros3, int tiros3Realizados, int tirosLibres, int tirosLibresRealizados, int tirosTotales) {
        File archivoExcel = new File("C:\\Users\\GS2\\Desktop\\EstadisticasBaloncesto.xlsx");
        Workbook libro = null;
        Sheet hoja;

        try {
            if (archivoExcel.exists()) {
                FileInputStream fis = new FileInputStream(archivoExcel);
                libro = new XSSFWorkbook(fis);
                hoja = libro.getSheetAt(0);
                fis.close();
            } else {
                libro = new XSSFWorkbook();
                hoja = libro.createSheet("Estadísticas de Jugadores");

                Row headerRow = hoja.createRow(0);
                headerRow.createCell(0).setCellValue("Nombre del Jugador");
                headerRow.createCell(1).setCellValue("Tiros de 2 Metidos");
                headerRow.createCell(2).setCellValue("Tiros de 2 Realizados");
                headerRow.createCell(3).setCellValue("Tiros de 3 Metidos");
                headerRow.createCell(4).setCellValue("Tiros de 3 Realizados");
                headerRow.createCell(5).setCellValue("Tiros Libres Metidos");
                headerRow.createCell(6).setCellValue("Tiros Libres Realizados");
                headerRow.createCell(7).setCellValue("Tiros Totales");
                headerRow.createCell(8).setCellValue("FG% (Porcentaje de tiros anotados)");
                headerRow.createCell(9).setCellValue("eFG% (Porcentaje efectivo)");
                headerRow.createCell(10).setCellValue("TS% (Tiro Real)");
            }
            
            int ultimaFila = hoja.getLastRowNum();
            boolean hayFilaDeMedias = hoja.getRow(ultimaFila).getCell(0).getStringCellValue().equals("Medias");

            if (hayFilaDeMedias) {
                hoja.removeRow(hoja.getRow(ultimaFila));
            }

            int siguiente = hoja.getLastRowNum() + 1;
            Row dataRow = hoja.createRow(siguiente);

            dataRow.createCell(0).setCellValue(nombreJugador);
            dataRow.createCell(1).setCellValue(tiros2);
            dataRow.createCell(2).setCellValue(tiros2Realizados);
            dataRow.createCell(3).setCellValue(tiros3);
            dataRow.createCell(4).setCellValue(tiros3Realizados);
            dataRow.createCell(5).setCellValue(tirosLibres);
            dataRow.createCell(6).setCellValue(tirosLibresRealizados);
            dataRow.createCell(7).setCellValue(tirosTotales);

            Integer fga = tiros2Realizados + tiros3Realizados;
            double fg = (tirosTotales > 0) ? ((double) (tiros2 + tiros3) / fga) * 100 : 0;
            dataRow.createCell(8).setCellValue(String.format("%.2f%%", fg));

            double efg = (tirosTotales > 0) ? ((tiros2 + (0.5 * tiros3)) / fga) * 100: 0;
            dataRow.createCell(9).setCellValue(String.format("%.2f%%", efg));
            
            Integer puntos = (2 * tiros2) + (3 * tiros3) + (tirosLibres);
            
            double ts = (tirosTotales > 0) ? (puntos / (2 * (fga + (0.44 * tirosLibresRealizados)))) * 100 : 0;
            dataRow.createCell(10).setCellValue(String.format("%.2f%%", ts));

            int filasDatos = hoja.getLastRowNum();
            Row filaMedias = hoja.createRow(filasDatos + 1);
            filaMedias.createCell(0).setCellValue("Medias");

            for (int col = 1; col <= 10; col++) {
                double suma = 0;
                int totalFilas = 0;
                for (int fila = 1; fila <= filasDatos; fila++) {
                    Row row = hoja.getRow(fila);
                    if (row != null && row.getCell(col) != null) {
                        Cell cell = row.getCell(col);
                        if (cell.getCellType() == CellType.NUMERIC) {
                            suma += cell.getNumericCellValue();
                            totalFilas++;
                        } else if (cell.getCellType() == CellType.STRING) {
                            String valor = cell.getStringCellValue().replace("%", "").replace(",", ".").trim();
                            if (!valor.isEmpty()) {
                                suma += Double.parseDouble(valor);
                                totalFilas++;
                            }
                        }
                    }
                }
                double media = totalFilas > 0 ? suma / totalFilas : 0;
                if (col >= 8) {
                    filaMedias.createCell(col).setCellValue(String.format("%.2f%%", media));
                } else {
                    filaMedias.createCell(col).setCellValue(media);
                }
            }
            
            for (int i = 0; i < 11; i++) {
                hoja.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(archivoExcel)) {
                libro.write(fileOut);
                JOptionPane.showMessageDialog(null, "Datos exportados a Excel correctamente.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al escribir en Excel: " + e.getMessage());
        } finally {
            try {
                if (libro != null) {
                    libro.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void calcularYExportar(JTextField NombreJugador, JSpinner Tiros2, JSpinner Tiros2Realizados, JSpinner Tiros3, JSpinner Tiros3Realizados, JSpinner TirosLibres, JSpinner TirosLibresRealizados, JSpinner TirosTotales) {
        String nombreJugador = NombreJugador.getText();
        int tiros2 = (int) Tiros2.getValue();
        int tiros2Realizados = (int) Tiros2Realizados.getValue();
        int tiros3 = (int) Tiros3.getValue();
        int tiros3Realizados = (int) Tiros3Realizados.getValue();
        int tirosLibres = (int) TirosLibres.getValue();
        int tirosLibresRealizados = (int) TirosLibresRealizados.getValue();
        int tirosTotales = (int) TirosTotales.getValue();

        if (nombreJugador.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del jugador es obligatorio.");
            return;
        }

        escribirEnExcel(nombreJugador, tiros2, tiros2Realizados, tiros3, tiros3Realizados, tirosLibres, tirosLibresRealizados, tirosTotales);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        NombreJugadorTexto = new javax.swing.JLabel();
        Tiros2Texto = new javax.swing.JLabel();
        Tiros2Texto2 = new javax.swing.JLabel();
        Tiros3Texto = new javax.swing.JLabel();
        Tiros3Texto3 = new javax.swing.JLabel();
        TirosLibresTexto = new javax.swing.JLabel();
        TirosLibresTexto2 = new javax.swing.JLabel();
        TirosTotalesTexto = new javax.swing.JLabel();
        NombreJugador = new javax.swing.JTextField();
        Tiros2 = new javax.swing.JSpinner();
        Tiros2Realizados = new javax.swing.JSpinner();
        Tiros3 = new javax.swing.JSpinner();
        Tiros3Realizados = new javax.swing.JSpinner();
        TirosLibres = new javax.swing.JSpinner();
        TirosLibresRealizados = new javax.swing.JSpinner();
        TirosTotales = new javax.swing.JSpinner();
        Calcular = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        NombreJugadorTexto.setText("Nombre de Jugador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(NombreJugadorTexto, gridBagConstraints);

        Tiros2Texto.setText("Tiros metidos de 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(Tiros2Texto, gridBagConstraints);

        Tiros2Texto2.setText("Tiros de 2 realizados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        getContentPane().add(Tiros2Texto2, gridBagConstraints);

        Tiros3Texto.setText("Tiros metidos de 3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(Tiros3Texto, gridBagConstraints);

        Tiros3Texto3.setText("Tiros de 3 realizados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        getContentPane().add(Tiros3Texto3, gridBagConstraints);

        TirosLibresTexto.setText("Tiros libres metidos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        getContentPane().add(TirosLibresTexto, gridBagConstraints);

        TirosLibresTexto2.setText("Tiros libres realizados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        getContentPane().add(TirosLibresTexto2, gridBagConstraints);

        TirosTotalesTexto.setText("Tiros totales");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(TirosTotalesTexto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(NombreJugador, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(Tiros2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(Tiros2Realizados, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(Tiros3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(Tiros3Realizados, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(TirosLibres, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(TirosLibresRealizados, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(TirosTotales, gridBagConstraints);

        Calcular.setText("Calcular");
        Calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CalcularActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        getContentPane().add(Calcular, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalcularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CalcularActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BaloncestoNBA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BaloncestoNBA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BaloncestoNBA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BaloncestoNBA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BaloncestoNBA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Calcular;
    private javax.swing.JTextField NombreJugador;
    private javax.swing.JLabel NombreJugadorTexto;
    private javax.swing.JSpinner Tiros2;
    private javax.swing.JSpinner Tiros2Realizados;
    private javax.swing.JLabel Tiros2Texto;
    private javax.swing.JLabel Tiros2Texto2;
    private javax.swing.JSpinner Tiros3;
    private javax.swing.JSpinner Tiros3Realizados;
    private javax.swing.JLabel Tiros3Texto;
    private javax.swing.JLabel Tiros3Texto3;
    private javax.swing.JSpinner TirosLibres;
    private javax.swing.JSpinner TirosLibresRealizados;
    private javax.swing.JLabel TirosLibresTexto;
    private javax.swing.JLabel TirosLibresTexto2;
    private javax.swing.JSpinner TirosTotales;
    private javax.swing.JLabel TirosTotalesTexto;
    // End of variables declaration//GEN-END:variables
}
