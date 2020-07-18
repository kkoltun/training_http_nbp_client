package org.example.nbp_client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Currency;
import java.util.Scanner;
import java.util.Set;

public class Application {
  public static void main(String[] args) throws Exception {
    // 1. Zapytaj użytkownika o kod waluty
    // 2. Pobierz z NBP odpowiedź dla podanego kodu
    // 3. Wyświetl odpowiedź

//    Set<Currency> currencySet = Currency.getAvailableCurrencies();
//    for (Currency currency : currencySet) {
//      System.out.println("Currency: " + currency.getDisplayName() + ": " + currency.getCurrencyCode());
//    }

    Scanner scanner = new Scanner(System.in);
    System.out.println("Podaj kod waluty:");
    String chosenCode = scanner.nextLine();

    String textUri = "http://api.nbp.pl/api/exchangerates/rates/A/" + chosenCode + "/";
    URI uri = URI.create(textUri);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(uri)
        .build();

    HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

    String body = response.body();

    ObjectMapper objectMapper = new ObjectMapper();
    RatesTableDto table = objectMapper.readValue(body, RatesTableDto.class);
    System.out.println(table);
  }
}
