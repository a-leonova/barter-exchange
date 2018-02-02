var apiPrefix = 'http://192.168.1.5:8080';
var userId = getCookie('userId');
function addWare(id)
{
    console.log('add ware for ' + id);

    createRequest(apiPrefix + '/api/new_wish/' + userId, id, 'POST', function(response) {
        window.location.href = 'IWantWare.html';
    });
}
// var form = document.getElementById('auth');
document.querySelector('.main').addEventListener('submit', function (event) {
    event.preventDefault();
// form.addEventListener('submit', function (event) {
//     event.preventDefault();

    var data = getFormData(event.target);
    console.log(data);

    createRequest(apiPrefix + '/api/ware/filter?category='+data.category + '&city=' + data.city +
        '&exploitation='+ data.exploitation, {}, 'GET', function (response) {
        response = response ? JSON.parse(response) : [];

        console.log('response', response);

        var result = '';

        for (var i = 0; i < response.length; i++) {
            var ware = response[i];

            result += '--------------------------------------------------------------------------------------------------------------------------------------------------------------------'
                + '<div class="title">' + 'Название: ' + '</div>' + '<div class="data">' + ware.name + '</div>'
                + '<br>' + '<div class="data">' + '<div class="title">' + 'Категория: ' + '</div>' + ware.category + '</div>'
                + '<br>' + '<div class="data">' + '<div class="title">' + 'Статус: ' + '</div>' + ware.status + '</div>'
                + '<br>' + '<div class="data">' + '<div class="title">' + 'Срок эксплуатации: ' + '</div>' + ware.exploitation + '</div>'
                + '<br>' + '<button type=button class="Add" style="border-color: #40c781; background-color: #40c781; font-weight: bold; color: white; width: 150px; height: 35px; text-transform: uppercase; font-size: 150%;" onclick=addWare('+ware.id+')> Хочу  </button>'
                + '<br>' + '--------------------------------------------------------------------------------------------------------------------------------------------------------------------';


        }

        document.querySelector('.getList').innerHTML = result;
    });
});