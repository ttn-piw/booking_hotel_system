package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.hotel;
import com.example.bookinghotel.models.hotelDTO;
import com.example.bookinghotel.models.room;
import com.example.bookinghotel.models.roomDTO;
import com.example.bookinghotel.services.HotelsService;
import com.example.bookinghotel.services.RoomsService;
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

@Controller
@RequestMapping("/rooms")
public class RoomsController {
    @Autowired
    private RoomsService roomsService;

    @Autowired
    private HotelsService hotelsService;

    @GetMapping("/")
    public String showRoomsList(Model model){
        model.addAttribute("rooms", roomsService.getAllRooms());
        return "rooms/roomList.html";
    }

    @GetMapping("/create")
    public String createRoom(Model model){
        roomDTO roomDTO = new roomDTO();
        model.addAttribute("roomDTO", roomDTO);
        model.addAttribute("hotels", hotelsService.getAllHotels());
        return "rooms/createRoom.html";
    }

    @PostMapping("/create")
    public String createRoom(@Valid @ModelAttribute roomDTO roomDTO, BindingResult bindingResult, Model model) throws IOException {
        if (roomDTO.getCTGImg().isEmpty()) {
            bindingResult.addError(new FieldError("roomDTO", "roomDTO.CTGImg", "Room image is required"));
        }
        if (bindingResult.hasErrors()) {
            return "rooms/createRoom.html";
        }

        MultipartFile image = roomDTO.getCTGImg();
        String storeImg = image.getOriginalFilename();

        try {
            String uploadDir = "D:/NLCN/bookinghotel_thymeleaf/bookinghotel/src/main/resources/static/images";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the image file
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = uploadPath.resolve(storeImg);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            System.out.println("Error uploading image: " + e.getMessage());
            bindingResult.rejectValue("CTGImg", "uploadError", "Could not upload image");
            return "rooms/createRoom.html";
        }

        roomsService.createNewRoom(roomDTO, storeImg);
        return "redirect:/rooms/";
    }

    @GetMapping("/edit")
    public String editRoomById(Model model, @RequestParam int id){

        try {
            room editRoom = roomsService.getRoomByCTGID(id);
            model.addAttribute("room", editRoom);

            //take exist data and pasing form
            roomDTO roomDTO = new roomDTO();
            roomDTO.setCTGName(editRoom.getCTGName());
            roomDTO.setCTGStar(editRoom.getCTGStar());
            roomDTO.setCTGQuantity(editRoom.getCTGQuantity());
            roomDTO.setCTGRemain(editRoom.getCTGRemain());
            roomDTO.setCTGImgPath(editRoom.getCTGImg());

            model.addAttribute("roomDTO", roomDTO);
            model.addAttribute("hotels", hotelsService.getAllHotels());



        } catch (Exception e) {
            System.out.println("Error editing room: " + e.getMessage());
            return "redirect:/rooms/";
        }
        return "rooms/editRoomById.html";
    }
    @PostMapping("/edit")
    public String updateRoom(@RequestParam int id, @Valid @ModelAttribute roomDTO roomDTO, BindingResult bindingResult) throws IOException {
        room editedRoom = roomsService.getRoomByCTGID(id);

        if (roomDTO.getCTGImg().isEmpty()) {
            bindingResult.addError(new FieldError("roomDTO", "roomDTO.CTGImg", "Room image is required"));
        }

        if (bindingResult.hasErrors()) {
            return "rooms/editRoomById.html";
        }

        String uploadDir = "D:/NLCN/bookinghotel_thymeleaf/bookinghotel/src/main/resources/static/images";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        if (!roomDTO.getCTGImg().isEmpty()) {
            if (editedRoom.getCTGImg() != null && !editedRoom.getCTGImg().isEmpty()) {
                Path oldImgPath = uploadPath.resolve(editedRoom.getCTGImg().substring(editedRoom.getCTGImg().lastIndexOf('/') + 1));
                try {
                    Files.deleteIfExists(oldImgPath);
                } catch (IOException e) {
                    System.out.println("Error deleting old image: " + e.getMessage());
                }
            }

            MultipartFile image = roomDTO.getCTGImg();
            String newFileName = image.getOriginalFilename();
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = uploadPath.resolve(newFileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                editedRoom.setCTGImg(newFileName);
            } catch (IOException e) {
                System.out.println("Error uploading new image: " + e.getMessage());
            }
        }
        roomsService.updateRoom(editedRoom, id);

        return "redirect:/rooms/";
    }

    @GetMapping("/delete")
    public String deleteRoomById(@RequestParam int id){
        room deleteRoom = roomsService.getRoomByCTGID(id);
        System.out.println(deleteRoom.getCTGID());

        String uploadDir = "D:/NLCN/bookinghotel_thymeleaf/bookinghotel/src/main/resources/static/images";
        String imageName = deleteRoom.getCTGImg().substring(deleteRoom.getCTGImg().lastIndexOf('/') + 1);
        Path oldImgPath = Paths.get(uploadDir, imageName);
        System.out.println("Path to delete: " + oldImgPath.toString());

        try {
            Files.deleteIfExists(oldImgPath);
        } catch (IOException e) {
            System.out.println("Error deleting old image: " + e.getMessage());
        }
        roomsService.deleteRoom(deleteRoom.getCTGID());

        return "redirect:/rooms/";
    }

}
