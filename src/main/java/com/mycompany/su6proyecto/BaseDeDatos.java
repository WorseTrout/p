package com.mycompany.su6proyecto;

/**
 *
 * @author Lucca Maldonado Barron
 */
public class BaseDeDatos {
    
private String nombre, email;
    private long tel;
    private int claveS;
    
    public BaseDeDatos() { nombre = ""; email = ""; tel = 0; claveS = 0; }
    
    public BaseDeDatos(String n, long t, String em, int c) {
        nombre = n;
        tel = t;
        email = em;
        claveS = c;
    }
    
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public long getTel() { return tel; }
    public int getclaveS () {return claveS; }
    
    public void setNombre(String n) { nombre = n; }
    public void setEmail(String e) { email = e; }
    public void setTel(long t) { tel = t; }
    public void setclaveS(int s){ claveS = s;}
    public String getDatos() {
        return nombre + "  " + email + "  " + tel + " " + claveS;
    }
    public int tamDatos() {
        return nombre.length() + email.length() + 25; 
    }
}
