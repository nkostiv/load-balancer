package com.nkostiv;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nkostiv.loadbalancer.LoadBalancerService;
import com.nkostiv.loadbalancer.model.Host;
import com.nkostiv.loadbalancer.model.Request;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final String LOAD_BALANCER_VARIANT = "lbvariant";

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(prepareOptions(), args);
        String lbAlgorithm = commandLine.getOptionValue(LOAD_BALANCER_VARIANT);

        List<Host> hostList = createHostList();
        logger.debug("Hosts: {}", hostList);

        LoadBalancerService loadBalancerService = new LoadBalancerService(hostList, lbAlgorithm);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            logger.info("Please write request body and press enter. To finish type \"end\" command");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            loadBalancerService.handleRequst(new Request(input));
        }
    }

    private static List<Host> createHostList() {
        Random random = new Random();
        return Stream.of(
                new Host("First", (float) Math.round(random.nextFloat() * 100) / 100),
                new Host("Second", (float) Math.round(random.nextFloat() * 100) / 100),
                new Host("Third", (float) Math.round(random.nextFloat() * 100) / 100)
        ).collect(Collectors.toList());
    }

    private static Options prepareOptions() {
        Option lbAlgorithm = Option.builder().required()
                .longOpt(LOAD_BALANCER_VARIANT)
                .hasArg()
                .desc("Specify load balancer algorithm variant")
                .build();

        Options options = new Options();
        options.addOption(lbAlgorithm);
        return options;
    }

}
