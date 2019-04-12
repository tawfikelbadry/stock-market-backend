package com.gradteam.porsaty.utilities;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by tawfik on 5/1/2018.
 */
@Component
public class FileUploader {

    public void uploadFile(MultipartFile file,String company_username) throws IOException {
        File convertFile = new File("src/main/resources/static/companies-documents/"+company_username+".pdf");
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
    }
}
