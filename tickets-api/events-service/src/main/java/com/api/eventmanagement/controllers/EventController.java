package com.api.eventmanagement.controllers;

import com.api.eventmanagement.services.EventsService;
import com.api.eventmanagement.dtos.EventDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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