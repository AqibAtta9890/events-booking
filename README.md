Event Management System


How to run this project :


a. First run registry service 


b. Then Run Event Service to create /manage event. Below are the curls for event service


       1. Create Event Curl
    
          curl --location 'http://localhost:7003/api/v1/events' \
          --header 'Content-Type: application/json' \
          --data '{
          "name": "independence day",
          "location": "dha y block",
          "date": "2024-08-17 17:30:00",
          "ticketDetails": [
          {
          "ticketType": "General",
          "price": 50.0,
          "ticketsAvailable": 100
          },
          {
          "ticketType": "VIP",
          "price": 100.0,
          "ticketsAvailable": 10
          }
          ]
          }'
    
          2. Update Event Details Curl 
    
             curl --location --request PUT 'http://localhost:7003/api/v1/events/1' \
             --header 'Content-Type: application/json' \
             --data '{
             "name": "birthday party",
             "location": "dha y block",
             "date": "2024-08-17 17:30:00",
             "ticketDetails": [
    
             {
             "ticketType": "VIP",
             "price": 100.0,
             "ticketsAvailable": 20
             }
             ]
             }'
    
          3. Get Event Details
    
             curl --location 'http://localhost:7003/api/v1/events/independence day'


c. Then Run Booking Service to create booking for event

    1. Create User booking curl

       curl --location 'http://localhost:7004/api/v1/booking' \
       --header 'Content-Type: application/json' \
       --data-raw '{
       "eventName": "independence day",
       "ticketType": "General",
       "noOfTickets": 4,
       "userEmail": "muhammadaqib91@gmail.com"
       }'
   
    2. Get All user bookings

      curl --location 'http://localhost:7004/api/v1/booking/userBooking?userEmail=muhammadaqib91%40gmail.com'
   
    3. Cancel booking curl

      curl --location 'http://localhost:7004/api/v1/booking/cancelBooking' \
      --header 'Content-Type: application/json' \
      --data-raw '{
      "eventName": "independence day",
      "ticketType": "General",
      "userEmail": "muhammadaqib91@gmail.com"
      }'
    
   