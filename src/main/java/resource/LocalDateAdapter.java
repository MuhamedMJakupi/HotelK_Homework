package resource;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

//for date because had problem and wasn't connectiong to DB
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        out.value(value != null ? value.toString() : null);
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String dateStr = in.nextString();
        return dateStr != null ? LocalDate.parse(dateStr) : null;
    }
}
