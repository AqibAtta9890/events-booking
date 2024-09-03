package com.mariusniemet.bookingservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketDto {

  private TicketType ticketType;

  private Double price;

  private Integer ticketsAvailable;


}
