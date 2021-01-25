package com.example.demo.service.impl;

import com.example.demo.dto.FactureDto;
import com.example.demo.entity.Facture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.FactureService;
import com.example.demo.service.mapper.FactureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Service contenant les actions métiers liées aux factures.
 */
@Service
@Transactional
public class FactureServiceImpl implements FactureService {
    private FactureRepository factureRepository;
    private FactureMapper factureMapper;
    private ClientRepository clientRepository;

    @Autowired
    public FactureServiceImpl(
            FactureRepository factureRepository,
            FactureMapper factureMapper,
            ClientRepository clientRepository) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<FactureDto> findAllFactures() {
        List<Facture> factures = factureRepository.findAll();
        // Transformation d'une liste de Facture en liste de FactureDto
        return factures.stream().map(facture -> factureMapper.factureDto(facture)).collect(toList());
    }

    @Override
    public FactureDto findById(Long id) {
        Facture facture = factureRepository.findById(id).get();
        // Transformation d'une Facture en FactureDto
        return factureMapper.factureDto(facture);
    }

}