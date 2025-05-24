package com.mycompany.su6proyecto;

/**
 *
 * @author Lucca Maldonado Barron
 */
import java.io.*;
import javax.swing.*;
public class AccesoRandom {
    private RandomAccessFile raf;
    private final int tamMaxReg;
    private int totalRegistros;
    private int registroActual;
    private BaseDeDatos ag;
    
    AccesoRandom(){ 
        tamMaxReg=135; 
        totalRegistros=0;  
        registroActual=0; 
        ag=new BaseDeDatos(); 
        raf=null;
    }
    
    public void abrir(File nomArch) {
        try {
            raf = new RandomAccessFile(nomArch, "rw");
            totalRegistros = (int) Math.ceil(raf.length() / (double) tamMaxReg);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al abrir archivo: " + e.getMessage());
        }
    }
    
    public void cerrar() {
        try {
            if (raf != null) {
                raf.close();
                totalRegistros=0;  
                registroActual=0;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar archivo: " + e.getMessage());
        }
    }
   
    public void agregar(BaseDeDatos agenda) {
        try {
            if (agenda.tamDatos()<=tamMaxReg){
                raf.seek(totalRegistros * tamMaxReg);
                raf.writeUTF(agenda.getNombre());
                raf.writeLong(agenda.getTel());
                raf.writeUTF(agenda.getEmail());
                registroActual=totalRegistros;
                totalRegistros++;
            }
            else JOptionPane.showMessageDialog(null,"El tamaño de los datos excede el maximo permitido!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar registro: " + e.getMessage());
        }
    }
    
    public void modificar (BaseDeDatos agenda){
        if (raf!=null)
        try{
            if (agenda.tamDatos()<=tamMaxReg){
                raf.seek(registroActual*tamMaxReg);
                raf.writeUTF(agenda.getNombre());
                raf.writeLong(agenda.getTel());
                raf.writeUTF(agenda.getEmail());
                JOptionPane.showMessageDialog(null,"Los datos se modificaron en el archivo");
            }
            else JOptionPane.showMessageDialog(null,"El tamaño de los datos excede el maximo permitido!");
        } catch(IOException e){
            System.out.println("Error 4: "+e.toString());
        }
    }

    public void eliminar(String nombreEliminar) {
        if (raf == null) return;
        
        try{
            File tempFile = new File("temp.dat");
            RandomAccessFile tempRAF = new RandomAccessFile(tempFile, "rw");
            
            raf.seek(0);
            long numRegs = raf.length() / tamMaxReg;
            
            for (int i = 0; i < numRegs; i++){
                raf.seek(i * tamMaxReg);
                
                String nombre = raf.readUTF();
                long tel = raf.readLong();
                String email = raf.readUTF();
                
                if (!nombre.equals(nombreEliminar)){
                    tempRAF.writeUTF(nombre);
                    tempRAF.writeLong(tel);
                    tempRAF.writeUTF(email);
                }
            }
            raf.close();
            tempRAF.close();
            
            File original = new File("datos.dat");
            original.delete();
            tempFile.renameTo(original);
            
            raf = new RandomAccessFile("datos.dat", "rw");
            JOptionPane.showMessageDialog(null, "registro eliminado exitosamente");
            
        }catch (IOException e){
            System.out.println("Error 4: "+e.toString());
        }

    }
    
    public BaseDeDatos leer(int pos) {
        ag = null;
        if (raf!=null) {
            try {
                if(pos>=0 && pos<totalRegistros){
                    raf.seek(tamMaxReg*pos);
                    registroActual=pos;
                    ag = new BaseDeDatos(raf.readUTF(), raf.readLong(), raf.readUTF());
                }
            } catch (IOException e){
                System.out.println("Error 5: "+e.toString());
            }
        }
        return ag;
    }
    
    public BaseDeDatos buscar(String name) {
        ag = null;
        boolean encontrado=false;
        if (raf!=null) {
            try {
                for (int i=0; i<totalRegistros; i++){
                    raf.seek(tamMaxReg*i);
                    registroActual=i;
                    ag = new BaseDeDatos(raf.readUTF(), raf.readLong(), raf.readUTF());
                    if (ag.getNombre().equalsIgnoreCase(name)) 
                        {encontrado=true; break;}
                }
            }
            catch (IOException e){
                System.out.println("Error 6: "+e.toString());
            }
        }
        if (!encontrado) {ag=null; registroActual=totalRegistros;}
        return ag;
    }
    public int getTamReg(){ return tamMaxReg;}
    public int getNumRegs(){ return totalRegistros;}
    public int getRegActual(){return registroActual;}
    public void setRegActual(int ra){registroActual=ra;}
    public void setNumRegs(int nr){totalRegistros=nr;}
}