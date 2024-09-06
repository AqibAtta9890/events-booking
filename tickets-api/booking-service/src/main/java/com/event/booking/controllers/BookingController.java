package com.event.booking.controllers;

import com.event.booking.dtos.BookingDto;
import com.event.booking.dtos.CancelBookingRequest;
import com.event.booking.dtos.CreateBookingResponse;
import com.event.booking.services.BookingService;
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
