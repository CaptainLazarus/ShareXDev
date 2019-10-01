/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferx;

/**
 *
 * @author captainlazarus
 */
import java.io.*;
import java.net.*;

public class Client {
        
        int flag = 0;

        static void pn(Object s){
                System.out.println(s);
        }

        static void p(Object s){
                System.out.print(s);
        }

        public Client(String host, int port, File file) {
                try {
                        Socket s = new Socket(host, port);
                        flag = 1;
                        sendFile(file , s);
                } catch (Exception e) {
                        pn(e);
                }               
        }
        
  
        
        public void sendFile(File file , Socket s) throws IOException {
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                FileInputStream fis = new FileInputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                
                long x = (long)Math.ceil((float)file.length()/512)*512;

                String sendMessage = file.getName()+" "+x+"\n";
                
                bw.write(sendMessage);
                bw.flush();
                System.out.println("Message sent to the server : "+sendMessage);
                
                byte[] buffer = new byte[4096];
                
                while (fis.read(buffer) > 0) {
                        dos.write(buffer);
                }
                
                s.shutdownOutput();
                s.shutdownInput();
                //fis.close();
                //dos.close();  
        }

}