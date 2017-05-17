package m09_uf1_practica1_6;

import java.util.logging.Level;
import java.util.logging.Logger;

public class M09_UF1_Practica1_6 {

    public static void main(String[] args) {
        Origen o = new Origen();
        Destino d = new Destino();
        byte[] signature;
        
        try {
            /////////ORIGEN/////////
            o.loadKeyStore(o.origen, o.passwd);//Generamos el almacen de origen
            o.getPko();//Obtenemos la clave privada
            o.getCertificate("desticert");//Obtenemos el certifidado de desti
            o.getPublicKey();//Generamos la clave publica de origen
            
            /////////DESTINO/////////
            d.loadKeyStore(d.desti, d.passwd);//Generamos el almacen de desti
            d.getPko();//Obtenemos la clave privada
            d.getCertificate("origen");
            d.getPublicKey();
            
            o.xifraDadesEmissor(o.text, d.getPublKey());//Ciframos el texto en un array de bytes
            signature=o.signData(o.getMsgEncriptado(), o.getkPrivOrigen());//Array de bytes con la firma
            
            
            d.validateSignature(o.getMsgEncriptado(), signature, d.getPublKey());
            System.out.println(new String(d.decryptedData));
            
        } catch (Exception ex) {
            Logger.getLogger(M09_UF1_Practica1_6.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
