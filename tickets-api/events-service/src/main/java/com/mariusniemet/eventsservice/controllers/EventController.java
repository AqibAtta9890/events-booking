package com.mariusniemet.eventsservice.controllers;

import com.mariusniemet.eventsservice.dtos.EventDto;
import com.mariusniemet.eventsservice.entities.Event;
import com.mariusniemet.eventsservice.services.EventsService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/events")
public class EventController {

  private final EventsService service;

  public EventController(EventsService service) {
    this.service = service;
  }


  @PostMapping
  public ResponseEntity<String> create(@RequestBody EventDto eventDto) {
    this.service.create(eventDto);
    return new ResponseEntity<>("event created successfully", HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody EventDto updateDto) {
     this.service.update(id, updateDto);

  }

  @GetMapping("/{name}")
  public ResponseEntity<EventDto> findByName(@PathVariable String name,
      @RequestParam(value = "ticketType", required = false) String ticketType) {
    EventDto result = this.service.findByEventName(name, ticketType);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }


}