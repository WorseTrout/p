package com.mycompany.su6proyecto;

/**
 *
 * @author Lucca Maldonado Barron
 */
public class BaseDeDatos {
    
private String nombre, email;
    private long tel;
    
    public BaseDeDatos() { nombre = ""; email = ""; tel = 0; }
    
    public BaseDeDatos(String n, long t, String em) {
        nombre = n;
        tel = t;
        email = em;
    }
    
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public long getTel() { return tel; }
    
    public void setNombre(String n) { nombre = n; }
    public void setEmail(String e) { email = e; }
    public void setTel(long t) { tel = t; }
    public String getDatos() {
        return nombre + "  " + email + "  " + tel;
    }
    public int tamDatos() {
        return nombre.length() + email.length() + 12; 
    }
}
