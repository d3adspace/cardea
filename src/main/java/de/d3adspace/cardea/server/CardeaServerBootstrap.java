package de.d3adspace.cardea.server;

import de.d3adspace.cardea.server.backend.Backend;
import de.d3adspace.cardea.server.backend.BackendBalancingType;
import de.d3adspace.cardea.server.config.CardeaConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public class CardeaServerBootstrap {

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption("p", "port", true, "The port of the server.");
        options.addOption("b", "balancing", true, "The balancing strategy");
        options.addOption("s", "services", true, "The backend services.");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine;

        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            throw new Error("Failed to read CLI arguments. Can't start");
        }

        int port = Integer.parseInt(commandLine.getOptionValue("p", "8081"));
        String[] backends = commandLine.getOptionValue("s").split(",");

        System.out.println(commandLine.getOptionValue("b"));
        System.out.println(commandLine.getOptionValue("s"));
        System.out.println(commandLine.getOptionValue("p"));
        BackendBalancingType balancingType = BackendBalancingType.values()[Integer.parseInt(commandLine.getOptionValue("b"))];

        final int[] i = {0};
        CardeaConfig cardeaConfig = new CardeaConfig(port, Arrays.stream(backends).map(s -> {
            String[] split = s.split(":");
            return new Backend("backend-" + i[0]++, split[0], Integer.parseInt(split[1]));
        }).collect(Collectors.toList()), balancingType);

        CardeaServer cardeaServer = CardeaServerFactory.createCardeaServer(cardeaConfig);
        cardeaServer.start();
    }
}
