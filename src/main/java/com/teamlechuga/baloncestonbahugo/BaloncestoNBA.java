/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.teamlechuga.baloncestonbahugo;
import com.itextpdf.io.image.ImageData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.element.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;
import com.itextpdf.text.Element;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
        ElegirEquipo.addItem("Boston Celtics");
        ElegirEquipo.addItem("Chicago Bulls");
        String[] boston = {"Jrue Holiday", "Derrick White", "Payton Pritchard", "JD Davison", "Baylor Scheierman"};
        String[] chicago = {"Lonzo Ball", "Zach LaVine", "Torrey Craig", "Adama Sanogo", "Nikola Vucevic"};
        ElegirEquipo.addActionListener(e -> actualizarjugadores(ElegirEquipo, ElegirJugador, boston, chicago));
        Grafica.addActionListener(e -> crearGrafico(ElegirJugador, ElegirEquipo));
        
        
        int Tiross2 = (int) Tiros2.getValue();

        Calcular.addActionListener(e -> {
            calcularYExportar(ElegirEquipo, ElegirJugador, Tiros2, Tiros2Realizados, Tiros3, Tiros3Realizados, TirosLibres, TirosLibresRealizados, TirosTotales, Rebotes, Asistencias, Robos, Tapones, TaponesRecibidos, Perdidas, FaltasRecibidas, FaltasRealizadas);
        });
        
        PDF.addActionListener(e -> {
            generarPDF(ElegirEquipo, ElegirJugador);
        });
        menuTamanoo();
        condiciones.addActionListener(e -> {
            frameCondiciones ventanaCondiciones = new frameCondiciones();
            ventanaCondiciones.setVisible(true);
        });
        tirosmaximos.addActionListener(e -> {
            Object tiros1o = TirosLibresRealizados.getValue();
            int tiros1 = (Integer) tiros1o;
            Object tiros2o = Tiros2Realizados.getValue();
            int tiros2 = (Integer) tiros2o;
            Object tiros3o = Tiros3Realizados.getValue();
            int tiros3 = (Integer) tiros3o;
            int tirosIntentados = tiros1 + tiros2 + tiros3;
            Object tirostot = TirosTotales.getValue();
            int tirosRealizados = (Integer) tirostot;
            if (tirosRealizados > tirosIntentados) {
            FrameAvisoTiros frameAviso = new FrameAvisoTiros();
            frameAviso.setVisible(true);
        }
        });
    }
    
    public void menuTamanoo() {
        pequeño.addActionListener(e -> {
            Equipo.ajustarTamano(1);
            Jugador.ajustarTamano(1);
            Tiros2Texto.ajustarTamano(1);
            Tiros2Texto2.ajustarTamano(1);
            Tiros3Texto.ajustarTamano(1);
            Tiros3Texto2.ajustarTamano(1);
            TirosLibresTexto.ajustarTamano(1);
            TirosLibresTexto2.ajustarTamano(1);
            TirosTotalesTexto.ajustarTamano(1);
            RebotesTexto.ajustarTamano(1);
            AsistenciasTexto.ajustarTamano(1);
            RobosTexto.ajustarTamano(1);
            TaponesTexto.ajustarTamano(1);
            TaponesRecibidosTexto.ajustarTamano(1);
            PerdidasTexto.ajustarTamano(1);
            FaltasRecibidasTexto.ajustarTamano(1);
            FaltasRealizadasTexto.ajustarTamano(1);
        });

        mediano.addActionListener(e -> {
            Equipo.ajustarTamano(2);
            Jugador.ajustarTamano(2);
            Tiros2Texto.ajustarTamano(2);
            Tiros2Texto2.ajustarTamano(2);
            Tiros3Texto.ajustarTamano(2);
            Tiros3Texto2.ajustarTamano(2);
            TirosLibresTexto.ajustarTamano(2);
            TirosLibresTexto2.ajustarTamano(2);
            TirosTotalesTexto.ajustarTamano(2);
            RebotesTexto.ajustarTamano(2);
            AsistenciasTexto.ajustarTamano(2);
            RobosTexto.ajustarTamano(2);
            TaponesTexto.ajustarTamano(2);
            TaponesRecibidosTexto.ajustarTamano(2);
            PerdidasTexto.ajustarTamano(2);
            FaltasRecibidasTexto.ajustarTamano(2);
            FaltasRealizadasTexto.ajustarTamano(2);
        });

        grande.addActionListener(e -> {
            Equipo.ajustarTamano(3);
            Jugador.ajustarTamano(3);
            Tiros2Texto.ajustarTamano(3);
            Tiros2Texto2.ajustarTamano(3);
            Tiros3Texto.ajustarTamano(3);
            Tiros3Texto2.ajustarTamano(3);
            TirosLibresTexto.ajustarTamano(3);
            TirosLibresTexto2.ajustarTamano(3);
            TirosTotalesTexto.ajustarTamano(3);
            RebotesTexto.ajustarTamano(3);
            AsistenciasTexto.ajustarTamano(3);
            RobosTexto.ajustarTamano(3);
            TaponesTexto.ajustarTamano(3);
            TaponesRecibidosTexto.ajustarTamano(3);
            PerdidasTexto.ajustarTamano(3);
            FaltasRecibidasTexto.ajustarTamano(3);
            FaltasRealizadasTexto.ajustarTamano(3);
        });
    }
  
    public static void crearGrafico(JComboBox jugadorSeleccionado, JComboBox archivoEquipo) {
        String nombreJugador = (String) jugadorSeleccionado.getSelectedItem();
        String nombreEquipo = (String) archivoEquipo.getSelectedItem();
        String PATH_GRAFICOS = "C:\\Users\\GS2\\Desktop\\Graficos";

        if (nombreJugador == null || nombreJugador.isEmpty() || nombreJugador.equals(" ")) {
            JOptionPane.showMessageDialog(null, "Seleccione un jugador para generar el gráfico.");
            return;
        }

        File archivoExcel = new File("C:\\Users\\GS2\\Desktop\\" + nombreEquipo + " Estadisticas Baloncesto.xlsx");

        try {
            Workbook libro = new XSSFWorkbook(archivoExcel);
            Sheet hojaJugador = libro.getSheet(nombreJugador);

            if (hojaJugador == null) {
                JOptionPane.showMessageDialog(null, "No hay datos para el jugador seleccionado.");
                libro.close();
                return;
            }

            DefaultCategoryDataset datasetPuntos = new DefaultCategoryDataset();
            DefaultCategoryDataset datasetRebotes = new DefaultCategoryDataset();
            double totalPuntos = 0;
            int partidos = 0;

            for (int i = 1; i < hojaJugador.getPhysicalNumberOfRows(); i++) {
                Row row = hojaJugador.getRow(i);
                if (row != null) {
                    double puntos = (2 * row.getCell(0).getNumericCellValue())
                            + (3 * row.getCell(2).getNumericCellValue())
                            + row.getCell(4).getNumericCellValue();
                    double rebotes = row.getCell(11).getNumericCellValue();

                    datasetPuntos.addValue(puntos, "Puntos", "Partido " + i);
                    datasetRebotes.addValue(rebotes, "Rebotes", "Partido " + i);

                    totalPuntos += puntos;
                    partidos++;
                }
            }

            double mediaPuntos = partidos > 0 ? totalPuntos / partidos : 0;

            DefaultCategoryDataset datasetMedia = new DefaultCategoryDataset();
            for (int i = 1; i <= partidos; i++) {
                datasetMedia.addValue(mediaPuntos, "Media Puntos", "Partido " + i);
            }

            JFreeChart chartPuntos = ChartFactory.createBarChart(
                    "Estadísticas de " + nombreJugador + " - Puntos y Media",
                    "Partidos",
                    "Puntos",
                    datasetPuntos
            );

            CategoryPlot plotPuntos = chartPuntos.getCategoryPlot();
            plotPuntos.setDataset(1, datasetMedia);
            LineAndShapeRenderer rendererMedia = new LineAndShapeRenderer();
            rendererMedia.setSeriesPaint(0, java.awt.Color.BLUE);
            plotPuntos.setRenderer(1, rendererMedia);

            JFreeChart chartRebotes = ChartFactory.createLineChart(
                    "Estadísticas de " + nombreJugador + " - Rebotes",
                    "Partidos",
                    "Rebotes",
                    datasetRebotes
            );

            File carpetaPrincipal = new File(PATH_GRAFICOS);
            if (!carpetaPrincipal.exists()) {
                carpetaPrincipal.mkdirs();
            }
            File carpetaJugador = new File(carpetaPrincipal, nombreJugador);
            if (!carpetaJugador.exists()) {
                carpetaJugador.mkdirs();
            }

            String rutaGraficoPuntos = carpetaJugador + File.separator + "Grafico_Puntos_Media.png";
            ChartUtils.saveChartAsPNG(new File(rutaGraficoPuntos), chartPuntos, 800, 600);

            String rutaGraficoRebotes = carpetaJugador + File.separator + "Grafico_Rebotes.png";
            ChartUtils.saveChartAsPNG(new File(rutaGraficoRebotes), chartRebotes, 800, 600);

            JOptionPane.showMessageDialog(null, "Gráficas generadas en:\n" 
                + "- " + rutaGraficoPuntos + "\n" 
                + "- " + rutaGraficoRebotes);

            JFrame framePuntos = new JFrame("Gráfico de Puntos y Media - " + nombreJugador);
            framePuntos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            framePuntos.setSize(800, 600);
            ChartPanel panelPuntos = new ChartPanel(chartPuntos);
            framePuntos.add(panelPuntos);
            framePuntos.setVisible(true);

            JFrame frameRebotes = new JFrame("Gráfico de Rebotes - " + nombreJugador);
            frameRebotes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameRebotes.setSize(800, 600);
            ChartPanel panelRebotes = new ChartPanel(chartRebotes);
            frameRebotes.add(panelRebotes);
            frameRebotes.setVisible(true);

            libro.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar gráficos: " + ex.getMessage());
        }
    }
    
    public static void crearGraficoAsistencias(JComboBox jugadorSeleccionado, JComboBox archivoEquipo) {
        String nombreJugador = (String) jugadorSeleccionado.getSelectedItem();
        String nombreEquipo = (String) archivoEquipo.getSelectedItem();
        String PATH_GRAFICOS = "C:\\Users\\GS2\\Desktop\\Graficos";

        File archivoExcel = new File("C:\\Users\\GS2\\Desktop\\" + nombreEquipo + " Estadisticas Baloncesto.xlsx");

        try {
            Workbook libro = new XSSFWorkbook(archivoExcel);
            Sheet hojaJugador = libro.getSheet(nombreJugador);

            if (hojaJugador == null) {
                JOptionPane.showMessageDialog(null, "No hay datos para el jugador seleccionado.");
                libro.close();
                return;
            }

            DefaultCategoryDataset datasetAsistencias = new DefaultCategoryDataset();
            int partidos = 0;

            for (int i = 1; i < hojaJugador.getPhysicalNumberOfRows(); i++) {
                Row row = hojaJugador.getRow(i);
                if (row != null) {
                    double asistencias = row.getCell(12).getNumericCellValue();

                    datasetAsistencias.addValue(asistencias, "Asistencias", "Partido " + i);

                    partidos++;
                }
            }

            if (partidos == 0) {
                JOptionPane.showMessageDialog(null, "No hay datos de asistencias para este jugador.");
                libro.close();
                return;
            }

            JFreeChart chartAsistencias = ChartFactory.createLineChart(
                    "Estadísticas de " + nombreJugador + " - Asistencias",
                    "Partidos",
                    "Asistencias",
                    datasetAsistencias
            );

            File carpetaPrincipal = new File(PATH_GRAFICOS);
            if (!carpetaPrincipal.exists()) {
                carpetaPrincipal.mkdirs();
            }
            File carpetaJugador = new File(carpetaPrincipal, nombreJugador);
            if (!carpetaJugador.exists()) {
                carpetaJugador.mkdirs();
            }

            String rutaGraficoAsistencias = carpetaJugador + File.separator + "Grafico_Asistencias.png";
            ChartUtils.saveChartAsPNG(new File(rutaGraficoAsistencias), chartAsistencias, 800, 600);

            libro.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar gráfico de asistencias: " + ex.getMessage());
        }
    }
    
    public static void generarPDF(JComboBox ElegirEquipo, JComboBox ElegirJugador) {
        String nombreJugador = (String) ElegirJugador.getSelectedItem();
        String nombreEquipo = (String) ElegirEquipo.getSelectedItem();

        if (nombreJugador == null || nombreJugador.isEmpty() || nombreEquipo == null || nombreEquipo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un jugador y un equipo.");
            return;
        }

        crearGraficoAsistencias(ElegirJugador, ElegirEquipo);

        String PATH_GRAFICOS = "C:\\Users\\GS2\\Desktop\\Graficos\\" + nombreJugador;
        String rutaGraficoPuntos = PATH_GRAFICOS + "\\Grafico_Puntos_Media.png";
        String rutaGraficoRebotes = PATH_GRAFICOS + "\\Grafico_Rebotes.png";
        String rutaGraficoAsistencias = PATH_GRAFICOS + "\\Grafico_Asistencias.png";

        double fg = obtenerFg(nombreJugador, nombreEquipo);
        double efg = obtenerEfg(nombreJugador, nombreEquipo);
        double ts = obtenerTs(nombreJugador, nombreEquipo);
        double triplesMetidosProm = obtenerPromedioTriples(nombreJugador, nombreEquipo);

        try {
            Document document = new Document();
            String rutaPDF = "C:\\Users\\GS2\\Desktop\\Graficos\\" + nombreJugador + "\\Estadisticas_" + nombreJugador + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));
            document.open();

            document.add(new Paragraph("Estadísticas de Baloncesto - " + nombreJugador + " (" + nombreEquipo + ")"));

            Image puntosImage = Image.getInstance(rutaGraficoPuntos);
            puntosImage.scaleToFit(250, 200);
            puntosImage.setAlignment(Image.ALIGN_CENTER);
            document.add(puntosImage);

            Image rebotesImage = Image.getInstance(rutaGraficoRebotes);
            rebotesImage.scaleToFit(250, 200);
            rebotesImage.setAlignment(Image.ALIGN_CENTER);
            document.add(rebotesImage);

            Image asistenciasImage = Image.getInstance(rutaGraficoAsistencias);
            asistenciasImage.scaleToFit(250, 200);
            asistenciasImage.setAlignment(Image.ALIGN_CENTER);
            document.add(asistenciasImage);

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Otras Estadísticas:"));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(2);
            table.addCell(new PdfPCell(new Paragraph("Promedio Triples Metidos por Partido:")));
            table.addCell(new PdfPCell(new Paragraph(String.format("%.2f", triplesMetidosProm))));
            table.addCell(new PdfPCell(new Paragraph("%FG:")));
            table.addCell(new PdfPCell(new Paragraph(String.format("%.2f%%", fg))));
            table.addCell(new PdfPCell(new Paragraph("%eFG:")));
            table.addCell(new PdfPCell(new Paragraph(String.format("%.2f%%", efg))));
            table.addCell(new PdfPCell(new Paragraph("%TS:")));
            table.addCell(new PdfPCell(new Paragraph(String.format("%.2f%%", ts))));

            document.add(table);

            document.close();

            JOptionPane.showMessageDialog(null, "PDF generado correctamente en: " + rutaPDF);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage());
        }
    }

    private static double obtenerFg(String nombreJugador, String nombreEquipo) {
        String rutaExcel = "C:\\Users\\GS2\\Desktop\\" + nombreEquipo + " Estadisticas Baloncesto.xlsx";

        try {
            File archivoExcel = new File(rutaExcel);
            Workbook libro = new XSSFWorkbook(archivoExcel);

            Sheet hojaJugador = libro.getSheet(nombreJugador);

            if (hojaJugador == null) {
                JOptionPane.showMessageDialog(null, "No se encontraron datos para el jugador seleccionado.");
                libro.close();
                return 0.0;
            }

            int totalTiros2 = 0;
            int totalTiros3 = 0;
            int totaltiros2ence = 0;
            int totaltiros3ence = 0;

            for (int i = 1; i < hojaJugador.getPhysicalNumberOfRows(); i++) {
                Row row = hojaJugador.getRow(i);
                if (row != null) {
                    int tiros2 = (int) row.getCell(0).getNumericCellValue();
                    int tiros2Realizados = (int) row.getCell(1).getNumericCellValue();
                    int tiros3 = (int) row.getCell(2).getNumericCellValue();
                    int tiros3Realizados = (int) row.getCell(3).getNumericCellValue();

                    totalTiros2 += tiros2Realizados;
                    totaltiros2ence += tiros2;
                    totalTiros3 += tiros3Realizados;
                    totaltiros3ence += tiros3;
                }
            }

            libro.close();

            int fga = totalTiros2 + totalTiros3;
            double fg = (fga > 0) ? ((double) (totaltiros2ence + totaltiros3ence) / fga) * 100 : 0;
            System.out.println(fg);

            return fg;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el FG: " + e.getMessage());
            return 0.0;
        }
    }

    private static double obtenerEfg(String nombreJugador, String nombreEquipo) {
        String rutaExcel = "C:\\Users\\GS2\\Desktop\\" + nombreEquipo + " Estadisticas Baloncesto.xlsx";

        try {
            File archivoExcel = new File(rutaExcel);
            Workbook libro = new XSSFWorkbook(archivoExcel);

            Sheet hojaJugador = libro.getSheet(nombreJugador);

            if (hojaJugador == null) {
                JOptionPane.showMessageDialog(null, "No se encontraron datos para el jugador seleccionado.");
                libro.close();
                return 0.0;
            }

            int totalTiros2 = 0;
            int totalTiros3 = 0;
            int totaltiros2ence = 0;
            int totaltiros3ence = 0;

            for (int i = 1; i < hojaJugador.getPhysicalNumberOfRows(); i++) {
                Row row = hojaJugador.getRow(i);
                if (row != null) {
                    int tiros2 = (int) row.getCell(0).getNumericCellValue();
                    int tiros2Realizados = (int) row.getCell(1).getNumericCellValue();
                    int tiros3 = (int) row.getCell(2).getNumericCellValue();
                    int tiros3Realizados = (int) row.getCell(3).getNumericCellValue();

                    totalTiros2 += tiros2Realizados;
                    totaltiros2ence += tiros2;
                    totalTiros3 += tiros3Realizados;
                    totaltiros3ence += tiros3;
                }
            }

            libro.close();

            int fga = totalTiros2 + totalTiros3;
            double efg = (fga > 0) ? ((double) (totaltiros2ence + (0.5 * totaltiros3ence)) / fga) * 100 : 0;

            return efg;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el EFG: " + e.getMessage());
            return 0.0;
        }
    }

    private static double obtenerTs(String nombreJugador, String nombreEquipo) {
        String rutaExcel = "C:\\Users\\GS2\\Desktop\\" + nombreEquipo + " Estadisticas Baloncesto.xlsx";

        try {
            File archivoExcel = new File(rutaExcel);
            Workbook libro = new XSSFWorkbook(archivoExcel);

            Sheet hojaJugador = libro.getSheet(nombreJugador);

            if (hojaJugador == null) {
                JOptionPane.showMessageDialog(null, "No se encontraron datos para el jugador seleccionado.");
                libro.close();
                return 0.0;
            }

            int totalTiros2 = 0;
            int totalTiros3 = 0;
            int totalTirosLibres = 0;
            int totalPuntos = 0;
            int totalTirosTotales = 0;
            int totaltiros2ence = 0;
            int totaltiros3ence = 0;
            int totalTirosEnce = 0;
            

            for (int i = 1; i < hojaJugador.getPhysicalNumberOfRows(); i++) {
                Row row = hojaJugador.getRow(i);
                if (row != null) {
                    int tiros2 = (int) row.getCell(0).getNumericCellValue();
                    int tiros2Realizados = (int) row.getCell(1).getNumericCellValue();
                    int tiros3 = (int) row.getCell(2).getNumericCellValue();
                    int tiros3Realizados = (int) row.getCell(3).getNumericCellValue();
                    int tirosLibresEncestados = (int) row.getCell(4).getNumericCellValue();
                    int tirosLibres = (int) row.getCell(5).getNumericCellValue();
                    int tirosTotales = (int) row.getCell(6).getNumericCellValue();
                    
                    totalTiros2 += tiros2Realizados;
                    totalTiros3 += tiros3Realizados;
                    totalTirosEnce += tirosLibresEncestados;
                    totalTirosLibres += tirosLibres;
                    totalTirosTotales += tirosTotales;
                    totaltiros2ence += tiros2;
                    totaltiros3ence += tiros3;
                    
                    totalPuntos += (2 * tiros2) + (3 * tiros3) + tirosLibresEncestados;
                }
            }

            libro.close();

            int fga = totalTiros2 + totalTiros3;
            double ts = (fga > 0) ? ((double) totalPuntos / (2 * (fga + (0.44 * totalTirosLibres)))) * 100 : 0;

            return ts;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el TS: " + e.getMessage());
            return 0.0;
        }
    }

    private static double obtenerPromedioTriples(String nombreJugador, String nombreEquipo) {
        String rutaExcel = "C:\\Users\\GS2\\Desktop\\" + nombreEquipo + " Estadisticas Baloncesto.xlsx";

        try {
            File archivoExcel = new File(rutaExcel);
            Workbook libro = new XSSFWorkbook(archivoExcel);

            Sheet hojaJugador = libro.getSheet(nombreJugador);

            if (hojaJugador == null) {
                JOptionPane.showMessageDialog(null, "No se encontraron datos para el jugador seleccionado.");
                libro.close();
                return 0.0;
            }

            int totalTriplesRealizados = 0;
            int totalPartidos = 0;

            for (int i = 1; i < hojaJugador.getPhysicalNumberOfRows(); i++) {
                Row row = hojaJugador.getRow(i);
                if (row != null) {
                    int triplesRealizados = (int) row.getCell(2).getNumericCellValue();

                    totalTriplesRealizados += triplesRealizados;
                    totalPartidos++;
                }
            }

            libro.close();

            double promedioTriples = (totalPartidos > 0) ? (double) totalTriplesRealizados / totalPartidos : 0;

            return promedioTriples;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el promedio de triples: " + e.getMessage());
            return 0.0;
        }
    }
    
    public static void actualizarjugadores(JComboBox Elegiquipo, JComboBox Elegijuga, String[] boston, String[] chicago){
        String equipoSeleccionado = (String) Elegiquipo.getSelectedItem();

        Elegijuga.removeAllItems();

        if (equipoSeleccionado.equals("Boston Celtics")) {
            for (String jugador : boston) {
                Elegijuga.addItem(jugador);
            }
        } else if (equipoSeleccionado.equals("Chicago Bulls")) {
            for (String jugador : chicago) {
                Elegijuga.addItem(jugador);
            }
        }}
    
    public static void escribirEnExcel(String equipo, String nombreJugador, int tiros2, int tiros2Realizados, int tiros3, int tiros3Realizados, int tirosLibres, int tirosLibresRealizados, int tirosTotales, int rebotes, int asistencias, int robos, int tapones, int taponesRecibidos, int perdidas, int faltasRecibidas, int faltasRealizadas) {
        File archivoExcel = new File("C:\\Users\\GS2\\Desktop\\" + equipo + " Estadisticas Baloncesto.xlsx");
        Workbook libro = null;
        Sheet hoja = null;

        try {
            if (archivoExcel.exists()) {
                FileInputStream fis = new FileInputStream(archivoExcel);
                libro = new XSSFWorkbook(fis);
                hoja = libro.getSheet(nombreJugador);
                if (hoja == null) {
                    hoja = libro.createSheet(nombreJugador);
                }
                fis.close();
            } else {
                libro = new XSSFWorkbook();
                hoja = libro.createSheet(nombreJugador);
            }

            if (hoja.getPhysicalNumberOfRows() == 0) {
                Row headerRow = hoja.createRow(0);
                headerRow.createCell(0).setCellValue("Tiros de 2 Metidos");
                headerRow.createCell(1).setCellValue("Tiros de 2 Realizados");
                headerRow.createCell(2).setCellValue("Tiros de 3 Metidos");
                headerRow.createCell(3).setCellValue("Tiros de 3 Realizados");
                headerRow.createCell(4).setCellValue("Tiros Libres Metidos");
                headerRow.createCell(5).setCellValue("Tiros Libres Realizados");
                headerRow.createCell(6).setCellValue("Tiros Totales");
                headerRow.createCell(7).setCellValue("FG% (Porcentaje de tiros anotados)");
                headerRow.createCell(8).setCellValue("eFG% (Porcentaje efectivo)");
                headerRow.createCell(9).setCellValue("TS% (Tiro Real)");
                headerRow.createCell(10).setCellValue("Valoración");
                headerRow.createCell(11).setCellValue("Rebotes");
                headerRow.createCell(12).setCellValue("Asistencias");
            }

            int filaJugador = hoja.getPhysicalNumberOfRows();
            Row dataRow = hoja.createRow(filaJugador);

            dataRow.createCell(0).setCellValue(tiros2);
            dataRow.createCell(1).setCellValue(tiros2Realizados);
            dataRow.createCell(2).setCellValue(tiros3);
            dataRow.createCell(3).setCellValue(tiros3Realizados);
            dataRow.createCell(4).setCellValue(tirosLibres);
            dataRow.createCell(5).setCellValue(tirosLibresRealizados);
            dataRow.createCell(6).setCellValue(tirosTotales);

            Integer fga = tiros2Realizados + tiros3Realizados;
            double fg = (tirosTotales > 0) ? ((double) (tiros2 + tiros3) / fga) * 100 : 0;
            dataRow.createCell(7).setCellValue(String.format("%.2f%%", fg));

            double efg = (tirosTotales > 0) ? ((tiros2 + (0.5 * tiros3)) / fga) * 100 : 0;
            dataRow.createCell(8).setCellValue(String.format("%.2f%%", efg));

            Integer puntos = (2 * tiros2) + (3 * tiros3) + (tirosLibres);
            double ts = (tirosTotales > 0) ? (puntos / (2 * (fga + (0.44 * tirosLibresRealizados)))) * 100 : 0;
            dataRow.createCell(9).setCellValue(String.format("%.2f%%", ts));

            Integer tirosFallados = (tiros3Realizados - tiros3) + (tiros2Realizados - tiros2) + (tirosLibresRealizados - tirosLibres);
            Integer valoracion = (puntos + rebotes + asistencias + robos + tapones + faltasRecibidas) - (tirosFallados + perdidas + taponesRecibidos + faltasRealizadas);
            dataRow.createCell(10).setCellValue(valoracion);
            dataRow.createCell(11).setCellValue(rebotes);
            dataRow.createCell(12).setCellValue(asistencias);

            Sheet hojaDeMedias = libro.getSheet("Medias");
            if (hojaDeMedias == null) {
                hojaDeMedias = libro.createSheet("Medias");
                Row encabezado = hojaDeMedias.createRow(0);
                encabezado.createCell(0).setCellValue("Jugador");
                encabezado.createCell(1).setCellValue("Promedio Tiros de 2");
                encabezado.createCell(2).setCellValue("Promedio Tiros de 3");
                encabezado.createCell(3).setCellValue("Promedio Tiros Libres");
                encabezado.createCell(4).setCellValue("Promedio Puntos");
            }

            double totalTiros2Jugador = 0, totalTiros2RealizadosJugador = 0;
            double totalTiros3Jugador = 0, totalTiros3RealizadosJugador = 0;
            double totalTirosLibresJugador = 0, totalTirosLibresRealizadosJugador = 0;
            double totalPuntosJugador = 0;
            int partidosJugados = 0;

            Sheet hojaJugador = libro.getSheet(nombreJugador);
            if (hojaJugador != null) {
                for (int i = 1; i < hojaJugador.getPhysicalNumberOfRows(); i++) {
                    Row row = hojaJugador.getRow(i);
                    if (row != null) {
                        double tiros2Jugador = row.getCell(0).getNumericCellValue();
                        double tiros2RealizadosJugador = row.getCell(1).getNumericCellValue();
                        double tiros3Jugador = row.getCell(2).getNumericCellValue();
                        double tiros3RealizadosJugador = row.getCell(3).getNumericCellValue();
                        double tirosLibresJugador = row.getCell(4).getNumericCellValue();
                        double tirosLibresRealizadosJugador = row.getCell(5).getNumericCellValue();

                        totalTiros2Jugador += tiros2Jugador;
                        totalTiros2RealizadosJugador += tiros2RealizadosJugador;
                        totalTiros3Jugador += tiros3Jugador;
                        totalTiros3RealizadosJugador += tiros3RealizadosJugador;
                        totalTirosLibresJugador += tirosLibresJugador;
                        totalTirosLibresRealizadosJugador += tirosLibresRealizadosJugador;

                        totalPuntosJugador += (2 * tiros2Jugador + 3 * tiros3Jugador + tirosLibresJugador);

                        partidosJugados++;
                    }
                }
            }

            if (partidosJugados > 0) {
                double promedioTiros2Jugador = totalTiros2Jugador / partidosJugados;
                double promedioTiros3Jugador = totalTiros3Jugador / partidosJugados;
                double promedioTirosLibresJugador = totalTirosLibresJugador / partidosJugados;
                double promedioPuntosJugador = totalPuntosJugador / partidosJugados;

                int filaMedia = -1;
                for (int i = 1; i <= hojaDeMedias.getPhysicalNumberOfRows(); i++) {
                    Row row = hojaDeMedias.getRow(i);
                    if (row != null && row.getCell(0).getStringCellValue().equals(nombreJugador)) {
                        filaMedia = i;
                        break;
                    }
                }

                if (filaMedia == -1) {
                    filaMedia = hojaDeMedias.getPhysicalNumberOfRows();
                    Row filaNueva = hojaDeMedias.createRow(filaMedia);
                    filaNueva.createCell(0).setCellValue(nombreJugador);
                }

                Row filaMediaExistente = hojaDeMedias.getRow(filaMedia);
                filaMediaExistente.createCell(1).setCellValue(promedioTiros2Jugador);
                filaMediaExistente.createCell(2).setCellValue(promedioTiros3Jugador);
                filaMediaExistente.createCell(3).setCellValue(promedioTirosLibresJugador);
                filaMediaExistente.createCell(4).setCellValue(promedioPuntosJugador);
            }

            libro.setSheetOrder("Medias", libro.getNumberOfSheets() - 1);



            for (int i = 0; i < 12; i++) {
                hoja.autoSizeColumn(i);
            }
            for (int i = 0; i < 6; i++) {
                hojaDeMedias.autoSizeColumn(i);
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


    public static void calcularYExportar(JComboBox ElegirEquipo, JComboBox ElegirJugador, JSpinner Tiros2, JSpinner Tiros2Realizados, JSpinner Tiros3, JSpinner Tiros3Realizados, JSpinner TirosLibres, JSpinner TirosLibresRealizados, JSpinner TirosTotales, JSpinner Rebotes, JSpinner Asistencias, JSpinner Robos, JSpinner Tapones, JSpinner TaponesRecibidos, JSpinner Perdidas, JSpinner FaltasRecibidas, JSpinner FaltasRealizadas) {
        String nombreJugador = (String) ElegirJugador.getSelectedItem();
        String nombreEquipo = (String) ElegirEquipo.getSelectedItem();
        int tiros2 = (int) Tiros2.getValue();
        int tiros2Realizados = (int) Tiros2Realizados.getValue();
        int tiros3 = (int) Tiros3.getValue();
        int tiros3Realizados = (int) Tiros3Realizados.getValue();
        int tirosLibres = (int) TirosLibres.getValue();
        int tirosLibresRealizados = (int) TirosLibresRealizados.getValue();
        int tirosTotales = (int) TirosTotales.getValue();
        int rebotes = (int) Rebotes.getValue();
        int asistencias = (int) Asistencias.getValue();
        int robos = (int) Robos.getValue();
        int tapones = (int) Tapones.getValue();
        int taponesRecibidos = (int) TaponesRecibidos.getValue();
        int perdidas = (int) Perdidas.getValue();
        int faltasRecibidas = (int) FaltasRecibidas.getValue();
        int faltasRealizadas = (int) FaltasRealizadas.getValue();

        if (nombreJugador.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del jugador es obligatorio.");
            return;
        }

        escribirEnExcel(nombreEquipo, nombreJugador, tiros2, tiros2Realizados, tiros3, tiros3Realizados, tirosLibres, tirosLibresRealizados, tirosTotales, rebotes, asistencias, robos, tapones, taponesRecibidos, perdidas, faltasRecibidas, faltasRealizadas);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        Paneles = new javax.swing.JTabbedPane();
        Datos1 = new javax.swing.JPanel();
        Jugador = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        ElegirJugador = new javax.swing.JComboBox<>();
        Equipo = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        ElegirEquipo = new javax.swing.JComboBox<>();
        Tiros2Texto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        Tiros2Texto2 = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        Tiros3Texto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        Tiros3Texto2 = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        TirosLibresTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        TirosLibresTexto2 = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        TirosTotalesTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        Tiros2 = new javax.swing.JSpinner();
        Tiros2Realizados = new javax.swing.JSpinner();
        Tiros3 = new javax.swing.JSpinner();
        Tiros3Realizados = new javax.swing.JSpinner();
        TirosLibres = new javax.swing.JSpinner();
        TirosLibresRealizados = new javax.swing.JSpinner();
        TirosTotales = new javax.swing.JSpinner();
        Datos2 = new javax.swing.JPanel();
        RebotesTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        AsistenciasTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        RobosTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        TaponesTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        TaponesRecibidosTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        PerdidasTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        FaltasRecibidasTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        FaltasRealizadasTexto = new com.teamlechuga.baloncestonbahugo.TextoPersonalizado();
        Rebotes = new javax.swing.JSpinner();
        Asistencias = new javax.swing.JSpinner();
        Robos = new javax.swing.JSpinner();
        Tapones = new javax.swing.JSpinner();
        TaponesRecibidos = new javax.swing.JSpinner();
        Perdidas = new javax.swing.JSpinner();
        FaltasRecibidas = new javax.swing.JSpinner();
        FaltasRealizadas = new javax.swing.JSpinner();
        Calcular = new javax.swing.JButton();
        Grafica = new javax.swing.JButton();
        PDF = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuTamano = new javax.swing.JMenu();
        pequeño = new javax.swing.JRadioButtonMenuItem();
        mediano = new javax.swing.JRadioButtonMenuItem();
        grande = new javax.swing.JRadioButtonMenuItem();
        ayuda = new javax.swing.JMenu();
        condiciones = new javax.swing.JMenuItem();
        tirosmaximos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        Datos1.setLayout(new java.awt.GridBagLayout());

        Jugador.setText("Jugador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        Datos1.add(Jugador, gridBagConstraints);

        ElegirJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(ElegirJugador, gridBagConstraints);

        Equipo.setText("Equipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        Datos1.add(Equipo, gridBagConstraints);

        ElegirEquipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        ElegirEquipo.setMinimumSize(new java.awt.Dimension(140, 26));
        ElegirEquipo.setPreferredSize(new java.awt.Dimension(140, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(ElegirEquipo, gridBagConstraints);

        Tiros2Texto.setText("Tiros metidos de 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        Datos1.add(Tiros2Texto, gridBagConstraints);

        Tiros2Texto2.setText("Tiros de 2 realizados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        Datos1.add(Tiros2Texto2, gridBagConstraints);

        Tiros3Texto.setText("Tiros metidos de 3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        Datos1.add(Tiros3Texto, gridBagConstraints);

        Tiros3Texto2.setText("Tiros de 3 realizados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        Datos1.add(Tiros3Texto2, gridBagConstraints);

        TirosLibresTexto.setText("Tiros libres metidos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        Datos1.add(TirosLibresTexto, gridBagConstraints);

        TirosLibresTexto2.setText("Tiros libres realizados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        Datos1.add(TirosLibresTexto2, gridBagConstraints);

        TirosTotalesTexto.setText("Tiros totales");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        Datos1.add(TirosTotalesTexto, gridBagConstraints);

        Tiros2.setMinimumSize(new java.awt.Dimension(140, 26));
        Tiros2.setPreferredSize(new java.awt.Dimension(140, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(Tiros2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(Tiros2Realizados, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(Tiros3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(Tiros3Realizados, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(TirosLibres, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(TirosLibresRealizados, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos1.add(TirosTotales, gridBagConstraints);

        Paneles.addTab("Tiros", Datos1);

        Datos2.setLayout(new java.awt.GridBagLayout());

        RebotesTexto.setText("Rebotes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        Datos2.add(RebotesTexto, gridBagConstraints);

        AsistenciasTexto.setText("Asistencias");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        Datos2.add(AsistenciasTexto, gridBagConstraints);

        RobosTexto.setText("Robos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        Datos2.add(RobosTexto, gridBagConstraints);

        TaponesTexto.setText("Tapones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        Datos2.add(TaponesTexto, gridBagConstraints);

        TaponesRecibidosTexto.setText("Tapones Recibidos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        Datos2.add(TaponesRecibidosTexto, gridBagConstraints);

        PerdidasTexto.setText("Perdidas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        Datos2.add(PerdidasTexto, gridBagConstraints);

        FaltasRecibidasTexto.setText("Faltas Recibidas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        Datos2.add(FaltasRecibidasTexto, gridBagConstraints);

        FaltasRealizadasTexto.setText("Faltas Realizadas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        Datos2.add(FaltasRealizadasTexto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos2.add(Rebotes, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos2.add(Asistencias, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos2.add(Robos, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos2.add(Tapones, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos2.add(TaponesRecibidos, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos2.add(Perdidas, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos2.add(FaltasRecibidas, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        Datos2.add(FaltasRealizadas, gridBagConstraints);

        Calcular.setText("Calcular");
        Calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CalcularActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        Datos2.add(Calcular, gridBagConstraints);

        Grafica.setText("Crear Gráfica");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        Datos2.add(Grafica, gridBagConstraints);

        PDF.setText("Crear PDF");
        PDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PDFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        Datos2.add(PDF, gridBagConstraints);

        Paneles.addTab("Datos", Datos2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 10;
        getContentPane().add(Paneles, gridBagConstraints);

        menuTamano.setText("Tamaño del Texto");

        buttonGroup1.add(pequeño);
        pequeño.setSelected(true);
        pequeño.setText("Pequeño");
        menuTamano.add(pequeño);

        buttonGroup1.add(mediano);
        mediano.setText("Mediano");
        menuTamano.add(mediano);

        buttonGroup1.add(grande);
        grande.setText("Grande");
        menuTamano.add(grande);

        jMenuBar1.add(menuTamano);

        ayuda.setText("Ayuda");

        condiciones.setText("Condiciones De Servicio");
        ayuda.add(condiciones);

        tirosmaximos.setText("Comprobar Tiros");
        ayuda.add(tirosmaximos);

        jMenuBar1.add(ayuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalcularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CalcularActionPerformed

    private void PDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PDFActionPerformed

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
    private javax.swing.JSpinner Asistencias;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado AsistenciasTexto;
    private javax.swing.JButton Calcular;
    private javax.swing.JPanel Datos1;
    private javax.swing.JPanel Datos2;
    private javax.swing.JComboBox<String> ElegirEquipo;
    private javax.swing.JComboBox<String> ElegirJugador;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado Equipo;
    private javax.swing.JSpinner FaltasRealizadas;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado FaltasRealizadasTexto;
    private javax.swing.JSpinner FaltasRecibidas;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado FaltasRecibidasTexto;
    private javax.swing.JButton Grafica;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado Jugador;
    private javax.swing.JButton PDF;
    private javax.swing.JTabbedPane Paneles;
    private javax.swing.JSpinner Perdidas;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado PerdidasTexto;
    private javax.swing.JSpinner Rebotes;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado RebotesTexto;
    private javax.swing.JSpinner Robos;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado RobosTexto;
    private javax.swing.JSpinner Tapones;
    private javax.swing.JSpinner TaponesRecibidos;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado TaponesRecibidosTexto;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado TaponesTexto;
    private javax.swing.JSpinner Tiros2;
    private javax.swing.JSpinner Tiros2Realizados;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado Tiros2Texto;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado Tiros2Texto2;
    private javax.swing.JSpinner Tiros3;
    private javax.swing.JSpinner Tiros3Realizados;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado Tiros3Texto;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado Tiros3Texto2;
    private javax.swing.JSpinner TirosLibres;
    private javax.swing.JSpinner TirosLibresRealizados;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado TirosLibresTexto;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado TirosLibresTexto2;
    private javax.swing.JSpinner TirosTotales;
    private com.teamlechuga.baloncestonbahugo.TextoPersonalizado TirosTotalesTexto;
    private javax.swing.JMenu ayuda;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem condiciones;
    private javax.swing.JRadioButtonMenuItem grande;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JRadioButtonMenuItem mediano;
    private javax.swing.JMenu menuTamano;
    private javax.swing.JRadioButtonMenuItem pequeño;
    private javax.swing.JMenuItem tirosmaximos;
    // End of variables declaration//GEN-END:variables
}
