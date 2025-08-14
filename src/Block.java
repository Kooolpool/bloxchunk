

import java.util.Date;
public class Block {

	public String hash;
	public String previousHash;
	private String data; //our data will be a simple message.
	private long timeStamp; //as number of milliseconds since 1/1/1970.
	private int nonce; //used for proof of work, not implemented in this example.
	
	//Block Constructor.
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}

	// Standard SHA-256 implementation
	public static String applySha256(String input){	
		try {
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

    public String calculateHash() {
        String calculatedHash = StringUtil.applySha256(
            previousHash + 
            Long.toString(timeStamp) +
            data
            );
        return calculatedHash;

        
    }

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); // Create a string with difficulty * "0"
		while(!hash.substring(0, difficulty).equals(target)) {
			nonce++; // Increase nonce value until hash starts with the target string
			hash = calculateHash(); // Recalculate hash
		}
		System.out.println("Block Mined!!! : " + hash);
	}
}