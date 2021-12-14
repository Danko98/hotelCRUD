package uz.pdp.hotelcrud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hotelcrud.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsByName(String name);

}
