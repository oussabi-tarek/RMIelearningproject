package elearning;

import elearning.DatabaseConnection;
import elearningproject.Pain;
import elearningproject.Server;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.rmi.Naming;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.*;

public class CourseCardList {
    public HashMap buttons=new HashMap();
    public CourseCardList(String name,ResultSet rs) throws SQLException {
        // Create a new JFrame
        JFrame frame = new JFrame("Course Card List");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel
        JPanel panel = new JPanel();
        // set color to  of panel
        panel.setBackground(new java.awt.Color(204, 255, 255));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        // set to center of the panel every element
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // set a border to every line
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        DatabaseConnection db = new DatabaseConnection();
//        String query= "SELECT * FROM cours";
//        ResultSet rs2 = db.getStatement().executeQuery(query);
//        Set<String> keys= Server.cours.keySet();
//        System.out.println("keys: "+keys);
//        for(String k:keys) {
//            System.out.println("dddddddd");
//            panel.add(new JLabel("Course: "+k.toString()+"                by:  "+k+"  "));
//            JButton button = new JButton("Participer");
//            button.setPreferredSize(new Dimension(100, 30));
//            buttons.put(button,k);
//            panel.add(button);
//        }
       while (rs.next()){
            String objet=rs.getString("objet");
            int id=rs.getInt("prof_id");
            DatabaseConnection db = new DatabaseConnection();
            ResultSet rs1=db.getStatement().executeQuery("SELECT * FROM professor WHERE id="+id);
            String profname="";
            while (rs1.next()){
                  profname=rs1.getString("name");
            }
            panel.add(new JLabel("Course: "+objet+"                by:  "+profname+"  "));
            JButton button = new JButton("Participer");
            button.setPreferredSize(new Dimension(100, 30));
            buttons.put(button,profname);
            panel.add(button);
            panel.add(new JLabel("        \n"));
        }

        Set<JButton> keys1 = buttons.keySet();
        for(JButton key: keys1){
            key.addActionListener((ActionEvent e) -> {
                String profname=(String) buttons.get(key);
                System.out.println("this is profname: "+profname);
                String url="rmi://localhost/paint";
                try {
                    Pain od=(Pain) Naming.lookup(url);
                    od.principale(name,profname);
                }catch (Exception ex){
                    System.out.println("Serveur Introuvable");
                    ex.printStackTrace();
                }
               frame.dispose();
            });
        }


        // Set the JPanel as the content pane of the JFrame
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

}
