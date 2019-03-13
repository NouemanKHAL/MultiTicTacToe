/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;


/**
 *
 * @author oumaima
 */
public class CryptoRSA {

    private static String PRIVATE_KEY_FILE;
    private static String PUBLIC_KEY_FILE;
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    
    
    public static void initRSA() {
        
        setPRIVATE_KEY_FILE("PRIVATE_KEY_FILE.txt");
        setPUBLIC_KEY_FILE("PUBLIC_KEY_FILE.txt");

        try {

            if (!areKeysPresent()) {
                generateKey();
            }

            ObjectInputStream inputStream = null;

            inputStream = new ObjectInputStream(new FileInputStream(getPUBLIC_KEY_FILE()));
            publicKey = (PublicKey) inputStream.readObject();
          
            inputStream = new ObjectInputStream(new FileInputStream(getPRIVATE_KEY_FILE()));
            privateKey = (PrivateKey) inputStream.readObject();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void generateKey() {
        try {

            // Generate Keys
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);
            KeyPair key = keyGen.generateKeyPair();

            File privateKeyFile = new File(PRIVATE_KEY_FILE);
            File publicKeyFile = new File(PUBLIC_KEY_FILE);

            // Create files to store public and private key
//            if (privateKeyFile.getParentFile() != null) {
//                privateKeyFile.getParentFile().mkdirs();
//            }
            privateKeyFile.createNewFile();
            publicKeyFile.createNewFile();
//            if (publicKeyFile.getParentFile() != null) {
//                publicKeyFile.getParentFile().mkdirs();
//            }
            

            // Saving the Public key in a file
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();

            // Saving the Private key in a file
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean areKeysPresent() {

        File privateKey = new File(PRIVATE_KEY_FILE);
        File publicKey = new File(PUBLIC_KEY_FILE);

        if (privateKey.exists() && publicKey.exists()) {
            return true;
        }
        return false;
    }
    

    public static byte[] encrypt(String text) {
        byte[] cipherText = null;

        try {

            // get an RSA cipher object and print the provider
            Cipher cipher = Cipher.getInstance("RSA");

            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherText = cipher.doFinal(text.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    public static String decrypt(byte[] text) {
        byte[] dectyptedText = null;
        try {

            // get an RSA cipher object and print the provider    
            final Cipher cipher = Cipher.getInstance("RSA");
            System.out.println("to decrypt by RSA : " + text.length);
            // decrypt the private key
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            dectyptedText = cipher.doFinal(text);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

    public static String getPRIVATE_KEY_FILE() {
        return PRIVATE_KEY_FILE;
    }

    public static void setPRIVATE_KEY_FILE(String PRIVATE_KEY_FILE) {
        CryptoRSA.PRIVATE_KEY_FILE = PRIVATE_KEY_FILE;
    }

    public static String getPUBLIC_KEY_FILE() {
        return PUBLIC_KEY_FILE;
    }

    public static void setPUBLIC_KEY_FILE(String PUBLIC_KEY_FILE) {
        CryptoRSA.PUBLIC_KEY_FILE = PUBLIC_KEY_FILE;
    }

}
