package ru.sfu.querang.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfu.querang.models.ShopUser;
import ru.sfu.querang.repositories.ShopUsersRepository;

/**
 * Сервис для работы с пользователями
 */
@Service
@Transactional(readOnly = true)
public class ShopUserService {

    private final ShopUsersRepository shopUsersRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для ShopUserService.
     *
     * @param shopUsersRepository Репозиторий пользователей
     * @param passwordEncoder    Кодировщик паролей
     */
    @Autowired
    public ShopUserService(ShopUsersRepository shopUsersRepository, PasswordEncoder passwordEncoder) {
        this.shopUsersRepository = shopUsersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Проверяет наличие пользователя с определенным именем
     *
     * @param username Имя пользователя
     * @return статус наличия имя в бд
     */
    public boolean hasUsername(String username) {
        return shopUsersRepository.findByUsername(username).isPresent();
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param shopUser модель пользователя
     */
    @Transactional
    public void register(ShopUser shopUser) {
        shopUser.setPassword(passwordEncoder.encode(shopUser.getPassword()));
        if (shopUser.getUsername().equals("admin")) {
            shopUser.setRole("ROLE_ADMIN");
        } else {
            shopUser.setRole("ROLE_USER");
        }
        shopUsersRepository.save(shopUser);
    }
}
