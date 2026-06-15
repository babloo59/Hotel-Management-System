package com.Icwd.hotel.service;

import com.Icwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    // create
    Hotel saveHotel(Hotel hotel);

    // get all
    List<Hotel> getAllHotel();

    // get single
    Hotel getHotel(String hotelId);

    // delete
    Hotel deleteHotel(String hotelId);

    // update
    Hotel updateHotel(Hotel hotel, String hotelId);
}
