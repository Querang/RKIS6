package ru.sfu.querang.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sfu.querang.models.ShopUser;
import ru.sfu.querang.repositories.ShopUsersRepository;
import ru.sfu.querang.security.ShopUserDetails;

/**
 * Сервис загрузки пользовательских данных из репозитория пользователей
 */
@Service
public class ShopUserDetailsService implements UserDetailsService {

    private final ShopUsersRepository shopUsersRepository;

    /**
     * Конструктор ShopUserDetailsService
     *
     * @param shopUsersRepository Репозиторий пользователей
     */
    @Autowired
    public ShopUserDetailsService(ShopUsersRepository shopUsersRepository) {
        this.shopUsersRepository = shopUsersRepository;
    }

    /**
     * Получает пользователя по имени
     *
     * @param username имя пользователя
     * @return ShopUserDetails для пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ShopUser> user = shopUsersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new ShopUserDetails(user.get());
    }
}
