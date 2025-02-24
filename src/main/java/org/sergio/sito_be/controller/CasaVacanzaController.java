package org.sergio.sito_be.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.sergio.sito_be.DTO.request.CreaCasaVacanzaRequest;
import org.sergio.sito_be.DTO.request.UpdateCasaVancanzaRequest;
import org.sergio.sito_be.DTO.response.CasaVacanzaDTO;
import org.sergio.sito_be.entities.CasaVacanza;
import org.sergio.sito_be.repository.CasaVacanzaRepo;
import org.sergio.sito_be.service.def.CasaVacanzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/casaVacanza")
@AllArgsConstructor
public class CasaVacanzaController {

    private CasaVacanzaService casaVacanzaService;

    @PostMapping("/crea")
    public ResponseEntity<CasaVacanzaDTO> creaCasaVacanza(@Valid @RequestBody CreaCasaVacanzaRequest request) {
        try {
            System.out.println("Dati casa ricevuti dalla richiesta: " + request.getNome() + " " + request.getIndirizzo() + " " + request.getCitta() + " " + request.getRegione() + " " + request.getDescrizione() + " " + request.getPrezzo() + " " + request.getPostiLetto() );
            casaVacanzaService.creaCasaVacanza(request);
            return  ResponseEntity.status(HttpStatus.OK).header("Casa vacanza creata", "token").build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CasaVacanzaDTO> updateCasaVacanza(@Valid @RequestBody UpdateCasaVancanzaRequest request) {
        try {
            System.out.println("Dati casa ricevuti dalla richiesta: " + request.getId() + " " + request.getNome() + " " + request.getCitta() + " " + request.getRegione() + " " + request.getDescrizione() + " " + request.getPrezzo() + " " + request.getPostiLetto()+" "+request.getImmagine());
            casaVacanzaService.updateCasaVacanza(request);
            return  ResponseEntity.status(HttpStatus.OK).header("Casa vacanza aggiornata", "token").build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            casaVacanzaService.deleteCasaVacanza(id);
            return  ResponseEntity.status(HttpStatus.OK).header("Casa vacanza eliminata", "token").build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CasaVacanzaDTO>> findAll(){
        List<CasaVacanza> caseVacanza = casaVacanzaService.findAll();
        List<CasaVacanzaDTO> caseVacanzaDTO = caseVacanza.stream()
                .map(casa -> new CasaVacanzaDTO((int)casa.getId(), casa.getNome(), casa.getIndirizzo(), casa.getCitta(), casa.getRegione(), casa.getDescrizione(), casa.getPostiLetto(), casa.getPrezzo(), casa.isDisponibile(), casa.getImmagine()))
                .sorted(Comparator.comparing(casa -> casa.getNome().toLowerCase())) //inserisco ordinamento confrontando il nome non contanto minuscole e maiuscole
                .toList();
        return ResponseEntity.ok(caseVacanzaDTO);
    }


}
