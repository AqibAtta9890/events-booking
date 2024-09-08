package com.api.eventmanagement.services;

import com.api.eventmanagement.dtos.EventDto;
import com.api.eventmanagement.dtos.TicketDto;
import com.api.eventmanagement.dtos.TicketType;
import com.api.eventmanagement.entities.Event;
import com.api.eventmanagement.entities.EventTicket;
import com.api.eventmanagement.exception.BadRequestException;
import com.api.eventmanagement.repositories.IEventRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EventsService {

  private final IEventRepository repository;
  private final Logger logger = LoggerFactory.getLogger(EventsService.class);

  public EventsService(IEventRepository repository) {
    this.repository = repository;
  }

  public void create(EventDto createDto) {
    this.logger.info("Creating an event... [{}]", createDto);

    if (repository.findByName(createDto.getName()).isPresent()) {
      throw new BadRequestException(
          String.format("event with name : %s already present", createDto.getName()));
    }

    Event event = new Event();

    event.setName(createDto.getName());

    event.setLocation(createDto.getLocation());

    event.setDate(createDto.getDate());

    if (!CollectionUtils.isEmpty(createDto.getTicketDetails())) {

      event.setEventTickets(createDto.getTicketDetails().stream().map(ticketDto -> {

        EventTicket eventTicket = new EventTicket();
        eventTicket.setTicketType(ticketDto.getTicketType().name());
        eventTicket.setPrice(ticketDto.getPrice());
        eventTicket.setTicketsAvailable(ticketDto.getTicketsAvailable());
        eventTicket.setEvent(event);

        return eventTicket;
      }).collect(Collectors.toList()));
    }

    repository.saveAndFlush(event);

    logger.info("Event created successfully");

  }

  public EventDto findByEventName(String eventName, String ticketType) {
    this.logger.info("fetching event by Name");

    Event event = repository.findByName(eventName).orElseThrow(
        () -> new BadRequestException(String.format("event with name : %s not found", eventName)));

    EventDto eventDto = new EventDto();
    eventDto.setId(event.getId());
    eventDto.setName(event.getName());
    eventDto.setDate(event.getDate());
    eventDto.setLocation(event.getLocation());
    if (StringUtils.isEmpty(ticketType)) {
      eventDto.setTicketDetails(event.getEventTickets().stream().map(eventTicket -> {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPrice(eventTicket.getPrice());
        ticketDto.setTicketsAvailable(eventTicket.getTicketsAvailable());
        ticketDto.setTicketType(TicketType.valueOf(eventTicket.getTicketType()));
        return ticketDto;
      }).collect(Collectors.toList()));
    } else {
      eventDto.setTicketDetails(event.getEventTickets().stream().filter(
              eventTicket -> ticketType.equalsIgnoreCase(eventTicket.getTicketType()))
          .map(eventTicket -> {
            TicketDto ticketDto = new TicketDto();
            ticketDto.setPrice(eventTicket.getPrice());
            ticketDto.setTicketsAvailable(eventTicket.getTicketsAvailable());
            ticketDto.setTicketType(TicketType.valueOf(eventTicket.getTicketType()));
            return ticketDto;
          }).collect(Collectors.toList()));
    }

    return eventDto;

  }

  @Transactional
  public void update(Long id, EventDto updateDto) throws BadRequestException {
    logger.info("Updating the event {} with data {}", id, updateDto);

    // check if the event exists
    Optional<Event> toUpdate = this.repository.findById(id);

    if (toUpdate.isEmpty()) {
      logger.error("Failed to fetch the event {}", id);
      throw new BadRequestException("Event doesn't exist");
    }
    // update
    Event event = toUpdate.get();

    if(StringUtils.isNotEmpty(updateDto.getName())) {
      event.setName(updateDto.getName());
    }

    if(StringUtils.isNotEmpty(updateDto.getLocation())) {
      event.setLocation(updateDto.getLocation());
    }

    if(updateDto.getDate() != null) {
      event.setDate(updateDto.getDate());
    }

    if (!CollectionUtils.isEmpty(updateDto.getTicketDetails())) {

      TicketDto ticketDto = updateDto.getTicketDetails().get(0);

      EventTicket eventTicket = event.getEventTickets().stream().filter(
              evTicketDet -> ticketDto.getTicketType().name().equals(evTicketDet.getTicketType()))
          .findAny().orElseThrow(
              () -> new BadRequestException(String.format(
                  "event ticket details not found  with event name : %s and ticket type : %s",
                  event.getName(), ticketDto.getTicketType().name())));

      eventTicket.setTicketsAvailable(ticketDto.getTicketsAvailable());

      if(ticketDto.getPrice() != null){
        eventTicket.setPrice(ticketDto.getPrice());
      }

      this.logger.info("Event updated");

    }

    this.repository.save(event);
  }


}
