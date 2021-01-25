package com.example.demo.service;

import com.example.demo.dto.FactureDto;

import java.util.List;

public interface FactureService {
    List<FactureDto> findAllFactures();

    FactureDto findById(Long id);
}