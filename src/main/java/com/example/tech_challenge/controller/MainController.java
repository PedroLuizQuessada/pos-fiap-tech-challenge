package com.example.tech_challenge.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String handleError(HttpServletRequest request, Model model) {
        String errorMessage = "";
        StringBuilder stackTrace = new StringBuilder();
        if ((Integer) request.getAttribute("jakarta.servlet.error.status_code") == 404) {
            errorMessage = "Página não encontrada";
        }
        else if ((Integer) request.getAttribute("jakarta.servlet.error.status_code") == 403) {
            errorMessage = "Página proibida";
        }
        else if ((Integer) request.getAttribute("jakarta.servlet.error.status_code") == 401) {
            errorMessage = "Página nao autorizada";
        }
        else if ((Integer) request.getAttribute("jakarta.servlet.error.status_code") == 400) {
            errorMessage = "Requisiçao incorreta";
            MethodArgumentNotValidException obj = ((MethodArgumentNotValidException) request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR"));
            if (obj != null) {
                for (Object e : obj.getDetailMessageArguments()) {
                    stackTrace.append(e.toString()).append("\n");
                }
            }
        }
        else {
            Exception obj = (Exception) request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
            if (obj != null) {
                errorMessage = obj.getMessage();
                for (StackTraceElement e : obj.getStackTrace()) {
                    stackTrace.append(e.getClassName()).append(".").append(e.getMethodName()).append("(").append(e.getFileName()).append(":").append(e.getLineNumber()).append(")\n");
                }
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorStacktrace", stackTrace);
        return "error";
    }
}
