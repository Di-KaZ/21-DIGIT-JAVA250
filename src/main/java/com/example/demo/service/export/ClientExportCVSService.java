package com.example.demo.service.export;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientExportCVSService {

    @Autowired
    private ClientRepository clientRepository;

    public void export(PrintWriter writer) {
        List<Client> clients = clientRepository.findAll();
        writer.println("\"Nom\";\"Prénom\";\"Age\"");
        for (Client client : clients) {
            writer.println(String.format("\"%s\";\"%s\";\"%s\"", client.getNom(), client.getPrenom(),
                    Period.between(client.getDateNaissance(), LocalDate.now()).getYears()));
        }
    }
}
