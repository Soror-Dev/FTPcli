import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class FTP {
    private Socket connectSock;
    private PrintStream outputStream;
    private BufferedReader inputStream;
    public Socket DataSock;
    public BufferedWriter write, writerData;
    public BufferedInputStream reade,reader;
    public static boolean logged = false;
    public String reponse;
    public String dataIP;

    public int dataPort;
    public static long restartPoint = 0L;



    public String list() throws IOException{

        send("TYPE ASCII");
        read();
        enterPassiveMode();

        DataSock = new Socket(dataIP, dataPort);

        reader = new BufferedInputStream(DataSock.getInputStream());

        writerData = new BufferedWriter(new OutputStreamWriter(DataSock.getOutputStream()));


        executeCommand("LIST");

        return getFullServerReply()+"\n"+readData();

    }



    private String readData() throws IOException{

        String response = "";

        byte[] b = new byte[1024];

        int stream;
        while((stream = reader.read(b)) != -1){

            response += new String(b, 0, stream);

        }

        return response;

    }

    public boolean connection(String host, int port)throws IOException
    {
        /*création de la socket de connexion*/
        connectSock = new Socket(host, port);
        /*envoyer les données au server */
        outputStream = new PrintStream(connectSock.getOutputStream());
        /*lire la réponse du server*/
        inputStream = new BufferedReader(new InputStreamReader(connectSock.getInputStream()));

        reade = new BufferedInputStream(connectSock.getInputStream());
        write = new BufferedWriter(new OutputStreamWriter(connectSock.getOutputStream()));

        if ( Integer.parseInt(getFullServerReply().substring(0, 3))!=220){
            disconnect();
            return false;
        }
        return true;
    }
    public boolean login(String username, String password)throws IOException
    {
        /*envoyer nom du user et le passeword au server FTP*/
        int response = executeCommand("USER " + username);
        if (response!=331) {
            setRep("Erreur de connexion avec le compte utilisateur : \n" + response);
            return false;
        }
        response = executeCommand("PASS " + password);
        if(response!=230) {
            logged =false;
            return false;
        }
        else {
            logged =true;
            return true;
        }
    }

    public int executeCommand(String command)throws IOException
    {
        outputStream.println(command);
        return  Integer.parseInt(getFullServerReply().substring(0, 3));
    }

    public void stor(File file) throws IOException {
        executeCommand("TYPE ASCII");
        openPasv();
        writerData = new BufferedWriter(new OutputStreamWriter(DataSock.getOutputStream()));

        OutputStream outputStream=DataSock.getOutputStream();
        //la commande stor
        executeCommand("STOR "+file.getName());
        //the size
        byte[] bytes = new byte[16 * 1024];

        //read
        InputStream in = new FileInputStream(file);
        //send
        int size;
        //
        while ((size = in.read(bytes)) > 0)
        {
            outputStream.write(bytes, 0, size);
        }

    }
    public  String getExecutionResponse(String command)throws IOException
    {
        outputStream.println(command);
        String reply;
        reply = inputStream.readLine();
        return reply;
    }

    public  boolean openPasv() throws IOException
    {
        String tmp = getExecutionResponse("PASV");

        String ip = null;
        int port = -1;
        int debut = tmp.indexOf('(');
        int fin = tmp.indexOf(')', debut + 1);
        if (debut > 0) {
            //they must start with (
            String dataLink = tmp.substring(debut + 1, fin);
            StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
            System.out.println(tmp.indexOf(2));

            //ip adress contains .
            ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."
                    + tokenizer.nextToken() + "." + tokenizer.nextToken();
            //calculate the port number
            port = Integer.parseInt(tokenizer.nextToken()) * 256
                    + Integer.parseInt(tokenizer.nextToken());
            DataSock = new Socket(ip, port);

        }
        int response=Integer.parseInt(tmp.substring(0,3));
        if(response >= 200 && response < 300) return true;
        else return false;
    }


    public void ret(String fileName) throws IOException {

        executeCommand("TYPE ASCII");
        openPasv();
        writerData = new BufferedWriter(new OutputStreamWriter(DataSock.getOutputStream()));
        executeCommand("RETR "+fileName);
        RandomAccessFile outfile = new RandomAccessFile(fileName, "rw");
        if (restartPoint != 0) {
            outfile.seek(restartPoint);
        }
        FileOutputStream out = new FileOutputStream(outfile.getFD());
        InputStream inputStream = DataSock.getInputStream();
        byte[] bytes = new byte[16 * 1024];
        int size;
        while ((size = inputStream.read(bytes)) > 0)
        {
            out.write(bytes, 0, size);
        }

        getFullServerReply();
        DataSock.close();
        inputStream.close();

    }


    public void deletefile(String filename) throws IOException {
        executeCommand("DELE "+filename);
    }

    public void directories(String filename) throws IOException, InterruptedException {
        executeCommand("RMD "+filename);
    }

    private String getFullServerReply() throws IOException
    {
        String reply;
        do {
            reply = inputStream.readLine();
            debugPrint(reply);
            setRep(reply);
        } while(!(Character.isDigit(reply.charAt(0)) &&
                Character.isDigit(reply.charAt(1)) &&
                Character.isDigit(reply.charAt(2)) &&
                reply.charAt(3) == ' '));

        return reply;
    }

    public void quit(){

        try {

            executeCommand("QUIT");

        } catch (IOException e) {

            e.printStackTrace();

        }  finally{



            try {

                connectSock.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

            finally{

                connectSock = null;

            }

        }

    }
    private void send(String command) throws IOException{

        command += "\r\n";



        write.write(command);

        write.flush();

    }



    /**

     * Méthode permettant de lire les réponses du FTP

     * @return

     * @throws IOException

     */
    public String cwd(String dir) throws IOException{

        //On envoie la commande

        send("CWD " + dir);

        //On lit la réponse

        return read();

    }

    public String pwd() throws IOException{

        //On envoie la commande

        send("PWD");

        //On lit la réponse

        return read();

    }
    public String read() throws IOException{

        String response = "";

        int stream;

        byte[] b = new byte[4096];

        stream = reade.read(b);

        response = new String(b, 0, stream);



        return response;

    }

    private void enterPassiveMode() throws IOException{


        send("PASV");

        String response = read();

        System.out.println(response);

        String ip = null;

        int port = -1;

        int debut = response.indexOf('(');

        int fin = response.indexOf(')', debut + 1);

        if(debut > 0){

            String dataLink = response.substring(debut + 1, fin);

            StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");

            try {

                ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."

                        + tokenizer.nextToken() + "." + tokenizer.nextToken();

                port = Integer.parseInt(tokenizer.nextToken()) * 256

                        + Integer.parseInt(tokenizer.nextToken());

                dataIP = ip;
                System.out.println(dataIP +"   "+ip);

                dataPort = port;

                System.out.println(dataPort +"   "+ port);

            } catch (Exception e) {

                throw new IOException("SimpleFTP received bad data link information: "

                        + response);

            }

        }

    }



    public void disconnect()
    {
        if (outputStream != null) {
            try {
                if (logged) { logout(); };
                outputStream.close();
                inputStream.close();
                connectSock.close();
            } catch (IOException e) {}

            outputStream = null;
            inputStream = null;
            connectSock = null;
        }
    }

    public boolean logout()throws IOException
    {
        /*envoyer la commande au server qui permet de se deconnecter*/
        int response = executeCommand("Logout");
        if(response==221) {
            logged =false;
            return false;
        }else{
            logged =true;
            return true;
        }
    }

    private void debugPrint(String message)
    {
        System.out.println(message);
        setRep(message);
    }

    private void setRep(String rep) {
        this.reponse =rep;
    }

    public String getRep() {
        return reponse;
    }

    private void addRep(String s) {
        this.reponse +=" \n "+s;
    }

}



