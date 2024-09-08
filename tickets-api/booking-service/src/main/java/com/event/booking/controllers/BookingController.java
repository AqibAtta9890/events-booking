package com.event.booking.controllers;

import com.event.booking.dtos.BookingDto;
import com.event.booking.dtos.CancelBookingRequest;
import com.event.booking.dtos.CreateBookingResponse;
import com.event.booking.services.BookingService;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public String cancelBooking(@RequestBody CancelBookingRequest cancelBookingRequest){
    bookingService.cancelBookingRequest(cancelBookingRequest);
    return "booking cancelled successfuly";

  }

  @GetMapping("/userBooking")
  public List<BookingDto> getUserBookings(@RequestParam("userEmail") String userEmail){


    return  bookingService.getUserBookings(userEmail);

  }


}
