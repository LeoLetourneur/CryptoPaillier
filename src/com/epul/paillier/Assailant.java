package com.epul.paillier;


import java.math.BigInteger;
import java.util.ArrayList;

public class Assailant extends Actor {

	private ArrayList<BigInteger> messages;
	
	public Assailant(String p_name) {
		super(p_name);
		this.setMessages(new ArrayList<BigInteger>());
	}
	
	public BigInteger attaque(ArrayList<BigInteger> publicKey) {
		
		BigInteger response = new BigInteger("1");
		
		BigInteger e = publicKey.get(0);
		BigInteger n = publicKey.get(1);
		BigInteger msg1 = messages.get(0);
		BigInteger msg2 = messages.get(1);
		
		System.out.println("e => "+e);
		System.out.println("n => "+n);
		
		response = msg2.subtract(msg1).add(new BigInteger("2")).modInverse(n)
                .multiply(msg2.add(msg1.multiply(new BigInteger("2"))).subtract(new BigInteger("1")))
                .mod(n);
		
		return response;
	}

	public ArrayList<BigInteger> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<BigInteger> messages) {
		this.messages = messages;
	}	
}
