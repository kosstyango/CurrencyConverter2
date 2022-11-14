import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

    public class CurrencyConverter2 {
        public static void main(String[] args) throws JSONException, IOException {
            HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();
            // добавляем коды валют
            currencyCodes.put(1,"USD");
            currencyCodes.put(2,"EUR");
            currencyCodes.put(3,"RUB");

            String fromCode, toCode;
            double amount;

            Scanner sc = new Scanner(System.in);
            System.out.println("Currency converting FROM?");
            System.out.println("1:USD \t 2:EUR \t 3:RUB");
            fromCode = currencyCodes.get(sc.nextInt());

            System.out.println("Currency converting TO?");
            System.out.println("1:USD \t 2:EUR \t 3:RUB");
            toCode = currencyCodes.get(sc.nextInt());

            System.out.println("Amount you wish to convert?");
            amount = sc.nextFloat();
            sendHttpGETRequest(fromCode, toCode, amount);

        }

        private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException, JSONException {
            String API_KEY = "94470fbc238bdca2f3c1ce9e69707d78"; //получаем ключ API после регистрации,
            // кажется проект не поддерживается, но нам сейчас не важно
            String GET_URL ="https://currate.ru/api/?get=rates&pairs="+ fromCode + toCode +"&key=" + API_KEY; //формируем запрос
            System.out.println(GET_URL); //для проверки посмотрим в браузере ответ
            URL url = new URL(GET_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();

            if(responseCode==HttpURLConnection.HTTP_OK) { //если успешно
                BufferedReader in = new BufferedReader((new InputStreamReader(httpURLConnection.getInputStream())));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject obj = new JSONObject(response.toString());
                Double exchangeRate = obj.getJSONObject("data").getDouble(fromCode+toCode);
                System.out.println(obj.getJSONObject("data"));
                System.out.println(exchangeRate); //для проверки
                System.out.println();
                System.out.println(amount + fromCode + " = " + amount * exchangeRate + toCode); //умножаем валюту на курс
            }else {
                System.out.println("Get request failed!");
            }
        }
    }
