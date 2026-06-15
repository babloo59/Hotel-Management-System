package com.Icwd.rating.service;

import com.Icwd.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    // create
    Rating createRating(Rating rating);

    // get all rating
    List<Rating> getRating();

    // get all userId
    List<Rating> getRatingByUserId(String userId);

    // get all hotelId
    List<Rating> getRatingByHotelId(String hotelId);
}
