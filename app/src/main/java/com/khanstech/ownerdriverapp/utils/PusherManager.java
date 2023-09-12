package com.khanstech.ownerdriverapp.utils;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

import java.util.HashMap;

public class PusherManager {

    private static final String APP_KEY = "773419301d1ebabfbb90";
    private static final String PATH_AUTH = "http://khanstech.com/rts/pusher_auth.php";
    private static final String CHANNEL_PRESENCE = "presence-khanstech-channel";
    private static final String EVENT_1 = "custom_event";

    public PusherManager(String username) {
        HttpAuthorizer authorizer = new HttpAuthorizer(PATH_AUTH);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        authorizer.setQueryStringParameters(params);
        PusherOptions options = new PusherOptions();
        options.setAuthorizer(authorizer);
        options.setActivityTimeout(4000);
        options.setEncrypted(true);

        Pusher pusher = new Pusher(APP_KEY, options);
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
            }
        }, ConnectionState.ALL);

        // Subscribe to a channel
        Channel channel = pusher.subscribe(CHANNEL_PRESENCE);


        // Bind to listen for events sent to channel
        channel.bind(EVENT_1, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, String data) {
                System.out.println("Received event with data: " + data);
            }
        });

        // Disconnect from the service (or become disconnected my network conditions)
        pusher.disconnect();

        // Reconnect, with all channel subscriptions and event bindings automatically recreated
        pusher.connect();
        // The state change listener is notified when the connection has been re-established,
        // the subscription to "my-channel" and binding on "my-event" still exist.
    }
}
