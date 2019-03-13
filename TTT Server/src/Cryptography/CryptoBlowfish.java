/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author oumaima
 */
public class CryptoBlowfish {

    static final private String strkey = "996b352bbeb95628a61d4f0fbb72844e";

    static public byte[] encryptBlowfish(String to_encrypt) {
        try {
            SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(to_encrypt.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public String decryptBlowfish(byte[] to_decrypt) {
        try {
            SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decrypted = cipher.doFinal(to_decrypt);
            return new String(decrypted, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
