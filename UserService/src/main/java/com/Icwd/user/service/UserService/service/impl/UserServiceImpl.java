package com.Icwd.user.service.UserService.service.impl;

import com.Icwd.user.service.UserService.entities.Hotel;
import com.Icwd.user.service.UserService.entities.Rating;
import com.Icwd.user.service.UserService.entities.User;
import com.Icwd.user.service.UserService.exceptions.ResourseNotFoundException;
import com.Icwd.user.service.UserService.external.services.HotelService;
import com.Icwd.user.service.UserService.repository.UserRepository;
import com.Icwd.user.service.UserService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        // generate unique userid
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*@Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User with given Id is not found on server !!"));
        // fetch rating of above user from RATING SERVICE
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(),Rating[].class);
        logger.info("{}",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to HOTEL SERVICE to get the hotel
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(),Hotel.class);
            Hotel hotel = forEntity.getBody();

            logger.info("response status code: {}",forEntity.getStatusCode());

            // set the hotel to rating
            rating.setHotel(hotel);

            // return rating
            return rating;
        }).collect(Collectors.toList());

        user.setRating(ratingList);

        return user;
    }*/

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User with given Id is not found on server !!"));
        // fetch rating of above user from RATING SERVICE
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(),Rating[].class);
        logger.info("{}",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to HOTEL SERVICE to get the hotel
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            // set the hotel to rating
            rating.setHotel(hotel);

            // return rating
            return rating;
        }).collect(Collectors.toList());

        user.setRating(ratingList);

        return user;
    }

    @Override
    public User deleteUser(String userId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User with given Id is not found on server !!"));
        userRepository.deleteById(userId);
        return user;
    }

    @Override
    public User updateUser(User newUser, String userId) {
        User oldUser =  userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User with given Id is not found on server !!"));
        oldUser.setName(newUser.getName());
        oldUser.setAbout(newUser.getAbout());
        oldUser.setEmail(newUser.getEmail());
        return userRepository.save(oldUser);
    }
}
