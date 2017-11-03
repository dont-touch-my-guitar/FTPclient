import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

public class MySimpleFtpClient {

    private String server = "ftp.byfly.by";
    private int port = 21;
    private String login = "";
    private String password = "";

    private int reply;
    private boolean error = false;

    public MySimpleFtpClient(String server, int port, String login, String password) {
        this.server = server;
        this.port = port;
        this.login = login;
        this.password = password;

    }


    public void getFiles() {

        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(server, port);
            System.out.println("Connected to " + server + ":" + port);
            System.out.print(ftp.getReplyString());
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }

            ftp.login(login, password);
            ftp.enterLocalPassiveMode();

            //FTPFile files[] = ftp.listFiles();


            for (FTPFile f : ftp.listDirectories()) {
                System.out.println(f.getName());
                //System.out.println(f.toFormattedString(displayTimeZoneId));
            }

            for (FTPFile f : ftp.listFiles()) {
                System.out.println(f.getName());
                //System.out.println(f.toFormattedString(displayTimeZoneId));
            }

            ftp.logout();

        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e1) {

                }


            }
            System.out.println(error ? 1 : 0);
        }
    }

    public void uploadFile(String filename, String destination) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(server, port);
            System.out.println("Connected to " + server + ":" + port);
            System.out.print(ftp.getReplyString());
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }

            ftp.login(login, password);
            ftp.enterLocalPassiveMode();

            InputStream input = new FileInputStream(filename);

            ftp.storeFile(destination, input);

            input.close();
            ftp.logout();

        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e1) {

                }


            }
            System.out.println(error ? 1 : 0);
        }
    }

    public void downloadFile(String path_to_file, String path_to_download) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(server, port);
            System.out.println("Connected to " + server + ":" + port);
            System.out.print(ftp.getReplyString());
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }

            ftp.login(login, password);
            ftp.enterLocalPassiveMode();

            File download_file = new File(path_to_download);

            OutputStream out = new BufferedOutputStream(new FileOutputStream(download_file));
            boolean success = ftp.retrieveFile(path_to_file, out);
            out.close();
            ftp.logout();

            if (success) {
                System.out.println("Downloaded Successfully!");
            } else {
                System.out.println("Error Downloading File!");
                System.exit(1);
            }


        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e1) {

                }


            }
            System.out.println(error ? 1 : 0);
        }
    }
}