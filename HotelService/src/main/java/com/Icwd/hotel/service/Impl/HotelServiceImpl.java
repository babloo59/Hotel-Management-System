package com.Icwd.hotel.service.Impl;

import com.Icwd.hotel.entities.Hotel;
import com.Icwd.hotel.exceptions.ResourseNotFoundException;
import com.Icwd.hotel.repository.HotelRepository;
import com.Icwd.hotel.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Override
    public Hotel saveHotel(Hotel hotel) {
        String randomHotelId = UUID.randomUUID().toString();
        hotel.setId(randomHotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourseNotFoundException("hotel with given Id is not found on server !!"));

        ArrayList forObject = restTemplate.getForObject("http://localhost:8083/ratings/hotels/"+hotel.getId(),ArrayList.class);
        logger.info("{}",forObject);

        return hotel;
    }

    @Override
    public Hotel deleteHotel(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourseNotFoundException("hotel with given Id is not found on server !!"));
        hotelRepository.deleteById(hotelId);
        return hotel;
    }

    @Override
    public Hotel updateHotel(Hotel newHotel, String hotelId) {
        Hotel oldHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourseNotFoundException("hotel with given Id is not found on server !!"));
        oldHotel.setName(newHotel.getName());
        oldHotel.setAbout(newHotel.getAbout());
        oldHotel.setLocation(newHotel.getLocation());
        hotelRepository.save(oldHotel);
        return oldHotel;
    }
}
