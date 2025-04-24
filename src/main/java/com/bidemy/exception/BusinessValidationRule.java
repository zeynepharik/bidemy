package com.bidemy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public enum BusinessValidationRule implements IBusinessValidationRule{
    COURSE_NOT_FOUND("1000","Course bulunamadı"),
    COURSE_TİTLE_REQUİRED("1001","Course title boş olamaz"),
    FILE_IS_EMPTY("1002","Dosya boş." ),
    CATEGORY_NOT_FOUND("2000","Category bulunamadı"),
    USER_NOT_FOUND("3000","User bulunamadı"),
    USER_EMAİL_REQUİRED("3001","User email boş olamaz"),
    VİDEO_NOT_FOUND("4000","Video bulunamadı"),
    VİDEO_TİTLE_REQUİRED("4001","Video başlığı boş olamaz"),
    INVALID_FILE_TYPE("1004","Formata uygun değil" ),
    FILE_TOO_LARGE("1005","File dosyasının büyüklüğü fazla." );


    private static final String DEFAULT_CODE = "SYSTEM";
    private String code = DEFAULT_CODE;
    private final String message;
    private HttpStatus httpStatus = BAD_REQUEST; // use only 4XX and 5XX codes

    BusinessValidationRule(String code, String message) {
        this.code = code;
        this.message = message;
    }

    BusinessValidationRule(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
