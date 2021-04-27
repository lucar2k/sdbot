package dev.enderoffice.twitch;

import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import java.util.ArrayList;

public class MongoManager {

    private final String connID;
    private final String dbName;
    private final String collectionName;

    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;
    private final MongoDatabase database;

    public MongoManager(String connID, String dbName, String collectionName) {
        this.connID = connID;
        this.dbName = dbName;
        this.collectionName = collectionName;
        this.mongoClient = new MongoClient(new MongoClientURI(connID));
        this.database = mongoClient.getDatabase(dbName);
        this.collection = database.getCollection(collectionName);
    }

    public boolean insertOneDocument(Document doc) {
        boolean result = false;
        try {
            collection.insertOne(doc);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean checkForTwitchName(String twitchName) {
        boolean out = false;
        if(checkIfDocumentExists("_id", twitchName)) {
            out = true;
        }
        return out;
    }

    public boolean checkForUserID(String field, String value) {
        boolean result = false;
        result = checkIfDocumentExists(field, value) == true;
        return result;
    }

    public String findRequestStatus(String twitchName) {
        BsonDocument query = new BsonDocument().append("_id", new BsonString(twitchName));
        FindIterable<Document> cursor = collection.find(query);
        Document jo = cursor.iterator().next();
        return jo.get("status").toString();
    }

    public void updateRequestStatus(String twitchName) {
        collection.updateOne(Filters.eq("_id", twitchName), Updates.set("status", "connected"));
    }

    public boolean deleteDocumentByID(String field, String value) {
        boolean result = false;
        BasicDBObject theQuery = new BasicDBObject();
        theQuery.put(field, value);
        if(checkIfDocumentExists(field, value)) {
            DeleteResult res = collection.deleteMany(theQuery);
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public boolean checkIfDocumentExists(String field, String value) {
        boolean result = false;
        result = collection.find(Filters.eq(field, value)).first() != null;
        return result;
    }

    public ArrayList<String> getAllUsersNonConnected() {
        ArrayList<String> liste = new ArrayList<>();
        MongoCollection<Document> coll = database.getCollection("twitch");
        FindIterable<Document> iterable = coll.find();
        MongoCursor<Document> cursor = iterable.iterator();
        try {
            while(cursor.hasNext()) {
                Document jo = cursor.next();
                if(jo.get("status").toString().equalsIgnoreCase("none")) {
                    liste.add(jo.get("_id").toString());
                }
            }
        } finally {
            cursor.close();
        }
        return liste;
    }

    public ArrayList<String> getAllUsersConnected() {
        ArrayList<String> liste = new ArrayList<>();
        MongoCollection<Document> coll = database.getCollection("twitch");
        FindIterable<Document> iterable = coll.find();
        MongoCursor<Document> cursor = iterable.iterator();
        try {
            while(cursor.hasNext()) {
                Document jo = cursor.next();
                if(jo.get("status").toString().equalsIgnoreCase("connected")) {
                    liste.add(jo.get("_id").toString());
                }
            }
        } finally {
            cursor.close();
        }
        return liste;
    }

    public Long getUsersRequestTimeMillis(String twitchName) {
        BsonDocument query = new BsonDocument().append("_id", new BsonString(twitchName));
        FindIterable<Document> cursor = collection.find(query);
        Document jo = cursor.iterator().next();
        Long out = Long.valueOf(jo.get("creationMillis").toString());
        return out;
    }

    public Long getUsersDiscordID(String twitchName) {
        BsonDocument query = new BsonDocument().append("_id", new BsonString(twitchName));
        FindIterable<Document> cursor = collection.find(query);
        Document jo = cursor.iterator().next();
        Long out = Long.valueOf(jo.get("discordID").toString());
        return out;
    }

    public void createCollection() {
        database.createCollection("twitch");
    }

    public boolean collectionExists(final String collectionName) {
        boolean out = false;
        MongoIterable<String> liste = database.listCollectionNames();
        for(String name : liste) {
            out = name.equalsIgnoreCase(collectionName);
        }
        return out;
    }

}
