package uz.pdp.hotelcrud.dto;

import lombok.Data;

@Data
public class RoomDto {
    private Integer size;
    private Integer floor;
    private Integer number;
    private Integer hotelId;
}
