package com.bidemy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public enum BusinessValidationRule implements IBusinessValidationRule {
    COURSE_NOT_FOUND("1000", "Course bulunamadı"),
    COURSE_TITLE_REQUIRED("1001", "Course title boş olamaz"),
    FILE_IS_EMPTY("1002", "Dosya boş."),
    CATEGORY_NOT_FOUND("2000", "Category bulunamadı"),
    USER_NOT_FOUND("3000", "User bulunamadı"),
    USER_EMAIL_REQUIRED("3001", "User email boş olamaz"),
    VIDEO_NOT_FOUND("4000", "Video bulunamadı"),
    VIDEO_TITLE_REQUIRED("4001", "Video başlığı boş olamaz"),
    VIDEO_UPLOAD_FAILED("4002","Video yüklenirken hata oluştu"),
    IMAGE_UPLOAD_FAILED("4003","Resim yüklenemedi: %s"),
    IMAGE_UPDATE_FAILED("4004","Resim güncellenemedi: %s"),
    INVALID_FILE_TYPE("1004", "Formata uygun değil"),
    FILE_TOO_LARGE("1005", "File dosyasının büyüklüğü fazla."),
    SECTION_NOT_FOUND("5000", "Section bulunamadı"),
    LESSON_NOT_FOUND("6000", "Lesson bulunamadı"),
    CURRICULUM_NOT_FOUND("7000", "Curriculum Item bulunamadı"),
    INVALID_CURRICULUM_ITEM_TYPE("7001", "Curriculum türü bulunamadı"),
    EXAM_NOT_FOUND("8000", "Exam bulunamadı"),
    HOMEWORK_NOT_FOUND("9000", "Homework bulunamadı"),
    CODING_ASSIGNMENT_NOT_FOUND("9001", "Coding Assignment bulunamadı"),
    CONTENT_NOT_FOUND("9002", "Content bulunamadı"),
    INVALID_CONTENT_TYPE("9003", "Content türü bulunamadı"),
    INVALID_CREDENTIALS("9004","E-posta adresi veya şifre hatalı" );


    private static final String DEFAULT_CODE = "SYSTEM";
    private String code = DEFAULT_CODE;
    private final String message;
    private HttpStatus httpStatus = BAD_REQUEST; // use only 4XX and 5XX codes

    BusinessValidationRule(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
