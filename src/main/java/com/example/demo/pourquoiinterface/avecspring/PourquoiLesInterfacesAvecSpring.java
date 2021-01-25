package com.example.demo.pourquoiinterface.avecspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

interface Notification {

    void notifierClient(Client client);
}

class Client {

}

@Component
class ResteDuCode {

    @Autowired
    InscriptionService inscriptionService;

    void traitement() {
        //blabla
        inscriptionService.inscrireClient(new Client());
    }
}

@Component
class InscriptionService {

    @Autowired
    @Qualifier("sms")
    Notification notification;

    public void inscrireClient(Client client) {
        //faire les controles et enregistrer le client
        //blabla

        notification.notifierClient(client);
    }
}

public class PourquoiLesInterfacesAvecSpring {


    public static void main(String[] args) {
        //CONFIG
        // NotificationMail notification = new NotificationMail();
        // InscriptionService inscriptionService = new InscriptionService(notification);
        // RestDuCode resteDuCode = new RestDuCode(inscriptionService);

        //Init de spring
        ApplicationContext spring = null /*...*/;
        ResteDuCode resteDuCode = spring.getBean(ResteDuCode.class);

        //simulation d'une inscription
        resteDuCode.traitement();
    }
}

@Component
@Qualifier("mail")
class NotificationMail implements Notification {

    @Override
    public void notifierClient(Client client) {
        //TODO envoyer un mail
    }
}

@Component
@Qualifier("sms")
class NotificationSms implements Notification {

    @Override
    public void notifierClient(Client client) {
        //TODO envoyer un sms
    }
}

@Component
class NotificationCourier implements Notification {

    @Override
    public void notifierClient(Client client) {
        //TODO utiliser les service de la poste pour envoyer des courier
    }
}


@Configuration
class Config {

    @Bean
    Notification notificationAUtiliser() {
        String param_variable_denv = "";
        if (!param_variable_denv.equals("mail")) {
            return new NotificationSms();
        } else {
            return new NotificationMail();
        }
    }
}