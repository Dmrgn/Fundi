package data_access;

import entity.TimeSeriesPoint;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.watchlist.TimeSeriesDataAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TwelveDataAccess implements TimeSeriesDataAccess {
    private final TwelveDataClient client = new TwelveDataClient();

    @Override
    public List<TimeSeriesPoint> getTimeSeries(String symbol, String range) throws Exception {
        String interval;
        int outputsize;
        switch (range) {
            case "1W":
                interval = "1h"; // 1 week hourly
                outputsize = 168; // 24*7
                break;
            case "3M":
                interval = "1day";
                outputsize = 70; // ~ 3 months trading days
                break;
            case "6M":
                interval = "1day";
                outputsize = 130; // ~ 6 months trading days
                break;
            case "1Y":
                interval = "1day";
                outputsize = 260; // ~ 1 year trading days
                break;
            case "1M":
            default:
                interval = "1day";
                outputsize = 25; // ~ 1 month trading days
                break;
        }

        String json = client.fetchTimeSeriesJson(symbol, interval, outputsize);
        JSONObject root = new JSONObject(json);
        String status = root.optString("status", "ok");
        if (!"ok".equalsIgnoreCase(status)) {
            String msg = root.optString("message", "API error");
            throw new Exception(msg);
        }
        JSONArray values = root.optJSONArray("values");
        if (values == null) return new ArrayList<>();

        // TwelveData dates are in local market tz, string like "2025-08-07" or with time for intraday
        // We'll parse to epoch millis in UTC
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        dfDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dfDateTime.setTimeZone(TimeZone.getTimeZone("UTC"));

        List<TimeSeriesPoint> out = new ArrayList<>();
        for (int i = values.length() - 1; i >= 0; i--) { // reverse to chronological
            JSONObject v = values.getJSONObject(i);
            String dt = v.optString("datetime");
            String closeStr = v.optString("close");
            if (dt == null || closeStr == null || closeStr.isEmpty()) continue;
            try {
                Date d;
                if (dt.length() == 10) {
                    d = dfDate.parse(dt);
                } else {
                    // TwelveData intraday sometimes returns "YYYY-MM-DD HH:mm:ss"
                    if (dt.length() == 16) dt = dt + ":00";
                    d = dfDateTime.parse(dt);
                }
                double close = Double.parseDouble(closeStr);
                out.add(new TimeSeriesPoint(d.getTime(), close));
            } catch (Exception ignore) { }
        }
        return out;
    }
}
