package com.kat.bankaccxmlproc.xml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.kat.bankaccxmlproc.entity.Account;
import com.kat.bankaccxmlproc.model.AccountContainer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class XmlReader {

    private Scanner scanner = new Scanner(System.in);
    private XmlMapper xmlMapper = new XmlMapper();

    public String getPath() {
        System.out.println("Please enter file path");
        return scanner.nextLine();
    }

    public List<Account> mapXmlFileToCollection(File file) throws IOException {
        return xmlMapper.readValue(file, new TypeReference<List<Account>>() {});
    }

    private void xmlConfiguration (){
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true)
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    public void mapCollectionToXmlFile (File file, AccountContainer accountContainer) {
        xmlConfiguration();
        try {
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(file, accountContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
