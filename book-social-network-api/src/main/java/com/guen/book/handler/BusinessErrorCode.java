package com.guen.book.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCode
{
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "Aucun code"),
    INCORRECT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Le mot de passe est incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "Les mots de passe ne correspondent pas"),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "Le compte de cet utilisateur est vérouillé"),
    ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "Le compte de cet utilisateur est désactivé"),
    BAD_CREDENTIALS(304, HttpStatus.FORBIDDEN, "Identifiant ou mot de passe incorrect"),
    ;

    @Getter
    private final int code;

    @Getter
    private final String description;

    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCode(int code, HttpStatus httpStatus, String description)
    {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
