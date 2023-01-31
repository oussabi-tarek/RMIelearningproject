package elearningproject;

import elearning.DatabaseConnection;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.security.Key;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;


public class Elearning extends javax.swing.JFrame implements Serializable{
    private  String iD,clientID="";
    private DatagramSocket s;


    InetAddress address;

    // fichier de reception
    String FILE_TO_RECEIVED = "C:\\Users\\DELL\\Downloads\\L.";
    File myfile;
    DefaultListModel dlm;
    String profname;

    public Elearning() {
        initComponents();
    }

    public  Elearning(String id,String profname) {
        this.profname=profname;
        this.iD=id;
        try{
            if(profname.equals("")) {
                this.s = new DatagramSocket();
                address = InetAddress.getByName("localhost");
                initComponents();
                dlm = new DefaultListModel();
                UL.setModel(dlm);
                idlabel.setText(iD);
                String name = id + ": register:null";
                byte[] buf = name.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, 2020);
                s.send(datagramPacket);
                new Read().start();
            }
            else{
                this.s = new DatagramSocket();
                address = InetAddress.getByName("localhost");
                initComponents();
                dlm = new DefaultListModel();
                UL.setModel(dlm);
                idlabel.setText(iD);
                String name = id + ": register:Prof "+profname;
                byte[] buf = name.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, 2020);
                s.send(datagramPacket);
                new Read().start();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public PaintPanel getPaintPanel() {
        return (PaintPanel) jPanel2;
    }

    class Read extends  Thread{
        public void run(){
            while(true){
                try{
                    byte b[]=new byte[20002];
                    DatagramPacket datagramPacket=new DatagramPacket(b,b.length);
                    s.receive(datagramPacket);
                    String m=new String(datagramPacket.getData(),0,datagramPacket.getLength());
                    System.out.println("I am "+iD+" and I received "+m);
                    if (m.contains("envoie a un client")){
//                        m=m.substring(18);
//                        StringTokenizer st=new StringTokenizer(m,":");
//                        String idClient=st.nextToken();
//                        m=idClient+" A quitté le chat";

                    }
                    // si le message contient le mot "a t envoye un fichier" c'est que le serveur a envoyé un fichier à un client en particulier
                    if(m.contains("a t envoye un fichier")){
                        m=m.substring(21);
                        StringTokenizer st = new StringTokenizer(m, ":");
                        // extraire l'ID du client qui a envoyé le fichier et l extension du fichier et la longueur du fichier
                        String idclient= st.nextToken();
                        String extension =st.nextToken();
                        String sizefile =st.nextToken();

                        System.out.println("idclient : "+idclient);
                        System.out.println("extension : "+extension);
                        System.out.println("sizefile : "+sizefile);

                        FILE_TO_RECEIVED=FILE_TO_RECEIVED+extension;
                        byte[] tampon = new byte[Integer.parseInt(sizefile)];
                        datagramPacket = new DatagramPacket(tampon, tampon.length);
                        s.receive(datagramPacket);
                        File file=new File(FILE_TO_RECEIVED);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        bufferedOutputStream.write(tampon, 0 , tampon.length );
                        bufferedOutputStream.flush();
                        msgBox.append("<"+idclient+" a t envoye un fichier>\n");
                    }
                    // c est que le client expediteur a envoye un fichier a  tous les clients
                    else if(m.contains("envoye un fichier a tous")){
                        m=m.substring(24);
                        StringTokenizer st = new StringTokenizer(m, ":");
                        // extraire l'ID du client qui a envoyé le fichier et l extension du fichier et la longueur du fichier
                        String idclient= st.nextToken();
                        String extension =st.nextToken();
                        String sizefile =st.nextToken();
                        FILE_TO_RECEIVED=FILE_TO_RECEIVED+extension;
                        byte[] tampon = new byte[Integer.parseInt(sizefile)];
                        datagramPacket = new DatagramPacket(tampon, tampon.length);
                        s.receive(datagramPacket);
                        File file=new File(FILE_TO_RECEIVED);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        bufferedOutputStream.write(tampon, 0 , tampon.length );
                        bufferedOutputStream.flush();
                        msgBox.append("<"+idclient+" a envoye a tous  un fichier>\n");
                    }
                    else{
                        // le cas ou le message est une liste des clients connectés au serveur UDP  on va afficher cette liste dans la liste UL
                        if(m.contains("listprepare")){
                            m=m.substring(11);
                            if(m.length()>0 ) {
                                System.out.println("hhhhhhhhh");
                                dlm.clear();

                                StringTokenizer st = new StringTokenizer(m, ",");
                                while (st.hasMoreTokens()) {
                                    String u = st.nextToken();
                                    System.out.println("voila : " + u);
                                    while (u.contains("listprepare")) {
                                        u = u.substring(11);
                                    }
                                    if (!iD.equals(u)) {

                                        dlm.addElement(u);

                                    }
                                    ArrayList<String> list = new ArrayList<String>();
                                    for (int i = 0; i < dlm.size(); i++) {
                                        list.add(dlm.get(i).toString());
                                    }
                                    list.add(iD);
                                    getPaintPanel().setUsers(list);
                                }
                            }

                        }
                        // la cas ou le message est une annonce par le serveur au client pour dire que le client x a quitte le chat
                        else if(!m.contains("envoie a un client")){
                            msgBox.append(""+ m + "\n");
                        }
                    }
                }catch(Exception ex){
                    break;
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        idlabel = new javax.swing.JLabel();
        selectall = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        sendButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        UL = new javax.swing.JList<>();
        fileupload = new javax.swing.JButton();
        jPanel2 = new PaintPanel(this,500,500,iD,this.profname);
        jToolBar1 = new PaintControl(this);


        setBackground(new java.awt.Color(204, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Bonjour :");

        idlabel.setText("................................................");

        selectall.setBackground(new java.awt.Color(242, 242, 242));
        selectall.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectall.setText("Selectionner tout");
        selectall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        selectall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectallActionPerformed(evt);
            }
        });

        msgBox.setColumns(20);
        msgBox.setRows(5);
        jScrollPane1.setViewportView(msgBox);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        sendButton.setBackground(new java.awt.Color(204, 204, 204));
        sendButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sendButton.setText("Envoyer");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Message");

        UL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ULMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(UL);

        fileupload.setText("Upload File");
        fileupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileuploadActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        jToolBar1.setRollover(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(idlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 969, Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(selectall, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 23, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(fileupload)
                                                .addGap(18, 18, 18)
                                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(idlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(selectall, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator1)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jTextField1)
                                        .addComponent(fileupload, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>


    private void selectallActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        clientID="";
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // c est le bouton pour envoyer un message à un client ou à tous les clients connectés au serveur en fonction de la valeur de l attribut clientID
    // et on verifiar si le message est vide ou non avant de l envoyer au serveur pour verifier que le client veut partager un fichier ou non
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here
        try{
            String msg=jTextField1.getText(),mm=msg;
            String CI=clientID;
            // si le message est vide on verifie si le client veut partager un fichier ou non
            if(msg.isEmpty() && myfile!=null){
                byte[] tampon = new byte[(int)myfile.length()];
                FileInputStream fileInputStream = new FileInputStream(myfile);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                bufferedInputStream.read(tampon, 0, tampon.length);
                // verifie si le client veut envoyer a tous les clients ou a un seul client
                if(!CI.isEmpty()){
                    // Get the file name
                    String fileName = myfile.getName();
                    // Find the index of the last '.' character in the file name
                    int lastDotIndex = fileName.lastIndexOf('.');
                    // Extract the extension from the file name
                    String extension = fileName.substring(lastDotIndex + 1);
                    msg="envoie fichier client"+myfile.length()+":"+CI+":"+extension;
                    DatagramPacket datagramPacket= new DatagramPacket(msg.getBytes(), msg.length(), address, 2020);
                    s.send(datagramPacket);
                    datagramPacket= new DatagramPacket(tampon, tampon.length, address, 2020);
                    s.send(datagramPacket);

                    System.out.println("Envoi du fichier "+myfile.length());
                    System.out.println("Fait.");
                    msgBox.append("<tu as envoye a "+CI+" un fichier>\n");
                    clientID="";
                }
                else{
                    String fileName = myfile.getName();
                    // Find the index of the last '.' character in the file name
                    int lastDotIndex = fileName.lastIndexOf('.');
                    // Extract the extension from the file name
                    String extension = fileName.substring(lastDotIndex + 1);
                    msg="envoiefile vers tous"+myfile.length()+":"+extension;
                    DatagramPacket datagramPacket= new DatagramPacket(msg.getBytes(), msg.length(), address, 2020);
                    s.send(datagramPacket);
                    datagramPacket= new DatagramPacket(tampon, tampon.length, address, 2020);
                    s.send(datagramPacket);
                    System.out.println("Envoi du fichier "+myfile.length());
                    System.out.println("Fait.");
                    msgBox.append("<tu as envoye a tous un fichier>\n");
                }
            }
            // si le client veut envoyer juste un message
            else{
                if(!clientID.isEmpty()){
                    msg="envoie a un client"+CI+":"+mm+":"+iD;
                    jTextField1.setText("");
                    byte[] tampon = msg.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(tampon, tampon.length, address, 2020);
                    s.send(datagramPacket);
                    msgBox.append("<tu as envoye a "+CI+" >"+mm+"\n");
                    clientID="";
                }else{
                    jTextField1.setText("");
                    byte[] tampon = msg.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(tampon, tampon.length, address, 2020);
                    s.send(datagramPacket);
                    msgBox.append("<tu as envoye a tous>"+mm+"\n");
                }
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    // on affecte le client choisi de la liste à l attribut clientID pour pouvoir envoyer un message à un client specifique
    private void ULMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        clientID=(String)UL.getSelectedValue();
    }

    // si le client ferme la fenetre on envoie un message au serveur pour le supprimer de la liste des clients connectés et informer les autres clients
    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        String i="formWindowClosing:"+iD;
        try{
            byte[] tampon = i.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(tampon, tampon.length, address, 2020);
            s.send(datagramPacket);
            // delete the course of the prof from the server
            if(iD.contains("Prof")) {
                String name=iD.substring(5);
                System.out.println("delete course "+name);
                DatabaseConnection db = new DatabaseConnection();
                // get the prof id
                String query = "SELECT id FROM professor WHERE name = '"+name+"'";
                ResultSet rs = db.getStatement().executeQuery(query);
                int id = 0;
                while (rs.next()) {
                    id = rs.getInt("id");
                }
                db.getStatement().executeUpdate("DELETE FROM cours WHERE prof_id=" + id);
            }
            for(PaintPanel pt:Server.pt){
                if(pt.equals(this)){
                    Server.pt.remove(pt);
                    break;
                }
            }
//            Set<String> keys=Server.cours.keySet();
//            for(String key:keys){
//                if(Server.cours.get(key).equals(iD)){
//                    Server.cours.remove(key);
//                    break;
//                }
//            }
            this.dispose();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    // on verifie si le client veut partager un fichier ou non et on affecte le fichier choisi à l attribut myfile
    private void fileuploadActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        // Show the file chooser and get the user's selection
        int result = fileChooser.showOpenDialog(this);
        // If the user clicked the "Open" button, set the selected image as the icon for the label
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.myfile=file;
        }
    }

//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Elearning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Elearning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Elearning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Elearning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Elearning().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify
    private javax.swing.JList<String> UL;
    private javax.swing.JButton fileupload;
    private javax.swing.JLabel idlabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JButton selectall;
    private javax.swing.JButton sendButton;
    // End of variables declaration
}
