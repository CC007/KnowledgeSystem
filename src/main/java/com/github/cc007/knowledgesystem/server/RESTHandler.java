/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.server;

import com.github.cc007.knowledgesystem.model.knowledge.items.ChoiceSelectionItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.MultipleChoiceSelectionItem;
import com.github.cc007.knowledgesystem.view.RESTView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static spark.Spark.*;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class RESTHandler implements Runnable {

    private final Map<Integer, RESTView> views;
    private int idCounter;
    private int responseIdCounter;

    public RESTHandler() {
        this.views = new HashMap<>();
        this.idCounter = 0;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        options("/rest", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Method", "POST");
            response.header("Access-Control-Allow-Headers", "Content-Type");
            return "";
        });
        post("/rest", "application/json", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Method", "POST");
            response.header("Access-Control-Allow-Headers", "Content-Type");
            JsonElement data = parser.parse(request.body());
            int id;
            RESTView view;

            //Determine the type of request (first visit, inquiry response, stops server)
            if (data != null && data.isJsonObject()) {
                if (data.getAsJsonObject().has("stop")) {
                    // stop the server
                    stop();
                    return new Object() {
                        private final boolean stopped = true;
                    };
                } else if (data.getAsJsonObject().has("id")) {
                    // inquiry response
                    ResponseMessage respMsg = new Gson().fromJson(data, ResponseMessage.class);
                    id = respMsg.getId();
                    view = views.get(id);
                    String name = respMsg.getName();
                    Object value = respMsg.getValue();
                    if (view.getKnowledge().getName().equals(name)) {
                        try {
                            switch (respMsg.getType()) {
                                case "index":
                                    view.getKnowledge().setValue(((ChoiceSelectionItem) view.getKnowledge()).getIndex((String) value));
                                    break;
                                case "indexlist":
                                    view.getKnowledge().setValue(((MultipleChoiceSelectionItem) view.getKnowledge()).getIndices((List<String>) value));
                                    break;
                                case "integer":
                                    view.getKnowledge().setValue(Integer.parseInt((String) value));
                                    break;
                                case "decimal":
                                    view.getKnowledge().setValue(Double.parseDouble((String) value));
                                    break;
                                case "boolean":
                                    view.getKnowledge().setValue(Boolean.parseBoolean((String) value));
                                    break;
                                default:
                                    view.getKnowledge().setValue(value);
                            }
                        } catch (Exception e) {
                            Logger.getLogger(RESTHandler.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                    view.setNewRestHandlerData(true);
                } else {
                    // first visit
                    id = getNewId();
                    Session newSession = new Session(this);
                    Thread t = new Thread(newSession);
                    t.start();
                    view = newSession.getView();
                    views.put(id, view);
                }
            } else {
                // unknown request, send error back
                return new Object() {
                    private final String error = "Unknown request";
                };
            }

            //wait for next inquiry or result
            synchronized (view.knowledgeSystemLock) {
                while (!view.hasNewKnowledgeSystemData()) {
                    try {
                        view.knowledgeSystemLock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RESTHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            //prevent sending the same inquiry twice
            view.setNewKnowledgeSystemData(false);
            response.header("Content-Type", "application/json; charset=UTF-8");
            Map<String, Object> retVal = new HashMap<>();
            retVal.put("id", id);
            retVal.put("respId", getNewResponseId());
            if (!view.isGoalReached()) {
                //case inquiry
                retVal.put("type", "inquiry");
                retVal.put("knowledge", view.getKnowledge());
            } else {
                //case result
                retVal.put("type", "result");
                retVal.put("goal", view.getKnowledge());
                retVal.put("context", view.getContext());
            }
            return retVal;
        }, gson::toJson);
    }

    public int getNewId() {
        idCounter++;
        return idCounter - 1;
    }

    public int getNewResponseId() {
        responseIdCounter++;
        return responseIdCounter - 1;
    }

}
