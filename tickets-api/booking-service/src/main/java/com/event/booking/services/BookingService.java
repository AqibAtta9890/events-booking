package com.event.booking.services;

import com.event.booking.dtos.BookingDto;
import com.event.booking.dtos.CancelBookingRequest;
import com.event.booking.dtos.CreateBookingResponse;
import com.event.booking.dtos.EventDto;
import com.event.booking.dtos.TicketDto;
import com.event.booking.dtos.TicketType;
import com.event.booking.entities.Booking;
import com.event.booking.exception.BadRequestException;
import com.event.booking.repositories.IBookingRepository;
import jakarta.transaction.Transactional;
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

  @Autowired
  private NotificationService notificationService;


  public CreateBookingResponse createBooking(BookingDto bookingDto) {

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

    //update the tickets available for event after booking successful

    EventDto eventDto = new EventDto();
    eventDto.setId(event.getId());
    TicketDto ticketDtoUpdate = new TicketDto();
    ticketDtoUpdate.setTicketsAvailable(ticketDto.getTicketsAvailable() - booking.getNoOfTickets());
    ticketDtoUpdate.setTicketType(TicketType.valueOf(bookingDto.getTicketType()));
    eventDto.setTicketDetails(List.of(ticketDtoUpdate));

    eventServiceClient.updateEvent(event.getId(), eventDto);

    notificationService.sendBookingConfirmationEmail(eventDto,booking);


    CreateBookingResponse bookingResponse = new CreateBookingResponse();

    bookingResponse.setBookingId(booking.getId());
    bookingResponse.setEventName(booking.getEventName());
    bookingResponse.setUserEmail(booking.getUserEmail());

    return bookingResponse;


  }


  @Transactional
  public void cancelBookingRequest(CancelBookingRequest cancelBookingRequest) {

    Booking booking = bookingRepository.findByUserEmailAndEventName(
        cancelBookingRequest.getUserEmail(),
        cancelBookingRequest.getEventName()).orElseThrow(() -> new BadRequestException(
        String.format("no booking found with event name : %s and user Id : %s ",
            cancelBookingRequest.getUserEmail(), cancelBookingRequest.getEventName())));

    booking.setStatus("CANCELLED");


    EventDto event = eventServiceClient.getEventDetail(cancelBookingRequest.getEventName(),
        cancelBookingRequest.getTicketType());

    if (CollectionUtils.isEmpty(event.getTicketDetails())) {
      throw new BadRequestException(" no event ticket details found");
    }

    TicketDto ticketDto = event.getTicketDetails().get(0);

    EventDto eventDto = new EventDto();
    eventDto.setId(event.getId());
    TicketDto ticketDtoUpdate = new TicketDto();
    ticketDtoUpdate.setTicketsAvailable(ticketDto.getTicketsAvailable() + booking.getNoOfTickets());
    ticketDtoUpdate.setTicketType(TicketType.valueOf(cancelBookingRequest.getTicketType()));
    eventDto.setTicketDetails(List.of(ticketDtoUpdate));

    eventServiceClient.updateEvent(event.getId(), eventDto);


    bookingRepository.saveAndFlush(booking);





  }


}


