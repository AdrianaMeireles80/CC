import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class PDU implements Serializable {

	private int numSeq; 
    private byte[] data;

    public PDU(){

    }
	
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

    public Map<Integer, PDU> stringToBlocks(String s, int size){
        
        byte buf[] = new byte[5*1024+200];
        buf = s.getBytes();

        Map<Integer, PDU> aux = new HashMap<>();

        byte value[];
        int pos = 0;
        int byteN, i, j, k;

        for(i = 0; pos < buf.length; pos = (i+1)*size, i++){
            byteN = Math.min(size, buf.length-pos);
            value = new byte[byteN];

            for(j = pos, k = 0; k < byteN; j++, k++)
                value[k] = buf[j];
            
            PDU pdu = new PDU(i, value);
            aux.put(i, pdu);
        }          

        return aux;
    }
}	