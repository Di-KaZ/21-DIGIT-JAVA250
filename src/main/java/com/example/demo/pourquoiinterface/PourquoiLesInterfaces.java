package com.example.demo.pourquoiinterface;

import java.util.ArrayList;
import java.util.List;

interface Notification {

    void notifierClient(Client client);
}

class Client {

}

class ResteDuCode {

    InscriptionService inscriptionService;

    public ResteDuCode(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    void traitement() {
        //blabla
        inscriptionService.inscrireClient(new Client());
    }
}

class InscriptionService {

    List<Notification> notifications;

    InscriptionService(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void inscrireClient(Client client) {
        //faire les controles et enregistrer le client
        //blabla

        for (Notification notification : notifications) {
            notification.notifierClient(client);
        }
    }
}

public class PourquoiLesInterfaces {
    public static void main(String[] args) {
        // String variable = null;
        // System.out.println(variable);
        // System.out.println(variable.equals("test")); //NullPointerException
        // System.out.println(variable.length()); //NullPointerException

        //CONFIG
        List<Notification> systemeNotifications = new ArrayList<>();

        systemeNotifications.add(new NotificationMail());
        systemeNotifications.add(new NotificationSms());
        systemeNotifications.add(new NotificationCourier());

        InscriptionService inscriptionService = new InscriptionService(systemeNotifications);

        ResteDuCode resteDuCode = new ResteDuCode(inscriptionService);

        //simulation d'une inscription
        resteDuCode.traitement();
    }
}

class NotificationMail implements Notification {

    @Override
    public void notifierClient(Client client) {
        //TODO envoyer un mail
    }
}

class NotificationSms implements Notification {

    @Override
    public void notifierClient(Client client) {
        //TODO envoyer un sms
    }
}

class NotificationCourier implements Notification {

    @Override
    public void notifierClient(Client client) {
        //TODO utiliser les service de la poste pour envoyer des courier
    }
}