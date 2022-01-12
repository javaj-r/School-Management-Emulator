package data.user;

import constant.Permit;
import model.User;

public class Root extends User {

    private Root() {
    }

    private static class Singleton {
        private static final Root INSTANCE = new Root();
        static {
            INSTANCE.setId(0);
            INSTANCE.setUsername("admin");
            INSTANCE.setPassword("admin");
            INSTANCE.addGrants(Permit.CREATE, Permit.READ, Permit.UPDATE, Permit.DELETE);
        }
    }

    public static Root getInstance() {
        return Singleton.INSTANCE;
    }


}
