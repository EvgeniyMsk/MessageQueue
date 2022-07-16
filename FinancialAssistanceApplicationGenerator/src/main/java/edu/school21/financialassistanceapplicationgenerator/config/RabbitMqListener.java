package edu.school21.financialassistanceapplicationgenerator.config;

import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import edu.school21.financialassistanceapplicationgenerator.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@EnableRabbit
@Component
public class RabbitMqListener {
    @Value("${pathPDF}")
    private String PATH;

    public final String FONT = "/fonts/tnr.ttf";


    @RabbitListener(queues = "financial_assistance")
    public void processSocial_food(String message) throws DocumentException, IOException {
        Person person = new Gson().fromJson(message, Person.class);
        createFinancialAgreement(person);
    }

    private void createFinancialAgreement(Person person) throws DocumentException, IOException {
        Document document = new Document();
        File file = new File(PATH + person.getLastName() + person.getName());
        if (!file.exists())
            file.mkdir();
        PdfWriter.getInstance(document, new FileOutputStream(String.format("%s%s%s/FinancialAgreement.pdf",
                PATH,
                person.getLastName(),
                person.getName())));
        document.open();
        BaseFont baseFont= BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontTitle = new Font(baseFont, 16, Font.NORMAL);
        String main = String.format("Это Ваш FinancialAgreement, %s %s!\n" +
                "Ваш возраст: %d\n" +
                "Ваш курс: %d", person.getName(), person.getLastName(), person.getAge(), person.getCourse());
        Paragraph p1 = new Paragraph(main, fontTitle);
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);
        document.close();
    }
}