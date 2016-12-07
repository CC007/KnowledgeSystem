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
import com.github.cc007.knowledgesystem.model.knowledge.items.FloatingPointItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.IntegerItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.KnowledgeItem;
import com.github.cc007.knowledgesystem.model.knowledge.items.StringItem;
import com.github.cc007.knowledgesystem.model.rules.RuleBase;
import com.github.cc007.knowledgesystem.model.rules.RuleBuilder;
import com.github.cc007.knowledgesystem.model.rules.conditions.Condition;
import com.github.cc007.knowledgesystem.model.rules.conditions.EqualityCondition;
import com.github.cc007.knowledgesystem.model.rules.conditions.ValueCondition;
import com.github.cc007.knowledgesystem.model.rules.conditions.ValueOperator;
import java.util.Arrays;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class HardCodedModelLoader extends ModelLoader {

    @Override
    public void load(RuleBase ruleBase, KnowledgeBase knowledgeBase) {
        //fill the knowledge base with the variables
        knowledgeBase.setItem(new ChoiceSelectionItem("baan", Arrays.asList("Ja", "Nee"), KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Heeft u een betaalde baan, waarbij uw problemen in de weg komen te staan?"));
        knowledgeBase.setItem(new ChoiceSelectionItem("leerling", Arrays.asList("Ja", "Nee"), KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Gaat het om een minderjarige die naar school gaat?"));
        knowledgeBase.setItem(new ChoiceSelectionItem("lzIndicatie", Arrays.asList("Ja", "Nee"), KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Heeft u een positieve indicatie ontvangen onder de Wet Langdurige Zorg?"));
        knowledgeBase.setItem(new ChoiceSelectionItem("dekking", Arrays.asList("Ja", "Nee"), KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Kan de compensatie gedekt worden door de zorgverzekering?"));
        knowledgeBase.setItem(new BooleanItem("wetten", KnowledgeOrigin.INFERRED, false));
        knowledgeBase.setItem(new ChoiceSelectionItem("voorzieningen", Arrays.asList("Scootmobiel pool", "Rolstoel verhuur", "Wandelvrijwilligers", "Deeltaxi"), KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Selecteer uit de onderstaande lijst wijk voorzieningen waarvan u denkt ondersteuning te kunnen gebruiken."));
        knowledgeBase.setItem(new ChoiceSelectionItem("algemeenGebruik", Arrays.asList("Scootmobiel", "Reguliere rolstoel", "Krukken"), KnowledgeOrigin.CHOICESELECTION, false).setQuestion("Selecteer uit de onderstaande lijst van hulpmiddelen, welke u denkt te kunnen gebruiken."));
        knowledgeBase.setItem(new BooleanItem("WMO", KnowledgeOrigin.INFERRED, false));
        
        knowledgeBase.setItem(new BooleanItem("UWV", KnowledgeOrigin.INFERRED, true).setGoalResponse("Ga naar UWV voor een compensatie."));
        knowledgeBase.setItem(new BooleanItem("leerlingwet", KnowledgeOrigin.INFERRED, true).setGoalResponse("Ga naar leerlingenwet voor een compensatie."));
        knowledgeBase.setItem(new BooleanItem("WLZ", KnowledgeOrigin.INFERRED, true).setGoalResponse("Ga naar WLZ voor een compensatie."));
        knowledgeBase.setItem(new BooleanItem("zorgverzekering", KnowledgeOrigin.INFERRED, true).setGoalResponse("Ga naar uw zorgverzekering voor een compensatie."));
                
                
        //fill the rule base with the rules
        Condition heeftBaan = new EqualityCondition("baan", "Ja", true);
        Condition UWVnietTrue = new EqualityCondition("UWV", true, false);
        KnowledgeItem onderUWV = knowledgeBase.getItem("UWV").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(onderUWV).addCondition(heeftBaan).addCondition(UWVnietTrue).build());
        
        Condition isLeerling = new EqualityCondition("leerling", "Ja", true);
        Condition leerlingwetNietTrue = new EqualityCondition("leerlingwet", true, false);
        KnowledgeItem onderLeerlingwet = knowledgeBase.getItem("leerlingwet").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(onderLeerlingwet).addCondition(isLeerling).addCondition(leerlingwetNietTrue).build());
        
        Condition heeftIndicatie = new EqualityCondition("lzIndicatie", "Ja", true);
        Condition WLZnietTrue = new EqualityCondition("WLZ", true, false);
        KnowledgeItem onderWLZ = knowledgeBase.getItem("WLZ").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(onderWLZ).addCondition(heeftIndicatie).addCondition(WLZnietTrue).build());
        
        Condition heeftDekking = new EqualityCondition("dekking", "Ja", true);
        Condition zorgverzekeringNietTrue = new EqualityCondition("zorgverzekering", true, false);
        KnowledgeItem onderZorgverzekering = knowledgeBase.getItem("zorgverzekering").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(onderZorgverzekering).addCondition(heeftDekking).addCondition(zorgverzekeringNietTrue).build());
        
        Condition heeftGeenBaan = new EqualityCondition("baan", "Nee", true);
        Condition isGeenLeerling = new EqualityCondition("leerling", "Nee", true);
        Condition heeftGeenIndicatie = new EqualityCondition("lzIndicatie", "Nee", true);
        Condition heeftGeenDekking = new EqualityCondition("dekking", "Nee", true);
        Condition wettenNietTrue = new EqualityCondition("wetten", true, false);
        KnowledgeItem onderWetten = knowledgeBase.getItem("wetten").copy().setValue(false);
        ruleBase.addRule(new RuleBuilder(onderWetten).addCondition(heeftGeenBaan).addCondition(isGeenLeerling).addCondition(heeftGeenIndicatie).addCondition(heeftGeenDekking).addCondition(wettenNietTrue).build());
        
        Condition valtOnderWetten = new EqualityCondition("wetten", false);
        Condition scootmobielPool = new EqualityCondition("voorzieningen", "Scootmobiel pool", false);
        Condition rolstoelVerhuur = new EqualityCondition("voorzieningen", "Rolstoel verhuur", false);
        Condition wandelvrijwilligers = new EqualityCondition("voorzieningen", "Wandelvrijwilligers", false);        
        Condition onderAlgemeenGebruik = new EqualityCondition("algemeenGebruik", "Scootmobiel", false);
        Condition WMOnietTrue = new EqualityCondition("WMO", true, false);
        KnowledgeItem WMO = knowledgeBase.getItem("WMO").copy().setValue(true);
        ruleBase.addRule(new RuleBuilder(WMO).addCondition(valtOnderWetten).addCondition(scootmobielPool).addCondition(rolstoelVerhuur).addCondition(wandelvrijwilligers).addCondition(onderAlgemeenGebruik).addCondition(WMOnietTrue).build());
         
        
        
    }
}