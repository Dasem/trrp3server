import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class Main {



    public static void main(String[] args) throws IOException, InterruptedException {

        FileInputStream fileInputStream = new FileInputStream("src/main/resources/default.properties");
        Properties property = new Properties();
        property.load(fileInputStream);

        int port = Integer.parseInt(property.getProperty("port"));

        Server server = ServerBuilder.forPort(port)
                .addService(new CalculatorServiceImpl())
                .build();
        server.start();

        System.out.println(MessageFormat.format("Server successfully started on port : {0}", String.valueOf(port)));

        server.awaitTermination();
    }
}
