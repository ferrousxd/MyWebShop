import controllers.AuthorizationController;
import controllers.UserController;
import filters.CorsFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.MultiPartMediaTypes;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> hs = new HashSet<>();
        hs.add(MultiPartMediaTypes.class);
        hs.add(UserController.class);
        hs.add(CorsFilter.class);
        hs.add(AuthorizationController.class);
        return hs;
    }
}

