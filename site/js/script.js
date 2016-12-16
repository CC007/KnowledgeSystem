function init() {
    var form = document.getElementById("question");
    form.onsubmit = function (e) {
        // stop the regular form submission
        e.preventDefault();
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
            request.name = data["name"];
            request.type = data["type"];
            request.value = data["value"];
        }
        console.log("Request: " + JSON.stringify(request));
        // construct an HTTP request
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                console.log(xhr.responseText)
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
    };
}

function setQuestion(response) {
    console.log(response.knowledge.origin);
    clearNode();
    document.getElementById("question").appendChild(document.createTextNode(response.knowledge.question));
    document.getElementById("question").appendChild(document.createElement("br"));
    setInput("hidden", "id", response.id);
    setInput("hidden", "type", response.knowledge.type);
    setInput("hidden", "name", response.knowledge.name);
    if (response.knowledge.origin === "CHOICESELECTION") {
        for (var i = 0; i < response.knowledge.options.length; i++) {
            setInput("radio", "value", response.knowledge.options[i]);
            document.getElementById("question").appendChild(document.createTextNode(response.knowledge.options[i]));
            document.getElementById("question").appendChild(document.createElement("br"));
        }
        //setChoiceSelection(response.knowledge.options);

    }
    document.getElementById("question").appendChild(document.createElement("br"));
    setInput("submit", "Submit", "Volgende vraag");
    if (response.knowledge.tip) {
        document.getElementById("tipContent").appendChild(document.createTextNode(response.knowledge.tip));
    }
}

function setResult(response) {
    if (response.goal !== undefined) {
        console.log("Result:");
        clearNode();
        document.getElementById("question").appendChild(document.createTextNode(response.goal.goalResponse));
    }else{
        console.log("No result");
        clearNode();
        document.getElementById("question").appendChild(document.createTextNode("Het is onbekend of u in aanmerking komt. Contacteer een WMO consulent voor meer informatie."));
    }
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
function setInput(type, name, value = "") {
    var node = document.createElement("input");
    node.setAttribute("type", type);
    node.setAttribute("name", name);
    node.setAttribute("value", value);
    document.getElementById("question").appendChild(node);
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