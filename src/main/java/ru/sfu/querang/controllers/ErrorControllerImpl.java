package ru.sfu.querang.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер обработки ошибок
 */
@Controller
public class ErrorControllerImpl implements ErrorController {

    /**
     * Обработчик запроса для обработки ошибок.
     *
     * @param request Запрос HTTP.
     * @param model   Модель для передачи данных в представление.
     * @return Имя представления для обработки ошибок.
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == 403) {
                return "error403";
            }
            model.addAttribute("status", Integer.toString(statusCode));
        }

        return "error";
    }

}

