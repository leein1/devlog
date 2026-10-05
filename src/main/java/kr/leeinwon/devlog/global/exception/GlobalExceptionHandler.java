package kr.leeinwon.devlog.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    // 응답 작성
    private ResponseEntity<CustomErrorResponse> respond(HttpStatusCode statusCode, String message) {

        return ResponseEntity.status(statusCode)
                 .body(new CustomErrorResponse(statusCode.value(), message));
    }

    // Spring 예외면 그 상태 그대로, 아니면 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception e){

        if(e instanceof org.springframework.web.ErrorResponse errorResponse){
            HttpStatusCode statusCode = errorResponse.getStatusCode();
            log.warn("처리되지 않은 Spring 예외({}): {}", statusCode.value(), e.getMessage());
            return respond(statusCode, "요청을 처리할 수 없습니다");
        }

        log.error("처리되지 않은 예외", e);
        /*
        catchAll()에서 자동처리 되지 않는 오류라 server_error에 걸리면 새로운 핸들러 필요
         */
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다");
    }

    // 내가 직접 던지는 예외 -> 문구를 내가 작성했으니까 그대로 응답
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        return respond(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFound(NotFoundException e) {
        return respond(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CustomErrorResponse> handleConflict(ConflictException e){
        return respond(HttpStatus.CONFLICT, e.getMessage());
    }

    // 요청 형식 오류. Spring이 던짐. 상태랑 문구'만' 내가 직접 정한거
    /*
    MethodArgumentNotValidException
    MethodArgumentTypeMismatchException
    HttpMessageNotReadableException
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return respond(HttpStatus.BAD_REQUEST,message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return respond(HttpStatus.BAD_REQUEST, "값의 형식이 올바르지 않습니다");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse>
    handleMessageNotReadable(HttpMessageNotReadableException e) {
        return respond(HttpStatus.BAD_REQUEST, "요청 본문을 읽을 수 없습니다");
    }

    /*
    db 제약 위반: 원인따라 409 500
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {

        ConstraintViolationException.ConstraintKind kind = (e.getCause() instanceof ConstraintViolationException cve)
                ? cve.getKind()
                : null;

        if (kind == ConstraintViolationException.ConstraintKind.UNIQUE) {
            log.warn("유니크 제약 위반: {}",
                    e.getMostSpecificCause().getMessage());
            return respond(HttpStatus.CONFLICT, "이미 존재하는 값입니다");
        }
        if (kind == ConstraintViolationException.ConstraintKind.FOREIGN_KEY) {
            log.warn("외래 키 제약 위반: {}",
                    e.getMostSpecificCause().getMessage());
            return respond(HttpStatus.CONFLICT, "다른 데이터가 참조하고 있어 처리할 수 없습니다");
        }

        log.error("예상하지 못한 데이터 무결성 위반", e);
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다");
    }

}
