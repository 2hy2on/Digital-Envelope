package com.project.digitalenvelope.keys;

import lombok.Getter;

import java.io.*;
import java.security.*;

@Getter
public class KeyManager {
    KeyPair keypair;
    PublicKey publicKey;
    PrivateKey privateKey;
    final static String algorithm = "RSA";

    public KeyPair create(int bytes) {
        //KeyPair 생성
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(bytes);

        keypair = keyPairGen.generateKeyPair();
        publicKey = keypair.getPublic();
        privateKey = keypair.getPrivate();

        return keypair;
    }

    public Key saveKey(Key key, String fileName) {
        try (FileOutputStream fstream = new FileOutputStream(fileName);
             ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
            ostream.writeObject(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    public Key loadKey(String fileName) {
        try(FileInputStream fis = new FileInputStream(fileName)){
            try(ObjectInputStream ois = new ObjectInputStream(fis)){
                Object obj = ois.readObject();
                return (Key) obj;
            }
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
