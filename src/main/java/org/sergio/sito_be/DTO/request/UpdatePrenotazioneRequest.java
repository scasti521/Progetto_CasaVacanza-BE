package org.sergio.sito_be.DTO.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdatePrenotazioneRequest extends CreaPrenotazioneRequest {
    @Min(value = 1, message = "L'id non può essere minore o uguale a 0")
    private long id;

}
