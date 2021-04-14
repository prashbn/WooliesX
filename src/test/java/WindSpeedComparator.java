

import java.util.Comparator;

/**
 * This comparator compares two Post codes .
 */
public class WindSpeedComparator implements Comparator<WeatherData> {

    @Override
    public int compare(WeatherData wd1, WeatherData wd2) {
        /*return wd1.getwindspd().compareTo(wd2.getwindspd());*/
        return wd1.getwindspd() < wd2.getwindspd() ? -1
                : wd1.getwindspd() > wd2.getwindspd() ? 1
                : 0;
    }
}