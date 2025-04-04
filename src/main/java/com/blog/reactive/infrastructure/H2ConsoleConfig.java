package com.blog.reactive.infrastructure;

import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;

@Configuration
// the profile annotation allows one to control in which environment your code will run, dev, prod, test
//@Profile({"dev", "test"})
@Profile("!prod & !production")
public class H2ConsoleConfig {
    private Server webServer;

    // create a method that starts the web server immediately after the application starts
    @EventListener(ApplicationStartedEvent.class)
    // the method will create a web server on that port, and it will be started immediately the app starts
    public void startH2Console() throws SQLException {
        //    h2 is only useful in testing and development not in production
        // define the port number which you want the h2 console to be accessible.
        String WEB_PORT = "8082";
        this.webServer = Server.createWebServer("webPort", WEB_PORT).start();
    }

    // create an event listener to stop the web server when the application is closed
    @EventListener(ContextClosedEvent.class)
    public void stop() {
        this.webServer.stop();
    }

}
