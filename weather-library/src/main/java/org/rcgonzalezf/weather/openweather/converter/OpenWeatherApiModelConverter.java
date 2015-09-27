package org.rcgonzalezf.weather.openweather.converter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import org.rcgonzalezf.weather.common.models.WeatherData;
import org.rcgonzalezf.weather.common.models.converter.ModelConverter;
import org.rcgonzalezf.weather.openweather.models.OpenWeatherApiRawData;

public class OpenWeatherApiModelConverter implements ModelConverter<Void, OpenWeatherApiRawData> {

  private InputStream mInputStream;

  @Override public Void fromInputStream(@NonNull InputStream inputStream) {
    mInputStream = inputStream;
    return null;
  }

  @Override public @Nullable List<OpenWeatherApiRawData> generateRawModel() throws IOException {
    final OpenApiWeatherJsonParser parser = new OpenApiWeatherJsonParser();
    return parser.parseJsonStream(mInputStream);
  }

  @Override public List<WeatherData> getModel() throws IOException {

    List<OpenWeatherApiRawData> rawModelList = generateRawModel();
    List<WeatherData> weatherData = null;

    if (rawModelList != null && rawModelList.size() > 0) {
      weatherData = new ArrayList<>(rawModelList.size());
      for (OpenWeatherApiRawData rawData : rawModelList) {

        if (rawData.getCod() == HttpURLConnection.HTTP_OK) {

          weatherData.add(new WeatherData(rawData.getName(), rawData.getWind().getSpeed(),
              rawData.getWind().getDeg(), rawData.getMain().getTemp(),
              rawData.getMain().getHumidity(), rawData.getSys().getSunrise(),
              rawData.getSys().getSunset()));
        }
      }
    }
    return weatherData;
  }
}
