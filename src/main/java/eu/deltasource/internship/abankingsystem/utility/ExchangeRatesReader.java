package eu.deltasource.internship.abankingsystem.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.deltasource.internship.abankingsystem.exception.FileCannotBeReadException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class ExchangeRatesReader {

    private ExchangeRatesReader() {}

    public static Map<String, Double> readExchangeRatesFile(String jsonName) {
        Map<String, Double> exchangeRates;
        Type REVIEW_TYPE = new TypeToken<Map<String, Double>>() {}.getType();
        Gson gson = new Gson();
        try (Reader reader = new FileReader(jsonName)) {
            exchangeRates = gson.fromJson(reader, REVIEW_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileCannotBeReadException("File Cannot be read: " + jsonName);
        }
        return exchangeRates;
    }

}
