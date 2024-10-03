package com.example.janesdb.service;


import com.example.janesdb.bo.ServerConnectionDetails;
import com.example.janesdb.entity.JanesDBEntity;
import com.example.janesdb.repository.JanesDBRepository;
import com.sun.tools.javac.Main;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

@Component
public class DataManagementService {

    ServerConnectionDetails serverConnectionDetails;

    @Autowired
    private JanesDBRepository janesDBRepository;

//    @PostConstruct
//    public void postCreateActivities() {
//        excelFileRead();
//    }

    public String sendImageToAICE(MultipartFile file , String json){
        return "send";
    }

    public void serverConnectionMethod(ServerConnectionDetails serverConnectionDetails){
            this.serverConnectionDetails = serverConnectionDetails;
    }

    public List<JanesDBEntity> receivedMessageFromServer(){
        if(serverConnectionDetails.getShipLength()!=null){
            List<JanesDBEntity> janesDBEntity = findByLengthWithThreshold(serverConnectionDetails.getShipLength());
            System.out.println(janesDBEntity);
            if(janesDBEntity!=null){
                return janesDBEntity;
            }else{
                throw new RuntimeException("Data Not Found");
            }
        }else{
            throw  new RuntimeException("Ship Length is Null");
        }


    }

    public List<JanesDBEntity> findByLengthWithThreshold(BigDecimal length) {
        BigDecimal minLength = length.multiply(BigDecimal.valueOf(0.95));
        BigDecimal maxLength = length.multiply(BigDecimal.valueOf(1.05));

        return janesDBRepository.findByLengthRange(minLength, maxLength);
    }

    public String excelFileRead(String fileName) {
        String filename = "janes-updated-data.xlsx";
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(fileName);
        System.out.println(inputStream);
        if(inputStream!=null) {
            assert inputStream != null;
            try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);
                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row != null) {
                        JSONObject jsonObject = new JSONObject();

                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            Cell headerCell = headerRow.getCell(cell.getColumnIndex());
                            jsonObject.put(String.valueOf(headerCell), getCellValue(cell));
                        }
                        JanesDBEntity jdb = new JanesDBEntity();
                        jdb.setCountry(jsonObject.optString("Country"));
                        jdb.setShipLength(jsonObject.optBigDecimal("Length(m)",null));
                        jdb.setShipBeam(jsonObject.optBigDecimal("Beam(m)",null));
                        jdb.setVesselClass(jsonObject.optString("Vessel_class"));
                        System.out.println(jdb);
                        janesDBRepository.save(jdb);

                    }

                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally{
                if(inputStream != null){
                    try{
                        inputStream.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }else{
            throw new RuntimeException("FILE_NOT_FOUND");
        }
        return "Pushed Data to DB";

    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                    return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            default:
                return null;
        }
    }
}


