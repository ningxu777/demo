package com.neil.demo.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neil on 2018/3/4.
 */
public class MongoService {

    private static MongoClient mongoClient;

//    private static String hosts = "taojin.simazb.com";
    private static String hosts = MyProperties.getByKey("mongohosts");
//    private static String ports = "27017";
    private static String ports = MyProperties.getByKey("mongoports");
    private static String username = "";
    private static String database = "";
    private static String password = "";

//    private static Map<String,Datastore> datastoreMap = new HashMap<String, Datastore>();
    private static Map<String,MongoDatabase> databaseMap = new HashMap<String, MongoDatabase>();


    static {
        String[] host = StringUtils.split(hosts, ",");
        String[] port = StringUtils.split(ports, ",");

        List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
        for (int i = 0; i < host.length; i++) {
            try {
                serverAddresses.add(new ServerAddress(host[i], Integer.parseInt(port[i])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
//        MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, password.toCharArray());
//        credentialList.add(credential);
//        mongoClient = new MongoClient(serverAddresses, credentialList);
        mongoClient = new MongoClient(serverAddresses);
    }

//    public static Datastore getDataStore(String database){
//        if(datastoreMap.get(database) != null){
//            return datastoreMap.get(database);
//        }
//        MorphiaLoggerFactory.reset();
//        MorphiaLoggerFactory.registerLogger(SLF4JLoggerImplFactory.class);
//        Morphia morphia = new Morphia();
//        Datastore datastore = morphia.createDatastore(mongoClient, database);
//        datastore.ensureIndexes(true);
//        datastoreMap.put(database,datastore);
//        return datastore;
//    }

    public static MongoDatabase getDatabase(String database){
        if(databaseMap.get(database) != null){
            return databaseMap.get(database);
        }
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);

        return mongoDatabase;
    }

}
