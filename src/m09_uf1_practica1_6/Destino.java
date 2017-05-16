package m09_uf1_practica1_6;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class Destino {

    String origen = "C:\\Users\\ALUMNEDAM\\Documents\\NetBeansProjects\\M09_UF1_Practica1_6\\src\\SSL\\desti.crt";
    String desti = "C:\\Users\\ALUMNEDAM\\Documents\\NetBeansProjects\\M09_UF1_Practica1_6\\src\\SSL\\origen.crt";
    String passwd = "123456";
    private KeyStore ksd; //desti.jks
    private PrivateKey pkd; //PrivateKey desti    

    public void loadKeyStore(String ksFile, String ksPwd) throws Exception {
        KeyStore ks = KeyStore.getInstance("JCEKS"); // JCEKS ó JKS
        File f = new File(ksFile);
        if (f.isFile()) {
            FileInputStream in = new FileInputStream(f);
            ks.load(in, ksPwd.toCharArray());
        }
        ksd = ks;
    }
    

}
