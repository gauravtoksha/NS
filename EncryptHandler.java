import java.io.Serializable;
import java.util.*;
class EncryptHandler implements Serializable {
	String plaintext;
	String encrypted;
	String cipher;
	String key;
	EncryptHandler(){
	}
	EncryptHandler(String plaintext,String cipher,String key){
		this.plaintext=plaintext;
		this.cipher=cipher;
		this.key=key;

		//for Testing
		this.encrypted=this.plaintext;
		System.out.println("In Encrypted object set:"+this.encrypted);
		//

	}
	// execute this function to start encrypting
	public void startEncryption(){
		switch(cipher){
			case "Ceasar":{
				ceasar_encrypt cea = new ceasar_encrypt(this.plaintext,Integer.parseInt(this.key));
				this.encrypted=cea.encrypt();
				break;
			}
			case "Playfair":{
				//playfair
				break;
			}
			case "Hill":{
				Scanner sc = new Scanner(this.key).useDelimiter("[,|]");
				int m=2;
				if(this.key.length()>4)
					m=3;

				int[][] key1=new int[m][m];
					

				for (int i=0,k=0; i<m; i++,k++) {
					for (int j=0; j<m; j++,k++) {
						key1[i][j]=sc.nextInt();
					}					
				}

				this.encrypted=HillCipherEncryption.encrypt(this.plaintext,key1);
				
				break;
			}
			case "Columnar":{

				this.encrypted=columnarTranspose.encryptCT(this.key,this.plaintext);

				break;

			}
			case "Onetime":{
				
				this.key=OTPCipher.RandomAlpha(this.plaintext.length());
				this.encrypted=OTPCipher.OTPEncryption(this.plaintext,this.key);

				break;

			}
			case "SDES":{
				    KeyGeneration KG = new KeyGeneration();
				    Encryption enc  = new Encryption();
				    KG.GenerateKeys(this.key);
					int[] ct=enc.encrypt( this.plaintext,KG.getK1(),KG.getK2());
					System.out.println(ct);

					this.encrypted="";
					for (int i=0; i<ct.length; i++) {
						this.encrypted+=String.valueOf(ct[i]);
					}
					
				break;

			}
			default:{System.out.println("NotFound");}
		}
		
	} 
	public String getEncrypted(){return this.encrypted;}
	public String getCipher(){return this.cipher;}
	public String getKey(){return this.key;}

	public void setPlaintext(String plaintext){this.plaintext=plaintext;}
	public void setCipher(String cipher){this.cipher=cipher;}
	public void setKey(String key){this.key=key;}
}