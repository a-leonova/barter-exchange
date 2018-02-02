var apiPrefix = 'http://172.16.16.229:7878';

document.querySelector('.main_get_send').addEventListener('click', function (event) {
    event.preventDefault();

    createRequest(apiPrefix + '/api/users/', {}, 'GET', function(response) {
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

document.querySelector('.main_post_form').addEventListener('submit', function (event) {
    event.preventDefault();

    var formData = getFormData(event.target);
    formData.age = Number(formData.age);

    console.log(formData);

    createRequest(apiPrefix + '/api/users/', JSON.stringify(formData), 'POST', function(response) {
        console.log('data', formData, response);
    });
});