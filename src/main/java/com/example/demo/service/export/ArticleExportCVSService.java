package com.example.demo.service.export;

import java.io.PrintWriter;
import java.util.List;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleExportCVSService {

    @Autowired
    private ArticleRepository articleRepository;

    public void export(PrintWriter writer) {
        List<Article> articles = articleRepository.findAll();
        writer.println("\"Libelé\";\"Prix\";\"Description\"");
        for (Article article : articles) {
            writer.println(String.format("\"%s\";\"%s\";\"%s\"", article.getLibelle(), article.getPrix(),
                    article.getDescription()));
        }
    }
}
