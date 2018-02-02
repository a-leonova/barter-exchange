var apiPrefix = 'http://172.16.19.43:7878';

var form = document.getElementById('auth');

form.addEventListener('submit', function (event) {
    event.preventDefault();

    var data = getFormData(form);
    console.log(data);

    createRequest(apiPrefix + '/api/login/', data, 'POST', function(response) {
        response = response ? JSON.parse(response) : [];

        console.log('response', response);

        var result = '';

        for (var i = 0; i < response.length; i++) {
            var item = response[i];

            result += '<div class="main_get_list_item">'
                + 'id: ' + item.id
                + '  |  name: ' + item.name
                + '  |  age: ' + item.age
                + '</div>'
        }

        document.querySelector('.main_get_list').innerHTML = result;
    });
});