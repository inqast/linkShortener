package http.handler.dock;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import http.handler.Handler;

public class DocsHandler extends Handler implements HttpHandler {
    @Override
        public void handle(HttpExchange t) throws IOException {
            StringBuilder responseBuilder = new StringBuilder()
            .append("GET /docs - список ручек с параметрами<br>");

            responseBuilder.append("GET /links/{$user} получения оригинальной ссылки по короткой ссылке<br>")
            .append(" - user uuid пользователя ссылки которого отображаем<br>");

            responseBuilder.append("GET /link/{$hash} получения оригинальной ссылки по короткой ссылке<br>")
            .append(" - hash string хэш короткой ссылки, которую нужно получить<br>");

            responseBuilder
            .append("POST /link создание короткой ссылки<br>")
            .append(" - link string ссылка которую нужно сократить<br>")
            .append(" - user uuid идентификатор пользователя --опционально<br>")
            .append(" - limit int ограничение на количество использований --опционально<br>");

            responseBuilder
            .append("PATCH /link создание короткой ссылки<br>")
            .append(" - hash string ссылка которую нужно обновить<br>")
            .append(" - user uuid идентификатор пользователя<br>")
            .append(" - link string целевая ссылка для перенаправления --опционально<br>")
            .append(" - limit int ограничение на количество использований --опционально<br>");

            responseBuilder
            .append("DELETE /hash удаление короткой ссылки</br>")
            .append(" - hash string ссылка которую нужно обновить</br>")
            .append(" - user uuid идентификатор пользователя</br>");

            byte[] response = responseBuilder.toString().getBytes(StandardCharsets.UTF_8);

            t.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, response.length);

            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
}
