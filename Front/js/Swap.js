var apiPrefix = 'http://192.168.1.5:8080';
var userId = getCookie('userId');

function getParameterByName(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}
console.log('response', 1);
if (getParameterByName('simple') === '1')
{
    console.log('response', 2);
    createRequest(apiPrefix + '/api/exchanges/find-simple?userId='+userId+'&id='+getParameterByName('wishId'), {}, 'GET', function(response) {
        response = response ? JSON.parse(response) : [];

        console.log('response', 3);
        console.log('response', response);


        var result = '';

        for (var i = 0; i < response.length; i++) {
            var ex = response[i];

            result += '--------------------------------------------------------------------------------------------------------------------------------------------------------------------'
                + '<br>' + '<div class="title">' + 'От кого: ' + '</div>' + '<div class="data">' + ex.firstUser.name + '</div>'
                + '<br>' + '<div class="title">' + 'Название: ' + '</div>' + '<div class="data">' + ex.firstWare.name + '</div>'
                + '<br>' + '<div class="data">'+ '<div class="title">'+ 'Категория: ' + '</div>'+ ex.firstWare.category + '</div>'
                + '<br>' + '<div class="data">'+ '<div class="title">'+ 'Статус: '+ '</div>' + ex.firstWare.status + '</div>'
                + '<br>' + '<div class="data">'+ '<div class="title">' +'Срок эксплуатации: ' + '</div>' + ex.firstWare.exploitation+ '</div>'
                + '<br>' + '<div class="title">' + 'Кому: ' + '</div>' + '<div class="data">' + ex.secondUser.name + '</div>'
                + '<br>' + '<div class="title">' + 'Название: ' + '</div>' + '<div class="data">' + ex.secondWare.name + '</div>'
                + '<br>' + '<div class="data">'+ '<div class="title">'+ 'Категория: ' + '</div>'+ ex.secondWare.category + '</div>'
                + '<br>' + '<div class="data">'+ '<div class="title">'+ 'Статус: '+ '</div>' + ex.secondWare.status + '</div>'
                + '<br>' + '<div class="data">'+ '<div class="title">' +'Срок эксплуатации: ' + '</div>' + ex.secondWare.exploitation+ '</div>'
                + '<br>'+'--------------------------------------------------------------------------------------------------------------------------------------------------------------------';




        }

        document.querySelector('.getList').innerHTML = result;
    });
}
else{
    createRequest(apiPrefix + '/api/exchanges/find-chain?userId='+userId+'&id='+getParameterByName('wishId'), {}, 'GET', function(response) {
        response = response ? JSON.parse(response) : [];

        console.log('response', response);

        var result = '';

        for (var i = 0; i < response.length; i++) {
            var ware = response[i];

            result += '--------------------------------------------------------------------------------------------------------------------------------------------------------------------'
                + '<div class="title">' + 'Название: ' + '</div>' + '<div class="data">' + ware.name + '</div>'
                + '<br>' + '<div class="data">'+ '<div class="title">'+ 'Категория: ' + '</div>'+ ware.category + '</div>'
                + '<br>'   + '<div class="data">'+ '<div class="title">'+ 'Статус: '+ '</div>' + ware.status + '</div>'
                + '<br>'   + '<div class="data">'+ '<div class="title">' +'Срок эксплуатации: ' + '</div>' + ware.exploitation+ '</div>'
                + '<br>'+'--------------------------------------------------------------------------------------------------------------------------------------------------------------------';




        }

        document.querySelector('.getList').innerHTML = result;
    });
}