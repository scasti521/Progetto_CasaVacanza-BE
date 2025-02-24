package org.sergio.sito_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sergio.sito_be.DTO.request.CreaCasaVacanzaRequest;
import org.sergio.sito_be.DTO.request.UpdateCasaVancanzaRequest;
import org.sergio.sito_be.entities.CasaVacanza;
import org.sergio.sito_be.repository.CasaVacanzaRepo;
import org.sergio.sito_be.service.def.CasaVacanzaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CasaVacanzaServiceJPA implements CasaVacanzaService {

    private final CasaVacanzaRepo casaVacanzaRepo;

    @Override
    public CasaVacanza creaCasaVacanza(CreaCasaVacanzaRequest request) {
        CasaVacanza c = new CasaVacanza();
        c.setNome(request.getNome());
        c.setIndirizzo(request.getIndirizzo());
        c.setCitta(request.getCitta());
        c.setRegione(request.getRegione());
        c.setDescrizione(request.getDescrizione());
        c.setPrezzo(request.getPrezzo());
        c.setPostiLetto(request.getPostiLetto());
        c.setImmagine(request.getImmagine());
        casaVacanzaRepo.save(c);
        return c;
    }

    @Override
    public CasaVacanza updateCasaVacanza(UpdateCasaVancanzaRequest request) {
        Optional<CasaVacanza> casaVacanza = casaVacanzaRepo.findById(request.getId());
        if (casaVacanza.isEmpty()) {
            return null;
        }
        CasaVacanza c = casaVacanza.get();
        c.setNome(request.getNome());
        c.setIndirizzo(request.getIndirizzo());
        c.setCitta(request.getCitta());
        c.setRegione(request.getRegione());
        c.setDescrizione(request.getDescrizione());
        c.setPrezzo(request.getPrezzo());
        c.setPostiLetto(request.getPostiLetto());
        c.setImmagine(request.getImmagine());
        return casaVacanzaRepo.save(c);
    }

    @Override
    public void deleteCasaVacanza(long id) {
        Optional<CasaVacanza> casaVacanza = casaVacanzaRepo.findById(id);
        if(casaVacanza.isPresent()){
            casaVacanzaRepo.delete(casaVacanza.get());
        }
    }

    @Override
    public List<CasaVacanza> findAll() {
        return casaVacanzaRepo.findAll().stream().sorted().toList();
    }


}
