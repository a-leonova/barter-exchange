function createRequest(url, data, method, cb) {
    method = method || 'GET';
    var urlData = url.split('/');
    for (var i = 0; i < urlData.length; i++) {
        var step = urlData[i];
        if (step && typeof step === 'string' && step[0] === ':' && data) {
            urlData[i] = data[step.substr(1)];
        }
    }
    url = urlData.join('/');
    var xhr = new XMLHttpRequest();

    xhr.open(method, url, true);

    if (method === 'GET') {
        data = Object.keys(data).map(function (key) {
            return encodeURIComponent(key) + '=' + encodeURIComponent(data[key]);
        }).join('&');
    } else if (method === 'POST') {
        xhr.setRequestHeader("Content-Type", "application/json");
    }

    xhr.send(data);

    xhr.timeout = 30000;

    xhr.ontimeout = function () {
        alert('Проверьте связь с интернетом');
    };

    xhr.onreadystatechange = function () { // (3)
        if (xhr.readyState === 4 && xhr.responseText) {
            cb(xhr.responseText);
        }
    };
}

function getFormData(form) {
    var elements = form.querySelectorAll('input, textarea, select');
    var data = {};
    for (var i = 0; i < elements.length; i++) {
        var element = elements[i];

        data[element.name] = element.value;
    }

    return data;
}
function deleteCookie(name) {
    setCookie(name, "", {
        expires: -1
    })
}
// возвращает cookie с именем name, если есть, если нет, то undefined
function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
