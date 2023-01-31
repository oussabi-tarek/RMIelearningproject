package elearningproject;

import java.awt.*;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PainImpl extends UnicastRemoteObject implements Pain {
    public  PainImpl() throws  RemoteException{
        super();
    }
    @Override
    public void principale(String name,String profname) throws RemoteException {
        if(!profname.equals("")) {
            Elearning frame = new Elearning(name, profname);
            frame.setTitle("Elearning application");
            frame.setVisible(true);
        }
        else {
            Elearning frame = new Elearning(name,"");
            frame.setTitle("Elearning application");
            frame.setVisible(true);
        }

    }


}

