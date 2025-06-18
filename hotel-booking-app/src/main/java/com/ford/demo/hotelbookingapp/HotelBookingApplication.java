package com.ford.demo.hotelbookingapp;

import com.ford.demo.hotelbookingapp.entities.Hotel;
import com.ford.demo.hotelbookingapp.repository.IHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class HotelBookingApplication implements CommandLineRunner {

    @Autowired
    private IHotelRepository hotelRepository;

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<Hotel> hotels = List.of(
                new Hotel("ITC Grand Hotel", "Mumbai", 5, "Luxury hotel with city views", "https://www.itchotels.com/content/dam/itchotels/in/umbrella/itc/hotels/itcmaratha-mumbai/images/overview-landing-page/headmast/desktop/outer-facade-exterior.jpg", 200),
                new Hotel("ITC Elite Resort", "Goa", 4, "Beachfront resort with stunning views", "https://images.trvl-media.com/hotels/31000000/30790000/30783800/30783748/0fe221d8_z.jpg", 150),
                new Hotel("ITC Mountain Inn", "Shimla", 3, "Cozy inn nestled in the mountains", "https://ak-d.tripcdn.com/images/0224j120008c9iajh9C72_R_960_660_R5_D.jpg", 300),
                new Hotel("ITC City Lodge", "Delhi", 3, "Conveniently located city lodge", "https://seoimgak.mmtcdn.com/blog/sites/default/files/images/ITC%20Maurya%20New%20Delhi.jpg", 250),
                new Hotel("ITC Seaside Palace", "Chennai", 5, "Exclusive palace right on the beach", "https://cache.marriott.com/marriottassets/marriott/MAALC/maalc-exterior-9797-hor-feat.jpg", 400),
                new Hotel("ITC Forest Retreat", "Kerala", 4, "Peaceful retreat in a forest setting", "https://www.itchotels.com/content/dam/itchotels/in/umbrella/storii/hotels/storiiamoha-dharamshala/images/headmast/desktop/facade.jpg", 150),
                new Hotel("ITC Desert Oasis", "Jaipur", 4, "Oasis of luxury amid the desert", "https://www.itchotels.com/content/dam/itchotels/in/umbrella/itc/hotels/itcrajputana-jaipur/images/overview/headmast-desktop/steps-down-to-Spa.jpg", 180),
                new Hotel("ITC Urban Hub", "Bangalore", 5, "Modern hub in the heart of the city", "https://cf-cdn.cityinfoservices.com/public/uploads/project/images/xlarge/358045cc2a6b6e5e6cITC%20Green%20Centre%201.jpg", 220),
                new Hotel("ITC Historic Haven", "Kolkata", 4, "Historic hotel with a rich past", "https://im.whatshot.in/img/2020/Mar/itc-royal-bengal-2-copy-1584467750.jpg", 200),
                new Hotel("ITC Island Escape", "Andaman", 5, "Escape to paradise on the island", "https://chabilmarvillas.com/wp-content/uploads/2021/01/belize-eco-luxury-resort-2048x1150.jpg", 300)
        );

        hotelRepository.saveAll(hotels);
    }
}