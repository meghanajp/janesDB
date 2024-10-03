package com.example.janesdb.bo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
public class ServerConnectionDetails {
    private String country;
    private String vesselName;
    private BigDecimal shipLength;
    private BigDecimal radarRange;
    private BigDecimal radarCourse;
    private BigDecimal cameraFov;
    private MultipartFile image;
    private String pennantNo;
}
