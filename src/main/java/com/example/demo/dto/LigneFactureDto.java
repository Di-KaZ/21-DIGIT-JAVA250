package com.example.demo.dto;

public class LigneFactureDto {
    private ArticleDto article;
    private int quantite;

    public LigneFactureDto(ArticleDto article, int quantite) {
        this.article = article;
        this.quantite = quantite;
    }

    public ArticleDto getArticle() {
        return article;
    }

    public void setArticle(ArticleDto article) {
        this.article = article;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}