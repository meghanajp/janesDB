package com.example.janesdb.controller;

import com.example.janesdb.bo.ImageDetailsDto;
//import com.example.janesdb.bo.ServerConnectionDetails;
import com.example.janesdb.entity.JanesDBEntity;
import com.example.janesdb.repository.JanesDBRepository;
import com.example.janesdb.service.DataManagementService;
import com.example.janesdb.service.WebsocketConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usermodule")
public class JanesdbController {

    @Autowired
    private WebsocketConnectionService websocketConnectionService;

    @Autowired
    private DataManagementService dataManagementService;

    @Autowired
    JanesDBRepository janesDBRepository;

//    @Autowired
//    ServerConnectionDetails serverConnectionDetails;


    @CrossOrigin(origins="*")
    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("excelFile")MultipartFile Excelfile) throws IOException {

        System.out.println("file Received");
        String fileName = Excelfile.getOriginalFilename();
        System.out.println("file "+" "+fileName);
        dataManagementService.excelFileRead(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins="*")
    @RequestMapping(value = "/sendImage", method = RequestMethod.POST)
    public ResponseEntity<?> handleSendingImage(@RequestParam("image") MultipartFile imageFile, @RequestParam("details") String json) throws IOException {
        System.out.println(imageFile+" "+ json);
        String fileName = imageFile.getOriginalFilename();
        String contentType = imageFile.getContentType();
        long fileSize = imageFile.getSize();
        byte[] fileContent = imageFile.getBytes();
        ImageDetailsDto imageDetailsDto = new ImageDetailsDto();
        imageDetailsDto.setVesselImage(fileName);
        imageDetailsDto.setJson(json);
        return new ResponseEntity<>(websocketConnectionService.sendImageToAICE(imageDetailsDto), HttpStatus.OK);
    }

    @CrossOrigin(origins="*")
    @RequestMapping(value = "/receiveData", method = RequestMethod.POST)
    public ResponseEntity<?> handleReceiveMessage(){
        return new ResponseEntity<>(dataManagementService.receivedMessageFromServer(),HttpStatus.OK);
    }

//    @CrossOrigin(origins="*")
//    @RequestMapping(value = "/sendLength", method = RequestMethod.POST)
//    public ResponseEntity<?> handleLengthData() throws IOException {
//        BigDecimal shipLength = serverConnectionDetails.getShipLength();
//        BigDecimal minLength = shipLength.multiply(BigDecimal.valueOf(0.95));
//        BigDecimal maxLength = shipLength.multiply(BigDecimal.valueOf(1.05));
//
//        List<JanesDBEntity> janesDBEntity = janesDBRepository.findByLengthRange(minLength, maxLength);
//        return new ResponseEntity<>(websocketConnectionService.sendLengthToAICE(janesDBEntity),HttpStatus.OK);
//    }


}
