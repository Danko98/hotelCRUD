package uz.pdp.hotelcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotelcrud.dto.RoomDto;
import uz.pdp.hotelcrud.entity.Hotel;
import uz.pdp.hotelcrud.entity.Room;
import uz.pdp.hotelcrud.repository.HotelRepository;
import uz.pdp.hotelcrud.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public Page<Room> getRoomPagable(@RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> listRoom = roomRepository.findAll(pageable);
        return listRoom;
    }

    @GetMapping(value = "/byHotelId/{id}")
    public Page<Room> getRoomPageableByHotelId(@PathVariable Integer id,@RequestParam int page){

        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> listRoom = roomRepository.findAllByHotel_Id(id,pageable);

        return listRoom;
    }

    @GetMapping(value = "/{id}")
    public Room getRoomById(@PathVariable Integer id){
        if (roomRepository.existsById(id)){
            Optional<Room> optionalRoom = roomRepository.findById(id);
            return optionalRoom.get();
        }else
            return new Room();
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto){
        if (!roomRepository.existsByNumberAndFloorAndSizeAndHotelId(roomDto.getNumber(),roomDto.getFloor(),roomDto.getSize(),roomDto.getHotelId())){
            if (hotelRepository.existsById(roomDto.getHotelId())){
                Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
                Room room = new Room();
                room.setFloor(roomDto.getFloor());
                room.setSize(roomDto.getSize());
                room.setNumber(roomDto.getNumber());
                room.setHotel(optionalHotel.get());
                roomRepository.save(room);
                return "Room saved";
            }else
                return "Not found hotel";
        }else
            return "This room already exists";
    }

    @PutMapping(value = "/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        if (roomRepository.existsById(id)) {
            if (!roomRepository.existsByNumberAndFloorAndSizeAndHotelId(roomDto.getNumber(), roomDto.getFloor(), roomDto.getSize(), roomDto.getHotelId())) {
                if (hotelRepository.existsById(roomDto.getHotelId())) {
                    Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
                    Optional<Room> optionalRoom = roomRepository.findById(id);
                    Room room = optionalRoom.get();
                    room.setFloor(roomDto.getFloor());
                    room.setSize(roomDto.getSize());
                    room.setNumber(roomDto.getNumber());
                    room.setHotel(optionalHotel.get());
                    roomRepository.save(room);
                    return "Room edited";
                } else
                    return "Not found hotel";
            } else
                return "This room already exists";
        }else
            return "Not found room";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteRoom(@PathVariable Integer id){
        if (roomRepository.existsById(id)){
            roomRepository.deleteById(id);
            return "Room deleted";
        }else
            return "Not found room";
    }
}
