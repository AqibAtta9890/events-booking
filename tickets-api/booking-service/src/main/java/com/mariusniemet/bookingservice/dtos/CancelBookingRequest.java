package com.mariusniemet.bookingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CancelBookingRequest {

  private String userEmail;

  private String eventName;

  private String ticketType;

}
