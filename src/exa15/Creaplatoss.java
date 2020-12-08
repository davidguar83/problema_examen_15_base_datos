package exa15;

import exa15.Platos;
import java.io.*;
public class Creaplatoss {

    public static void main(String args[]) {
    // Object serialization
     String[] codes={"p1","p2"};
     String[] descricion ={"platocarnico1","platocarnico2 "};

    try {
    Platos pl=null;
    
    FileOutputStream fos = new FileOutputStream("platoss");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    
     
        
        for (int i=0; i < codes.length;i++)
        {
        pl = new Platos();  
        pl.setCodigop(codes[i]);
        pl.setNomep(descricion[i]);
        System.out.println("object: " + pl);
        oos.writeObject(pl);
        oos.flush();

       }   
    oos.writeObject(null);
    oos.close();
    fos.close();
    }
    catch(Exception e) {
    System.out.println("Exception during serialization: " + e);
    }
    /*
    System.out.println("LE PLATOSS");
    try {
    Platos object2;
    FileInputStream fis = new FileInputStream("platoss");
    ObjectInputStream ois = new ObjectInputStream(fis);
    
    
    while ((object2 = (Platos)ois.readObject()) != null) {
      System.out.println("object2: " + object2);
        //System.out.println(object2.getPrice());
    }
    ois.close();
    fis.close();
    }
    catch(Exception e) {
    System.out.println("Exception during deserialization: " +
    e);
    //System.exit(0);
    }
    */
    }
    
}
