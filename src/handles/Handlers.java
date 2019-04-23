package handles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

class Handlers {
    private String message;

    Handlers() {
    }

    void setMessage(String message) {
        this.message = message;
    }

    void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    String convertMessage() {
        Message sendBack = new Message(message);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(sendBack);
    }

    private class Message {
        private final String message;

        private Message(String context) {
            this.message = context;
        }
    }
}
