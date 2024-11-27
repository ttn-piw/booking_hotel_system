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
    public String showHotelDetailPage(HttpSession session,@RequestParam("id") Integer id, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        Integer userPID = (Integer) session.getAttribute("userId");
        hotel hotel = hotelsService.getHotelByHID(id);
        if (hotel != null) {
            model.addAttribute("hotel", hotel);
            model.addAttribute("userEmail", userEmail);
            model.addAttribute("userId", userPID);
        }
        return "Website/hotel_detail.html";
    }

    @GetMapping("/search")
    public String showSearchHotel(Model model) {
        return "Website/search_hotel.html";
    }


    @GetMapping("/search/location")
    @ResponseBody
    public List<hotel> searchHotel(@RequestParam("location") String location) {
        return hotelsService.showSearchHotels(location);
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
            System.out.println("Image file is missing or empty.");
            bindingResult.addError(new FieldError("hotelDTO", "hotelDTO.HImg", "hotelDTO.HImg is required"));
        }
        if(bindingResult.hasErrors()){
            System.out.println("BindingResult errors: " + bindingResult.getAllErrors());
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
            hotelDTO.setHDescription(editHotel.getHDescription());
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

        if (hotelDTO.getHImg() != null && !hotelDTO.getHImg().isEmpty()) {
            String uploadDir = "D:/NLCN/bookinghotel_thymeleaf/bookinghotel/src/main/resources/static/images";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try {
                MultipartFile image = hotelDTO.getHImg();
                String newFileName = image.getOriginalFilename();

                if (editedHotel.getHImg() != null && !editedHotel.getHImg().isEmpty()) {
                    Path oldImgPath = uploadPath.resolve(editedHotel.getHImg());
                    Files.deleteIfExists(oldImgPath);
                }

                Path newImagePath = uploadPath.resolve(newFileName);
                Files.copy(image.getInputStream(), newImagePath, StandardCopyOption.REPLACE_EXISTING);
                editedHotel.setHImg(newFileName);

            } catch (IOException e) {
                bindingResult.addError(new FieldError("hotelDTO", "HImg", "Error uploading image."));
            }
        }

        if (bindingResult.hasErrors()) {
            return "hotels/editHotelById.html";
        }

        editedHotel.setHName(hotelDTO.getHName());
        editedHotel.setHAddress(hotelDTO.getHAddress());
        editedHotel.setHStar(hotelDTO.getHStar());
        editedHotel.setHPhone(hotelDTO.getHPhone());
        editedHotel.setHDescription(hotelDTO.getHDescription());

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