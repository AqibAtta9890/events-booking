package com.mariusniemet.bookingservice.controllers;

import com.mariusniemet.bookingservice.dtos.BookingDto;
import com.mariusniemet.bookingservice.dtos.CancelBookingRequest;
import com.mariusniemet.bookingservice.dtos.CreateBookingResponse;
import com.mariusniemet.bookingservice.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

  @Autowired
  private BookingService bookingService;


  @PostMapping
  public CreateBookingResponse createBooking(@RequestBody BookingDto bookingDto) {

    return bookingService.createBooking(bookingDto);
  }

  @PostMapping("/cancelBooking")
  public void cancelBooking(@RequestBody CancelBookingRequest cancelBookingRequest){
    bookingService.cancelBookingRequest(cancelBookingRequest);

  }


}
