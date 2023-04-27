package uj.jwzp.ticketmaster.exceptions;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotExistsException.class)
	public ProblemDetail handleEntityNotExistsException(EntityNotExistsException ex, WebRequest request) {
		return createProblemFromException(ex, HttpStatus.NOT_FOUND, "errors/not-found");
	}

	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ProblemDetail handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
		return createProblemFromException(ex, HttpStatus.BAD_REQUEST, "errors/already-exists");
	}

	private ProblemDetail createProblemFromException(Exception ex, HttpStatus status, String problemType) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, ex.getLocalizedMessage());
		problem.setType(URI.create(problemType));
		problem.setDetail(ex.getLocalizedMessage());
		return problem;
	}

}
