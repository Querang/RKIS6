package ru.sfu.querang.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.querang.models.ShopUser;

/**
 * Репозиторий для работы с пользователями
 */
@Repository
public interface ShopUsersRepository extends JpaRepository<ShopUser, Integer> {

    /**
     * Поиск пользователя по имени пользователя
     *
     * @param username имя пользователя
     * @return Optional с пользователем
     */
    Optional<ShopUser> findByUsername(String username);
}
