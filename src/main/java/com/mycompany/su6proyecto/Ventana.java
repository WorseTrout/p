package com.mycompany.su6proyecto;

/**
 *
 * @author Lucca Maldonado Barron
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;

public class Ventana extends JFrame implements ActionListener {
    private AccesoRandom accesoRAF;
    private BaseDeDatos ag;
    private File archivo;
    
    private JMenuBar barraMenu;
    private JMenu menuArch, menuDatos;
    private JMenuItem miAbrir, miCerrar, miAgregar, miModificar;
    private JMenuItem miBuscar, miEliminar, miFinalizar;
    
    private JLabel etNombre, etEmail, etTelefono;
    private JLabel etRegistro;
    private JTextField tfNombre, tfEmail, tfTelefono;
    private JTextField tfRegistro;
    private JButton btIni, btFin, btAtras, btAdelante, btLimpiar;
    private JPanel panelCaptura, panelEti, panelTextField, panelNav;
    private JComboBox cb1, cb2, cb3;
    
    Ventana(){
        setLayout(new BorderLayout());
        setTitle("Base de datos (Clientes) - RAF");
        setSize(540,280);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        etNombre=new JLabel("Cliente"); 
        etEmail=new JLabel("Email"); 
        etTelefono=new JLabel("Telefono"); 
        etRegistro=new JLabel ("Registro:");
        tfNombre=new JTextField(30); 
        tfEmail=new JTextField(30);  
        tfTelefono=new JTextField("998",10); 
        tfRegistro=new JTextField(7); tfRegistro.setEnabled(false);
        btIni=new JButton(" << "); btFin=new JButton(" >> "); 
        btAtras=new JButton(" < "); btAdelante=new JButton(" > ");
        btLimpiar=new JButton("Limpiar campos");         
        
        panelCaptura=new JPanel(); panelCaptura.setLayout(new BorderLayout());
        
        panelEti=new JPanel(); panelEti.setLayout(new GridLayout(3,1));
        panelTextField=new JPanel(); panelTextField.setLayout(new GridLayout(3,1));
        panelNav=new JPanel(); panelNav.setLayout(new FlowLayout());
        
        panelCaptura.setBackground(Color.red);
        panelEti.setBackground(Color.LIGHT_GRAY);
        panelTextField.setBackground(Color.blue);
        panelNav.setBackground(Color.lightGray);
        
        panelEti.add(etNombre); 
        panelEti.add(etEmail);
        panelEti.add(etTelefono); 
        panelTextField.add(tfNombre); 
        panelTextField.add(tfEmail);
        panelTextField.add(tfTelefono);
        panelCaptura.add(panelEti, BorderLayout.WEST);
        panelCaptura.add(panelTextField, BorderLayout.CENTER);
        
        panelNav.add(btIni); panelNav.add(btAtras);
        panelNav.add(btAdelante); panelNav.add(btFin);
        panelNav.add(btLimpiar);
        panelNav.add(etRegistro); panelNav.add(tfRegistro);       
        
        barraMenu=new JMenuBar();
        menuArch=new JMenu("Archivo");
        menuDatos=new JMenu("Datos");
        miAbrir=new JMenuItem  ("Nuevo/Abrir");
        miCerrar=new JMenuItem  ("Cerrar");
        miFinalizar=new JMenuItem  ("Finalizar App");
        miAgregar=new JMenuItem("Agregar"); 
        miModificar=new JMenuItem("Modificar"); 
        miBuscar=new JMenuItem("Buscar");         
        miEliminar = new JMenuItem("Eliminar");

        menuArch.add(miAbrir);
        menuArch.add(miCerrar);
        menuArch.add(miFinalizar);
        menuDatos.add(miAgregar);
        menuDatos.add(miModificar);
        menuDatos.add(miBuscar);
        menuDatos.add(miEliminar);
        barraMenu.add(menuArch);
        barraMenu.add(menuDatos);
        
        miAbrir.addActionListener(this);
        miCerrar.addActionListener(this);    
        miFinalizar.addActionListener(this);    
        btLimpiar.addActionListener(this);
        miAgregar.addActionListener(this);
        miModificar.addActionListener(this);
        miBuscar.addActionListener(this);
        miEliminar.addActionListener(this);
        btIni.addActionListener(this);
        btFin.addActionListener(this);
        btAdelante.addActionListener(this);
        btAtras.addActionListener(this);

        setJMenuBar(barraMenu);                
        add(panelCaptura,BorderLayout.CENTER);
        add(panelNav,BorderLayout.SOUTH);

        inhabilitarComponentes();
    
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        String comando = e.getActionCommand();
        switch(comando) {
          case "Nuevo/Abrir":         
            JFileChooser chooser=new JFileChooser();
            FileNameExtensionFilter filtro = new 
              FileNameExtensionFilter ("Archivos RandomAccessFile", "raf");
            chooser.setFileFilter(filtro);
            int seleccionado=chooser.showOpenDialog(this);
            if (seleccionado == JFileChooser.APPROVE_OPTION){  //0
                archivo = chooser.getSelectedFile();
                accesoRAF=new AccesoRandom();
                accesoRAF.abrir(archivo);
                if (accesoRAF.getNumRegs()>0) {
                    ag=accesoRAF.leer(0);
                    if(ag!=null) setDatos();
                }
                habilitarComponentes();
            }
            break;
        case "Cerrar":
            accesoRAF.cerrar();
            tfRegistro.setText("");
            inhabilitarComponentes();
            cleanDatos();
            break; 
        case "Finalizar App":
            if (accesoRAF != null)accesoRAF.cerrar();
            dispose();
            System.exit(0);
            break; 
        case "Agregar":
            ag = new BaseDeDatos(tfNombre.getText(),Long.parseLong(tfTelefono.getText()), tfEmail.getText());
            accesoRAF.agregar(ag);
            break; 
        case "Modificar":
            ag = new BaseDeDatos(tfNombre.getText(),Long.parseLong(tfTelefono.getText()), tfEmail.getText());
            accesoRAF.modificar(ag);
            break; 
        case "Eliminar":
            accesoRAF.eliminar(tfNombre.getText());
            cleanDatos();
            break;
        case "Buscar":
            ag=null; String tfNombreABuscar="";
            tfNombreABuscar=JOptionPane.showInputDialog(null,"Escribe el tfNombre");
            if(tfNombreABuscar!=null) {ag = accesoRAF.buscar(tfNombreABuscar);
            if(ag!=null) {setDatos();
            }else {
                JOptionPane.showMessageDialog(null, tfNombreABuscar+" No se encuentra almacenado en el archivo!!!");
            }
                if(accesoRAF.getNumRegs()>=1){
                    ag=accesoRAF.leer(accesoRAF.getNumRegs()-1);
                    setDatos();
                }
            }
            break; 
        case " |< ":
            if (accesoRAF.getRegActual()!=0){          
                ag=accesoRAF.leer(0);
                setDatos();
            }
            break; 
        case " < ":
            if (accesoRAF.getRegActual()>0){
                ag=accesoRAF.leer(accesoRAF.getRegActual()-1);
                setDatos();
            }
            break; 
        case " > ":
            if (accesoRAF.getRegActual()<(accesoRAF.getNumRegs()-1)){
                ag=accesoRAF.leer(accesoRAF.getRegActual()+1);
                setDatos();
            }
            break; 
        case " >| ":
            if (accesoRAF.getNumRegs()>0 && accesoRAF.getRegActual()!=(accesoRAF.getNumRegs()-1)){
                ag=accesoRAF.leer(accesoRAF.getNumRegs()-1);
                setDatos();
            }
            break;
        case "Limpiar campos": 
            cleanDatos();
            break; 
        } 
        
        if (accesoRAF!=null && accesoRAF.getNumRegs()>0) tfRegistro.setText(""+(accesoRAF.getRegActual()+1)+"/"+accesoRAF.getNumRegs());
    }
    
    private void inhabilitarComponentes(){
        miAbrir.setEnabled(true);
        miCerrar.setEnabled(false);
        miAgregar.setEnabled(false); 
        miModificar.setEnabled(false); 
        miBuscar.setEnabled(false);
        miEliminar.setEnabled(false);
    }
    
    private void habilitarComponentes(){
        miAbrir.setEnabled(false);
        miCerrar.setEnabled(true);
        miAgregar.setEnabled(true); 
        miModificar.setEnabled(true); 
        miBuscar.setEnabled(true); 
        miEliminar.setEnabled(true);
    }
    
    private void setDatos(){
        tfNombre.setText(""+ag.getNombre());
        tfEmail.setText(""+ag.getEmail());
        tfTelefono.setText(""+ag.getTel());
    }
    
    private void cleanDatos(){
        tfNombre.setText("");
        tfEmail.setText("");
        tfTelefono.setText("998");
    } 
}