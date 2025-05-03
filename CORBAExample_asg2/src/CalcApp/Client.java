package CalcApp;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Calc calcImpl = CalcHelper.narrow(ncRef.resolve_str("Calc"));

            System.out.println("Result of 5 + 3 = " + calcImpl.add(5, 3));
            System.out.println("Result of 10 - 4 = " + calcImpl.sub(10, 4));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
