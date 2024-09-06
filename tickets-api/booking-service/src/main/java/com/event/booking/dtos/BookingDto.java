package com.event.booking.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingDto {

  private String eventName;

  private String ticketType;

  private Integer noOfTickets;

  private String userEmail;

  private Long bookingId;


}
