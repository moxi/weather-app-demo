package org.rcgonzalezf.weather.openweather;

import androidx.annotation.NonNull;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rcgonzalezf.weather.common.listeners.OnUpdateWeatherListListener;
import org.rcgonzalezf.weather.common.models.WeatherInfo;
import org.rcgonzalezf.weather.openweather.network.OpenWeatherApiError;
import org.rcgonzalezf.weather.openweather.network.OpenWeatherApiResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class OpenWeatherApiCallbackTest {

  private OpenWeatherApiCallback uut;

  private boolean mListUpdated;
  private boolean mOnErrorCalled;
  private OnUpdateWeatherListListener mOnUpdateWeatherListListener = new OnUpdateWeatherListListener() {

    @Override public void updateList(@NonNull List<WeatherInfo> weatherInfoList) {
      mListUpdated = true;
    }

    @Override public void onError(String error) {
      mOnErrorCalled = true;
    }
  };

  @Test
  public void shouldUpdateTheListOnSuccess() {
    givenUut();

    whenCallbackIsNotifiedForSuccess(mock(OpenWeatherApiResponse.class));

    thenListUpdated(true);
  }

  @Test
  public void shouldIgnoreUpdateTheListOnSuccessWhenListenerIsNull() {
    givenUut();

    whenCallbackIsNotifiedForSuccessWithNullListener(mock(OpenWeatherApiResponse.class));

    thenListUpdated(false);
    thenOnErrorIsCalled(false);
  }

  @Test
  public void shouldNotifyErrorOnError() {
    givenUut();

    whenCallbackIsNotifiedForError(new OpenWeatherApiError());

    thenOnErrorIsCalled(true);
  }

  @Test
  public void shouldIgnoreErrorOnErrorWhenListenerIsNull() {
    givenUut();

    whenCallbackIsNotifiedForErrorWithNullListener(new OpenWeatherApiError());

    thenOnErrorIsCalled(false);
    thenListUpdated(false);
  }

  private void whenCallbackIsNotifiedForSuccessWithNullListener(OpenWeatherApiResponse openWeatherApiResponse) {
    uut.onSuccess(openWeatherApiResponse, null);
  }

  private void whenCallbackIsNotifiedForErrorWithNullListener(OpenWeatherApiError openWeatherApiError) {
    uut.onError(openWeatherApiError, null);
  }

  private void thenOnErrorIsCalled(boolean expected) {
    assertEquals(expected, mOnErrorCalled);
  }

  private void whenCallbackIsNotifiedForError(OpenWeatherApiError openWeatherApiError) {
    uut.onError(openWeatherApiError);
  }

  private void givenUut() {
    uut = new OpenWeatherApiCallback(mOnUpdateWeatherListListener);
  }

  private void thenListUpdated(boolean expected) {
    assertEquals(expected, mListUpdated);
  }

  private void whenCallbackIsNotifiedForSuccess(OpenWeatherApiResponse openWeatherApiResponse) {
    uut.onSuccess(openWeatherApiResponse);
  }
}
