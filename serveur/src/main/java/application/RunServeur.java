package application;

import rmifacade.RMIFacadePandemic9;
import rmifacade.RMIFacadePandemic9Impl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RunServeur {
    public static final String RMI_SERVEUR = "rmi://127.0.0.1/pandemic9";
    public static final int RMI_PORT = 1099;

    public static void main(String[] args) {
        //Scanner sc = new Scanner(System.in);
        try {
            //Port de communication
            LocateRegistry.createRegistry(RMI_PORT);
            //Création de l'instance de la façade à embarquer
            RMIFacadePandemic9 rmiFacadePandemic9 = RMIFacadePandemic9Impl.getInstance();

            //Publication
            Naming.rebind(RMI_SERVEUR, rmiFacadePandemic9);

            System.out.println("Serveur démarré");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
