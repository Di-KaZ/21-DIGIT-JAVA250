package com.example.demo.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe permettant d'insérer des données dans l'application.
 */
@Service
@Transactional
public class InitData implements ApplicationListener<ApplicationReadyEvent> {

    private EntityManager entityManager;

    public InitData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        insertTestData();
    }

    private void insertTestData() {
        Article a1 = createArticle("Chargeurs de téléphones Portables", 22.98, 9, "Test;hmm");
        Article a2 = createArticle("Playmobil Hydravion de Police", 14.39, 2, "Test;hmm");
        Article a3 = createArticle("Distributeur de croquettes pour chien", 12.99, 0, "Test;hmm");

        Client cl1 = createClient("John", "Doe;Ci", LocalDate.of(1999, Month.JANUARY, 17));

        Map<Integer, Article> facture1 = new HashMap<>();
        facture1.put(2, a2);
        facture1.put(5, a3);
        Facture fac1 = createFacture(cl1, facture1);
    }

    private Facture createFacture(Client client, Map<Integer, Article> articles) {
        Facture facture = new Facture();
        facture.setClient(client);
        LigneFacture ligne = null;
        Set<LigneFacture> lignes = new HashSet<>();

        for (Map.Entry m : articles.entrySet()) {
            ligne = new LigneFacture();
            ligne.setArticle((Article) m.getValue());
            ligne.setQuantite((Integer) m.getKey());
            ligne.setFacture(facture);
            entityManager.persist(ligne);
            lignes.add(ligne);
        }
        facture.setLigneFactures(lignes);
        entityManager.persist(facture);
        return facture;
    }

    private Client createClient(String prenom, String nom, LocalDate dateNaissance) {
        Client client = new Client();
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setDateNaissance(dateNaissance);
        entityManager.persist(client);
        return client;
    }

    private Article createArticle(String libelle, double prix, int stock, String description) {
        Article a1 = new Article();
        a1.setLibelle(libelle);
        a1.setPrix(prix);
        a1.setStock(stock);
        a1.setDescription(description);
        entityManager.persist(a1);
        return a1;
    }

}
