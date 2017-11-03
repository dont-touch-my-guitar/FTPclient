public class Main {

    public static void main(String[] args) {

        MySimpleFtpClient connection = new MySimpleFtpClient("ftp.byfly.by", 21, "anonymous", "");
        //connection.getFiles();
        connection.downloadFile("/test/10mb.txt", "D:/IDEA/FTPclient/downloaded_file.txt");
    }
}
