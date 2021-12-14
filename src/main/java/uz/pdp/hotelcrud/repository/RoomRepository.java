package uz.pdp.hotelcrud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hotelcrud.entity.Hotel;
import uz.pdp.hotelcrud.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByNumberAndFloorAndSizeAndHotelId(Integer number, Integer floor, Integer size, Integer hotel_id);

    Page<Room> findAllByHotel_Id(Integer hotel_id, Pageable pageable);

}
