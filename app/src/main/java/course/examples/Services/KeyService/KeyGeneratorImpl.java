package course.examples.Services.KeyService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import course.examples.Services.KeyCommon.KeyGenerator;

public class KeyGeneratorImpl extends Service {
    public static String set_status="Service not yet bound!";

    public boolean isRunning = false;
    static String item3;
    List<String> items1, items2;
    int year;
    int year1;
    int month;
    int day;
    int wDays;

    // Implement the Stub for this Object
    private final KeyGenerator.Stub mBinder = new KeyGenerator.Stub() {

        @Override
        public List<String> api1(int a) throws RemoteException {
            year=a;

            Runnable api1_Runnable = new Runnable() {
                @Override
                public void run() {

                    try {
                        URL url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT%20%22table%22,%20%22open_mo%22%20FROM%20t1%20WHERE%20(%22year%22%20==%20%27"+year+"%27%20)%20LIMIT%2010");
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                        StringBuilder builder = new StringBuilder();
                        String inputString;
                        while ((inputString = bufferedReader.readLine()) != null) {
                            builder.append(inputString);
                        }
                        String data = builder.toString();
                        List<String> items = Arrays.asList(data.split("\\s*,\\s*"));
                        items1=items;
                        urlConnection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
            Thread api1_Thread= new Thread((api1_Runnable));
            api1_Thread.start();
            return items1;
        }

        @Override
        public  List<String> api2(int x, int y, int z, int w) throws RemoteException {
            day=x;
            month=y;
            year1=z;
            wDays=w;

            Runnable api2_Runnable = new Runnable() {
                @Override
                public void run() {

                    try {
                        URL url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=select%20open_today%20from" +
                                "%20t1%20where%20account=%22Total%20Operating%20Balance%22%20and%20date%20%3E%20%27" + year1 + "-" + month + "-" + day + "%27%20limit%20" + wDays + ";");
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                        StringBuilder builder = new StringBuilder();
                        String inputString;
                        while ((inputString = bufferedReader.readLine()) != null) {
                            builder.append(inputString);
                        }
                        String data = builder.toString();
                        //data11= data;
                        List<String> items = Arrays.asList(data.split("\\s*,\\s*"));
                        items2=items;
                        urlConnection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
            Thread api2_Thread= new Thread((api2_Runnable));
            api2_Thread.start();
            return items2;

        }

        @Override
        public String api3(int i) throws RemoteException{
            year1=i;

            Runnable api3_Runnable = new Runnable() {
                @Override
                public void run() {
            try {
                URL url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=select%20avg(open_today)%20from%20t1%20where%20date%20%3E%20%27" + year1 + "-01-01%27AND%20date%20%3C%20%27" + year1 + "-12-31%27;");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);

                }
                String data = builder.toString();
                item3=data;
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
            Thread api3_Thread= new Thread((api3_Runnable));
            api3_Thread.start();
            return item3;
        }

        @Override
        public String status(String x) throws RemoteException{
            set_status=x;
            return set_status;
        }
    };

    // Return the Stub defined above
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}