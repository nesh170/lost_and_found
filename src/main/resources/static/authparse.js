var url = window.location;
var redirectString = "http://localhost:8080/loginsuccess";
if (!window.location.hash) {
    if (url.indexOf("localhost") != -1) {
        window.location = "http://localhost:8080/loginfailure";
    }
    else {
        window.location = "http://colab-sbx-122.oit.duke.edu:8080/loginfailure";
    }
} else {
    var hash = window.location.hash.substring(1); //get string of hash parameters
    var parameters = hash.split("&");
    var accesstokenParam;
    for (i = 0; i < parameters.length; i++) { //
        if (parameters[i].indexOf("access_token") != -1) {
            accesstokenParam = parameters[i];
        }
    }
    redirectString = redirectString.concat("?");
    redirectString = redirectString.concat(accesstokenParam);
    window.location = redirectString;
}

