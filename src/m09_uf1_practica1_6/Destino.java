package m09_uf1_practica1_6;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Destino {
    
    String desti = "C:\\Users\\ALUMNEDAM\\Documents\\NetBeansProjects\\M09_UF1_Practica1_6\\src\\SSL\\desti.jks";
    String passwd = "123456";
    private KeyStore ksd; //desti.jks
    private PrivateKey kPrivDesti; //PrivateKey desti
    private PublicKey kPublDesti; //PublicKey desti 
    private X509Certificate certPublOrigen; //Certificado origen - publicKey    
    public byte[] decryptedData;

    public void loadKeyStore(String ksFile, String ksPwd) throws Exception {
        ksd = KeyStore.getInstance("JCEKS"); // JCEKS ó JKS
        File f = new File(ksFile);
        if (f.isFile()) {
            FileInputStream in = new FileInputStream(f);
            ksd.load(in, ksPwd.toCharArray());
        }
    }

    public void getPko() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        kPrivDesti = (PrivateKey) ksd.getKey("desti", passwd.toCharArray());
    }

    public void getCertificate(String alias) throws KeyStoreException {
        certPublOrigen = (X509Certificate) ksd.getCertificate(alias);
    }

    public void getPublicKey() {
        kPublDesti = certPublOrigen.getPublicKey();
    }

    public PublicKey getPublKey() {
        return kPublDesti;
    }

    public PrivateKey getkPrivDesti() {
        return kPrivDesti;
    }

    public boolean validateSignature(byte[] data, byte[] signature, PublicKey pub) {
        boolean isValid = false;
        try {
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initVerify(pub);
            signer.update(data);
            isValid = signer.verify(signature);
        } catch (Exception ex) {
            System.out.println("Algo falla cabesa || ValidateSignature");
        }
        return isValid;
    }

    public void desxifraDadesReceptor(byte[] data, PrivateKey privK) {
        Cipher ciph;
        try {
            ciph = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            ciph.init(Cipher.DECRYPT_MODE, privK);
            decryptedData = ciph.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Algo falla cabesa || desxifraDadesReceptor");
        }

    }

}
