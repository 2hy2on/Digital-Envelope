package com.project.digitalenvelope;

import java.security.*;

public class SignatureManager {


    final String algorithm = "SHA1withRSA";
    public byte[] create(Key key, byte[] data) {
        byte[] signature = null;
        try{
            Signature sig =  Signature.getInstance(algorithm);
            sig.initSign((PrivateKey) key);
            sig.update(data);
            signature = sig.sign();

        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return signature;
    }

    public boolean verify(byte[] data, byte[] signature, PublicKey publicKey)  {
        boolean res = false;
        try{
            Signature sig =  Signature.getInstance(algorithm);
            sig.initVerify(publicKey);
            sig.update(data);
            res = sig.verify(signature);
        }catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
}
