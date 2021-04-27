package dev.enderoffice.twitch;

import org.bson.Document;

public class TwitchConnection {

    MongoManager mm = new MongoManager("mongodb://localhost:27017", "SoodBot", "twitch");

    public TwitchConnection() {
    }

    public boolean addTwitchVerification(String twitchName, String discordID, String discordName) {
        boolean result = false;
        if(!reqExists(twitchName)) {
            Document ticket = new Document("_id", twitchName.toLowerCase())
                    .append("discordID", discordID)
                    .append("memberName", discordName)
                    .append("status", "none")
                    .append("creationMillis", String.valueOf(System.currentTimeMillis()));
            result = mm.insertOneDocument(ticket);
        }
        return result;
    }

    public String getRequestStatus(String twitchName) {
        return mm.findRequestStatus(twitchName);
    }

    public void updateRequestStatus(String twitchName) {
        mm.updateRequestStatus(twitchName);
    }

    public boolean discordIDHasConnection(String discordID) {
        boolean result = false;
        result = mm.checkForUserID("discordID", discordID);
        return result;
    }

    public boolean removeRequest(String twitchName) {
        boolean result = mm.deleteDocumentByID("_id", twitchName);
        return result;
    }

    public boolean reqExists(String twitchName) {
        boolean result;
        result = mm.checkIfDocumentExists("_id", twitchName);
        return result;
    }

}