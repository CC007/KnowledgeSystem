function init() {
    var form = document.getElementById("question");
    form.onsubmit = function (e) {
        // stop the regular form submission
        e.preventDefault();
    };
}

function handleSubmit(submitButtonName) {
    var form = document.getElementById("question");
    // collect the form data while iterating over the inputs
    var data = {};
    for (var i = 0, ii = form.length; i < ii; ++i) {
        var input = form[i];
        if (input.name) {
            if (input.type !== "radio" || input.checked) {
                data[input.name] = input.value;
            }
        }
    }
    console.log(JSON.stringify(data));
    var request = new Object();
    if (data["id"] !== undefined) {
        request.id = data["id"];
        if (submitButtonName == "Vorige") {
            request.back = true;
        } else {
            request.name = data["name"];
            request.type = data["type"];
            request.value = data["value"];
        }
    }
    console.log("Request: " + JSON.stringify(request));
    // construct an HTTP request
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            console.log(xhr.responseText);
            clearNode();
            if (xhr.responseText == "<html><body><h2>500 Internal Error</h2></body></html>") {
                document.getElementById("question").appendChild(document.createTextNode("An error occured on the server side"));
                return;
            }
            var response = JSON.parse(xhr.responseText);
            if (response.type === "inquiry") {
                setQuestion(response);
            } else {
                setResult(response);
            }
        }
    };
    xhr.open(form.method, form.action, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

    // send the collected data as JSON
    xhr.send(JSON.stringify(request));

    xhr.onloadend = function () {
        //Done
    };
}

function setQuestion(response) {
    console.log(response.knowledge.origin);
    document.getElementById("question").appendChild(document.createTextNode(response.knowledge.question));
    document.getElementById("question").appendChild(document.createElement("br"));
    setInput("hidden", "id", response.id);
    setInput("hidden", "type", response.knowledge.type);
    setInput("hidden", "name", response.knowledge.name);
    if (response.knowledge.origin === "CHOICESELECTION") {
        for (var i = 0; i < response.knowledge.options.length; i++) {
            node = setInput("radio", "value", response.knowledge.options[i]);
            node.setAttribute("id", response.knowledge.options[i]);
            if (i == 0) {
                node.setAttribute("checked", "checked");
            }
            var label = document.createElement("label");
            label.appendChild(document.createTextNode(response.knowledge.options[i]));
            label.setAttribute("for", response.knowledge.options[i]);
            document.getElementById("question").appendChild(label);
            document.getElementById("question").appendChild(document.createElement("br"));
        }
        //setChoiceSelection(response.knowledge.options);
    }
    if (response.knowledge.origin === "MULTIPLECHOICESELECTION") {
        for (var i = 0; i < response.knowledge.options.length; i++) {
            node = setInput("checkbox", "value", response.knowledge.options[i]);
            node.setAttribute("id", response.knowledge.options[i]);
            if (i == 0) {
                node.setAttribute("checked", "checked");
            }
            var label = document.createElement("label");
            label.appendChild(document.createTextNode(response.knowledge.options[i]));
            label.setAttribute("for", response.knowledge.options[i]);
            document.getElementById("question").appendChild(label);
            document.getElementById("question").appendChild(document.createElement("br"));
        }
        //setChoiceSelection(response.knowledge.options);
    }
    document.getElementById("question").appendChild(document.createElement("br"));
    node = setInput("submit", "Submit1", "Vorige");
    node.setAttribute("onclick", "handleSubmit(\"Vorige\");");
    node = setInput("submit", "Submit2", "Volgende");
    node.setAttribute("onclick", "handleSubmit(\"Volgende\");");
    if (response.knowledge.tip) {
        document.getElementById("tipContent").appendChild(document.createTextNode(response.knowledge.tip));
    }
}

function setResult(response) {
    if (response.goal !== undefined) {
        console.log("Result:");
        document.getElementById("question").appendChild(document.createTextNode(response.goal.goalResponse));
    } else {
        console.log("No result");
        document.getElementById("question").appendChild(document.createTextNode("Het is onbekend of u in aanmerking komt. Contacteer een WMO consulent voor meer informatie."));
    }
    document.getElementById("question").appendChild(document.createElement("br"));
    node = setInput("submit", "Restart", "Terug naar begin");
    node.setAttribute("onclick","handleSubmit(\"Restart\");")
}

function clearNode() {
    var node = document.getElementById("question");
    while (node.firstChild) {
        node.removeChild(node.firstChild);
    }
    var tip = document.getElementById("tipContent");
    if (tip.firstChild) {
        tip.removeChild(tip.firstChild);
    }
}

function setInput(type, name, value) {
    var node = document.createElement("input");
    node.setAttribute("type", type);
    node.setAttribute("name", name);
    node.setAttribute("value", value);
    document.getElementById("question").appendChild(node);
    return node;
}

function setChoiceSelection(options) {
    var node = document.createElement("select");
    node.setAttribute("name", "value");
    for (var i = 0; i < options.length; i++) {
        var optionNode = document.createElement("option");
        node.setAttribute("value", i);
        var textNode = document.createTextNode(options[i]);
        optionNode.appendChild(textNode);
        node.appendChild(optionNode);
    }
    document.getElementById("question").appendChild(node);
}