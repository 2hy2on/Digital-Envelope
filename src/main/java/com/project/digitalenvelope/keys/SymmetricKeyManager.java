package com.project.digitalenvelope.keys;

import lombok.Getter;

import javax.crypto.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;

@Getter
public class SymmetricKeyManager {
    final String algorithm = "AES";
    public Key secretKey;

    public Key create(int bytes) {
        //키 생성
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            keyGen.init(bytes);
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return secretKey;

    }

    public byte[] decrypt(byte[] data, Key key) {
        byte[] decrypedData = null;
        Cipher c ;

        try {
            c = Cipher.getInstance(key.getAlgorithm());
            c.init(Cipher.DECRYPT_MODE, key);
            decrypedData = c.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
            e1.printStackTrace();
        } catch (InvalidKeyException e1) {
            e1.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return decrypedData;
    }

    public byte[] encrypt(byte[] data, Key key) {
        byte[] encryptedData = null;
        Cipher c;

        try {
            c = Cipher.getInstance(key.getAlgorithm());
            c.init(Cipher.ENCRYPT_MODE, key);
            encryptedData = c.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
            e1.printStackTrace();
        } catch (InvalidKeyException e1) {
            e1.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return encryptedData;
    }

    public boolean saveKey(Key secretKey, String fileName) {
        try(FileOutputStream fstream = new FileOutputStream(fileName)){
            try(ObjectOutputStream ostream = new ObjectOutputStream(fstream)){
                ostream.writeObject(secretKey);
            }
            return true;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
