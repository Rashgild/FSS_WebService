package WS_ClientToFss.SignAndEncrypt;

import HelpersMethods.GlobalVariables;
import ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by rkurbanov on 10.11.16.
 */

public class Certificate {

    public  static X509Certificate ExtractCertFromCertStore ()
            throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore allCertStore = null;
        String storepass="123456";
        String alias="FSS.cer";

        allCertStore = KeyStore.getInstance("CertStore");
        File f = new File(GlobalVariables.PathToCertStore[1]);
        allCertStore.load(new FileInputStream(f), storepass.toCharArray());
        X509Certificate UserCert = (X509Certificate) allCertStore.getCertificate(alias);

        return UserCert;
    }

    public  static PrivateKey GetPrivateKey (String Password, String Alias)
            throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        JCPXMLDSigInit.init();
        KeyStore keystore = KeyStore.getInstance("HDImageStore");
        keystore.load(null, null);
        PrivateKey privateKey = (PrivateKey)keystore.getKey(Alias, Password.toCharArray());
        //System.out.println(UserCert);

        return privateKey;
    }


    public  static X509Certificate GetCertificateFromStorage(String Alias)
            throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {

        JCPXMLDSigInit.init();
        KeyStore ks = KeyStore.getInstance("HDImageStore");
        ks.load(null, null);
        X509Certificate certificate = (X509Certificate)ks.getCertificate(Alias);
        return certificate;
    }

    /**
     * Вывод сертификата X509 в формате base64 в строке
     * @param cert Сертификат
     * @return String Строка с сертификатом в формате base64
     */
    public static String certToBase64(X509Certificate cert) throws CertificateEncodingException {
        StringWriter sw = new StringWriter();
        //sw.write("-----BEGIN CERTIFICATE-----\n");
        sw.write(DatatypeConverter.printBase64Binary(cert.getEncoded()).replaceAll("(.{64})", "$1\n"));
        //sw.write("\n-----END CERTIFICATE-----\n");
        return sw.toString();
    }
}