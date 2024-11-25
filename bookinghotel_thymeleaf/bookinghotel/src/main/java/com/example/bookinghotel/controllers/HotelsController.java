package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.hotel;
import com.example.bookinghotel.models.hotelDTO;
import com.example.bookinghotel.models.room;
import com.example.bookinghotel.repository.HotelRepository;
import com.example.bookinghotel.services.HotelsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelsController {
    @Autowired
    private HotelsService hotelsService;

    @GetMapping("")
    public String showHotelList(Model model) {
        model.addAttribute("hotels", hotelsService.getAllHotels());
        return "hotels/index.html";
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session,Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        model.addAttribute("userEmail", userEmail);
        return "Website/main_page.html";
    }

    @GetMapping("/home/hotels")
    public String showHotelPage(Model model) {
        return "Website/hotels_page.html";
    }

//    @GetMapping("/hotel_detail")
//    public String showHotelDetailPage(Model model) {
//        return "Website/hotel_detail.html";
//    }

    @GetMapping("/api")
    @ResponseBody
    public List<hotel> getAllHotelsApi() {
        return hotelsService.getAllHotels();
    }

    @GetMapping("/api/hotelId")
    @ResponseBody
    public hotel getHotelByHotelID(@RequestParam("hotel_id") Integer hotelId) {
        return hotelsService.getHotelByHID(hotelId);
    }

    @GetMapping("/hotel_detail")
    public String showHotelDetailPage(@RequestParam("id") Integer id, Model model) {
        hotel hotel = hotelsService.getHotelByHID(id);
        if (hotel != null) {
            model.addAttribute("hotel", hotel);
        }
        return "Website/hotel_detail.html";
    }


    @GetMapping("/api/bestHotels")
    @ResponseBody
    public List<hotel> getAllHotelsBest() {
        return hotelsService.getBestHotels();
    }

    @GetMapping("/create")
    public String createHotel(Model model){
        hotelDTO hotelDTO = new hotelDTO();
        model.addAttribute("hotelDTO", hotelDTO);
        return "hotels/createHotel.html";
    }

    @PostMapping("/create")
    public String createHotel(@Valid @ModelAttribute hotelDTO hotelDTO, BindingResult bindingResult) throws IOException {
        if (hotelDTO.getHImg().isEmpty()) {
            bindingResult.addError(new FieldError("hotelDTO", "hotelDTO.HImg", "hotelDTO.HImg is required"));
        }
        if(bindingResult.hasErrors()){
            return "hotels/createHotel.html";
        }

        MultipartFile image = hotelDTO.getHImg();
        String storeImg = image.getOriginalFilename();

        try {
            String uploadDir = "D:/NLCN/bookinghotel_thymeleaf/bookinghotel/src/main/resources/static/images";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            //Save img
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = uploadPath.resolve(storeImg);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            System.out.println("Error uploading image: " + e.getMessage());
        }

        hotelsService.createNewHotel(hotelDTO, storeImg);
        return "redirect:/hotels";
    }

    @GetMapping("/edit")
    public String editHotelById(Model model, @RequestParam int id){

        try {
            hotel editHotel = hotelsService.getHotelByHID(id);
            model.addAttribute("hotel", editHotel);

            //take exist data and pasing form
            hotelDTO hotelDTO = new hotelDTO();
            hotelDTO.setHName(editHotel.getHName());
            hotelDTO.setHAddress(editHotel.getHAddress());
            hotelDTO.setHPhone(editHotel.getHPhone());
            hotelDTO.setHStar(editHotel.getHStar());
            hotelDTO.setHImgPath(editHotel.getHImg());

            model.addAttribute("hotelDTO", hotelDTO);

        } catch (Exception e) {
            System.out.println("Error editing hotel: " + e.getMessage());
            return "redirect:/hotels";
        }
        return "hotels/editHotelById.html";
    }

    @PostMapping("/edit")
    public String updateHotel(@RequestParam int id, @Valid @ModelAttribute hotelDTO hotelDTO, BindingResult bindingResult) throws IOException {
        hotel editedHotel = hotelsService.getHotelByHID(id);

        if (hotelDTO.getHImg().isEmpty()) {
            bindingResult.addError(new FieldError("hotelDTO", "hotelDTO.HImg", "hotelDTO.HImg is required"));
        }

        if (bindingResult.hasErrors()) {
            return "hotels/editHotelById.html";
        }

        String uploadDir = "D:/NLCN/bookinghotel_thymeleaf/bookinghotel/src/main/resources/static/images";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        if (!hotelDTO.getHImg().isEmpty()) {
            if (editedHotel.getHImg() != null && !editedHotel.getHImg().isEmpty()) {
                Path oldImgPath = uploadPath.resolve(editedHotel.getHImg().substring(editedHotel.getHImg().lastIndexOf('/') + 1));
                try {
                    Files.deleteIfExists(oldImgPath);
                } catch (IOException e) {
                    System.out.println("Error deleting old image: " + e.getMessage());
                }
            }

            MultipartFile image = hotelDTO.getHImg();
            String newFileName = image.getOriginalFilename();
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = uploadPath.resolve(newFileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                editedHotel.setHImg(newFileName);
            } catch (IOException e) {
                System.out.println("Error uploading new image: " + e.getMessage());
            }
        }
        hotelsService.updateHotel(editedHotel);

        return "redirect:/hotels";
    }

    @GetMapping("/delete")
    public String deleteHotelById(@RequestParam int id){
        hotel deleteHotel = hotelsService.getHotelByHID(id);
        System.out.println(deleteHotel.getHID());

        String uploadDir = "D:/NLCN/bookinghotel_thymeleaf/bookinghotel/src/main/resources/static/images";
        String imageName = deleteHotel.getHImg().substring(deleteHotel.getHImg().lastIndexOf('/') + 1);
        Path oldImgPath = Paths.get(uploadDir, imageName);
        System.out.println("Path to delete: " + oldImgPath.toString());

        try {
            Files.deleteIfExists(oldImgPath);
        } catch (IOException e) {
            System.out.println("Error deleting old image: " + e.getMessage());
        }
        hotelsService.deleteHotel(deleteHotel.getHID());

        return "redirect:/hotels";
    }

}