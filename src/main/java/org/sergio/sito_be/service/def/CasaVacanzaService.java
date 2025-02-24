package org.sergio.sito_be.service.def;

import org.sergio.sito_be.DTO.request.CreaCasaVacanzaRequest;
import org.sergio.sito_be.DTO.request.UpdateCasaVancanzaRequest;
import org.sergio.sito_be.entities.CasaVacanza;

import java.util.List;

public interface CasaVacanzaService {

    public CasaVacanza creaCasaVacanza(CreaCasaVacanzaRequest request);
    public CasaVacanza updateCasaVacanza(UpdateCasaVancanzaRequest request);
    public void deleteCasaVacanza(long id);
    public List<CasaVacanza> findAll();

}
