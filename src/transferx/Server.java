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
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Server extends Thread {
        
    
        private ServerSocket ss;
        String fileName;
        long fileSize;
        
        static void pn(Object s){
                System.out.println(s);
        }

        static void p(Object s){
                System.out.print(s);
        }

        public Server(int port) {
                try {
                        ss = new ServerSocket(port);
                        pn("Listening on Server Port: " + port);
                        
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        public void run() {
                while (true) {
                        try {
                                Socket clientSock = ss.accept();
                                saveFileInfo(clientSock);
                                saveFile(clientSock);
                                
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
        }

        void saveFileInfo(Socket clientFileInfo) throws Exception{
                InputStream is = clientFileInfo.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String info = br.readLine();
                System.out.println(info);
                
                String[] fileInfo = info.split(" ");
                fileName = fileInfo[0];
                fileSize = Long.parseLong(fileInfo[1]);
         
        }

        private void saveFile(Socket clientSock) throws IOException {


                DataInputStream dis = new DataInputStream(clientSock.getInputStream());
                FileOutputStream fos = new FileOutputStream(fileName);
                byte[] buffer = new byte[4096];
                
                
                int filesize = (int)fileSize; // Send file size in separate msg
                int read = 0;
                int totalRead = 0;
                int remaining = filesize;
                while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                        totalRead += read;
                        remaining -= read;
                      //  System.out.println("read " + totalRead + " bytes.");
                        fos.write(buffer, 0, read);
                }
                System.out.println("Transfer complete !!\n");
                fos.close();
                dis.close();
        }
//        
//        public static void main(String[] args) {
//                Server fs = new Server(42000);
//                fs.start();
//        }

}