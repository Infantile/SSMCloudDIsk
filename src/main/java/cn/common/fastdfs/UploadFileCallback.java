package cn.common.fastdfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.csource.fastdfs.UploadCallback;

public class UploadFileCallback implements UploadCallback{
	private InputStream inStream;  
    
    public UploadFileCallback(InputStream inStream) {  
        this.inStream = inStream;  
    }
	
	@Override
	public int send(OutputStream out) throws IOException {
		int readBytes;  
        while((readBytes = inStream.read()) > 0) {  
            out.write(readBytes);  
        }  
        return 0;
	}

}
