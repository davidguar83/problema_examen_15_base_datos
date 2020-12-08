/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exa15;

import java.io.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author oracle
 */
public class Exa15 {
    
    public static Connection conexion = null;
    
    public static Connection getConexion() throws SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;
        
        conexion = DriverManager.getConnection(ulrjdbc);
        return conexion;
    }
    
    public static void closeConexion() throws SQLException {
        conexion.close();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, XMLStreamException {
        
        ArrayList<Platos> ps = new ArrayList();
        ArrayList<Integer> pesoin = new ArrayList();
        
        leerArchivo(ps);
        pesoin=metodoInformacion(ps);
        escribirXML(ps, pesoin);
    }
    
    public static void leerArchivo(ArrayList<Platos> b) {
        
        Platos a;
        
        try {
            ObjectInputStream leer = new ObjectInputStream(new FileInputStream("/home/oracle/Desktop/compartido/platoss"));
            
            while ((a = (Platos) leer.readObject()) != null) {
                b.add(a);
                
            }
            
            leer.close();
            
        } catch (IOException e) {
            System.out.println("error1");
        } catch (ClassNotFoundException ex) {
            System.out.println("error2");
        }
    }
    
    public static ArrayList<Integer> metodoInformacion(ArrayList<Platos> a) {
        
        ArrayList<Integer> pesos = new ArrayList();
        ArrayList<Integer> graxas = new ArrayList();
        ArrayList<String> Codc = new ArrayList();
        int totalgraxa = 0, graxaplato = 0;
        
        String preg1 = "select codc,peso from composicion where codp=";
        String preg2 = "select graxa from componentes where codc=";
        
        Statement s1, s2;
        ResultSet r1, r2;
        
        try {
            conexion = getConexion();
            s1 = conexion.createStatement();
            s2 = conexion.createStatement();
            
            for (int i = 0; i < a.size(); i++) {
                r1 = s1.executeQuery(preg1 + "'" + a.get(i).getCodigop() + "'");
                System.out.println("Codigo del Plato: " + a.get(i).getCodigop());
                System.out.println("Nombre del Plato: " + a.get(i).getNomep());
                while (r1.next()) {
                    String codp = r1.getString(1);
                    String codc = r1.getString("codc");
                    int peso = r1.getInt("peso");
                    pesos.add(peso);
                    Codc.add(codc);
                }
                
                for (int j = 0; j < Codc.size(); j++) {
                    
                    r2 = s1.executeQuery(preg2 + "'" + Codc.get(j) + "'");
                    
                    while (r2.next()) {
                        System.out.println("grasa por cada 100 g de componentes = " + r2.getInt("graxa"));
                        
                        graxaplato = r2.getInt("graxa") * pesos.get(j) / 100;
                        
                        totalgraxa = totalgraxa + graxaplato;
                        
                    }
                    
                    System.out.println("Peso =" + pesos.get(j));
                    System.out.println("graxas totales por componentes= " + graxaplato);
                }
                
                graxas.add(totalgraxa);
                System.out.println("graxas totales del plato= " + totalgraxa);
                totalgraxa = 0;
                Codc.clear();
                pesos.clear();
                
            }
            
            closeConexion();
            
        } catch (SQLException ex) {
            Logger.getLogger(Exa15.class.getName()).log(Level.SEVERE, null, ex);
        }
        return graxas;
    }
    
    public static void escribirXML(ArrayList<Platos> b, ArrayList<Integer> a) {
        
        XMLOutputFactory x = XMLOutputFactory.newInstance();
        
        try {
            XMLStreamWriter escribir = x.createXMLStreamWriter(new FileOutputStream("/home/oracle/Desktop/compartido/totalgx.xml"));
            escribir.writeStartDocument("1.0");
            escribir.writeStartElement("Platos");
            for (int i = 0; i < b.size(); i++) {
                
                escribir.writeStartElement("plato");
                escribir.writeAttribute("codigo", b.get(i).getCodigop());
                escribir.writeStartElement("nomep");                
                escribir.writeCharacters(b.get(i).getNomep());
                escribir.writeEndElement();
                escribir.writeStartElement("grasaTotal");
                escribir.writeCharacters(a.get(i).toString());
                escribir.writeEndElement();
                escribir.writeEndElement();
                
            }
            escribir.writeEndElement();
            escribir.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Exa15.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Exa15.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
