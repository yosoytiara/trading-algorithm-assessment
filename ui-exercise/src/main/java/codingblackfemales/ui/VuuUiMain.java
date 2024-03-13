package codingblackfemales.ui;

import codingblackfemales.ui.module.MyModule;
import org.finos.toolbox.jmx.MetricsProvider;
import org.finos.toolbox.jmx.MetricsProviderImpl;
import org.finos.toolbox.lifecycle.LifecycleContainer;
import org.finos.toolbox.time.Clock;
import org.finos.toolbox.time.DefaultClock;
import org.finos.vuu.core.*;
import org.finos.vuu.core.module.TableDefContainer;
import org.finos.vuu.core.module.ViewServerModule;
import org.finos.vuu.net.AlwaysHappyLoginValidator;
import org.finos.vuu.net.Authenticator;
import org.finos.vuu.net.LoggedInTokenValidator;
import org.finos.vuu.net.auth.AlwaysHappyAuthenticator;
import org.finos.vuu.net.http.VuuHttp2ServerOptions;
import org.finos.vuu.plugin.Plugin;
import org.finos.vuu.state.MemoryBackedVuiStateStore;
import org.finos.vuu.state.VuiStateStore;
import scala.Option;

public class VuuUiMain {

    public static void main(String[] args){

    final MetricsProvider metrics = new MetricsProviderImpl();
    final Clock clock = new DefaultClock();
    final LifecycleContainer lifecycle = new LifecycleContainer(clock);
    final TableDefContainer tableDefContainer = new TableDefContainer();

    final VuiStateStore store = new MemoryBackedVuiStateStore(100);

    lifecycle.autoShutdownHook();

    final Authenticator authenticator = new AlwaysHappyAuthenticator();
    final LoggedInTokenValidator loginTokenValidator = new LoggedInTokenValidator();

    //final String webRoot = "";
    final String certPath = "ui-exercise/src/main/resources/certs/cert.pem";
    final String keyPath = "ui-exercise/src/main/resources/certs/key.pem";

    final VuuServerConfig config = new VuuServerConfig(
            VuuHttp2ServerOptions.apply()
                    //.withWebRoot(webRoot)
                    //if we specify a web root, it means we will serve the files from a directory on the file system
                    //if we don't the files will be served from the vuu-ui jar directly.
                    //.withWebRoot("/Users/chris/Code/vuu/vuu-ui/deployed_apps/app-vuu-example")
                    .withSsl(certPath, keyPath)
                    .withDirectoryListings(true)
                    .withPort(8443),
            VuuWebSocketOptions.apply()
                    .withUri("websocket")
                    .withWsPort(8090)
                    .withWss(certPath, keyPath, Option.apply(null))
                    .withBindAddress("0.0.0.0"),
            VuuSecurityOptions.apply()
                    .withAuthenticator(authenticator)
                    .withLoginValidator(new AlwaysHappyLoginValidator()),
            VuuThreadingOptions.apply()
                    .withTreeThreads(4)
                    .withViewPortThreads(4),
            new scala.collection.mutable.ListBuffer<ViewServerModule>().toList(),
            new scala.collection.mutable.ListBuffer<Plugin>().toList()
    ).withModule(new MyModule().create(tableDefContainer))       ;

    final VuuServer vuuServer = new VuuServer(config, lifecycle, clock, metrics);

    lifecycle.start();

    vuuServer.join();
    }
}
