package utilities.aws;

import utilities.PropertiesLoader;

import java.util.Properties;

public class AWSClient {
    protected final static Properties property = PropertiesLoader.loadPropertiesFromPackage("aws.properties");
}
