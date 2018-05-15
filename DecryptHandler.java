
import java.util.*;
class DecryptHandler {
	String decrypted;
	String encrypted;
	String cipher;
	String key;
	DecryptHandler(String encrypted,String cipher,String key){
		this.encrypted=encrypted;
		this.cipher=cipher;
		this.key=key;
		switch(cipher){
			case "Ceasar":{
				ceasar_decrypt cea = new ceasar_decrypt(this.encrypted,Integer.parseInt(this.key));
				this.decrypted=cea.decrypt();
				break;
			}
			case "Playfair":{
				this.encrypted=this.encrypted.toUpperCase();
				this.key=this.key.toUpperCase();
				playfair_decrypt pd=new playfair_decrypt(this.encrypted,this.key);
				this.decrypted=pd.decrypt();
				System.out.println("decrupted:"+this.decrypted);
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
				this.decrypted=HillCipherDecryption.decrypt(this.encrypted,key1);

				break;
			}
			case "Columnar":{
				
				this.decrypted=columnarTranspose.decryptCT(this.key,this.encrypted);

				break;

			}
			case "Onetime":{
				
				this.decrypted=OTPCipher.OTPDecryption(this.encrypted,this.key);
				break;

			}
			case "SDES":{
				KeyGeneration KG = new KeyGeneration();
				KG.GenerateKeys(this.key);
				Encryption enc  = new Encryption();
				int[] ct=enc.encrypt( this.encrypted ,KG.getK2(),KG.getK1());
				
					this.decrypted="";
					for (int i=0; i<ct.length; i++) {
						this.decrypted+=String.valueOf(ct[i]);
					}
					
	
				break;

			}
			case "Railfence":{
				this.decrypted=Railfence.decrypt(this.encrypted,Integer.parseInt(this.key));
				System.out.println(this.decrypted);


				break;
			}
			default:{System.out.println("NotFound31232"+this.cipher);}
		}

	}
	public String getDecrypted(){return this.decrypted;}
	public String getCipher(){return this.cipher;}
	public String getKey(){return this.key;}
	public String getEncrypted(){return this.encrypted;}

	public void setEncrypted(String encrypted){this.encrypted=encrypted;}
	public void setCipher(String cipher){this.cipher=cipher;}
	public void setKey(String key){this.key=key;}
}