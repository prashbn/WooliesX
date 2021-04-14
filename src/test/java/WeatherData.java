public class WeatherData {
    private String postCode;
    private String temp;
    private Float uv;
    private Float windspd;

    public WeatherData(String postCode, String temp, Float uv, Float windspd) {
        this.postCode = postCode;
        this.temp = temp;
        this.uv = uv;
        this.windspd = windspd;

    }

    public Float getwindspd() {
        return windspd;
    }

    public Float getUv() {
        return uv;
    }

    public String toString() {
        return String.format("%s\t%s\t%f\t%f", postCode, temp, uv, windspd);
    }
}
