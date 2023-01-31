package elearningproject;

import java.net.DatagramSocket;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Pain extends Remote {
    public void principale(String name,String profname) throws RemoteException;

}



















