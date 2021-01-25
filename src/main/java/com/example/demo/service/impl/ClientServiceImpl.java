package com.example.demo.service.impl;

import com.example.demo.dto.ClientDto;
import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service contenant les actions métiers liées aux articles.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientDto> findAll() {
        List<Client> clients = clientRepository.findAll();

        List<ClientDto> dtos = new ArrayList<>();
        for (Client client : clients) {
            ClientDto clientDto = new ClientDto(client.getId(), client.getNom(), client.getPrenom());
            dtos.add(clientDto);
        }
        return dtos;
    }

}
