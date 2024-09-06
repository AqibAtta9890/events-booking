package com.event.booking.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBookingResponse {

  private Long bookingId;

  private String eventName;

  private String userEmail;

}
