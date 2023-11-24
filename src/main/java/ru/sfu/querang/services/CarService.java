package ru.sfu.querang.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfu.querang.models.Car;
import ru.sfu.querang.repositories.CarRepository;

/**
 * Сервис для работы с автомобилями
 */
@Service
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;

    /**
     * Конструктор сервиса
     *
     * @param carRepository репозиторий автомобилей
     */
    @Autowired
    public CarService(
            CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Получает все автомобили
     *
     * @return список всех автомобилей
     */
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    /**
     * Находит автомобиль по id
     *
     * @param id автомобиля
     * @return автомобиль
     */
    public Car findOne(int id) {
        return carRepository.findById(id).orElse(null);
    }

    /**
     * Сохраняет новый автомобиль
     *
     * @param car объект автомобиля
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void save(Car car) {
        carRepository.save(car);
    }

    /**
     * Обновляет информацию об автомобиле
     *
     * @param id автомобиля
     * @param car  объект автомобиля
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void update(int id, Car car) {
        car.setId(id);
        carRepository.save(car);
    }

    /**
     * Удаляет автомобиль по id
     *
     * @param id автомобиля
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void delete(int id) {
        carRepository.deleteById(id);
    }

    /**
     * Фильтрует автомобили по цене не выше максимальной
     *
     * @param maxPrice максимальная цена
     * @return список автомобилей с ценой ниже максимальной
     */
    public List<Car> filterByPrice(float maxPrice) {
        return carRepository.findByPriceLessThanEqual(maxPrice);
    }


    /**
     * Заполняет бд готовыми данными
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void fillExample() {
        carRepository.insertExample();
    }

    /**
     * Удаляет все автомобили
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void wipe() {
        carRepository.deleteAll();
    }
}
