var apiPrefix = 'http://192.168.1.5:8080';
var userId = getCookie('userId');
function addWare1(id)
{
    console.log('add ware for ' + id);
    window.location.href = 'Swap.html?wishId=' + id +'&simple=' + 1;
}
function addWare2(id)
{
    console.log('add ware for ' + id);

    // createRequest(apiPrefix + '/api/exchanges/find-chain/' + userId, id, 'POST', function(response) {
    window.location.href = 'Swap.html?wishId=' + id + '&simple=' + 0;
    // });
}
createRequest(apiPrefix + '/api/user_wish/' + userId, {}, 'GET', function (response) {
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
            + '<br>' + '<button type=button class="Add" style="border-color: #40c781; background-color: #40c781; font-weight: bold; color: white; width: 250px; height: 35px; text-transform: uppercase; font-size: 150%;" onclick=addWare1('+ware.id+')>  Прямой обмен  </button>'
            + '<br>' + '<button type=button class="Add" style="border-color: #40c781; background-color: #40c781; font-weight: bold; color: white; width: 250px; height: 35px; text-transform: uppercase; font-size: 150%;" onclick=addWare2('+ware.id+')>  Сложный обмен  </button>'
            + '<br>'+'--------------------------------------------------------------------------------------------------------------------------------------------------------------------';




    }

    document.querySelector('.getList').innerHTML = result;
});

