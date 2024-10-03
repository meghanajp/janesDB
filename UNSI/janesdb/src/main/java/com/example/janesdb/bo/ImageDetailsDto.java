package com.example.janesdb.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageDetailsDto {
    private String vesselImage;
    private String json;
}
