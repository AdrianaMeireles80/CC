import java.io.*;
import java.nio.ByteBuffer;

public class PDU implements Serializable {

	private int numSeq; 
    	private byte[] data;
	
	public PDU (int numSeq, byte[] data){
		this.numSeq = numSeq;
		this.data = data;
	}
	
	public PDU(byte[] data) throws IOException, ClassNotFoundException{
		
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(in);
		PDU p = (PDU) ois.readObject();	
		this.numSeq = p.getNumSeq();
		this.data = p.getData();

	} 

	public byte[] toByte ()  throws IOException{
	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		oos.flush();
		return bos.toByteArray();
	} 

	public int getNumSeq() {

		return numSeq;

	}

	public void setNumSeq(int numSeq) {
	
		this.numSeq = numSeq;

	}
   	
	public byte[] getData() {

		return data;

	}

	public void setData(byte[] data) {
	
		this.data = data;

	}

}	