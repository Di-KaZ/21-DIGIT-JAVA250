package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.BaseXLSXService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleExportXLSXService extends BaseXLSXService {

    @Autowired
    private ArticleRepository articleRepository;

    // Constante pour index de celles
    private static final int LIBLELE = 0;
    private static final int PRIX = 1;
    private static final int STOCK = 2;

    public void export(OutputStream outputSteam) {

        List<Article> articles = articleRepository.findAll();
        int actualRowCount = 0;
        Row actualRow = null;

        try {
            // Apache POI
            try (Workbook wb = new HSSFWorkbook()) {
                // création d'une feuille de calcul
                Sheet sheet = wb.createSheet("Articles");
                // Première row
                actualRow = sheet.createRow(actualRowCount++);
                createCell(actualRow, LIBLELE, "Libelé");
                createCell(actualRow, PRIX, "Prix");
                createCell(actualRow, STOCK, "Description");

                // on boucle sur les articles et creer une row pour chaqun d'entre eux + 3 cell
                // pour mettre le valeurs dedans
                for (Article article : articles) {
                    actualRow = sheet.createRow(actualRowCount++);
                    createCell(actualRow, LIBLELE, article.getLibelle());
                    createCell(actualRow, PRIX, Double.toString(article.getPrix()));
                    createCell(actualRow, STOCK, article.getDescription());
                }
                autoResizeSheet(sheet);
                clearAllStyles();
                wb.write(outputSteam);
            }
            outputSteam.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
