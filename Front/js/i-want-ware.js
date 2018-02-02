var apiPrefix = 'http://192.168.1.5:8080';
var userId = getCookie('userId');

createRequest(apiPrefix + '/api/user_wish/' + userId, {}, 'GET', function (response) {
    response = response ? JSON.parse(response) : [];

    console.log('response', response);

    var result = '';

    for (var i = 0; i < response.length; i++) {
        var ware = response[i];

        result += '<div class="title">' + 'Название: ' + '</div>' + '<div class="data">' + ware.name + '</div>'
            + '<div class="data">'+ '<div class="title">'+ 'Категория: ' + '</div>'+ ware.category + '</div>'
            + '<div class="data">'+ '<div class="title">'+ 'Статус: '+ '</div>' + ware.status + '</div>'
            + '<div class="data">'+ '<div class="title">' +'Срок эксплуатации: ' + '</div>' + ware.exploitation+ '</div>';

        document.querySelector('.getList').innerHTML = result;


    }

    // document.querySelector('.getList').innerHTML = result;
});

