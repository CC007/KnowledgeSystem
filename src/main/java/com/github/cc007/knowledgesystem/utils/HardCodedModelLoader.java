/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.knowledgesystem.utils;

import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeBase;
import com.github.cc007.knowledgesystem.model.knowledge.KnowledgeOrigin;
import com.github.cc007.knowledgesystem.model.knowledge.items.BooleanItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.ChoiceSelectionItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBuilder;
import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import com.github.cc007.knowledgesystem.model.rules.conditions.EqualityCondition;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class HardCodedModelLoader extends ModelLoader {

    @Override
    public void load(RuleBase ruleBase, KnowledgeBase knowledgeBase) {
        // prepare questionlists
        List jaNeeList = Arrays.asList("Ja", "Nee");
        List voorzieningenList = Arrays.asList("Scootmobiel pool", "Rolstoel verhuur", "Wandelvrijwilligers", "Deeltaxi");
        List algemeenGebruikList = Arrays.asList("Scootmobiel", "Reguliere rolstoel", "Krukken");

        //fill the knowledge base with the variables
        knowledgeBase.setItem(new ChoiceSelectionItem("baan", jaNeeList, KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Heeft u een betaalde baan, waarbij uw problemen in de weg komen te staan?"));
        knowledgeBase.setItem(new ChoiceSelectionItem("leerling", jaNeeList, KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Gaat het om een minderjarige die naar school gaat?").setTip("Wil je een schoolbus?"));
        knowledgeBase.setItem(new ChoiceSelectionItem("lzIndicatie", jaNeeList, KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Heeft u een positieve indicatie ontvangen onder de Wet Langdurige Zorg?"));
        knowledgeBase.setItem(new ChoiceSelectionItem("dekking", jaNeeList, KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Kan de compensatie gedekt worden door de zorgverzekering?"));
        knowledgeBase.setItem(new BooleanItem("wetten", KnowledgeOrigin.INFERRED, false));
        knowledgeBase.setItem(new ChoiceSelectionItem("voorzieningen", voorzieningenList, KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Selecteer uit de onderstaande lijst wijk voorzieningen waarvan u denkt ondersteuning te kunnen gebruiken."));
        knowledgeBase.setItem(new ChoiceSelectionItem("algemeenGebruik", algemeenGebruikList, KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Selecteer uit de onderstaande lijst van hulpmiddelen, welke u denkt te kunnen gebruiken."));
        knowledgeBase.setItem(new BooleanItem("WMO", KnowledgeOrigin.INFERRED, true).setGoalResponse("U komt waarschijnlijk in aanmerking voor compensatie door de WMO. Neem contact op met de WMO consulent voor meer informatie."));

        knowledgeBase.setItem(new BooleanItem("UWV", KnowledgeOrigin.INFERRED, true).setGoalResponse("Ga naar UWV voor een compensatie."));
        knowledgeBase.setItem(new BooleanItem("leerlingwet", KnowledgeOrigin.INFERRED, true).setGoalResponse("Ga naar leerlingenwet voor een compensatie."));
        knowledgeBase.setItem(new BooleanItem("WLZ", KnowledgeOrigin.INFERRED, true).setGoalResponse("Ga naar WLZ voor een compensatie."));
        knowledgeBase.setItem(new BooleanItem("zorgverzekering", KnowledgeOrigin.INFERRED, true).setGoalResponse("Ga naar uw zorgverzekering voor een compensatie."));

        //fill the rule base with the rules
        
        //rule 0
        Condition heeftBaan = new EqualityCondition("baan", jaNeeList.indexOf("Ja"), true);
        Condition uwvNietTrue = new EqualityCondition("UWV", true, false);
        KnowledgeItem onderUWV = knowledgeBase.getItem("UWV").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(onderUWV).addCondition(heeftBaan).addCondition(uwvNietTrue).build());

        //rule 1
        Condition isLeerling = new EqualityCondition("leerling", jaNeeList.indexOf("Ja"), true);
        Condition leerlingwetNietTrue = new EqualityCondition("leerlingwet", true, false);
        KnowledgeItem onderLeerlingwet = knowledgeBase.getItem("leerlingwet").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(onderLeerlingwet).addCondition(isLeerling).addCondition(leerlingwetNietTrue).build());

        //rule 2
        Condition heeftIndicatie = new EqualityCondition("lzIndicatie", jaNeeList.indexOf("Ja"), true);
        Condition wlzNietTrue = new EqualityCondition("WLZ", true, false);
        KnowledgeItem onderWLZ = knowledgeBase.getItem("WLZ").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(onderWLZ).addCondition(heeftIndicatie).addCondition(wlzNietTrue).build());

        //rule 3
        Condition heeftDekking = new EqualityCondition("dekking", jaNeeList.indexOf("Ja"), true);
        Condition zorgverzekeringNietTrue = new EqualityCondition("zorgverzekering", true, false);
        KnowledgeItem onderZorgverzekering = knowledgeBase.getItem("zorgverzekering").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(onderZorgverzekering).addCondition(heeftDekking).addCondition(zorgverzekeringNietTrue).build());

        //rule 4
        Condition heeftGeenBaan = new EqualityCondition("baan", jaNeeList.indexOf("Nee"), true);
        Condition isGeenLeerling = new EqualityCondition("leerling", jaNeeList.indexOf("Nee"), true);
        Condition heeftGeenIndicatie = new EqualityCondition("lzIndicatie", jaNeeList.indexOf("Nee"), true);
        Condition heeftGeenDekking = new EqualityCondition("dekking", jaNeeList.indexOf("Nee"), true);
        Condition wettenNietTrue = new EqualityCondition("wetten", false, false);
        KnowledgeItem onderWetten = knowledgeBase.getItem("wetten").copy().setValue(false);
        ruleBase.addRule(new RuleBuilder(onderWetten).addCondition(heeftGeenBaan).addCondition(isGeenLeerling).addCondition(heeftGeenIndicatie).addCondition(heeftGeenDekking).addCondition(wettenNietTrue).build());

        //rule 5
        Condition valtOnderWetten = new EqualityCondition("wetten", false);
        Condition scootmobielPool = new EqualityCondition("voorzieningen", voorzieningenList.indexOf("Scootmobiel pool"), false);
        Condition rolstoelVerhuur = new EqualityCondition("voorzieningen", voorzieningenList.indexOf("Rolstoel verhuur"), false);
        Condition wandelvrijwilligers = new EqualityCondition("voorzieningen", voorzieningenList.indexOf("Wandelvrijwilligers"), false);
        Condition algemeenGebruik = new EqualityCondition("algemeenGebruik", algemeenGebruikList.indexOf("Scootmobiel"), false);
        Condition wmoNietTrue = new EqualityCondition("WMO", true, false);
        KnowledgeItem WMO = knowledgeBase.getItem("WMO").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(WMO).addCondition(valtOnderWetten).addCondition(scootmobielPool).addCondition(rolstoelVerhuur).addCondition(wandelvrijwilligers).addCondition(algemeenGebruik).addCondition(wmoNietTrue).build());

    }
}
