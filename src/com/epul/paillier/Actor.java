package com.epul.paillier;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Actor {

	public static final boolean DEBUG = false;
	public static final int KEY_SIZE = 512;
	public static final int GEN_SIZE = 16;
	
	private String name;
	private HashMap<String, BigInteger> keys;
    
	public Actor(String p_name) {
		
		this.setName(p_name);
		this.setKeys(new HashMap<>());
	}

	public BigInteger getPublicKey() {
        return this.keys.get("n");
    }
	
	private BigInteger getPrivateKey() {
        return this.keys.get("phi");
    }
	
	public BigInteger Encrypt(BigInteger x, BigInteger pKey) {

        BigInteger N = pKey.multiply(pKey);

        BigInteger r;
		do {
			r = new BigInteger(pKey.bitLength(), new Random());
		} while (r.compareTo(pKey) >= 0);
		
		pKey = pKey.add(BigInteger.ONE).modPow(x, N).multiply(r.modPow(pKey, N)).mod(N);
		
		return pKey;
    }

	public BigInteger Decrypt(BigInteger x) {
        
		BigInteger calcul = this.getPublicKey().modPow(BigInteger.ONE.negate(), this.getPrivateKey());
        
        BigInteger r = x.modPow(calcul, this.getPublicKey());
		BigInteger N = this.getPublicKey().multiply(this.getPublicKey());
		BigInteger m = x.multiply(r.modPow(this.getPublicKey().negate(), N)).mod(N).subtract(BigInteger.ONE).divide(this.getPublicKey());
		
		return m;
	}
	
	public void Keygen() {
		
		Random rand = new Random();
        HashMap<String, BigInteger> keygen = new HashMap<>();
        
        BigInteger p = new BigInteger(KEY_SIZE, 2, rand);
        while (!primalite_fermat(p)) { p = p.nextProbablePrime(); }
        if(DEBUG) System.out.println("p premier : " + p.toString());
	        
	    BigInteger q = new BigInteger(KEY_SIZE, 2, rand);
        while (!primalite_fermat(q)) { q = q.nextProbablePrime(); }
        if(DEBUG) System.out.println("q premier : " + q.toString());
        
        BigInteger n = p.multiply(q);
        if(DEBUG) System.out.println("n : " + n.toString());
        
        BigInteger phi_n = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        if(DEBUG) System.out.println("phi_n : " + phi_n.toString());
        
        keygen.put("n", n);
        keygen.put("phi", phi_n);
		
        setKeys(keygen);
    }
	
	public static boolean primalite_fermat(BigInteger m) {
        return new BigInteger("2").modPow(m.subtract(BigInteger.ONE), m).equals(BigInteger.ONE);
    }
	
	public static String IntToString(String s) {
        String msg = "";
        while (s.length() % 3 != 0) {
            s = "0" + s;
        }
        for (int i = 0; i < s.length() / 3; i++) {
            int temp = Integer.parseInt(s.subSequence(i * 3, (i + 1) * 3).toString());
            msg += Character.toString((char) temp);
        }
        return msg;
    }

    public static String StringToInt(String s) {
        String msg = "";
        for (int i = 0; i < s.length(); i++) {
            String character = ((int) s.charAt(i)) + "";
            while (character.length() < 3) {
                character = "0" + character;
            }
            msg += character;
        }
        return msg;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, BigInteger> getKeys() {
		return keys;
	}

	public void setKeys(HashMap<String, BigInteger> keys) {
		this.keys = keys;
	}
	
	public String toString() {
		
		return "Actor "+this.getName();
	}
}
