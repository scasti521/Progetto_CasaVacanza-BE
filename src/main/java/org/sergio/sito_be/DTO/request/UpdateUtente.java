package org.sergio.sito_be.DTO.request;

import jakarta.validation.constraints.Min;

public class UpdateUtente extends CreaUtenteRequest{
    @Min(value = 1, message = "L'id non può essere minore o uguale a 0")
    private long id;
}
