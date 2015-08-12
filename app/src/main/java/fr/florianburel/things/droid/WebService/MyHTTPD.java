package fr.florianburel.things.droid.WebService;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by fl0 on 07/08/15.
 */
public class MyHTTPD extends NanoHTTPD {

    private static final int PORT = 8080;
    public static final String UPDATE_AVAILABLE = "fr.florianburel.things.droid.httpdServer.broadcastupdate";
    private final Context context;

    public MyHTTPD(Context context) throws IOException {
        super(PORT);
        this.context = context.getApplicationContext();
        start();
    }

    @Override
    public Response serve(IHTTPSession session) {


        Intent broadcast = new Intent(UPDATE_AVAILABLE);

        // Si besoin de transferer des infos complementaires a l'activity/fragment
//        broadcast.putExtra(... , ...)

        context.sendBroadcast(broadcast);


        return new NanoHTTPD.Response("hello");

    }
}
