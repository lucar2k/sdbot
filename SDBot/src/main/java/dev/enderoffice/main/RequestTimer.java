package dev.enderoffice.main;

import dev.enderoffice.twitch.TwitchConnection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class RequestTimer extends TimerTask {

    @Override
    public void run() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        //Get all non connected users
        System.out.println("[" + strDate + "]" + " LOGGER: Starting Non Connected Delete Timer");
        ArrayList<String> nonConnected = Main.mm.getAllUsersNonConnected();
        long minInMilli = 300000;
        for(String user : nonConnected) {
            if(System.currentTimeMillis() - Main.mm.getUsersRequestTimeMillis(user) > minInMilli) {
                TwitchConnection tConn = new TwitchConnection();
                tConn.removeRequest(user);
            }
        }
    }
}
