package m09_uf1_practica1_6;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Origen {
    Destino dest = new Destino();

    private KeyStore kso; //origen.jks
    private PrivateKey pko; //PrivateKey origen
    private X509Certificate certPriv; //Certificado origen - publicKey    
    private X509Certificate certPub = null; //Certificado desticrt - publicKey
    private PublicKey publKey;
    

    public void loadKeyStore(String ksFile, String ksPwd) throws Exception {
        KeyStore ks = KeyStore.getInstance("JCEKS"); // JCEKS ó JKS
        File f = new File(ksFile);
        if (f.isFile()) {
            FileInputStream in = new FileInputStream(f);
            kso.load(in, ksPwd.toCharArray());
        }
    }

    public void getPko() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        pko = (PrivateKey) kso.getKey("origen", dest.passwd.toCharArray());        
    }
    
    public void getCertificate(String alias) throws KeyStoreException{
        certPriv =(X509Certificate) kso.getCertificate(alias);
    }
    
    public void getPublicKey (){
        publKey = certPub.getPublicKey();
    }

    

    
    
    
}
