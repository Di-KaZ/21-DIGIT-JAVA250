package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.BaseXLSXService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientExportXLSXService extends BaseXLSXService {

    @Autowired
    private ClientRepository clientRepository;

    // Constante pour index de celles
    private static final int NOM = 0;
    private static final int PRENOM = 1;
    private static final int AGE = 2;

    // Constante pour index de celles
    private static final int FACTURE_LIBELE_NOM = 0;
    private static final int FACTURE_LIBELE_PRENOM = 0;
    private static final int FACTURE_LIBELE_NAISSANCE = 0;
    private static final int FACTURES_LIBELE = 0;
    private static final int FACTURES_DESIGNATION = 0;
    private static final int FACTURES_QUANTITE = 1;
    private static final int FACTURES_PRIX_UNITAIRE = 2;
    private static final int FACTURES_TOTAL = 2;
    private static final int FACTURES_LIBELE_TOTAL = 1;

    private String getAge(Client client) {
        return Integer.toString(Period.between(client.getDateNaissance(), LocalDate.now()).getYears());
    }

    private void createFactureSheet(Workbook wb, Facture facture) {
        Sheet sheet = wb.createSheet(String.format("Facture n°%d", facture.getId()));
        Row actualRow = null;
        int actualRowCount = 0;
        Double total = 0d;

        actualRow = sheet.createRow(actualRowCount++);
        setActiveStyle("BOLD");
        createCell(actualRow, FACTURES_DESIGNATION, "Désignation");
        createCell(actualRow, FACTURES_QUANTITE, "Quantité");
        createCell(actualRow, FACTURES_PRIX_UNITAIRE, "Prix Unitaire");
        setActiveStyle("NORMAL");

        Set<LigneFacture> lignesfacture = facture.getLigneFactures();
        for (LigneFacture ligne : lignesfacture) {
            actualRow = sheet.createRow(actualRowCount++);
            createCell(actualRow, FACTURES_DESIGNATION, ligne.getArticle().getLibelle());
            createCell(actualRow, FACTURES_QUANTITE, Integer.toString(ligne.getQuantite()));
            createCell(actualRow, FACTURES_PRIX_UNITAIRE, Double.toString(ligne.getArticle().getPrix()));
            // on met a jour le total de la facture
            total += ligne.getQuantite() * ligne.getArticle().getPrix();
        }

        actualRow = sheet.createRow(actualRowCount);
        setActiveStyle("BOLD");
        createCell(actualRow, FACTURES_LIBELE_TOTAL, "Total");
        setActiveStyle("NORMAL");
        createCell(actualRow, FACTURES_TOTAL, Double.toString(total));
        autoResizeSheet(sheet);
    }

    public void export(OutputStream outputStream, Long id) {

        Optional<Client> client = clientRepository.findById(id);
        int actualRowCount = 0;
        Row actualRow = null;

        if (client.isEmpty()) {
            throw new IllegalAccessError("an error occured");
        }

        try {
            // Apache POI
            try (Workbook wb = new HSSFWorkbook()) {
                // initialisation des styles
                initStyle(wb, NONE, BLACK, "BOLD");
                initFont(wb, BLACK, BOLD, "BOLD");
                initStyle(wb, NONE, BLACK, "NORMAL");
                initFont(wb, BLACK, NORMAL, "NORMAL");

                // création d'une feuille de calcul
                Sheet sheet = wb.createSheet(client.get().getNom() + " " + client.get().getPrenom());

                // infos client
                setActiveStyle("NORMAL");
                actualRow = sheet.createRow(actualRowCount++);
                createCell(actualRow, FACTURE_LIBELE_NOM, "Nom : ");
                createCell(actualRow, FACTURE_LIBELE_NOM + 1, client.get().getNom());

                actualRow = sheet.createRow(actualRowCount++);
                createCell(actualRow, FACTURE_LIBELE_PRENOM, "Prénom :");
                createCell(actualRow, FACTURE_LIBELE_PRENOM + 1, client.get().getPrenom());

                actualRow = sheet.createRow(actualRowCount++);
                createCell(actualRow, FACTURE_LIBELE_NAISSANCE, "Année de naissance:");
                createCell(actualRow, FACTURE_LIBELE_PRENOM + 1,
                        Integer.toString(client.get().getDateNaissance().getYear()));

                actualRow = sheet.createRow(actualRowCount++);
                Set<Facture> factures = client.get().getFactures(); // recuperation des
                // facture du client
                setActiveStyle("BOLD");
                createCell(actualRow, FACTURES_LIBELE, String.format("%d facture(s) :", factures.size()));
                setActiveStyle("NORMAL");
                // affichage id de factrures
                int count = FACTURES_LIBELE + 1; // conteur pour print les facture dans
                // des celle indépendantes
                for (Facture facture : factures) {
                    createCell(actualRow, count++, facture.getId().toString());
                    createFactureSheet(wb, facture); // creation d'une nouvelle sheet pour cette facture
                }
                autoResizeSheet(sheet);
                clearAllStyles();
                wb.write(outputStream);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void export(OutputStream outputSteam) {

        List<Client> clients = clientRepository.findAll();
        int actualRowCount = 0;
        Row actualRow = null;

        try {
            // Apache POI
            try (Workbook wb = new HSSFWorkbook()) {
                // initialisation des styles
                initStyle(wb, THICK /* 👁️👄👁️ */, BLUE, "HEADER");
                initFont(wb, PINK, NORMAL, "HEADER");
                initStyle(wb, THICK, BLUE, "BODY");
                initFont(wb, BLACK, NORMAL, "BODY");
                // création d'une feuille de calcul
                Sheet sheet = wb.createSheet("Articles");
                // Première row
                setActiveStyle("HEADER");
                actualRow = sheet.createRow(actualRowCount++);
                createCell(actualRow, NOM, "Nom");
                createCell(actualRow, PRENOM, "Prénom");
                createCell(actualRow, AGE, "Age");
                // on boucle sur les articles et creer une row pour chaqun d'entre eux + 3 cell
                // pour mettre le valeurs dedans
                setActiveStyle("BODY");
                for (Client client : clients) {
                    actualRow.createCell(0).setCellValue(client.getDateNaissance().toString());
                    actualRow = sheet.createRow(actualRowCount++);
                    createCell(actualRow, NOM, client.getNom());
                    createCell(actualRow, PRENOM, client.getPrenom());
                    createCell(actualRow, AGE, getAge(client));
                }
                // Joli formatting 🤩
                autoResizeSheet(sheet);
                wb.write(outputSteam);
            }
            outputSteam.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
