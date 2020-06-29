import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main {

        public static void main(String [] args) throws IOException
        {
            JFrame f=new JFrame();
            f.setBounds(0,0,400,400);

            JPanel panel = new JPanel();
            panel.setBackground(java.awt.Color.gray);

            JLabel l =new JLabel("Enter your Username and Password SVP...");
            l.setBounds(80,40,400,30);

            JLabel client=new JLabel("Username:");
            client.setBounds(100,90,200,30);
            JTextField t1=new JTextField();
            t1.setBounds(100,125,200,30);

            JLabel mdp=new JLabel("Password:");
            mdp.setBounds(100,160, 200, 30);
            JPasswordField t2=new JPasswordField();
            t2.setBounds(100,195,200,30);

            JButton b1=new JButton("Connect");
            b1.setBounds(150, 250, 100, 40);

            JLabel l1=new JLabel();
            l1.setBounds(10,200,400,30);

            f.add(l);
            f.add(client);
            f.add(t1);
            f.add(mdp);
            f.add(t2);
            f.add(b1);
            f.add(l1);
            f.add(panel);

            b1.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent actionEvent) {

                                         {

                                                 // TODO Auto-generated method stub
                                                 FTP client = new FTP();
                                                 try {
                                                     client.connection("192.168.1.34", 21);
                                                     client.login(t1.getText(), t2.getText());
                                                     l1.setText(client.getRep());

                                                     if (client.logged) {
                                                         f.hide();
                                                         JFrame f2 = new JFrame("File Transfert Protocol");
                                                         f2.setSize(800, 650);

                                                         JPanel panel2 = new JPanel();
                                                         panel2.setBackground(Color.gray);

                                                         JTextArea l1 = new JTextArea();
                                                         l1.setBounds(100, 50, 600, 300);
                                                         JScrollPane jsp = new JScrollPane(l1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                                                         jsp.setBounds(100, 50, 600, 300);

                                                         JLabel label1 = new JLabel("Retreive File: ");
                                                         label1.setBounds(150, 350, 300, 30);
                                                         JButton b1 = new JButton("Retreive");
                                                         b1.setBounds(150, 380, 150, 25);

                                                         JLabel label2 = new JLabel("Store File: ");
                                                         label2.setBounds(150, 400, 300, 30);
                                                         JButton b2 = new JButton("Store");
                                                         b2.setBounds(150, 430, 150, 25);

                                                         JLabel label3 = new JLabel("List: ");
                                                         label3.setBounds(150, 450, 300, 30);
                                                         JButton b3 = new JButton("List");
                                                         b3.setBounds(150, 480, 150, 25);

                                                         JLabel label4 = new JLabel("Delete File: ");
                                                         label4.setBounds(150, 500, 300, 30);
                                                         JButton b4 = new JButton("Delete");
                                                         b4.setBounds(150, 530, 150, 25);

                                                         JLabel label5 = new JLabel("Current path:");
                                                         label5.setBounds(500, 350, 300, 30);
                                                         JButton b5 = new JButton("PWD");
                                                         b5.setBounds(500, 380, 150, 25);

                                                         JLabel label6 = new JLabel("Choose Directory:");
                                                         label6.setBounds(500, 400, 300, 30);
                                                         JButton b6 = new JButton("CWD");
                                                         b6.setBounds(500, 430, 150, 25);

                                                         JLabel label7 = new JLabel("Deconnexion:");
                                                         label7.setBounds(500, 450, 300, 30);
                                                         JButton b7 = new JButton("Déconnexion");
                                                         b7.setBounds(500, 480, 150, 25);

                                                         JLabel label8 = new JLabel("delete repository:");
                                                         label8.setBounds(500, 500, 300, 30);
                                                         JButton b8 = new JButton("delete");
                                                         b8.setBounds(500, 530, 150, 25);

                                                         f2.add(jsp);
                                                         f2.add(label1);
                                                         f2.add(b1);
                                                         f2.add(label2);
                                                         f2.add(b2);
                                                         f2.add(label3);
                                                         f2.add(b3);
                                                         f2.add(label4);
                                                         f2.add(b4);
                                                         f2.add(label5);
                                                         f2.add(b5);
                                                         f2.add(label6);
                                                         f2.add(b6);
                                                         f2.add(label7);
                                                         f2.add(b7);
                                                         f2.add(label8);
                                                         f2.add(b8);

                                                         f2.add(panel2);


                                                         f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                         f2.setVisible(true);

                                                         b1.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent actionEvent) {
                                                                 try {
                                                                     JFileChooser jfc=new JFileChooser();

                                                                     jfc.setCurrentDirectory(new java.io.File("C:\\Users\\toshiba\\Desktop\\M1"));
                                                                     jfc.setDialogTitle("Serveur FTP");
                                                                     jfc.showOpenDialog(null);
                                                                     File file=jfc.getSelectedFile();

                                                                     client.ret(file.getName());
                                                                     l1.append(client.getRep());
                                                                         l1.append("\n");

                                                                 } catch (IOException e) {
                                                                     e.printStackTrace();
                                                                 }

                                                             }
                                                         });
                                                         b2.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent actionEvent) {
                                                                 try {
                                                                     JFileChooser jfc=new JFileChooser();

                                                                     jfc.setCurrentDirectory(new java.io.File("C:\\Users\\toshiba\\Desktop\\M1"));
                                                                     jfc.setDialogTitle("Serveur FTP");
                                                                     jfc.showOpenDialog(null);
                                                                     File file=jfc.getSelectedFile();

                                                                     client.stor(file);
                                                                     l1.append(client.getRep());
                                                                         l1.append("\n");

                                                                 } catch (IOException e) {
                                                                     e.printStackTrace();
                                                                 }

                                                             }
                                                         });
                                                         b3.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent actionEvent) {
                                                                 try {
                                                                     l1.append( client.list()+"\n");
                                                                 } catch (IOException e) {
                                                                     e.printStackTrace();
                                                                 }

                                                             }
                                                         });
                                                         b4.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent actionEvent) {
                                                                 try {
                                                                     JFileChooser jfc=new JFileChooser();

                                                                     jfc.setCurrentDirectory(new java.io.File("C:\\Users\\toshiba\\Desktop\\M1"));
                                                                     jfc.setDialogTitle("Serveur FTP");
                                                                     jfc.showOpenDialog(null);
                                                                     File file=jfc.getSelectedFile();
                                                                         String filename=file.getName();
                                                                     client.deletefile(filename);
                                                                     l1.append(client.getRep());
                                                                     l1.append("\n");

                                                                 } catch (IOException e) {
                                                                     e.printStackTrace();
                                                                 }

                                                             }
                                                         });  b5.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent actionEvent) {
                                                                 try {

                                                                     l1.append( client.pwd()+"\n");
                                                                 } catch (IOException e) {
                                                                     e.printStackTrace();
                                                                 }

                                                             }
                                                         });
                                                         b6.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent actionEvent) {
                                                                 try {

                                                                     String option=JOptionPane.showInputDialog("Choisissez un repertoire:");
                                                                     if(option!=null) {

                                                                         l1.append(client.cwd(option)+"\n");

                                                                     }
                                                                     } catch (IOException e) {
                                                                     e.printStackTrace();
                                                                 }

                                                             }
                                                         });
                                                         b7.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent actionEvent) {
                                                             client.quit();
                                                             f2.hide();
                                                             }
                                                         });
                                                         b8.addActionListener(new ActionListener() {
                                                             @Override
                                                             public void actionPerformed(ActionEvent actionEvent) {
                                                                 try {
                                                                     JFileChooser jfc=new JFileChooser();

                                                                     jfc.setCurrentDirectory(new java.io.File("C:\\Users\\toshiba\\Desktop\\M1"));
                                                                     jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                                                     jfc.setDialogTitle("Serveur FTP");
                                                                     jfc.showOpenDialog(null);
                                                                     File file=jfc.getSelectedFile();
                                                                     String filename=file.getName();
                                                                     client.directories(filename);
                                                                     l1.append(client.getRep());
                                                                     l1.append("\n");
                                                                 } catch (IOException | InterruptedException e) {
                                                                     e.printStackTrace();
                                                                 }

                                                             }
                                                         });
                                                     }
                                                 } catch (IOException e1) {
                                                     // TODO Auto-generated catch block
                                                     e1.printStackTrace();
                                                 }
                                         }
                                     }
                                 });


                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);

        }

    }



