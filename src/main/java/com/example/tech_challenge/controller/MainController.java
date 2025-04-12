package com.example.tech_challenge.controller;

import com.example.tech_challenge.exception.UnauthorizedActionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class MainController implements ErrorController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        String errorMessage = "";
        StringBuilder stackTrace = new StringBuilder();

        switch ((Integer) request.getAttribute("jakarta.servlet.error.status_code")) {
            case 404:
                errorMessage = "Página não encontrada";
                break;

            case 403:
                errorMessage = "Página proibida";
                break;

            case 401:
                errorMessage = "Página nao autorizada";
                break;

            case 400:
                errorMessage = "Requisiçao incorreta";
                MethodArgumentNotValidException methodArgumentNotValidException = ((MethodArgumentNotValidException) request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR"));
                if (methodArgumentNotValidException != null) {
                    for (Object e : methodArgumentNotValidException.getDetailMessageArguments()) {
                        stackTrace.append(e.toString()).append("\n");
                    }
                }
                break;

            default:
                Exception exception = (Exception) request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
                if (exception != null) {
                    errorMessage = exception.getMessage();
                    for (StackTraceElement e : exception.getStackTrace()) {
                        stackTrace.append(e.getClassName()).append(".").append(e.getMethodName()).append("(").append(e.getFileName()).append(":").append(e.getLineNumber()).append(")\n");
                    }

                    if (exception instanceof UnauthorizedActionException) {
                        response.setStatus(403);
                    }
                }
                break;
        }

        log.warn(errorMessage);
        log.error(stackTrace.toString());
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorStacktrace", stackTrace);
        return "error";
    }
}
