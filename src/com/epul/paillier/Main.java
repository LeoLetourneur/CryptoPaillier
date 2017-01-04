package com.epul.paillier;


import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		
		Sender alice = new Sender("Alice");
		Receiver bob = new Receiver("Bob");
		
		String sentence = "Salut c'est Jack";
		System.out.println("Texte à transmettre : " +sentence);
		
		BigInteger texteBig = new BigInteger(Actor.StringToInt(sentence));
		System.out.println("Texte biginteger à transmettre : " +texteBig.toString());
		
		BigInteger encrypt = alice.Encrypt(texteBig, bob.getPublicKey());
		System.out.println("Texte crypté : " +  encrypt.toString());
		
		BigInteger decrypt = bob.Decrypt(encrypt);
		System.out.println("Code décrypté : " + decrypt.toString());
		System.out.println("Texte décrypté : " + Actor.IntToString(decrypt.toString()));
		
	}
}
