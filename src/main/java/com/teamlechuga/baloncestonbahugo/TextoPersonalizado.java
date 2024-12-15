/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.teamlechuga.baloncestonbahugo;

import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author Hugo Jose
 */
public class TextoPersonalizado extends JLabel{
    
    public TextoPersonalizado() {
        super("Texto por defecto");
        setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
    
    public TextoPersonalizado(String texto) {
        super(texto);
        setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
    
    public void ajustarTamano(int tamano) {
        switch (tamano) {
            case 1:
                setFont(new Font(getFont().getName(), getFont().getStyle(), 12));
                break;
            case 2:
                setFont(new Font(getFont().getName(), getFont().getStyle(), 16));
                break;
            case 3:
                setFont(new Font(getFont().getName(), getFont().getStyle(), 20));
                break;
            default:
                throw new IllegalArgumentException("El tamaño debe ser 1 (pequeño), 2 (mediano) o 3 (grande).");
        }
    }
    
}
