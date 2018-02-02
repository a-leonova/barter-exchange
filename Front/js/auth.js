var apiPrefix = 'http://192.168.1.5:8080';

var form = document.getElementById('auth');

form.addEventListener('submit', function (event) {
    event.preventDefault();

    var data = getFormData(form);
    console.log(data);

    createRequest(apiPrefix + '/api/login/', JSON.stringify(data), 'POST', function (response) {
        response = response ? JSON.parse(response) : [];

        console.log('response', response.status);

        switch (response.status) {
            case "USER_NOT_FOUND":
                document.getElementsByClassName('auth_message')[0].innerHTML = 'Неверный Email';
                break;

            case "BAD_PASSWORD":
                document.getElementsByClassName('auth_message')[0].innerHTML = 'Неверный пароль';
                break;

            case "OK":
                // deleteCookie('userId');
                document.cookie = 'userId=' + response.data.id;
                window.location.href = 'PersonalPage.html';
                break;
        }
    });
});