package com.kat.bankaccxmlproc.xml;

import com.fasterxml.jackson.core.JsonParseException;
import com.kat.bankaccxmlproc.entity.Account;
import java.io.File;

import com.kat.bankaccxmlproc.model.AccountContainer;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class XmlReaderTest {

    private XmlReader xmlReader;
    private File inputFile;
    private File outputFile;
    private List<Account> accounts;


    @Before
    public void setUp() {
        xmlReader = new XmlReader();
        inputFile = new File("inputFile.xml");
        outputFile = new File("outputFile.xml");
        accounts = new ArrayList<>();
    }

    @Test(expected = JsonParseException.class)
    public void shouldThrowExceptionWhenFileContentIsNull() throws IOException {

        //given
        String fileContent = null;
        FileUtils.writeStringToFile(inputFile, fileContent);

        //when
        List<Account> xmlFile = xmlReader.mapXmlFileToCollection(inputFile);

        //then
        Assert.assertNotEquals(accounts, xmlFile);
    }

    @Test
    public void shouldSuccessfullyMapCollectionToXmlFile(){

        //given
        Account cindy = new Account("PL89109024029112846184748351", "Cindy Lauper", "PLN", 0.00, LocalDate.of(2000, 11, 3));
        Account christopher = new Account("PL59109024024852873896518635", "Christopher Lambert", "PLN", 6000000.00, LocalDate.of(2100, 5, 14));
        accounts.add(cindy);
        accounts.add(christopher);
        AccountContainer accountContainer = new AccountContainer(accounts);

        //when
        xmlReader.mapCollectionToXmlFile(outputFile, accountContainer);

        //then
        assertNotNull(outputFile);
    }

    @Test
    public void shouldSuccessfullyMapFooXmlToCollection() throws IOException {
        //given
        String fileContent = "<?xml version = \"1.0\"?>\n" +
                "<accounts>\n" +
                "    <account iban=\"PL06109024024575853497287939\">\n" +
                "        <name>Barbara Streisand</name>\n" +
                "        <currency>PLN</currency>\n" +
                "        <balance>111000.00</balance>\n" +
                "        <closingDate>2001-04-29</closingDate>\n" +
                "    </account>\n" +
                "    <account iban=\"PL85109024027852947462921442\">\n" +
                "        <name>Tuomas Holopainen</name>\n" +
                "        <currency>PLN</currency>\n" +
                "        <balance>0.00</balance>\n" +
                "        <closingDate>2121-10-22</closingDate>\n" +
                "    </account>\n" +
                "\n" +
                "</accounts>\n";
        Account barbara = new Account("PL06109024024575853497287939", "Barbara Streisand", "PLN", 111000.00, LocalDate.of(2001, 4, 29));
        Account tuomas = new Account("PL85109024027852947462921442", "Tuomas Holopainen", "PLN", 0.00, LocalDate.of(2121, 10, 22));
        accounts.add(barbara);
        accounts.add(tuomas);

        //when
        FileUtils.writeStringToFile(inputFile, fileContent);
        List<Account> result = xmlReader.mapXmlFileToCollection(inputFile);

        //then
        Assert.assertEquals(accounts, result);
    }

    @Test
    public void shouldSuccessfullyMapFooXmlToCollectionButErrorOccursWhenIbanNumberIsDifferent() throws IOException {
        //given
        String fileContent = "<?xml version = \"1.0\"?>\n" +
                "<accounts>\n" +
                "    <account iban=\"PL17109024023718559324678453\">\n" +
                "        <name>Anduin Wrynn</name>\n" +
                "        <currency>PLN</currency>\n" +
                "        <balance>123456.00</balance>\n" +
                "        <closingDate>2015-08-01</closingDate>\n" +
                "    </account>\n" +
                "</accounts>\n";
        Account anduin = new Account("PL1710902402371855932467845", "Anduin Wrynn", "PLN", 123456.00, LocalDate.of(2015, 8, 1));
        accounts.add(anduin);
        FileUtils.writeStringToFile(inputFile, fileContent);
        //when
        List<Account> result = xmlReader.mapXmlFileToCollection(inputFile);
        //then
        assertNotEquals(accounts, result);
    }
}