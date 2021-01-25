package com.example.demo.service.mapper;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.entity.Article;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class FactureMapper {

    @Autowired
    private ClientMapper clientMapper;

    public FactureDto factureDto(Facture f) {
        ClientDto clientDto = clientMapper.clientDto(f.getClient());
        List<LigneFactureDto> ligneFactureDtos = f.getLigneFactures()
                .stream()
                .map(lf -> ligneFactureDto(lf))
                .collect(toList());
        return new FactureDto(f.getId(), clientDto, ligneFactureDtos);
    }

    private LigneFactureDto ligneFactureDto(LigneFacture lf) {
        Article article = lf.getArticle();
        ArticleDto articleDto = new ArticleDto(article.getId(), article.getLibelle(), article.getPrix(), article.getStock());
        return new LigneFactureDto(articleDto, lf.getQuantite());
    }


}
