package ru.sfu.querang.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sfu.querang.models.ShopUser;
import ru.sfu.querang.services.ShopUserService;

/**
 * Валидатор для проверки уникальности имени пользователя
 */
@Component
public class UserValidator implements Validator {

    private final ShopUserService shopUserService;

    /**
     * Конструктор для UserValidator.
     *
     * @param shopUserService сервис пользователей
     */
    @Autowired
    public UserValidator(ShopUserService shopUserService) {
        this.shopUserService = shopUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ShopUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ShopUser shopUser = (ShopUser) target;
        if (shopUserService.hasUsername(shopUser.getUsername())) {
            errors.rejectValue("username", "", "This username was already taken!");
        }

    }
}
