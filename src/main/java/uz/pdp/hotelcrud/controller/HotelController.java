package uz.pdp.hotelcrud.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotelcrud.entity.Hotel;
import uz.pdp.hotelcrud.repository.HotelRepository;

import java.util.Optional;

@RestController
@RequestMapping(value = "/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;


    @GetMapping
    public Page<Hotel> getHotelList(@RequestParam(value = "page") int page){

        Pageable pageable = PageRequest.of(page,10);
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);

        return hotelPage;
    }

    @GetMapping(value = "/{id}")
    public Hotel getHotelById(@PathVariable Integer id){
        if (hotelRepository.existsById(id)){
            Optional<Hotel> optionalHotel = hotelRepository.findById(id);
            return optionalHotel.get();
        }else
            return new Hotel();
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotelDto){
        if (!hotelRepository.existsByName(hotelDto.getName())) {
            Hotel hotel = new Hotel();
            hotel.setName(hotelDto.getName());
            hotelRepository.save(hotel);
            return "Saqlandi";
        }

        return "Saqlanmadi";
    }

    @PutMapping(value = "/{id}")
    public String editHotel(@PathVariable Integer id,@RequestBody Hotel hotelDto){

        if (hotelRepository.existsById(id)) {
            if (hotelRepository.existsByName(hotelDto.getName())) {
                Optional<Hotel> optionalHotel = hotelRepository.findById(id);
                Hotel hotel = optionalHotel.get();
                hotel.setName(hotelDto.getName());
                hotelRepository.save(hotel);

                return "Hotel edted";

            } else
                return "Not found hotel";
        }else
            return "This hotel name already exists";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteHotel(@PathVariable Integer id){
        if (hotelRepository.existsById(id)){
            hotelRepository.deleteById(id);
            return "Hotel deleted";
        }else
            return "Not found Hotel";

    }











}





/*

        Mehmonxona strukturasini CRUD asosida yozing, bunda Hotel(name), Room(number, floor, size, Hotel)
        class lar bo'lsin. Hotel id orqali shu mehmonxonaga tegishli room lar ro'yxatini
        pageable qilib olib keluvchi method yozing.
        Proyektni git ga public qilib yuklang va vazifaga javob sifatida
        shu git repository ning link address ni yuboring.


        */



