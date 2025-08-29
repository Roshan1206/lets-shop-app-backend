package com.example.lets_shop_app.util.impl;

import com.example.lets_shop_app.util.FileHandlerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandlerUtilImpl implements FileHandlerUtil {

    @Override
    public List<String> extractFile(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line= reader.readLine()) != null){
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return lines;
    }
}
