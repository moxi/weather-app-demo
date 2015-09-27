package org.rcgonzalezf.weather;

import org.rcgonzalezf.weather.common.ServiceConfig;
import org.rcgonzalezf.weather.common.WeatherProvider;

public class WeatherTestLibApp extends WeatherLibApp {

  private ServiceConfig mServiceConfig;

  @Override public void onCreate() {
    super.onCreate();
    setAppInstance(this);
    mServiceConfig = ServiceConfig.getInstance();
    mServiceConfig.setApiKey(getString(R.string.open_weather_map_api_key));
    mServiceConfig.setWeatherProvider(WeatherProvider.OpenWeather);
  }

}
