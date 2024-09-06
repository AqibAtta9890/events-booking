package com.event.booking.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "BOOKING")
public class Booking {

  @Id
  @GeneratedValue
  private Long id;

  private String userEmail;

  private String eventName;

  private Double totalPrice;

  private Integer noOfTickets;

  private String ticketType;

  private String status;

}
