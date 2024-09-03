package com.mariusniemet.bookingservice.services;

import com.mariusniemet.bookingservice.dtos.BookingDto;
import com.mariusniemet.bookingservice.dtos.EventDto;
import com.mariusniemet.bookingservice.dtos.TicketDto;
import com.mariusniemet.bookingservice.dtos.TicketType;
import com.mariusniemet.bookingservice.entities.Booking;
import com.mariusniemet.bookingservice.exception.BadRequestException;
import com.mariusniemet.bookingservice.repositories.IBookingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BookingService {


  @Autowired
  private EventServiceClient eventServiceClient;

  @Autowired
  private MockPaymentService mockPaymentService;

  @Autowired
  private IBookingRepository bookingRepository;


  public BookingDto createBooking(BookingDto bookingDto) {

    EventDto event = eventServiceClient.getEventDetail(bookingDto.getEventName(),
        bookingDto.getTicketType());

    if (CollectionUtils.isEmpty(event.getTicketDetails())) {
      throw new BadRequestException(" no event ticket details found");
    }

    TicketDto ticketDto = event.getTicketDetails().get(0);

    if (ticketDto.getTicketsAvailable() <= 0) {
      throw new BadRequestException(
          "currently no ticket is available for ticket type :" + bookingDto.getTicketType());
    }

    if (ticketDto.getTicketsAvailable() < bookingDto.getNoOfTickets()) {
      throw new BadRequestException("no of available tickets are less than requested tickets");
    }

    double totalPrice = ticketDto.getPrice() * bookingDto.getNoOfTickets();

    Booking booking = new Booking();
    booking.setStatus("PENDING");
    booking.setTotalPrice(totalPrice);
    booking.setEventName(event.getName());
    booking.setTicketType(ticketDto.getTicketType().name());
    booking.setUserEmail(bookingDto.getUserEmail());
    booking.setNoOfTickets(bookingDto.getNoOfTickets());

    if (mockPaymentService.paymentSuccessful()) {
      booking.setStatus("BOOKED");
    }

    bookingRepository.saveAndFlush(booking);

    BookingDto dto = new BookingDto();

    //update the tickets available for event after booking successful

    EventDto eventDto = new EventDto();
    eventDto.setId(event.getId());
    TicketDto ticketDtoUpdate = new TicketDto();
    ticketDtoUpdate.setTicketsAvailable(ticketDto.getTicketsAvailable() - booking.getNoOfTickets());
    ticketDtoUpdate.setTicketType(TicketType.valueOf(bookingDto.getTicketType()));
    eventDto.setTicketDetails(List.of(ticketDtoUpdate));

    eventServiceClient.updateEvent(event.getId(),eventDto);

    dto.setBookingId(booking.getId());
    dto.setEventName(booking.getEventName());
    dto.setNoOfTickets(booking.getNoOfTickets());
    dto.setUserEmail(booking.getUserEmail());

    return bookingDto;


  }


}


