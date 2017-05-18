package m09_uf1_practica1_6;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Origen {    
    String origen = "C:\\Users\\ALUMNEDAM\\Documents\\NetBeansProjects\\M09_UF1_Practica1_6\\src\\SSL\\origen.jks";
    String passwd = "123456";
    private KeyStore kso; //origen.jks
    private PrivateKey kPrivOrigen; //PrivateKey origen
    private PublicKey kPublOrigen; //PublicKey origen
    private X509Certificate certPublDesti; //Certificado origen - publicKey   
    private byte[] msgEncriptado = null;
    public String text = "Jorge es un buen profe y todos vamos a sacar una muy "
            + "buena nota.";
    

    /**
     * Generamos el almacen de claves (KeyStore)
     * @param ksFile
     * @param ksPwd
     * @throws Exception 
     */
    public void loadKeyStore(String ksFile, String ksPwd) throws Exception {
        kso = KeyStore.getInstance("JCEKS"); // JCEKS ó JKS
        File f = new File(ksFile);
        if (f.isFile()) {
            FileInputStream in = new FileInputStream(f);
            kso.load(in, ksPwd.toCharArray());
            
            Enumeration<String> aliases = kso.aliases();
            while(aliases.hasMoreElements()){
                System.out.println(aliases.nextElement());
            }            
        }
    } 

    /**
     * Mediante el almacen de claves (KeyStore) y la contraseña generamos la 
     * clave privada de origen.
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException 
     */
    public void getPko() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        kPrivOrigen = (PrivateKey) kso.getKey("origen", passwd.toCharArray());
    }

    /**
     * Mediante el almacen de claves (KeyStore) y el alias "desticrt" obtenemos
     * el certificado publico de destino
     * @param alias
     * @throws KeyStoreException 
     */
    public void getCertificate(String alias) throws KeyStoreException {
        certPublDesti = (X509Certificate) kso.getCertificate(alias);
        //System.out.println(certPublDesti.getType());
    }

    /**
     * Obtenemos la clave publica (kPublOrigen) del certificado publico de destino
     */
    public void getPublicKey() {
        kPublOrigen = certPublDesti.getPublicKey();
    }

    /**
     * Getter para obtener la clave publica en destino
     * @return 
     */
    public PublicKey getPublKey() {
        return kPublOrigen;
    }

    /**
     * Getter para acceder al mensage difrado
     * @return 
     */
    public byte[] getMsgEncriptado() {
        return msgEncriptado;
    }

    public PrivateKey getkPrivOrigen() {
        return kPrivOrigen;
    }    

    /**
     * Metodo que recibe por parametro un mensaje y obtiene la clave publica del
     * par de claves que genera generarClau(). Mediante esta clave y
     * cipher.ENCRYPT_MODE codificamos el mensaje y lo almacenamos en
     * ecnryptedData.
     *
     * @param missatge_text
     * @param pub
     */
    public void xifraDadesEmissor(String missatge_text, PublicKey pub) {
        byte[] msg;
        try {
            System.out.println("primera linia");
            msg = missatge_text.getBytes("UTF-8");
            System.out.println("segunda linia");
            Cipher ciph = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            System.out.println("tercera linia");
            ciph.init(Cipher.ENCRYPT_MODE, pub);
            System.out.println("cuarta linia");
            msgEncriptado = ciph.doFinal(msg);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Algo falla cabesa || xifraDadesEmissor");
        }
    }

    /**
     * Mediante Signature firmamos los datos recibidos como array de bytes usando
     * la clave privada. Y devolvemos un array de bytes que es la firma.
     * @param entripted
     * @param priv
     * @return 
     */
    public byte[] signData(byte[] entripted, PrivateKey priv) {
        byte[] signature = null;
        try {
            System.out.println("primera linia");
            Signature signer = Signature.getInstance("SHA1withRSA");
            System.out.println("segunda linia");
            signer.initSign(priv); //Inicialitzem la firma digital a partirde l’algorisme utilitzat
            System.out.println("tercera linia");
            signer.update(entripted); //Li assignem a l’objecte firma les dades afirmar digitalment
            System.out.println("cuarta linia");
            signature = signer.sign();//Finalment generem la firma
        } catch (Exception ex) {
            System.out.println("Algo falla cabesa || signData");
        }
        return signature;
    }

}
