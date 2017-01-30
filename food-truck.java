import java.io.*;
import java.util.*;
import java.text.*;

public class Solution {
    static class Client {
        public double longitude, latitude;
        public Date timeStamp;
        public Client(double longitude, double latitude, Date timeStamp) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.timeStamp = timeStamp;
        }
    }
    
    static double longitude0, latitude0;
    static double pDist;
    static Map<String, Client> clients = new HashMap<>();
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String[] latLong = in.nextLine().split(",");
        latitude0 = Double.parseDouble(latLong[0]);
        longitude0 = Double.parseDouble(latLong[1]);
        pDist = Math.sin(in.nextDouble()/(2*6378.137));
        pDist = pDist*pDist;
        in.nextLine();
        
        String[] headers = in.nextLine().split(",");
        int locTime = 0, locLat = 1, locLong = 2, locPhone = 3;
        for (int i = 0; i < 4; ++i) {
            if (headers[i].equals("Date&Time"))
                locTime = i;
        }
        for (int i = 0; i < 4; ++i) {
            if (headers[i].equals("Latitude"))
                locLat = i;
        }
        for (int i = 0; i < 4; ++i) {
            if (headers[i].equals("Longitude"))
                locLong = i;
        }
        for (int i = 0; i < 4; ++i) {
            if (headers[i].equals("PhoneNumber"))
                locPhone = i;
        }
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");
            Client c = clients.get(line[locPhone]);
            if (c == null) {
                clients.put(line[locPhone], new Client(Double.parseDouble(line[locLong]),
                                                Double.parseDouble(line[locLat]),
                                                df.parse(line[locTime])));
            } else {
                Date d = df.parse(line[locTime]);
                int moreRecent = d.compareTo(c.timeStamp);
                if (moreRecent > 0) {
                    c.longitude = Double.parseDouble(line[locLong]);
                    c.latitude = Double.parseDouble(line[locLat]);
                    c.timeStamp = d;
                }
            }
        }
        
        List<String> inRange = new ArrayList<>();
        for (String phone : clients.keySet()) {
            Client c = clients.get(phone);
            if (distance(c.latitude, c.longitude) < pDist) {
                inRange.add(phone);
            }
        }
        Collections.sort(inRange);
        for (int i = 0; i < inRange.size()-1; ++i)
            System.out.print(inRange.get(i) + ",");
        System.out.println(inRange.get(inRange.size()-1));
    }
    
    static double distance(double latitude, double longitude) {
        double arg1 = Math.toRadians((latitude0 - latitude)/2);
        double arg2 = Math.toRadians((longitude0 - longitude)/2);
        double arg3 = Math.toRadians(latitude0);
        double arg4 = Math.toRadians(latitude);
        
        double sin1 = Math.sin(arg1);
        double cos1 = Math.cos(arg3);
        double cos2 = Math.cos(arg4);
        double sin2 = Math.sin(arg2);
        
        return sin1*sin1 + cos1*cos2*sin2*sin2;
    }
}