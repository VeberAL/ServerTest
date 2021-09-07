package com.company;

import java.util.Map;

public class HttpResponse {
    private final static String NEW_LINE ="\r\n";

    private Map <String, String> headers;
    private String body = "";
    private int statusCode = 200;
    private  String status = "Ok";

    public HttpResponse() {
        this.headers.put("Server", "Test");
        this.headers.put("Connection", "Close");
    }
    public  void addHeader(String key, String value) {
        this.headers.put(key, value);
    }
    public  void addHeader(Map<String, String> headers) {
        this.headers.putAll(headers);
    }
    //склеивание
    public String message(){
        StringBuilder builder = new StringBuilder();

          builder.append("HTTP/1.1 ")   //версия протокола
                 .append(statusCode)
                 .append(" ")
                 .append(status)
                 .append(NEW_LINE);

          for (Map.Entry<String, String> entry : headers.entrySet()){
               builder.append(entry.getKey())
                      .append(": ")
                      .append(entry.getValue())
                      .append(NEW_LINE);
          }
          //отделение headers от тела документов
          return builder
                  .append(NEW_LINE)
                  .append(body)
                  .toString();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
    public byte[] getBytes() {
        return message().getBytes();
    }
    //геттеры и сеттеры
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.headers.put("Content-Length", String.valueOf(body.length()));
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
