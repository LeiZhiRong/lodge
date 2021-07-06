package com.shgs.lodge.exception.handler;

import com.shgs.lodge.exception.exception.JsonException;
import com.shgs.lodge.exception.exception.PageException;
import com.shgs.lodge.exception.model.ApiResponse;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class CmsExceptionHandler {
	private static final String DEFAULT_ERROR_VIEW = "error";
	private static final Logger logger = LoggerFactory.getLogger(CmsExceptionHandler.class);

	/**
	 * 统一 json 异常处理
	 *
	 * @param exception JsonException
	 * @return 统一返回 json 格式
	 */
	@ExceptionHandler(value = JsonException.class)
	@ResponseBody
	public ApiResponse jsonErrorHandler(JsonException exception) {
		logger.error("【JsonException】:{}", exception.getMessage());
		return ApiResponse.ofException(exception);
	}

	/**
	 * 统一 页面 异常处理
	 *
	 * @param exception PageException
	 * @return 统一跳转到异常页面
	 */
	@ExceptionHandler(value = PageException.class)
	public ModelAndView pageErrorHandler(PageException exception) {
		logger.error("【PageException】:{}", exception.getMessage());
		ModelAndView view = new ModelAndView();
		view.addObject("message", exception.getMessage());
		view.setViewName(DEFAULT_ERROR_VIEW);
		return view;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ApiResponse ConstraintViolationExceptionHandler(ConstraintViolationException e) {
		String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
		return ApiResponse.of(500,message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ApiResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
		return ApiResponse.of(500,message);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ApiResponse exceptionHandle(Exception e){
		String message = e.getMessage();
		return ApiResponse.of(500,message);
	}


}
