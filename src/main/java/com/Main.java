package com;

import com.dataSet.Folder;
import com.service.DBService;
import com.service.DBServiceImpl;
import com.service.FileHandlerService;
import com.service.FileHandlerServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main (String[] args) {

        SpringApplication.run(Main.class, args);

        DBService dbService = DBServiceImpl.instance();
        FileHandlerService fileHandlerService = FileHandlerServiceImpl.instance();

    }
}
