

import java.util.Comparator;


public class UvComparator implements Comparator<WeatherData> {

    @Override
    public int compare(WeatherData wd1, WeatherData wd2) {


        return wd1.getUv() < wd2.getUv() ? -1
                : wd1.getUv() > wd2.getUv() ? 1
                : 0;
    }
}
